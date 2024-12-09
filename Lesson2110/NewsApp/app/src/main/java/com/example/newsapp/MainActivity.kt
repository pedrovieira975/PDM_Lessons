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
        enableEdgeToEdge() // Certifique-se de configurar corretamente
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        val currentDestination = navController.currentBackStackEntry?.destination?.route
                        val searchQuery = remember { mutableStateOf("") }
                        when (currentDestination) {
                            "home" -> MyTopBar(
                                title = "Home",
                                onSearchQueryChanged = { query -> searchQuery.value = query } // Passando a função para atualizar o estado
                            )
                            "bookmarks" -> MyTopBar(
                                title = "Bookmarks",
                                onSearchQueryChanged = { query -> searchQuery.value = query }
                            )
                            else -> MyTopBar(
                                title = "Article",
                                isBaseScreen = false,
                                onSearchQueryChanged = { query -> searchQuery.value = query }
                            )
                        }
                    },
                    bottomBar = {
                        MyBottomBar(navController = navController)
                    }
                ) { innerPadding ->
                    // Use innerPadding SOMENTE no Box que envolve a navegação
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "home"
                        ) {
                            composable(route = "home") {
                                HomeView(
                                    onArticleClick = { url ->
                                        navController.navigate("article/$url")
                                    },
                                    allArticles = listOf(/* Insira aqui sua lista de artigos */)
                                )
                            }
                            composable(route = "article/{url}") {
                                val url = it.arguments?.getString("url")
                                url?.let {
                                    ArticleDetailView(url = it)
                                }
                            }
                            composable(route = "bookmarks") {
                                BookmarksView(
                                    // Não aplique padding extra aqui
                                    onArticleClick = {
                                        navController.navigate("article/${it}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
