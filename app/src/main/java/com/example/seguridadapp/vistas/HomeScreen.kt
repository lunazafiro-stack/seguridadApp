package com.example.seguridadapp.vistas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth


import com.example.seguridadapp.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, auth: FirebaseAuth) {
    val userEmail = auth.currentUser?.email
    val context = LocalContext.current

    var lastUID by rememberSaveable { mutableStateOf("% DA C7 AF") }
    var doorClosed by rememberSaveable { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1F2D)) // fondo azul petróleo
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Encabezado
            Text(
                text = "Hola ${userEmail ?: "Usuario"}!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFC084FC), // morado claro
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(32.dp))

            // Último UID
            Button(
                onClick = { /* Acción futura para actualizar UID */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)), // morado intenso
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Último UID leído: $lastUID", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            // Estado de la puerta
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (doorClosed) Color(0xFF9333EA) else Color(0xFF10B981) // morado suave o verde
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = if (doorClosed) "Estado: Puerta Cerrada" else "Estado: Puerta Abierta",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))

            // Botón abrir/cerrar puerta
            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.api.toggleDoor()
                            Toast.makeText(context, "ESP32: ${response.mensaje}", Toast.LENGTH_SHORT).show()
                            doorClosed = !doorClosed
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)), // morado intenso
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Abrir / Cerrar Puerta", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            // Botón historial
            Button(
                onClick = { navController.navigate("historial") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA855F7)), // morado medio
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Ver Historial de Accesos", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
