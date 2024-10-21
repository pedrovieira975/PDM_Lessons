package com.example.newsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.Models.Article
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
        fun ArticleRowView(article: Article){
            Row (){
                Image(modifier = Modifier
                    .width(120.dp)
                    .height(120.dp),
                    painter = painterResource(id = R.drawable.baseline_photo_camera_back_24),
                    contentDescription = "Article Image")
                Column {
                    Text(text = article.title ?: "",
                        style = MaterialTheme.typography.titleMedium)
                    Text(text = article.description ?: "")
                    Text(modifier = Modifier.padding(top = 8.dp), text = article.publishedAt ?: "")
                }
            }
        }

@Preview(showBackground = true)
@Composable
fun ArticleRowViewPreview() {
    NewsAppTheme {
        val article = Article(title = "title1", description = "description1", url = "url1", urlToImage = "urltoimage", publishedAt = "publishedat", content = "content1")
        ArticleRowView(article = article)
    }
}
