package com.example.myshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                        startDestination = "login") {
                        composable("login") {
                            LoginView {
                                navController.navigate("home")
                            }
                        }
                        composable("home") {
                            Text(text = "Home")
                        }
                    }
                }
            }

            LaunchedEffect(Unit) {

                val auth = Firebase.auth

                val currentUser = auth.currentUser
                if (currentUser != null) {
                    navController.navigate("home")
                }
            }
        }
    }
}