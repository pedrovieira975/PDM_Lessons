package com.example.helloworld

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.helloworld.ui.theme.HelloWorldTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Greet(modifier: Modifier = Modifier){

    var name by remember { mutableStateOf("") }

    var greetingText by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
            TextField(value = name, onValueChange = { newValue ->
                name = newValue
            })
            Button(onClick = {
                greetingText = "Olá $name"
            }) {
                Text(text = "Greet")
            }
            Text(text = greetingText)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetPreview() {
    Greet()
}