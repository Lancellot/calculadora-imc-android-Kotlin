package com.example.calculadoraimc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

data class ResultadoIMC(
    val valor: Double,
    val categoria: String,
    val cor: Int
)

fun calcularIMC(peso: String, altura: String): ResultadoIMC? {
    val p = peso.replace(",", ".").toDoubleOrNull()
    val a = altura.replace(",", ".").toDoubleOrNull()

    if (p == null || a == null || a <= 0) return null

    val imc = p / (a * a)

    return when {
        imc < 18.5 -> ResultadoIMC(imc, "Abaixo do peso", R.color.cor_abaixo)
        imc < 24.9 -> ResultadoIMC(imc, "Peso normal", R.color.cor_normal)
        imc < 29.9 -> ResultadoIMC(imc, "Sobrepeso", R.color.cor_sobrepeso)
        else -> ResultadoIMC(imc, "Obesidade", R.color.cor_obesidade)
    }
}

@Composable
fun IMCScreen() {

    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<ResultadoIMC?>(null) }
    var erro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fundo_app))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Text(
            text = "Calculadora de IMC",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.texto_primario)
        )

        Text(
            text = "Índice de Massa Corporal",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            color = colorResource(R.color.texto_secundario)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.roxo_claro)),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(modifier = Modifier.padding(20.dp)) {

                Text("Peso (kg)", fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = peso,
                    onValueChange = {
                        peso = it.replace(",", ".")
                        resultado = null
                        erro = ""
                    },
                    placeholder = { Text("Ex: 70.5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )

                Text("Altura (m)", fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = altura,
                    onValueChange = {
                        altura = it.replace(",", ".")
                        resultado = null
                        erro = ""
                    },
                    placeholder = { Text("Ex: 1.75") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val res = calcularIMC(peso, altura)
                if (res == null) {
                    erro = "Preencha valores válidos"
                    resultado = null
                } else {
                    resultado = res
                    erro = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.roxo_principal)
            )
        ) {
            Text("Calcular IMC", color = colorResource(android.R.color.white))
        }

        if (erro.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = erro,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        resultado?.let {

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.verde_claro)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Seu IMC",
                        color = colorResource(R.color.verde_resultado)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = String.format(Locale.getDefault(), "%.2f", it.valor),
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = it.categoria,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(it.cor)
                    )
                }
            }
        }
    }
}
