package com.example.seguridadapp.vistas

// Firebase
import com.google.firebase.auth.FirebaseAuth

// Jetpack Compose UI
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*

// Estado y contexto
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Navegación
import androidx.navigation.NavController

// Android
import android.widget.Toast
import androidx.compose.material.icons.filled.Security

@Composable
//historial de accesos
fun HistorialScreen(navController: NavController, auth: FirebaseAuth) {
    // Obtiene el contexto actual para mostrar mensajes (Toast)
    val context = LocalContext.current
    // Estado para controlar si el menú desplegable está abierto
    var expanded by remember { mutableStateOf(false) } // menú hamburguesa
    // Lista simulada de accesos con fecha, hora y estado (true = autorizado)
    val historial = listOf(
        Triple("10-10-2025", "12:20", true),
        Triple("10-10-2025", "22:30", true),
        Triple("09-10-2025", "10:11", false),
        Triple("09-10-2025", "20:30", true)
    )

        //contenedor principal
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F2FF))
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Columna que organiza los elementos verticalmente
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la pantalla
            Text(
                text = "Historial de Accesos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3A8A),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))// Espacio entre título y filtro

            // escudo
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = "Ícono de seguridad",
                tint = Color(0xFF2563EB), // mismo azul del botón
                modifier = Modifier
                    .size(96.dp)
                    .padding(top = 16.dp)
            )

            // Botón de filtro (simulado)
            Button(
                onClick = { Toast.makeText(context, "Filtro aún no implementado", Toast.LENGTH_SHORT).show() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Filtrar: Todas", color = Color.White)
            }

            Spacer(Modifier.height(24.dp))

            // Recorre la lista de historial y muestra cada evento como botón
            historial.forEach { (fecha, hora, autorizado) ->
                val color = if (autorizado) Color(0xFF10B981) else Color(0xFFEF4444)
                val estado = if (autorizado) "Autorizado" else "No Autorizado"

                Button(
                    onClick = { /* Acción si se desea */ },
                    colors = ButtonDefaults.buttonColors(containerColor = color),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(vertical = 4.dp)
                ) {
                    Text("$fecha, $hora – $estado", color = Color.White)
                }
            }
        }
    }
}
