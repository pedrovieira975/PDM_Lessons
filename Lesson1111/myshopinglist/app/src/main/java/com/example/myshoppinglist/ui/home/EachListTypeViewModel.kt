package com.example.myshoppinglist.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshoppinglist.TAG
import com.example.myshoppinglist.models.Article
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.repositories.ListItemRepository
import com.example.myshoppinglist.repositories.ListItemRepository.getArticlesForList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.myshoppinglist.models.ListState

import kotlinx.coroutines.launch


class EachListTypeViewModel : ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    fun loadArticlesForList(docId: String) {
        ListItemRepository.getArticlesForList(docId) { articles ->
            if (_articles.value.isEmpty()) {
                // Apenas sobrescreve o estado inicial
                _articles.value = articles
            }
        }
    }

    fun updateArticles(updatedArticles: List<Article>) {
        _articles.value = updatedArticles
    }

    fun updateSingleArticle(updatedArticle: Article) {
        val currentArticles = _articles.value.toMutableList()
        val index = currentArticles.indexOfFirst { it.articleId == updatedArticle.articleId }
        if (index != -1) {
            currentArticles[index] = updatedArticle
            _articles.value = currentArticles
            Log.d(TAG, "Estado local do artigo ${updatedArticle.name} atualizado")
        }
    }

}


