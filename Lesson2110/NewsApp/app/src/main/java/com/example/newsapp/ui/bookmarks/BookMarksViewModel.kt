package com.example.newsapp.ui.bookmarks

import android.content.Context
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

    fun getArticles(context: Context) {

        viewModelScope.launch(Dispatchers.IO) {
            val articlesCache = AppDatabase
                .getDatabase(context)
                ?.articleCacheDao()
                ?.getAll()

            val articles : ArrayList<Article> = arrayListOf()
            for (a in articlesCache?: emptyList() ){
                articles.add(Article.fromJson(JSONObject(a.articleJsonString)))
            }
            viewModelScope.launch(Dispatchers.Main) {
                _uiState.value = ArticleState(
                    articles = articles
                )
            }
        }

    }
}