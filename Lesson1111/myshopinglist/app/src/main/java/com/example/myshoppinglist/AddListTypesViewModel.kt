package com.example.myshoppinglist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class AddListState(
    val listItems : List<ListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddListTypesViewModel : ViewModel() {

    var state = mutableStateOf(AddListState())
        private set

    fun addList() {
        ListItemRepository.add(
            ListItem("title", "description")
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