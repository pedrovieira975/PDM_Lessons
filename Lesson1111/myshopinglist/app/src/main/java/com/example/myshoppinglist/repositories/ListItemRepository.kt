package com.example.myshoppinglist.repositories

import android.util.Log
import com.example.myshoppinglist.TAG
import com.example.myshoppinglist.models.Article
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

    fun remove(docId: String, onRemoveSuccess: () -> Unit, onRemoveFailure: (Exception) -> Unit) {
        Log.d(TAG, "Removendo item com ID: $docId")
        val currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            Log.w(TAG, "Usuário não autenticado. Não é possível remover o item.")
            return
        }

        db.collection("listTypes")
            .document(docId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Item removido com sucesso.")
                onRemoveSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erro ao remover o item", e)
                onRemoveFailure(e)
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

    fun addArticleToList(
        docId: String,
        article: Article,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("listTypes")
            .document(docId) // Adicionar o artigo à coleção da lista
            .collection("articles")
            .add(article)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }



    fun getArticlesForList(docId: String, onResult: (List<Article>) -> Unit) {
        val db = Firebase.firestore
        db.collection("listTypes")
            .document(docId)
            .collection("articles") // Subcoleção "articles"
            .get()
            .addOnSuccessListener { result ->
                val articles = result.map { document ->
                    document.toObject(Article::class.java)
                }
                onResult(articles)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Erro ao buscar artigos: ${exception.message}", exception)
            }
    }



    fun checkItem(docId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val articleId = "article_doc_id"

        db.collection("articles").document(articleId)
            .update("completed", true)
            .addOnSuccessListener {
                Log.d("Firestore", "Artigo atualizado para concluído")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao atualizar artigo", e)
            }
    }

    fun updateArticle(articleId: String, completed: Boolean) {
        db.collection("articles").document(articleId)
            .update("completed", completed)
            .addOnSuccessListener {
                Log.d("Firestore", "Artigo marcado como concluído")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao marcar artigo", e)
            }
    }



}