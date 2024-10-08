package com.example.calculadora

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import com.example.calculadora.ui.theme.CalculadoraTheme
import com.example.ui.components.CalcButton

@Composable
fun CalculatorView(modifier: Modifier = Modifier ){

    var display by remember { mutableStateOf("0") }

    val onNumPress : (String) -> Unit = { num ->
        if (display.length <= 8) {

            if (display == "0") {
                display = num
            } else {
                display += num
            }
        }
    }

    Column(modifier = modifier
        .fillMaxSize()
//        .wrapContentSize(Alignment.Center)
        .wrapContentSize(Alignment.BottomCenter)) {
        Text(modifier = Modifier.fillMaxWidth(),
            text = display,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.displayLarge)
        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "AC",
                specialOperation = true,
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "+/-",
                specialOperation = true,
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                specialOperation = true,
                label = "%",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "/",
                isOperation = true,
                onButtonPress = onNumPress
            )

//            Button(
//                onClick = { display += "AC" }, modifier = Modifier.weight(1f)
//                    .aspectRatio(1f)
//                    .padding(4.dp))
//                    {
//                Text(text = "AC",
//                    style = MaterialTheme.typography.bodyLarge)
//            }
//            Button(
//                onClick = { display += "+/-" }, modifier = Modifier.weight(1f)
//                    .aspectRatio(1f)
//                    .padding(4.dp)) {
//                Text(text = "+/-")
//            }
//            Button(
//                onClick = { display += "%" }, modifier = Modifier.weight(1f)
//                    .aspectRatio(1f)
//                    .padding(4.dp)) {
//                Text(text = "%")
//            }
//            Button(
//                onClick = {display += "/"}, modifier = Modifier.weight(1f)
//                    .aspectRatio(1f)
//                    .padding(4.dp)){
//                Text(text = "/")
//            }
        }
        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "7",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "8",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "9",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "x",
                isOperation = true,
                onButtonPress = onNumPress
            )
        }
        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "4",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "5",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "6",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "-",
                isOperation = true,
                onButtonPress = onNumPress
            )
        }
        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "1",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "2",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "3",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "+",
                isOperation = true,
                onButtonPress = onNumPress
            )
        }
        Row {
            CalcButton(
                modifier = Modifier.weight(2f),
                label = "0",
                expandHorizontally = true,
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = ",",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "=",
                isOperation = true,
                onButtonPress = onNumPress
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculadoraTheme {
        CalculatorView()
    }
}
