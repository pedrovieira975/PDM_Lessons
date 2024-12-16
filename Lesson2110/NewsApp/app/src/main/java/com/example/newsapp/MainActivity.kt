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
                                isBaseScreen = true,
                                onSearchQueryChanged = { query ->
                                    searchQuery.value = query // Atualiza a busca na Home
                                }
                            )
                            "bookmarks" -> MyTopBar(
                                title = "Bookmarks",
                                isBaseScreen = true,
                                onSearchQueryChanged = { query ->
                                    searchQuery.value = query // Atualiza a busca na Bookmarks
                                }
                            )
                            else -> MyTopBar(
                                title = "Article",
                                isBaseScreen = false,
                                article = null, // Não precisa de busca aqui
                                onSearchQueryChanged = { }
                            )
                        }
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
                                HomeView(
                                    onArticleClick = { url ->
                                        navController.navigate("article/$url")
                                    }
                                )
                            }
                            composable("bookmarks") {
                                BookmarksView(
                                    onArticleClick = { url ->
                                        navController.navigate("article/$url")
                                    }
                                )
                            }
                            composable("article/{url}") {
                                val url = it.arguments?.getString("url")
                                ArticleDetailView(url = url ?: "")
                            }
                        }
                    }
                }

            }
        }
    }
}
