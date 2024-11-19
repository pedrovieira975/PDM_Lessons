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


        var currentUser = Firebase.auth.currentUser

        //if (currentUser == null) {
        //    state.value = state.value.copy(error = "User not logged in")
        //    return
        //}

        currentUser?.uid?.let {
            listItem.owners = arrayListOf(it)
        }

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
            .whereArrayContains("owners", Firebase.auth.currentUser?.uid!!)
            .addSnapshotListener { value, error ->
                val listItems = value?.documents?.mapNotNull {
                    val itemList  = it.toObject(ListItem::class.java)
                    itemList?.docId = it.id
                    itemList
                }
                listItems?.let {  onSuccess(it) }
            }
    }

}