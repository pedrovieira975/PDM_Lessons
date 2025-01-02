package com.example.myshoppinglist.ui.home

import ShareListViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme

@Composable
fun ShareListView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    docId: String // Recebe o `docId` da lista
) {
    val viewModel: ShareListViewModel = viewModel()
    val state = viewModel.state.value

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = state.username,
                onValueChange = viewModel::onEmailChange, // Aqui pode aceitar UID diretamente
                placeholder = { Text(text = "UID do Usuário") } // Altere para "UID do usuário"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val uid = state.username.trim() // Usando diretamente o UID
                if (uid.isNotEmpty()) {
                    viewModel.shareListWithUid(
                        docId = docId,
                        uid = uid,
                        onSuccess = {
                            println("Lista compartilhada com sucesso!")
                            navController.popBackStack()
                        },
                        onFailure = { error ->
                            println("Erro ao compartilhar: $error")
                        }
                    )
                } else {
                    println("UID não pode estar vazio!")
                }
            }) {
                Text("Partilhar Lista")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShareListViewPreview() {
    MyShoppingListTheme {
        ShareListView(
            docId = "sampleDocId", // Valor fictício para o preview
            navController = rememberNavController() // Necessário para evitar erro no NavController
        )
    }
}
