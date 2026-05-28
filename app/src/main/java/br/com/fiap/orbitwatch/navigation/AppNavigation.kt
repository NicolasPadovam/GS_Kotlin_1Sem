package br.com.fiap.orbitwatch.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.fiap.orbitwatch.screens.detalhes.DetalhesScreen
import br.com.fiap.orbitwatch.screens.estatisticas.EstatisticasScreen
import br.com.fiap.orbitwatch.screens.eventos.EventosScreen
import br.com.fiap.orbitwatch.screens.home.HomeScreen
import br.com.fiap.orbitwatch.screens.splash.SplashScreen
import br.com.fiap.orbitwatch.viewmodel.EventosViewModel

@Composable
fun AppNavigation(navController: NavHostController) {

    val eventosViewModel: EventosViewModel = viewModel()

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
                onNavigateBack = { navController.popBackStack() },
                viewModel      = eventosViewModel
            )
        }

        composable(
            route     = NavRoutes.Detalhes.route,
            arguments = listOf(navArgument("eventoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventoId = backStackEntry.arguments?.getInt("eventoId") ?: return@composable
            DetalhesScreen(
                eventoId       = eventoId,
                onNavigateBack = { navController.popBackStack() },
                viewModel      = eventosViewModel
            )
        }

        composable(NavRoutes.Estatisticas.route) {
            EstatisticasScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}