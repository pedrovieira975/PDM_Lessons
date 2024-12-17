package com.example.newsapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.Models.Article
import com.example.newsapp.Models.encodeURL
//import com.example.newsapp.MyTopBar
import com.example.newsapp.ui.theme.NewsAppTheme


import com.example.newsapp.ui.components.NewsItem

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    onArticleClick: (String) -> Unit
) {
    val context = LocalContext.current // Obtém o contexto atual do Composable
    val viewModel: HomeViewModel = viewModel() // Obtém o ViewModel associado
    val uiState by viewModel.uiState.collectAsState() // Observa o estado do ViewModel

    // Chama fetchArticles uma vez quando o Composable é exibido
    LaunchedEffect(key1 = true) {
        viewModel.fetchArticles(context)
    }

    // Exibe o conteúdo da Home
    HomeViewContent(
        modifier = modifier,
        uiState = uiState,
        onArticleClick = onArticleClick
    )
}

@Composable
fun HomeViewContent(
    modifier: Modifier = Modifier,
    uiState: ArticleState,
    onArticleClick: (String) -> Unit = {}) {
    if (uiState.isLoading) {
        Box(modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center)
        {
            Text(text = "Loading...")
        }
    }else if(uiState.errorMessage.isNotEmpty()){
        Box(modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center)
        {
            Text(text = uiState.errorMessage)
        }
    }else{
        LazyColumn(modifier = modifier.fillMaxSize()) {
            itemsIndexed(
                items = uiState.articles
            ){
                    _, item ->
                ArticleRowView(modifier = Modifier
                    .clickable {
                        onArticleClick(item.url?.encodeURL() ?: "")
                    },
                    article = item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    NewsAppTheme {
        HomeViewContent(
            uiState = ArticleState(
                articles = arrayListOf(
                    Article(
                        title = "Title 1",
                        description = "Description 1",
                        url = "https://www.google.com",
                        urlToImage = null,
                        publishedAt = null,
                        author = "Author 1",
                        content = null // Add an author here
                    ),
                    Article(
                        title = "Title 2",
                        description = "Description 2",
                        url = "https://www.google.com",
                        urlToImage = null,
                        publishedAt = null,
                        author = "Author 2",
                        content = null // Add an author here
                    ),
                )
            )
        )
    }
}
