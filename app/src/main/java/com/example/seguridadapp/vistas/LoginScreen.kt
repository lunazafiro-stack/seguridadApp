package com.example.seguridadapp.vistas // Define el paquete donde se encuentra esta pantalla

import android.content.Context // Permite acceder al contexto de la app (necesario para Toast)
import android.widget.Toast // Muestra mensajes emergentes al usuario
import androidx.compose.foundation.background // Permite aplicar color de fondo a componentes
import androidx.compose.foundation.layout.* // Contiene Column, Row, Spacer, Box y modificadores de tamaño y alineación
import androidx.compose.foundation.shape.RoundedCornerShape // Define bordes redondeados en botones y campos
import androidx.compose.foundation.text.KeyboardOptions // Configura el tipo de teclado (email, numérico, contraseña)
import androidx.compose.material.icons.Icons // Accede al conjunto de íconos predeterminados
import androidx.compose.material.icons.filled.Security // Ícono de escudo (seguridad)
import androidx.compose.material.icons.filled.Email // Ícono de correo electrónico
import androidx.compose.material.icons.filled.Lock // Ícono de candado (contraseña)
import androidx.compose.material3.* // Componentes visuales modernos: TextField, Button, Text, etc.
import androidx.compose.runtime.Composable // Marca funciones que generan UI con Compose
import androidx.compose.runtime.mutableStateOf // Crea variables reactivas que actualizan la interfaz
import androidx.compose.ui.Alignment // Alinea componentes dentro de Column, Row o Box
import androidx.compose.ui.Modifier // Permite aplicar estilos y comportamiento a los componentes
import androidx.compose.ui.text.font.FontWeight // Define el grosor del texto (negrita, normal, etc.)
import androidx.compose.ui.text.input.KeyboardType // Especifica el tipo de teclado (email, password, etc.)
import androidx.compose.ui.text.input.PasswordVisualTransformation // Oculta el texto del campo de contraseña con puntos
import androidx.compose.ui.text.style.TextAlign // Alinea el texto (centro, izquierda, derecha)
import androidx.compose.ui.unit.dp // Define medidas en dp (density-independent pixels)
import androidx.navigation.NavController // Controla la navegación entre pantallas (login, registro, etc.)
import androidx.compose.runtime.* // Importa funciones de estado como remember y LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable // Guarda el estado aunque se rote la pantalla
import androidx.compose.ui.graphics.Color // Permite definir colores personalizados en la UI
import androidx.compose.ui.platform.LocalContext // Obtiene el contexto actual dentro de una función composable
import com.google.firebase.auth.FirebaseAuth // Permite autenticar usuarios con Firebase Authentication

@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth) {
    // Estructura principal
    //rememberSaveable es para mantener el formato dando vuelta el telefono
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F2FF)) // azul muy claro fondo pantalla
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Bienvenido tu App de Seguridad",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3A8A), // azul oscuro
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // escudo
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = "Ícono de seguridad",
                tint = Color(0xFF2563EB), // mismo azul del botón
                modifier = Modifier
                    .size(96.dp)
                    .padding(top = 16.dp)
            )

            // Campo correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF2563EB),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color(0xFF2563EB),
                    unfocusedLabelColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF2563EB),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color(0xFF2563EB),
                    unfocusedLabelColor = Color.Gray
                ),
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))

            // Botón de inicio de sesión
            Button(
                onClick =
                    { validarCredencial(email, password, auth, context, navController)},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(56.dp) // altura estándar para botones
            ) {
                Text(
                    text = "Iniciar sesión",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }


            // Botón de registro
            TextButton(onClick = {navController.navigate("register")}) {
                Text("¿No tienes cuenta? Registrate", color = Color.Gray)
                    }

            }
        }
    }


private fun ColumnScope.validarCredencial(
    email: String,
    password: String,
    auth: FirebaseAuth,
    context: Context,
    navController: NavController
) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()

                navController.popBackStack() // Limpia la pila de navegación
                navController.navigate("home") // ← Navega sin argumentos si no los usas
            } else {
                Toast.makeText(context, "Error de inicio de sesión", Toast.LENGTH_SHORT).show()
            }
        }
    } else {
        Toast.makeText(context, "Ingresa el correo y la contraseña", Toast.LENGTH_SHORT).show()
    }
}



