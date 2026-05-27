package br.com.fiap.orbitwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import br.com.fiap.orbitwatch.navigation.AppNavigation
import br.com.fiap.orbitwatch.ui.theme.OrbitWatchTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrbitWatchTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}