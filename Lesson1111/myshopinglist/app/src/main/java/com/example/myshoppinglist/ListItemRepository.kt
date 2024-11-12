package com.example.myshoppinglist

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



object ListItemRepository {

    val db = Firebase.firestore

    fun add( listItem: ListItem,  onAddListSuccess: () -> Unit) {


        /*
        var currentUser = Firebase.auth.currentUser

        if (currentUser == null) {
            state.value = state.value.copy(error = "User not logged in")
            return
        }*/


        db.collection("listTypes")
            .add(listItem)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    fun getAll(onSuccess: (List<ListItem>) -> Unit){
        db.collection("listTypes")

            .addSnapshotListener { value, error ->
                val listItems = mutableListOf<ListItem>()
                value?.let{
                    for (document in it.documents) {
                        document.data?.let { it1 ->
                            listItems.add(ListItem.fromMap(it1))
                        }
                    }
                }
                onSuccess(listItems)
            }
    }

}