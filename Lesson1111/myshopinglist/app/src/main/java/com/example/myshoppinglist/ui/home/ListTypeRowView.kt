package com.example.myshoppinglist.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshoppinglist.R
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.repositories.ListItemRepository.remove
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme
import com.example.myshoppinglist.ui.theme.appFontBold16
import com.example.myshoppinglist.ui.home.ListTypesViewModel
import com.example.myshoppinglist.ui.theme.Red01

@Composable
fun ListTypeRowView(
    modifier: Modifier = Modifier,
    listItem: ListItem
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalArrangement = Arrangement.Center // Centraliza verticalmente os itens
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically // Alinha os itens verticalmente
        ) {
            Column(
                modifier = Modifier.weight(1f) // Ocupa o espaço restante no Row
            ) {
                Text(
                    text = listItem.name ?: "",
                    style = appFontBold16
                )
                Text(
                    text = listItem.description ?: ""
                )
            }
            Button(
                onClick = { remove(
                    docId = listItem.docId!!, // Força que o valor não seja nulo
                    onRemoveSuccess = {
                        Log.d("RemoveButton", "Item removido com sucesso.")
                    },
                    onRemoveFailure = { exception ->
                        Log.e("RemoveButton", "Erro ao remover item: ${exception.message}")
                    }
                )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red01,
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically) // Centraliza o botão verticalmente dentro do Row
                    .width(60.dp)
                    .aspectRatio( 2.0f),
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_remove_24),
                    contentDescription = "Remove",
                    tint = Color.White
                )
            }
        }
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