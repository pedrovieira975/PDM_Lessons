package com.example.newsapp


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


    fun fetchArticles() {
        _uiState.value = ArticleState(
            isLoading = true
        )
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/everything?q=tesla&from=2024-09-28&sortBy=publishedAt&apiKey=97657de396f346368122f45ab09de23a")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                _uiState.value = ArticleState(
                    errorMessage = e.message ?: "",
                    isLoading = false
                )
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    var articlesResult = arrayListOf<Article>()
                    val result = response.body!!.string()

                    val jsonObject = JSONObject(result)
                    val status = jsonObject.getString("status")
                    if (status == "ok") {
                        val articlesArray = jsonObject.getJSONArray("articles")
                        for (index in 0 until articlesArray.length()) {
                            val articleObject = articlesArray.getJSONObject(index)
                            val article = Article.fromJson(articleObject)
                            articlesResult.add(article)
                            println(article.urlToImage)
                        }
                        _uiState.value = ArticleState(
                            articles = articlesResult,
                            isLoading = false
                        )
                    }
                }
            }
        })
    }
}