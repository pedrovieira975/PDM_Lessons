package com.example.myshoppinglist.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myshoppinglist.models.Article
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.repositories.ListItemRepository

data class AddArticleTypeState(
    var name: String = "",
    var description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddArticleTypeViewModel : ViewModel() {

    var state = mutableStateOf(AddArticleTypeState())
        private set

    private val name
        get() = state.value.name
    private val description
        get() = state.value.description

    fun onNameChange(newValue: String) {
        state.value = state.value.copy(name = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        state.value = state.value.copy(description = newValue)
    }

    fun addArticle(docId: String) {
        val article = Article(
            articleId = "", // Será gerado automaticamente pelo Firestore
            name = name,
            description = description,
            completed = false,
            docId = docId // Associar à lista correta
        )

        ListItemRepository.addArticleToList(
            docId = docId,
            article = article,
            onSuccess = {
                println("Artigo adicionado com sucesso à lista $docId")
            },
            onFailure = { exception ->
                println("Erro ao adicionar artigo: ${exception.message}")
            }
        )
    }

}
