package com.example.myshoppinglist.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myshoppinglist.R
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme

@Composable
fun EachListTypeView(modifier: Modifier = Modifier,
                     navController : NavController = rememberNavController()
) {
//    val viewModel: EachListTypeViewModel = viewModel()
//    val state = viewModel.state
//
//    EachListTypeViewContent(
//        modifier = modifier,
//        state = state.value,
//    )
}

@Composable
fun EachListTypeViewContent(
    modifier: Modifier = Modifier,
    state: ListState
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {}
        }
    }
    Button(
        modifier = Modifier
            .padding(16.dp)
            .height(80.dp)
            .width(80.dp),
        onClick = {  }
    ){
        Icon(
            painter = painterResource(R.drawable.baseline_add_24),
            contentDescription = "Adicionar Lista",
            tint = Color.White
        )
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