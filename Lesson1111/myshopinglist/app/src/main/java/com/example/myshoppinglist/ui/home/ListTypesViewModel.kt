package com.example.myshoppinglist.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myshoppinglist.TAG
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.repositories.ListItemRepository

data class ListState(
    val listItems : List<ListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListTypesViewModel : ViewModel() {

    var state = mutableStateOf(ListState())
        private set

    fun addList() {
        ListItemRepository.add(
            ListItem("","title", "description", null)
        ){

        }
    }

    fun loadListTypes() {
        Log.d(TAG, "Carregando listas...")
        ListItemRepository.getAll { listItems ->
            state.value = state.value.copy(
                listItems = listItems // Substituir ou adicionar corretamente
            )

            for (item in listItems) {
                Log.d(TAG, "Lista carregada: ${item.name}")
            }
        }
    }

    fun getListItemById(docId: String): ListItem? {
        var item: ListItem? = null
        ListItemRepository.getItemById(docId) { listItem ->
            item = listItem
        }
        return item
    }

}