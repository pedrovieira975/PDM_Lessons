package com.example.myshoppinglist.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme

@Composable
fun AddArticleView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    docId: String // Receber o `docId` da lista
) {
    val viewModel: AddArticleTypeViewModel = viewModel()
    val state = viewModel.state

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            TextField(
                value = state.value.name,
                onValueChange = viewModel::onNameChange,
                placeholder = { Text(text = "Nome do artigo") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.value.description,
                onValueChange = viewModel::onDescriptionChange,
                placeholder = { Text(text = "Descrição do artigo") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (!viewModel.state.value.isLoading) {
                    viewModel.addArticle(docId) // Passar o `docId` ao ViewModel
                    navController.popBackStack()
                }
            }) {
                Text(text = "Adicionar Artigo")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddArticleViewPreview() {
    MyShoppingListTheme {
        AddArticleView(
            docId = "sampleDocId", // Valor fictício para o preview
            navController = rememberNavController() // Necessário para evitar erro no NavController
        )
    }
}