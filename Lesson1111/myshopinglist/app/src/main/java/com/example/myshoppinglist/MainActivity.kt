package com.example.myshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myshoppinglist.ui.home.AddListTypesView
import com.example.myshoppinglist.ui.home.EachListTypeView
import com.example.myshoppinglist.ui.home.ListTypesView
import com.example.myshoppinglist.ui.login.LoginView
import com.example.myshoppinglist.ui.theme.MyShoppingListTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.myshoppinglist.ui.home.ItemDetailView
import com.example.myshoppinglist.ui.home.ListTypesViewModel

const val TAG = "myshoppinglist"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            MyShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route
                    ) {
                        composable(Screen.Login.route) {
                            LoginView {
                                navController.navigate(Screen.ListTypes.route) {
                                    popUpTo(0) // Remove as rotas anteriores
                                }
                            }
                        }
                        composable(Screen.ListTypes.route) {
                            ListTypesView(
                                onNavigateToAddList = { navController.navigate(Screen.AddListType.route) },
                                onNavigateToEachListType = { docId ->
                                    navController.navigate("${Screen.EachListType.route}/$docId")
                                },
                                onLogoutSucess = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.AddListType.route) {
                            AddListTypesView(navController = navController)
                        }
                        composable(
                            route = "${Screen.EachListType.route}/{docId}",
                            arguments = listOf(navArgument("docId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val docId = backStackEntry.arguments?.getString("docId")
                            val viewModel: ListTypesViewModel = viewModel()
                            val listItem = docId?.let { viewModel.getListItemById(it) }

                            if (listItem != null) {
                                ItemDetailView(
                                    listItem = listItem,
                                    onCheckedChange = { isChecked ->
                                        // Atualize o estado ou execute outra ação ao marcar/desmarcar
                                    }
                                )
                            } else {
                                Text(text = "Item não encontrado")
                            }
                        }
                    }
                }
            }

            LaunchedEffect(Unit) {
                val auth = Firebase.auth
                val currentUser = auth.currentUser
                if (currentUser == null) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                } else {
                    navController.navigate(Screen.ListTypes.route) {
                        popUpTo(0)
                    }
                }
            }
        }
    }
}


sealed class Screen(val route : String){
    object Login : Screen("login")
    object ListTypes : Screen("list_types")
    object AddListType : Screen("add_list_type")
    object EachListType : Screen("each_list_type")
}