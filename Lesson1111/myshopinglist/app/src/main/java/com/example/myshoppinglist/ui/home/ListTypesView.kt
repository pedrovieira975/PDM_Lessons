package com.example.myshoppinglist.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshoppinglist.R
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource

@Composable
fun ListTypesView(
    modifier: Modifier = Modifier,
    onNavigateToAddList : ()->Unit
){

    val viewModel by remember { mutableStateOf(ListTypesViewModel()) }
    val state = viewModel.state

    ListTypesViewContent(
        modifier = modifier,
        state = state.value,
        onNavigateToAddList = onNavigateToAddList)

    LaunchedEffect (key1 = Unit){
        viewModel.loadListTypes()
    }
}
@Composable
fun ListTypesViewContent(
    modifier: Modifier = Modifier,
    state: ListState,
    onNavigateToAddList : ()->Unit = {}
){

    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                itemsIndexed(
                    items = state.listItems
                ) { index, item ->
                    ListTypeRowView(listItem = item)
                }
            }
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp)
                .width(80.dp)
            ,
            onClick = {
                onNavigateToAddList()
            }) {
            Image(
                modifier = Modifier
                    .size(60.dp),
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "add list",
                colorFilter = ColorFilter.tint(Color.White))
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .height(80.dp)
                .width(80.dp)
            ,
            onClick = {

            }) {}
    }

}



@Preview(showBackground = true)
@Composable
fun AddListTypesViewPreview() {
    MyShoppingListTheme {
        ListTypesViewContent(
            state =
            ListState(listItems = arrayListOf(
                ListItem("", "Compras de Casa", "As compras que vão para casa" , null),
                ListItem("", "Compras de Escritório", "As compras que vão para o trablho", null)

            )
            )
        )
    }
}