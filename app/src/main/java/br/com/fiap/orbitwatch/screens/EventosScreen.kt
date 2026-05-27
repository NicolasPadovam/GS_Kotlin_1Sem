// br/com/fiap/orbitwatch/screens/eventos/EventosScreen.kt

package br.com.fiap.orbitwatch.screens.eventos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.orbitwatch.components.EventoCard
import br.com.fiap.orbitwatch.components.OrbitTopBar
import br.com.fiap.orbitwatch.ui.theme.*
import br.com.fiap.orbitwatch.viewmodel.EventosViewModel

// "NOTIFICADOS" fica no final da lista de filtros
private val filtros = listOf("TODOS", "CRÍTICO", "ALTO", "MÉDIO", "BAIXO", "NOTIFICADOS")

@Composable
fun EventosScreen(
    onNavigateToDetalhes: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: EventosViewModel = viewModel()
) {
    val eventosFiltrados    by viewModel.eventosFiltrados.collectAsStateWithLifecycle()
    val filtroAtual         by viewModel.filtroRisco.collectAsStateWithLifecycle()
    val isLoading           by viewModel.isLoading.collectAsStateWithLifecycle()
    val showSnackbar        by viewModel.showSnackbar.collectAsStateWithLifecycle()
    val eventosNotificados  by viewModel.eventosNotificados.collectAsStateWithLifecycle()
    val snackbarHostState   = remember { SnackbarHostState() }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar("Dados atualizados!")
            viewModel.onSnackbarDismissed()
        }
    }

    Scaffold(
        topBar = {
            OrbitTopBar(
                titulo        = "Eventos Monitorados",
                mostrarVoltar = true,
                onVoltar      = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick        = { viewModel.atualizarDados() },
                containerColor = NasaBlue
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color       = NasaWhite,
                        modifier    = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(Icons.Default.Refresh, contentDescription = "Atualizar", tint = NasaWhite)
                }
            }
        },
        snackbarHost   = { SnackbarHost(snackbarHostState) },
        containerColor = NasaWhite
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(NasaWhite)
                .padding(padding)
        ) {
            // Chips de filtro em scroll horizontal
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filtros.forEach { filtro ->
                    val isNotificados = filtro == "NOTIFICADOS"
                    val badge         = if (isNotificados && eventosNotificados.isNotEmpty())
                        " (${eventosNotificados.size})" else ""

                    FilterChip(
                        selected = filtroAtual == filtro,
                        onClick  = { viewModel.setFiltro(filtro) },
                        label    = {
                            Text(
                                text       = "$filtro$badge",
                                fontSize   = 12.sp,
                                fontWeight = if (isNotificados) FontWeight.Bold else FontWeight.Normal,
                                color      = if (filtroAtual == filtro) NasaWhite
                                else if (isNotificados && eventosNotificados.isNotEmpty()) CriticalRed
                                else NasaTextPrimary
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor   = if (isNotificados) CriticalRed else NasaBlue,
                            selectedLabelColor       = NasaWhite,
                            containerColor           = NasaLightGray,
                        )
                    )
                }
            }

            // Lista
            AnimatedVisibility(
                visible = !isLoading,
                enter   = fadeIn(),
                exit    = fadeOut()
            ) {
                if (eventosFiltrados.isEmpty()) {
                    Box(
                        modifier         = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text     = if (filtroAtual == "NOTIFICADOS")
                                "Nenhuma notificação enviada ainda"
                            else "Nenhum evento encontrado",
                            color    = NasaSubtle,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier            = Modifier.fillMaxSize(),
                        contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(eventosFiltrados, key = { it.id }) { evento ->
                            EventoCard(
                                evento  = evento,
                                onClick = { onNavigateToDetalhes(evento.id) }
                            )
                        }
                        item { Spacer(modifier = Modifier.height(72.dp)) }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun EventosScreenPreview() {
    OrbitWatchTheme {
        EventosScreen(onNavigateToDetalhes = {}, onNavigateBack = {})
    }
}