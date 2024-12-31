package com.example.myshoppinglist.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myshoppinglist.R
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme

@Composable
fun ItemDetailView(
    modifier: Modifier = Modifier,
    listItems: List<ListItem>,
    onItemCheckedChange: (ListItem, Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f) // Faz a lista ocupar o espaço restante
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listItems.size) { index ->
                val item = listItems[index]
                var isChecked by remember { mutableStateOf(false) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { checked ->
                            isChecked = checked
                            onItemCheckedChange(item, checked)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = item.name ?: "Sem Nome",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = item.description ?: "Sem Descrição",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp)) // Espaço entre a lista e o botão
        Button(
            modifier = Modifier
                .align(Alignment.End)
                .width(70.dp)
                .height(56.dp),
            onClick = { /* Adicionar funcionalidade aqui */ }
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "Adicionar Lista",
                tint = Color.White
            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(text = "Adicionar Lista")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemDetailView() {
    val sampleItems = listOf(
        ListItem(docId = "1", name = "Maçãs", description = "Comprar 2kg", owners = null),
        ListItem(docId = "2", name = "Pão", description = "Integral ou de forma", owners = null),
        ListItem(docId = "3", name = "Leite", description = "Desnatado", owners = null)
    )

    MyShoppingListTheme {
        ItemDetailView(
            listItems = sampleItems,
            onItemCheckedChange = { item, isChecked ->
                println("Item: ${item.name} foi ${if (isChecked) "marcado" else "desmarcado"}")
            }
        )
    }
}

