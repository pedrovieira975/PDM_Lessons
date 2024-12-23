package com.example.newsapp.Models

import org.json.JSONObject
import java.net.URLEncoder
import java.util.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

// Remove ICU TimeZone import and use java.util.TimeZone
import java.util.TimeZone

fun String.encodeURL(): String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.toDate(): Date? {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
        // Use java.util.TimeZone
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        dateFormat.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null // Retorna null em caso de erro
    }
}

fun Date.toStringDate(): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd")
    return dateFormat.format(this)
}

fun Article.toJsonString(): String {
    return Json.encodeToString(this)
}

@Serializable
data class Article(
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?, // Armazena como String
    val author: String?,
    val content: String?
)

 {

     companion object {
         fun fromJson(articleObject: JSONObject): Article {
             val title = articleObject.optString("titulo")
             val description = articleObject.optString("descricao")
             val url = articleObject.optString("url")
             val urlToImage = articleObject.optString("multimediaPrincipal")
             val publishedAt = articleObject.optString("data").takeIf { it.isNotEmpty() } // Deixa como String

             // Extraindo o nome do autor, caso exista
             val authorsArray = articleObject.optJSONArray("autores")
             val author = if (authorsArray != null && authorsArray.length() > 0) {
                 authorsArray.getJSONObject(0).optString("nome")
             } else {
                 null
             }

             return Article(title, description, url, urlToImage, publishedAt, author, null)
         }
     }

 }