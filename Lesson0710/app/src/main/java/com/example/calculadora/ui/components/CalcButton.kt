package com.example.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadora.CalculatorView
import com.example.calculadora.ui.theme.Black
import com.example.calculadora.ui.theme.CalculadoraTheme
import com.example.calculadora.ui.theme.Gray
import com.example.calculadora.ui.theme.Orange
import com.example.calculadora.ui.theme.Pink40
import com.example.calculadora.ui.theme.PurpleGrey40
import com.example.calculadora.ui.theme.White
import com.example.calculadora.ui.theme.Whitegray

@Composable
fun CalcButton(modifier: Modifier = Modifier,
               label: String = "",
               isOperation: Boolean = false,
               specialOperation: Boolean = false,
               expandHorizontally: Boolean = false,
               onButtonPress: (String) -> Unit = {}
) {
    Button(
        modifier = modifier
            .then(
                if (!expandHorizontally) Modifier.aspectRatio(1f) else Modifier.aspectRatio(2f) // Aplica aspectRatio apenas se expandHorizontally for falso
            )

            .padding(4.dp),
        colors = ButtonDefaults.run {
            when {
                specialOperation -> buttonColors(Whitegray)
                isOperation -> buttonColors(Orange)
                else -> buttonColors(Gray)
            }
        },
        onClick = { onButtonPress(label) }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = if(label == "0") TextAlign.Left else TextAlign.Center,
            color = if (specialOperation) Black else White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculadoraTheme {
        CalcButton(
            label = "0"
        ){

        }
    }
}