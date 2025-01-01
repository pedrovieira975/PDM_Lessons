package com.example.myshoppinglist.models

data class ListItem(
    var docId: String?,
    var name: String?,
    var description: String?,
    var owners : List<String>?) {
    constructor():this(null, null, null, null){}
}

data class Article(
    var articleId: String?,
    var name: String,
    var description: String,
    var completed: Boolean = false,
    var docId: String? = null,
    ) {
    constructor():this(null, "", "", false, null){}
}

data class ListState(
    val articles: List<Article> = emptyList()
)
