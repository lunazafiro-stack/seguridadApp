package com.example.seguridadapp.vistas


import com.google.firebase.auth.FirebaseAuth

// Jetpack Compose UI
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Para poder hacer scroll
import androidx.compose.foundation.verticalScroll // Para habilitar el scroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
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

// IMPORTANTE: Asegúrate de importar tu cliente de red
import com.example.seguridadapp.network.RetrofitClient

@Composable
fun HistorialScreen(navController: NavController, auth: FirebaseAuth) {
    val context = LocalContext.current

    var historialLista by remember { mutableStateOf<List<String>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            historialLista = RetrofitClient.api.getHistory()
            cargando = false
        } catch (e: Exception) {
            cargando = false
            Toast.makeText(context, "No se pudo conectar al ESP32", Toast.LENGTH_SHORT).show()
            historialLista = listOf("Error de conexión: Verifique WiFi")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1F2D)) // fondo azul petróleo
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Encabezado
            Text(
                text = "Historial de Accesos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFC084FC), // morado claro
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Ícono escudo
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = "Ícono de seguridad",
                tint = Color(0xFFA855F7), // morado medio
                modifier = Modifier.size(96.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Botón estado
            Button(
                onClick = {
                    cargando = true
                    Toast.makeText(context, "Recargando al salir y entrar...", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)), // morado intenso
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Estado: ${if (cargando) "Cargando..." else "Actualizado"}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))

            if (historialLista.isEmpty() && !cargando) {
                Text(
                    text = "No hay registros recientes",
                    color = Color(0xFFCBD5E1), // gris claro
                    textAlign = TextAlign.Center
                )
            } else {
                historialLista.forEach { eventoTexto ->
                    val colorBoton = when {
                        eventoTexto.contains("Autorizado", ignoreCase = true) -> Color(0xFF10B981) // verde
                        eventoTexto.contains("Denegado", ignoreCase = true) -> Color(0xFFEF4444) // rojo
                        eventoTexto.contains("Fallido", ignoreCase = true) -> Color(0xFFEF4444) // rojo
                        else -> Color(0xFFA855F7) // morado medio por defecto
                    }

                    Button(
                        onClick = { /* Solo informativo */ },
                        colors = ButtonDefaults.buttonColors(containerColor = colorBoton),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = eventoTexto,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}
