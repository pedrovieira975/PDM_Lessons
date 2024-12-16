package com.example.newsapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
//import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.Models.Article
import com.google.ai.client.generativeai.type.content

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*

@Composable
fun NewsItem(article: Article, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(article.url ?: "") }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = article.title ?: "No Title",
                style = MaterialTheme.typography.titleMedium // Corrigido para Material3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.content ?: "No Content",
                style = MaterialTheme.typography.bodyMedium // Corrigido para Material3
            )
        }
    }
}

