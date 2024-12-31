package com.example.myshoppinglist.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.repositories.ListItemRepository

data class AddListTypeState(
    var name: String = "",
    var description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddListTypesViewModel : ViewModel() {

    var state = mutableStateOf(AddListTypeState())
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

    fun addList(){
        ListItemRepository.add(listItem = ListItem("", name, description, null)){

        }
    }
}