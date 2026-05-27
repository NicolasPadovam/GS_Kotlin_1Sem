package br.com.fiap.orbitwatch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.fiap.orbitwatch.screens.DetalhesScreen
import br.com.fiap.orbitwatch.screens.EstatisticasScreen
import br.com.fiap.orbitwatch.screens.eventos.EventosScreen
import br.com.fiap.orbitwatch.screens.HomeScreen
import br.com.fiap.orbitwatch.screens.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController    = navController,
        startDestination = NavRoutes.Splash.route
    ) {
        composable(NavRoutes.Splash.route) {
            SplashScreen(onNavigateToHome = {
                navController.navigate(NavRoutes.Home.route) {
                    popUpTo(NavRoutes.Splash.route) { inclusive = true }
                }
            })
        }

        composable(NavRoutes.Home.route) {
            HomeScreen(
                onNavigateToEventos      = { navController.navigate(NavRoutes.Eventos.route) },
                onNavigateToEstatisticas = { navController.navigate(NavRoutes.Estatisticas.route) }
            )
        }

        composable(NavRoutes.Eventos.route) {
            EventosScreen(
                onNavigateToDetalhes = { id: Int ->
                    navController.navigate(NavRoutes.Detalhes.createRoute(id))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route     = NavRoutes.Detalhes.route,
            arguments = listOf(navArgument("eventoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventoId = backStackEntry.arguments?.getInt("eventoId") ?: return@composable
            DetalhesScreen(
                eventoId       = eventoId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.Estatisticas.route) {
            EstatisticasScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}