package com.example.myshoppinglist.ui.home

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme

@Composable
fun EachListTypeView(modifier: Modifier = Modifier,
                     navController : NavController = rememberNavController()
) {
}

@Composable
fun EachListTypeViewContent(
    modifier: Modifier = Modifier,
    state: ListState
){

    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ){


    }
}

@Preview(showBackground = true)
@Composable
fun EachListTypeViewPreview() {
    MyShoppingListTheme {
        EachListTypeViewContent(
            state =
            ListState(listItems = arrayListOf(
                ListItem("", "Compras de Casa", "As compras que vão para casa" , null),
                ListItem("", "Compras de Escritório", "As compras que vão para o trablho", null)

            )
            )
        )
    }
}