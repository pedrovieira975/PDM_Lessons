package com.example.newsapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.newsapp.Models.Article
import androidx.compose.ui.layout.ContentScale
import com.example.newsapp.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ArticleRowView(modifier: Modifier = Modifier, article: Article) {
    Row(modifier = modifier) {
        article.urlToImage?.let {
            AsyncImage(
                model = it,
                contentDescription = "Imagem do Artigo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .padding(6.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        } ?: run {
            Image(
                painter = painterResource(id = R.drawable.baseline_photo_camera_back_24),
                contentDescription = "Imagem do Artigo",
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = article.title ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = article.description ?: "",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            article.author?.let {
                Text(
                    text = "Por: $it",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = article.publishedAt?.toString() ?: "Data indisponível"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleRowViewPreview() {
    val mockDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
        .parse("2024-12-08T16:17:05+00:00")

    val article = Article(
        title = "Título de Exemplo",
        description = "Descrição do artigo de exemplo.",
        url = "https://www.example.com",
        urlToImage = null,
        publishedAt = mockDate,
        author = "Autor Exemplo"  // Pass author value here
    )

    ArticleRowView(article = article)
}