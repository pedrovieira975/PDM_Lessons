package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.Models.Article
import com.example.newsapp.ui.ArticleDetailView
import com.example.newsapp.ui.bookmarks.BookmarksView
import com.example.newsapp.ui.home.HomeView
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                val searchQuery = remember { mutableStateOf("") }
                val currentArticle = remember { mutableStateOf<Article?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MyTopBar(
                            title = "NewsApp",
                            isBaseScreen = currentArticle.value == null, // Se tiver artigo, não é base
                            article = currentArticle.value, // Usa .value para acessar o valor
                            onSearchQueryChanged = { query ->
                                searchQuery.value = query
                            }
                        )
                    },
                    bottomBar = {
                        MyBottomBar(navController = navController)
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "home"
                        ) {
                            composable("home") {
                                currentArticle.value = null // Define como tela base
                                HomeView(
                                    onArticleClick = { url ->
                                        navController.navigate("article/$url")
                                    }
                                )
                            }
                            composable("bookmarks") {
                                currentArticle.value = null // Define como tela base
                                BookmarksView(
                                    onArticleClick = { url ->
                                        navController.navigate("article/$url")
                                    }
                                )
                            }
                            composable("article/{url}") { backStackEntry ->
                                val url = backStackEntry.arguments?.getString("url")
                                val title = backStackEntry.arguments?.getString("title")
                                val description = backStackEntry.arguments?.getString("description")
                                val urlToImage = backStackEntry.arguments?.getString("urlToImage")
                                val publishedAt = backStackEntry.arguments?.getString("publishedAt")
                                val author = backStackEntry.arguments?.getString("author")
                                val content = backStackEntry.arguments?.getString("content")
                                currentArticle.value = Article(
                                    title = title,
                                    description = description,
                                    url = url,
                                    urlToImage = urlToImage,
                                    publishedAt = publishedAt,
                                    author = author,
                                    content = content
                                )

                                ArticleDetailView(url = currentArticle.value?.url ?: "")

                            }
                        }
                    }
                }
            }
        }
    }
}


