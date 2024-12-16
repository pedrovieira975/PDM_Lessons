package com.example.newsapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.example.newsapp.Models.toJsonString

//package com.example.newsapp

import androidx.compose.material.icons.Icons
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    isBaseScreen: Boolean = true,
    article: Article? = null,
    onSearchQueryChanged: (String) -> Unit // Função que será chamada ao alterar o texto da pesquisa
) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }

    TopAppBar(
        title = {
            if (article != null) {
                Text(text = article.title ?: "")
            } else {
                TextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                        onSearchQueryChanged(newText) // Chama a função que foi passada
                    },
                    label = { Text("Search News") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
//                    colors = TextFieldDefaults.textFieldColors(
//                        containerColor = Color.Transparent
//                    )
                )
            }
        },
        actions = {
            if (!isBaseScreen) {
                IconButton(
                    onClick = {
                        article?.let {
                            val articleCache = ArticleCache(
                                it.url!!,
                                it.toJsonString()
                            )
                            GlobalScope.launch(Dispatchers.IO) {
                                AppDatabase
                                    .getDatabase(context)
                                    ?.articleCacheDao()
                                    ?.insert(articleCache)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
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

