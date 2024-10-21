package com.example.newsapp

import android.app.DownloadManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.Models.Article
import com.example.newsapp.ui.theme.NewsAppTheme
import org.json.JSONObject
import java.io.IOException

@Composable
fun HomeView( modifier: Modifier = Modifier) {

    val articles = arrayListOf(
        Article(title = "Title 1", description = "Description 1", url = "url 1", urlToImage = "urlToImage 1", publishedAt = "publishedAt 1", content = "content 1"),
        Article(title = "Title 2", description = "Description 2", url = "url 2", urlToImage = "urlToImage 2", publishedAt = "publishedAt 2", content = "content 2"),
        Article(title = "Title 3", description = "Description 3", url = "url 3", urlToImage = "urlToImage 3", publishedAt = "publishedAt 3", content = "content 3")
    )

    LazyColumn (modifier = modifier.fillMaxSize()) {
        itemsIndexed(
            items = articles

        ){
            _, item ->
            ArticleRowView(article = item)
        }
    }

    LaunchedEffect(key1 = true) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/everything?q=Apple&from=2024-10-21&sortBy=popularity&apiKey=97657de396f346368122f45ab09de23a")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val result = response.body?.string()
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString("status")
            if (status == "ok"){

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    NewsAppTheme {
        Greeting("Android")
    }
}