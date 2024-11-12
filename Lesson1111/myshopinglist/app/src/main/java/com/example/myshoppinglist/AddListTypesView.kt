package com.example.myshoppinglist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme


@Composable
fun AddListTypesView(modifier: Modifier = Modifier){

    val viewModel by remember { mutableStateOf(AddListTypesViewModel()) }
    val state = viewModel.state


    Box(modifier = modifier.fillMaxSize() ){
        Column {
            Button(onClick = {
                viewModel.addList()
            }) {
                Text(text = "Add new list")
            }
            LazyColumn {
                itemsIndexed(
                    items = state.value.listItems
                ) { index, item ->
                    Column {
                        Text(text = item.name ?: "")
                        Text(text = item.description ?: "")
                    }
                }
            }
        }
    }

    LaunchedEffect (key1 = Unit){
        viewModel.loadListTypes()
    }
}

@Preview(showBackground = true)
@Composable
fun AddListTypesViewPreview() {
    MyShoppingListTheme {
        AddListTypesView()
    }
}