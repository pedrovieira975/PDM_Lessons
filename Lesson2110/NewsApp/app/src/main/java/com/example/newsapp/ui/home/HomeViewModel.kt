package com.example.newsapp.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.AppDatabase
import com.example.newsapp.Models.Article
import com.example.newsapp.Models.ArticleCache
import com.example.newsapp.Models.toJsonString
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
import java.util.UUID

data class ArticleState (
    val articles : ArrayList<Article> = arrayListOf<Article>(),
    val isLoading  : Boolean = false,
    val errorMessage: String = "",
    val searchQuery: String = ""
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
    fun saveArticleToBookmarks(context: Context, article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            if (article.url.isNullOrBlank()) {
                Log.e("SaveArticle", "Erro: URL do artigo é nula ou vazia.")
                return@launch
            }

            val database = AppDatabase.getDatabase(context)
            val existing = database?.articleCacheDao()?.getAll()?.any { it.url == article.url } ?: false

            if (!existing) {
                Log.d("ArticleSerialization", "Estado do artigo antes da serialização: $this")
//                val completeArticle = article.copy(
//                    title = article.title ?: "Título não disponível",
//                    description = article.description ?: "Descrição não disponível",
//                    author = article.author ?: "Autor desconhecido",
//                    content = article.content ?: "Conteúdo não disponível"
//                )
                val articleCache = ArticleCache(
                    url = article.url, // URL já validada
                    title = article.title,
                    description = article.description,
                    urlToImage = article.urlToImage,
                    publishedAt = article.publishedAt,
                    author = article.author,
                    content = article.content,
                    articleJsonString = article.toJsonString()
                )
                val result = database?.articleCacheDao()?.insert(articleCache)
                Log.d("SaveArticle", "Artigo salvo com sucesso: Titulo=${article.title}, URL=${article.url}, Resultado=$result")
            } else {
                Log.d("SaveArticle", "Artigo já existe no banco: URL=${article.url}")
            }
            logAllBookmarks(context) // Loga os artigos salvos no banco
        }
    }


    fun loadBookmarks(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val articlesCache = AppDatabase.getDatabase(context)?.articleCacheDao()?.getAll()
            if (articlesCache.isNullOrEmpty()) {
                Log.d("BookmarksLoad", "Nenhum artigo encontrado no banco.")
            } else {
                articlesCache.forEach {
                    Log.d("BookmarksLoad", "Artigo carregado do banco: URL=${it.url}, JSON=${it.articleJsonString}")
                }
            }

            val bookmarks = articlesCache?.map {
                Article.fromJson(JSONObject(it.articleJsonString))
            } ?: emptyList()

            Log.d("BookmarksLoad", "Total de artigos carregados: ${bookmarks.size}")

            viewModelScope.launch(Dispatchers.Main) {
                _uiState.value = _uiState.value.copy(articles = ArrayList(bookmarks))
            }
        }
    }

    private val _bookmarkedArticles = MutableStateFlow<List<String>>(emptyList())
    val bookmarkedArticles: StateFlow<List<String>> = _bookmarkedArticles.asStateFlow()

    fun refreshBookmarkedArticles(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val articlesCache = AppDatabase.getDatabase(context)?.articleCacheDao()?.getAll()
            val bookmarkedUrls = articlesCache?.map { it.url } ?: emptyList()
            _bookmarkedArticles.value = bookmarkedUrls
        }
    }

    fun logAllBookmarks(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val articlesCache = AppDatabase.getDatabase(context)?.articleCacheDao()?.getAll()
            articlesCache?.forEach { cachedArticle ->
                Log.d("BookmarksCheck", "Artigo no banco: URL=${cachedArticle.url}, JSON=${cachedArticle.articleJsonString}")
            }
            Log.d("BookmarksCheck", "Total de favoritos no banco: ${articlesCache?.size ?: 0}")
        }
    }

//    fun testDatabase(context: Context) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val database = AppDatabase.getDatabase(context)
//            val articles = database?.articleCacheDao()?.getAll()
//            articles?.forEach {
//                Log.d("DatabaseTest", "Artigo salvo: URL=${it.url}, JSON=${it.articleJsonString}")
//            }
//            Log.d("DatabaseTest", "Total de artigos no banco: ${articles?.size ?: 0}")
//        }
//    }

//    fun testGetAllArticles(context: Context) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val articles = AppDatabase.getDatabase(context)?.articleCacheDao()?.getAll()
//            articles?.forEach {
//                Log.d("DAOTest", "Artigo no banco: URL=${it.url}, JSON=${it.articleJsonString}")
//            }
//            Log.d("DAOTest", "Total de artigos no banco: ${articles?.size ?: 0}")
//        }
//    }

//    fun testInsertFixedArticle(context: Context) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val articleCache = ArticleCache(
//                url = "https://teste.com/favorito",
//                articleJsonString = """{"title": "Artigo Teste", "description": "Descrição Teste"}"""
//            )
//            val result = AppDatabase.getDatabase(context).articleCacheDao().insert(articleCache)
//            Log.d("TestInsert", "Artigo inserido com resultado: $result")
//            logAllBookmarks(context) // Loga todos os artigos salvos
//        }
//    }

    fun getArticles(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val articlesCache = AppDatabase
                .getDatabase(context)
                ?.articleCacheDao()
                ?.getAll()

            val articles: List<Article> = articlesCache?.map {
                Article.fromJson(JSONObject(it.articleJsonString))
            } ?: emptyList()

            viewModelScope.launch(Dispatchers.Main) {
                _uiState.value = ArticleState(
                    articles = ArrayList(articles),
                    isLoading = false
                )
            }
        }
    }
}
