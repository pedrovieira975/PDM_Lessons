//package com.example.myshoppinglist.ui.home
//
//import android.widget.Toast
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.ScrollableState
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.material3.Button
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.myshoppinglist.R
//import com.example.myshoppinglist.models.Article
//import com.example.myshoppinglist.models.ListItem
//import com.example.myshoppinglist.repositories.ListItemRepository
//import com.example.myshoppinglist.ui.theme.MyShoppingListTheme
//
//@Composable
//fun EachListTypeView(modifier: Modifier = Modifier,
//                     navController : NavController = rememberNavController()
//) {
////    val viewModel: EachListTypeViewModel = viewModel()
////    val state = viewModel.state
////
////    EachListTypeViewContent(
////        modifier = modifier,
////        state = state.value,
////    )
//}
//
//@Composable
//fun EachListTypeView(
//    modifier: Modifier = Modifier,
//    navController: NavController = rememberNavController(),
//    docId: String
//) {
//    val viewModel: EachListTypeViewModel = viewModel()
//    val state by viewModel.state.collectAsState()
//
//    LaunchedEffect(docId) {
//        viewModel.loadArticlesForList(docId)
//    }
//
//    EachListTypeViewContent(
//        modifier = modifier,
//        state = state,
//        onItemCheckedChange = { listItem, isChecked ->
//            // Lógica para atualizar o estado do item (pode envolver chamada ao backend)
//            println("Item ${listItem.name} foi ${if (isChecked) "marcado" else "desmarcado"}")
//        }
//    )
//}
//
////Funcional!!!!
////@Composable
////fun EachListTypeViewContent(
////    modifier: Modifier = Modifier,
////    state: ListState,
////    onItemCheckedChange: (ListItem, Boolean) -> Unit
////) {
////    Box(
////        modifier = modifier.fillMaxSize(),
////        contentAlignment = Alignment.BottomEnd
////    ) {
////        Column(modifier = Modifier.fillMaxSize()) {
////            // Exibe a lista de itens usando ItemDetailView
////            ItemDetailView(
////                listItems = state.listItems,
////                onItemCheckedChange = onItemCheckedChange
////            )
////        }
////
////        // Botão de adicionar permanece no canto inferior direito
////        Button(
////            modifier = Modifier
////                .padding(16.dp)
////                .size(60.dp),
////            onClick = { /* Implementar ação de adicionar aqui */ }
////        ) {
////            Icon(
////                painter = painterResource(R.drawable.baseline_add_24),
////                contentDescription = "Adicionar Item",
////                tint = Color.White
////            )
////        }
////    }
////}
//
//@Composable
//fun EachListTypeViewContent(
//    state: ListState,
//    onAddArticle: (String) -> Unit = {}
//) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.BottomEnd
//    ) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            LazyColumn(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth(),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(state.articles.size) { index ->
//                    val article = state.articles[index]
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Checkbox(
//                            checked = article.completed,
//                            onCheckedChange = { checked ->
//                                // Atualizar o estado e o Firestore
//                                article.completed = checked
//                                ListItemRepository.updateArticle(
//                                    articleId = article.articleId,
//                                    completed = checked
//                                )
//                            }
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Column {
//                            Text(
//                                text = article.name,
//                                style = MaterialTheme.typography.titleMedium
//                            )
//                            Text(
//                                text = article.description,
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                    }
//                }
//            }
//
//            // Botão de adicionar artigo
//            Button(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .size(60.dp),
//                onClick = { onAddArticle(state.articles.firstOrNull()?.docId ?: "") }
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.baseline_add_24),
//                    contentDescription = "Adicionar Artigo",
//                    tint = Color.White
//                )
//            }
//        }
//    }
//}
//
//
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun EachListTypeViewPreview() {
//    MyShoppingListTheme {
//        EachListTypeViewContent(
//            state = ListState(
//                listItems = arrayListOf(
//                    ListItem(docId = "1", name = "Compras de Casa", description = "As compras que vão para casa", owners = null),
//                    ListItem(docId = "2", name = "Compras de Escritório", description = "As compras que vão para o trabalho", owners = null)
//                )
//            ),
//            onItemCheckedChange = { item, isChecked ->
//                println("Preview: Item ${item.name} foi ${if (isChecked) "marcado" else "desmarcado"}")
//            }
//        )
//    }
//}
package com.example.myshoppinglist.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myshoppinglist.R
import com.example.myshoppinglist.TAG
import com.example.myshoppinglist.models.Article
import com.example.myshoppinglist.models.ListItem
import com.example.myshoppinglist.repositories.ListItemRepository
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme
import com.example.myshoppinglist.models.ListState
import com.example.myshoppinglist.ui.theme.Green01
import com.example.myshoppinglist.ui.theme.appFontBold16


@Composable
fun EachListTypeView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    docId: String
) {
    val viewModel: EachListTypeViewModel = viewModel()
    val state by viewModel.articles.collectAsState()

    LaunchedEffect(docId) {
        viewModel.loadArticlesForList(docId)
    }

    EachListTypeViewContent(
        state = ListState(articles = state),
        docId = docId, // Passa o docId para a lista
        viewModel = viewModel,
        onAddArticle = { docId ->
            navController.navigate("add_article/$docId") // Navega para a tela de adicionar artigo
        },
        onShareList = { docId ->
            navController.navigate("share_list/$docId") // Navega para a tela de partilhar lista
        }
    )
}

@Composable
fun EachListTypeViewContent(
    state: ListState,
    docId: String, // Identificador da lista
    viewModel: EachListTypeViewModel = viewModel(),
    onAddArticle: (String) -> Unit,
    onShareList: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.articles.size) { index ->
                    val article = state.articles[index]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = article.completed,
                            onCheckedChange = { isChecked ->
                                // Atualiza localmente o estado do artigo na lista
                                val updatedArticle = article.copy(completed = isChecked)
                                viewModel.updateSingleArticle(updatedArticle)

                                // Atualiza no Firestore
                                ListItemRepository.updateArticleCompletionStatus(
                                    articleId = article.articleId,
                                    docId = article.docId ?: "",
                                    completed = isChecked,
                                    onSuccess = {
                                        Log.d(TAG, "Artigo ${article.name} atualizado com sucesso para: $isChecked")
                                    },
                                    onFailure = { exception ->
                                        Log.e(TAG, "Erro ao atualizar o artigo: ${exception.message}")
                                    }
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = article.name,
                                style = appFontBold16
                            )
                            Text(
                                text = article.description,
//                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
        // Botão de adicionar artigo
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp)
                .width(80.dp),
            onClick = { onAddArticle(docId) }, // Chama a função para navegar para adicionar artigo
            colors = ButtonDefaults.buttonColors(
                containerColor = Green01,
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "Adicionar Artigo",
                tint = Color.White
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .height(80.dp)
                .width(80.dp),
            onClick = { onShareList(docId) }, // Chama a função para navegar para adicionar artigo
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray, // Cor de fundo do botão
//                contentColor = Color.White  // Cor do texto ou ícones dentro do botão
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_people_alt_24),
                contentDescription = "Partilhar lista",
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EachListTypeViewPreview() {
    MyShoppingListTheme {
        EachListTypeViewContent(
            state = ListState(
                articles = listOf(
                    Article(
                        articleId = "1",
                        name = "Maçãs",
                        description = "Comprar 2kg",
                        completed = false,
                        docId = "list1"
                    ),
                    Article(
                        articleId = "2",
                        name = "Pão",
                        description = "Integral ou de forma",
                        completed = true,
                        docId = "list1"
                    )
                )
            ),
            docId = "list1",
            onAddArticle = { listId ->
                println("Preview: Adicionar artigo à lista $listId")
            },
            onShareList = { listId ->
                println("Preview: Partilhar lista $listId")
            }
        )
    }
}

