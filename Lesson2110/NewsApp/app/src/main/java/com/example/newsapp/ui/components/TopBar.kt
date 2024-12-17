package com.example.newsapp

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.example.newsapp.Models.toJsonString

//package com.example.newsapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.AppDatabase
import com.example.newsapp.Models.Article
import com.example.newsapp.Models.ArticleCache
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.home.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    isBaseScreen: Boolean = true,
    article: Article? = null,
    onSearchQueryChanged: (String) -> Unit
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel()
    var searchText by remember { mutableStateOf("") }
    val bookmarkedArticles by viewModel.bookmarkedArticles.collectAsState()

    TopAppBar(
        title = {
            TextField(
                value = searchText,
                onValueChange = { newText ->
                    searchText = newText
                    onSearchQueryChanged(newText)
                },
                label = { Text("Search News") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        actions = {
            if (article != null) {
                val isBookmarked = article.url in bookmarkedArticles
                IconButton(
                    onClick = {
                        viewModel.saveArticleToBookmarks(context, article)
                    }
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Bookmark"
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun MyTopBarPreview() {
    NewsAppTheme {
        // Passando uma função vazia para onSearchQueryChanged
        MyTopBar(
            title = "Test",
            onSearchQueryChanged = { query -> }
        )
    }
}

