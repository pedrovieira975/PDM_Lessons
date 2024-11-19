package com.example.myshoppinglist.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme
import com.example.myshoppinglist.ui.theme.appFontBold16

@Composable
fun ListTypeRowView(modifier: Modifier = Modifier,
                    listItem: ListItem) {
    Column (modifier = modifier
        .fillMaxWidth()
        .height(80.dp),
        verticalArrangement = Arrangement.Center
    ){
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = listItem.name ?: "",
            style = appFontBold16)
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = listItem.description ?: "")
    }
}

@Preview(showBackground = true)
@Composable
fun ListTypeRowViewPreview(){
    MyShoppingListTheme {
        ListTypeRowView(listItem =  ListItem("",
            "Compras de casa",
            "As compras que são para casa",
            null))
    }
}