package com.example.myshoppinglist.models

data class ListItem(
    var docId: String?,
    var name: String?,
    var description: String?,
    var owners : List<String>?) {
    constructor():this(null, null, null, null){}
}