package com.example.seguridadapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seguridadapp.ui.theme.SeguridadAppTheme
import com.example.seguridadapp.vistas.HomeScreen
import com.example.seguridadapp.vistas.LoginScreen
import com.example.seguridadapp.vistas.RegisterScreen
import com.example.seguridadapp.vistas.HistorialScreen
import com.example.seguridadapp.vistas.PerfilScreen
import com.google.firebase.auth.FirebaseAuth

// Actividad principal que inicializa Firebase y configura la navegación con Jetpack Compose.
class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialización de Firebase
        auth = FirebaseAuth.getInstance()

        setContent {
            SeguridadAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val context = LocalContext.current

                // Scaffold con barra inferior visible solo fuera de login y registro.
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute != "login" && currentRoute != "register") {
                            BottomAppBar(
                                containerColor = Color(0xFF0A1F2D), // azul petróleo coherente
                                contentColor = Color.White
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    // Botón Home
                                    IconButton(onClick = { navController.navigate("home") }) {
                                        Icon(Icons.Default.Home, contentDescription = "Inicio", tint = Color(0xFFA855F7))
                                    }
                                    // Botón Historial
                                    IconButton(onClick = { navController.navigate("historial") }) {
                                        Icon(Icons.Default.History, contentDescription = "Historial", tint = Color(0xFFA855F7))
                                    }
                                    // Botón Perfil
                                    IconButton(onClick = { navController.navigate("perfil") }) {
                                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color(0xFFA855F7))
                                    }
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "login"
                        ) {
                            composable("login") { LoginScreen(navController, auth) }
                            composable("register") { RegisterScreen(navController, auth) }
                            composable("home") { HomeScreen(navController, auth) }
                            composable("historial") { HistorialScreen(navController, auth) }
                            composable("perfil") { PerfilScreen(navController, auth, context) } // nueva pantalla perfil
                        }
                    }
                }
            }
        }
    }
}

