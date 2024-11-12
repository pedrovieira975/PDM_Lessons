package com.example.myshoppinglist

import com.google.firebase.database.DataSnapshot

data class ListItem(var name: String?,
                    var description: String?) {

    companion object {
        fun fromMap(map: Map<String, Any?>) : ListItem {
            return ListItem(
                map.get("name") as? String?,
                map.get("description") as? String?
            )
        }
    }
}