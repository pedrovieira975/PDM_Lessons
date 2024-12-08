package com.example.newsapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.newsapp.Models.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

data class ArticleState (
    val articles : ArrayList<Article> = arrayListOf<Article>(),
    val isLoading  : Boolean = false,
    val errorMessage: String = "",
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleState())
    val uiState: StateFlow<ArticleState> = _uiState.asStateFlow()

    fun fetchArticles() {
        _uiState.value = ArticleState(
            isLoading = true
        )
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.publico.pt/api/list/ultimas")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                _uiState.value = ArticleState(
                    errorMessage = e.message ?: "Erro desconhecido",
                    isLoading = false
                )
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        _uiState.value = ArticleState(
                            errorMessage = "Erro ao obter dados da API: ${response.code}",
                            isLoading = false
                        )
                        return
                    }

                    val articlesResult = arrayListOf<Article>()
                    val result = response.body!!.string()
                    val articlesArray = JSONArray(result) // API do Público retorna uma lista

                    for (i in 0 until articlesArray.length()) {
                        val articleObject = articlesArray.getJSONObject(i)
                        val article = Article.fromJson(articleObject)
                        articlesResult.add(article)
                    }

                    _uiState.value = ArticleState(
                        articles = articlesResult,
                        isLoading = false
                    )
                }
            }
        })
    }
}