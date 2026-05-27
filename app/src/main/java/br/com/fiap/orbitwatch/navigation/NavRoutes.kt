package br.com.fiap.orbitwatch.navigation

sealed class NavRoutes(val route: String) {
    object Splash       : NavRoutes("splash")
    object Home         : NavRoutes("home")
    object Eventos      : NavRoutes("eventos")
    object Estatisticas : NavRoutes("estatisticas")
    object Detalhes     : NavRoutes("detalhes/{eventoId}") {
        fun createRoute(id: Int) = "detalhes/$id"
    }
}