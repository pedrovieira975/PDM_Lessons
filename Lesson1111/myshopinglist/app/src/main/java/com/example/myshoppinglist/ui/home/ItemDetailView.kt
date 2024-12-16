package com.example.myshoppinglist.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myshoppinglist.models.ListItem

@Composable
fun ItemDetailView(
    modifier: Modifier = Modifier,
    listItem: ListItem,
    onCheckedChange: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = listItem.name ?: "Sem Nome",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = listItem.description ?: "Sem Descrição",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { checked ->
                    isChecked = checked
                    onCheckedChange(checked)
                }
            )
            Text(text = if (isChecked) "Feito" else "Não feito")
        }
    }
}
