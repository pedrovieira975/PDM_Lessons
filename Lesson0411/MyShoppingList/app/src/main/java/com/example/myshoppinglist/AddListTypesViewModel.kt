package com.example.myshoppinglist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class AddListState(

    val isLoading: Boolean = false,
    val error: String? = null
)

class AddListTypesViewModel : ViewModel() {

    var state = mutableStateOf(AddListState())
        private set

    fun addList( onAddListSuccess: () -> Unit) {
        val listType = hashMapOf(
            "name" to "New list",
            "description" to "New list description"
        )

        var currentUser = Firebase.auth.currentUser

        if (currentUser == null) {
            state.value = state.value.copy(error = "User not logged in")
            return
        }

        val db = Firebase.firestore
        db.collection("listTypes")
            .add(listType)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

}