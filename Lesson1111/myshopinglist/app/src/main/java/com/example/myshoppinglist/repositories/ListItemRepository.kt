package com.example.myshoppinglist.repositories

import android.util.Log
import com.example.myshoppinglist.TAG
import com.example.myshoppinglist.models.ListItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



object ListItemRepository {

    val db = Firebase.firestore

    fun add(listItem: ListItem, onAddListSuccess: () -> Unit) {
        Log.d(TAG, "Adicionando item: ${listItem.name}")
        val currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            Log.w(TAG, "Usuário não autenticado. Não é possível adicionar a lista.")
            return
        }

        listItem.owners = listOf(currentUser.uid)

        db.collection("listTypes")
            .add(listItem)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Documento adicionado com ID: ${documentReference.id}")
                onAddListSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erro ao adicionar documento", e)
            }
    }




    fun getAll(onSuccess: (List<ListItem>) -> Unit) {
        val userId = Firebase.auth.currentUser?.uid
        if (userId == null) {
            Log.w(TAG, "Usuário não autenticado. Não é possível carregar as listas.")
            return
        }

        Log.d(TAG, "Buscando listas para o usuário: $userId")

        db.collection("listTypes")
            .whereArrayContains("owners", userId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e(TAG, "Erro ao carregar listas: ${error.message}", error)
                    return@addSnapshotListener
                }

                val listItems = value?.documents?.mapNotNull {
                    val itemList = it.toObject(ListItem::class.java)
                    itemList?.docId = it.id
                    itemList
                }

                Log.d(TAG, "Listas carregadas: ${listItems?.size ?: 0}")
                listItems?.let { onSuccess(it) }
            }
    }

    fun getItemById(docId: String, onSuccess: (ListItem?) -> Unit) {
        db.collection("listTypes")
            .document(docId)
            .get()
            .addOnSuccessListener { document ->
                val listItem = document.toObject(ListItem::class.java)?.apply {
                    this.docId = document.id
                }
                onSuccess(listItem)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Erro ao buscar o item: ${e.message}", e)
                onSuccess(null)
            }
    }

}