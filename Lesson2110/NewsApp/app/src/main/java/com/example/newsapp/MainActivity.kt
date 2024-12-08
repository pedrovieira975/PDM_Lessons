package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
//                var isBaseScreen by remember{ mutableStateOf(false) }
//                var article by remember { mutableStateOf<Article?>(null) }
                Scaffold(modifier = Modifier.fillMaxSize(),
//                    topBar = {
//                        MyTopBar(title = "News App",
////                            isBaseScreen = isBaseScreen,
////                            article = article
//                        )
//                    },
//                    bottomBar = {
////                        BottomBar(navController = navController)
//                    }

                ) { innerPadding ->

                    NavHost(navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding))  {
                        composable( route = "home"){
                            HomeView(
                                modifier = Modifier.padding(innerPadding),

                                onArticleClick = {
                                    navController.navigate("article/${it}")
                                }
                            )
                        }
                        composable(route = "article/{url}"){
                            val url = it.arguments?.getString("url")
                            url?.let {
                                ArticleDetailView(url = it)
                            }
                        }
                    }

                }
            }
        }
    }
}