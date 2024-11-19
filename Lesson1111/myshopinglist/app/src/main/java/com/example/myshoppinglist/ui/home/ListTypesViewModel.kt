package com.example.myshoppinglist.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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

    fun loadListTypes(){
        ListItemRepository.getAll { listItems ->
            state.value = state.value.copy(
                listItems = listItems
            )

            for (item in listItems){
                Log.d("TAG", item.name?:"no name")
            }
        }
    }

}