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
            _articles.value = articles // Atualiza o estado com os artigos retornados
        }
    }
}


