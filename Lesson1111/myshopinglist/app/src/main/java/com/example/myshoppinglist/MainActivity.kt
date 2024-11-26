package com.example.myshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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

const val TAG = "myshoppinglist"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var navController = rememberNavController()

            MyShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route) {
                        composable(Screen.Login.route) {
                            LoginView{
                                navController.navigate(Screen.ListTypes.route)
                            }
                        }
                        composable(Screen.ListTypes.route) {
                            ListTypesView (
                                onNavigateToAddList = {navController.navigate(Screen.AddListType.route)},
                                onNavigateToEachListType = {navController.navigate(Screen.EachListType.route)},
                                onLogoutSucess = {navController.navigate(Screen.Login.route)}
                            )
                        }

                        composable(Screen.AddListType.route) {
                            AddListTypesView(
                                navController = navController
                            )
                        }
                        composable(Screen.AddListType.route) {
                            AddListTypesView(
                                navController = navController
                            )
                        }
                        composable(Screen.EachListType.route) {
                            EachListTypeView(
                                navController = navController
                            )
                        }
                    }
                }
            }

            LaunchedEffect(Unit) {

                val auth = Firebase.auth

                val currentUser = auth.currentUser
                if (currentUser != null) {
                    navController.navigate(Screen.ListTypes.route)
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