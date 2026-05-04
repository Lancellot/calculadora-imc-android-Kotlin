package com.example.calculadoraimc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IMCScreen() {

    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }

    var imc by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var mostrarResultado by remember { mutableStateOf(false) }

    fun calcularIMC() {
        val p = peso.toDoubleOrNull()
        val a = altura.toDoubleOrNull()

        if (p != null && a != null && a > 0) {
            val resultado = p / (a * a)
            imc = String.format("%.2f", resultado)

            categoria = when {
                resultado < 18.5 -> "Abaixo do peso"
                resultado < 24.9 -> "Peso normal"
                resultado < 29.9 -> "Sobrepeso"
                else -> "Obesidade"
            }

            mostrarResultado = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        // Título
        Text(
            text = "Calculadora de IMC",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF4A148C)
        )

        Text(
            text = "Índice de Massa Corporal",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            color = Color.Gray
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text("Peso (kg)", fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = peso,
                    onValueChange = { peso = it },
                    placeholder = { Text("Ex: 70.5") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )

                Text("Altura (m)", fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = altura,
                    onValueChange = { altura = it },
                    placeholder = { Text("Ex: 1.75") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { calcularIMC() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B1FA2))
        ) {
            Text("Calcular IMC", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (mostrarResultado) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Seus dados", color = Color(0xFF2E7D32))

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Peso:", modifier = Modifier.weight(1f))
                        Text(peso, fontWeight = FontWeight.Bold)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text("Altura:", modifier = Modifier.weight(1f))
                        Text(altura, fontWeight = FontWeight.Bold)
                    }

                    Divider()

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Seu IMC", color = Color(0xFF2E7D32))

                    Text(
                        text = imc,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = categoria,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        }
    }
}