package com.example.newsapp.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.AppDatabase
import com.example.newsapp.Models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

data class ArticleState (
    val articles : ArrayList<Article> = arrayListOf<Article>(),
    val isLoading  : Boolean = false,
    val errorMessage: String = "",
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleState())
    val uiState: StateFlow<ArticleState> = _uiState.asStateFlow()

    fun fetchArticles(context: Context) {
        _uiState.value = ArticleState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Inicializa o banco de dados
                val database = AppDatabase.getDatabase(context)
                if (database == null) {
                    Log.e("HomeViewModel", "Banco de dados não inicializado")
                    _uiState.value = ArticleState(
                        articles = arrayListOf(),
                        isLoading = false,
                        errorMessage = "Erro ao inicializar o banco de dados."
                    )
                    return@launch
                }

                // Carrega artigos do cache
                val articlesCache = database.articleCacheDao().getAll()
                val cachedArticles = articlesCache.map {
                    Article.fromJson(JSONObject(it.articleJsonString))
                }

                // Faz a requisição à API
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://www.publico.pt/api/list/ultimas")
                    .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val result = response.body!!.string()
                    val articlesArray = JSONArray(result)
                    val articlesFromApi = mutableListOf<Article>()

                    for (i in 0 until articlesArray.length()) {
                        val articleObject = articlesArray.getJSONObject(i)
                        val article = Article.fromJson(articleObject)
                        articlesFromApi.add(article)
                    }

                    // Atualiza o estado com os artigos da API
                    _uiState.value = ArticleState(
                        articles = ArrayList(articlesFromApi),
                        isLoading = false
                    )
                } else {
                    Log.e("HomeViewModel", "Erro na API: ${response.code}")
                    _uiState.value = ArticleState(
                        articles = ArrayList(cachedArticles),
                        isLoading = false,
                        errorMessage = "Erro ao carregar da API. Mostrando dados em cache."
                    )
                }
            } catch (e: IOException) {
                Log.e("HomeViewModel", "Erro de rede: ${e.message}", e)
                _uiState.value = ArticleState(
                    articles = arrayListOf(),
                    isLoading = false,
                    errorMessage = "Erro de rede. Verifique sua conexão."
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Erro inesperado: ${e.message}")
                e.printStackTrace() // Mostra detalhes completos do erro no Logcat
                _uiState.value = ArticleState(
                    articles = arrayListOf(),
                    isLoading = false,
                    errorMessage = "Ocorreu um erro inesperado: ${e.message}"
                )
            }
        }
    }
}
