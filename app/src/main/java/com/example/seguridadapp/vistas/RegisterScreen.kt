package com.example.seguridadapp.vistas

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import org.intellij.lang.annotations.Pattern


@Composable
fun RegisterScreen(navController: NavController, auth: FirebaseAuth) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }
    var showPass by rememberSaveable { mutableStateOf(false) }
    var showConfirm by rememberSaveable { mutableStateOf(false) }
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
            //encabezado
            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3A8A), // ← azul oscuro
                textAlign = TextAlign.Center
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

            // Correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it.trim() },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF2563EB),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color(0xFF2563EB),
                    unfocusedLabelColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // ← tamaño uniforme
            )

            Spacer(Modifier.height(16.dp))

            // Contraseña
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // ← tamaño uniforme
            )

            Spacer(Modifier.height(16.dp))

            // Confirmación
            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                label = { Text("Confirmar contraseña") },
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // ← tamaño uniforme
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))

        // Registrar
            Button(
                onClick = {
                    validarRegistro(email, password, confirm, auth, context, onSucess = {
                        navController.popBackStack()
                        navController.navigate("login")
                    })
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)), // azul uniforme
                shape = RoundedCornerShape(12.dp), // bordes redondeados
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // altura estándar
            ) {
                Text(
                    text = "Crear cuenta",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }


            TextButton(onClick = { navController.popBackStack(); navController.navigate("login") }) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}

private fun ColumnScope.validarRegistro(
    email: String,
    password: String,
    confirm: String,
    auth: FirebaseAuth,
    context: Context,
    onSucess: () -> Unit
){
    //validar si los campos estan vacios
    if(email.isBlank() || password.isBlank() || confirm.isBlank()){
        Toast.makeText(context, "Completa todos los campos", Toast
            .LENGTH_SHORT).show()
    }
    //validar que el formato del correo sea valido
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        Toast.makeText(context, "Correo invalido", Toast
            .LENGTH_SHORT).show()
    }
    if(password.length < 6){
        Toast.makeText(context, "La contraseña debe contener al menos 6 caracteres",
            Toast.LENGTH_SHORT).show()
    }
    if(password != confirm) {
        Toast.makeText(context, "Las contraseñas no coinciden",
            Toast.LENGTH_SHORT).show()
    }

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Registro Exitoso",Toast.LENGTH_SHORT)
                    .show()
                onSucess()
            } else {
                Toast.makeText(context, "Error al registrar",Toast.LENGTH_SHORT)
                    .show()
            }
        }}






