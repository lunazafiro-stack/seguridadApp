package com.example.seguridadapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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


import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// Actividad principal que inicializa Firebase y configura la navegación con Jetpack Compose.
class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //inicializacion de firebase
        auth = FirebaseAuth.getInstance()


        setContent {
            SeguridadAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val context = LocalContext.current

// Scaffold con barra inferior visible solo fuera de login y registro.
// Incluye navegación entre pantallas y botón para cerrar sesión.
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute != "login" && currentRoute != "register"){
                            BottomAppBar(containerColor = Color(0xFF2563EB),
                                contentColor = Color.White) {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly){
                                    IconButton(onClick = { navController.navigate("home") }) {
                                        Icon(Icons.Default.Home, contentDescription = "Inicio")
                                    }
                                    IconButton(onClick = { navController.navigate("historial") }) {
                                        Icon(Icons.Default.History, contentDescription = "historial")
                                    }
                                    IconButton(onClick = {
                                        auth.signOut()
                                        navController.popBackStack()
                                        navController.navigate("login")
                                        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                                    }) {
                                        Icon(Icons.Default.Close, contentDescription = "Cerrar sesión", tint = Color(0xFFEF4444))
                                    }
                                }
                            }
                        }

                    // Contenedor de navegación que define las rutas y pantallas de la app.
                    // Usa NavHost para mostrar la pantalla correspondiente según la ruta activa.
                    }) { innerPadding ->
                    Box(modifier = Modifier.padding( paddingValues = innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "login"
                        ) {
                            composable( route = "login"){ LoginScreen(navController, auth) }
                            composable ( route = "register") { RegisterScreen(navController, auth) }
                            composable ( route = "home" ) { HomeScreen(navController, auth)}
                            composable("historial") { HistorialScreen(navController, auth)
                            }
                        }
                    }
                }
            }
        }
    }
}
