package com.example.seguridadapp.vistas

import android.widget.Toast // Mensajes emergentes al usuario

import androidx.compose.foundation.background // Fondo de pantalla
import androidx.compose.foundation.layout.* // Column, Row, Spacer, Box, etc.
import androidx.compose.foundation.shape.RoundedCornerShape // Bordes redondeados en botones
import androidx.compose.material.icons.Icons // Íconos predeterminados
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home // Ícono de inicio
import androidx.compose.material.icons.filled.Menu // Ícono de menú
import androidx.compose.material3.* // Botones, textos, tema visual

import androidx.compose.runtime.* // Estado reactivo (remember, mutableStateOf)
import androidx.compose.runtime.saveable.rememberSaveable // Guarda estado al rotar pantalla

import androidx.compose.ui.Alignment // Alineación dentro de Column, Row, Box
import androidx.compose.ui.Modifier // Modificadores visuales
import androidx.compose.ui.graphics.Color // Colores personalizados
import androidx.compose.ui.platform.LocalContext // Accede al contexto actual
import androidx.compose.ui.text.font.FontWeight // Negrita en textos
import androidx.compose.ui.text.style.TextAlign // Alineación de texto
import androidx.compose.ui.unit.dp // Medidas en dp (density-independent pixels)

import androidx.navigation.NavController // Navegación entre pantallas
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(navController: NavController, auth: FirebaseAuth) {
    val userEmail = auth.currentUser?.email
    val context = LocalContext.current
    var lastUID by rememberSaveable { mutableStateOf("% DA C7 AF") }
    var doorClosed by rememberSaveable { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) } //hamburguesa


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F2FF)) // fondo celeste claro
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Saludo
            Text(
                text = "Hola ${userEmail ?: "Usuario"}!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3A8A),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(32.dp))

            // Último UID leído
            Button(
                onClick = { /* Acción si se desea */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
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
                onClick = { /* Acción si se desea  faltaaa */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = if (doorClosed) "Puerta Cerrada" else "Puerta Abierta",
                    color = Color.White
                )
            }

            Spacer(Modifier.height(16.dp))

            // Botón para abrir puerta
            Button(
                onClick = {
                    doorClosed = false
                    Toast.makeText(context, "Puerta abierta", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Abrir Puerta", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
