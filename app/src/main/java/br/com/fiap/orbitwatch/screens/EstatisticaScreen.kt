package br.com.fiap.orbitwatch.screens.estatisticas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.orbitwatch.components.OrbitTopBar
import br.com.fiap.orbitwatch.data.EventoRepository
import br.com.fiap.orbitwatch.model.EventoAmbiental
import br.com.fiap.orbitwatch.ui.theme.*
import br.com.fiap.orbitwatch.viewmodel.EstatisticasViewModel

@Composable
fun EstatisticasScreen(
    onNavigateBack: () -> Unit,
    viewModel: EstatisticasViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            OrbitTopBar(titulo = "Estatísticas", mostrarVoltar = true,
                onVoltar = onNavigateBack, acaoIcone = Icons.Default.BarChart)
        },
        containerColor = SpaceBlack
    ) { padding ->

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = LightBlue)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Carregando dados...", color = TextGray, fontSize = 13.sp)
                }
            }
            return@Scaffold
        }

        LazyColumn(
            modifier            = Modifier.fillMaxSize().background(SpaceBlack).padding(padding),
            contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Cabeçalho
            item {
                Text("VISÃO GLOBAL", fontSize = 10.sp, fontWeight = FontWeight.Bold,
                    color = LightBlue, letterSpacing = 2.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Dados por Evento", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(AccentBlue))
            }

            // Métricas globais
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MetricaCard("Total",    "${uiState.totalEventos}",   Icons.Default.Public,  LightBlue,   Modifier.weight(1f))
                    MetricaCard("Críticos", "${uiState.eventosCriticos}", Icons.Default.Warning, CriticalRed, Modifier.weight(1f))
                    MetricaCard("Regiões",  "${uiState.totalRegioes}",   Icons.Default.Map,     SafeGreen,   Modifier.weight(1f))
                }
            }

            // Índice de criticidade
            item {
                Surface(color = CardDark, shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(), shadowElevation = 2.dp) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Índice de Criticidade", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 14.sp)
                            Text("${"%.0f".format(uiState.percentualCritico * 100)}%",
                                fontWeight = FontWeight.Bold, color = CriticalRed, fontSize = 14.sp)
                        }
                        LinearProgressIndicator(
                            progress    = { uiState.percentualCritico },
                            modifier    = Modifier.fillMaxWidth().height(6.dp),
                            color       = CriticalRed,
                            trackColor  = CardDark2
                        )
                        Text("${uiState.eventosCriticos} de ${uiState.totalEventos} eventos em estado crítico",
                            fontSize = 12.sp, color = TextGray)
                    }
                }
            }

            item {
                Text("DETALHES POR EVENTO", fontSize = 10.sp, fontWeight = FontWeight.Bold,
                    color = LightBlue, letterSpacing = 2.sp)
            }

            items(EventoRepository.eventos) { evento ->
                EventoEstatisticaCard(evento = evento)
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun EventoEstatisticaCard(evento: EventoAmbiental) {
    val satelite = when (evento.risco) {
        "CRÍTICO" -> "GOES-18"
        "ALTO"    -> "Landsat-9"
        "MÉDIO"   -> "Terra/MODIS"
        else      -> "Aqua/MODIS"
    }
    val precisao      = when (evento.risco) { "CRÍTICO" -> "98.2%" "ALTO" -> "95.7%" "MÉDIO" -> "91.3%" else -> "87.6%" }
    val umidade       = "${(40..95).random()}%"
    val vento         = "${(15..180).random()} km/h"
    val chuva         = "${"%.1f".format((0..250).random().toDouble())} mm"
    val bordaCor      = when (evento.risco) { "CRÍTICO" -> CriticalRed "ALTO" -> WarningAmber "MÉDIO" -> AccentBlue else -> SafeGreen }

    Surface(
        color    = CardDark,
        shape    = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 2.dp,
        border   = androidx.compose.foundation.BorderStroke(1.dp, bordaCor.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(evento.nome, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextWhite)
            Text(evento.localizacao, fontSize = 11.sp, color = TextGray)

            Spacer(modifier = Modifier.height(12.dp))

            Text("DADOS ORBITAIS", fontSize = 9.sp, fontWeight = FontWeight.Bold,
                color = LightBlue, letterSpacing = 1.5.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MiniChip(Icons.Default.Satellite,  "Satélite",    satelite,                          Modifier.weight(1f))
                MiniChip(Icons.Default.AccessTime, "Últ. Leitura", evento.ultimaAtualizacao.takeLast(5), Modifier.weight(1f))
                MiniChip(Icons.Default.GpsFixed,   "Precisão",    precisao,                          Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("INDICADORES CLIMÁTICOS", fontSize = 9.sp, fontWeight = FontWeight.Bold,
                color = LightBlue, letterSpacing = 1.5.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MiniChip(Icons.Default.WaterDrop, "Umidade", umidade, Modifier.weight(1f))
                MiniChip(Icons.Default.Air,       "Vento",   vento,   Modifier.weight(1f))
                MiniChip(Icons.Default.Umbrella,  "Chuva",   chuva,   Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun MiniChip(icone: ImageVector, label: String, valor: String, modifier: Modifier = Modifier) {
    Surface(color = CardDark2, shape = RoundedCornerShape(6.dp), modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icone, null, tint = LightBlue, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.height(3.dp))
            Text(valor, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextWhite)
            Text(label, fontSize = 9.sp, color = TextGray)
        }
    }
}

@Composable
private fun MetricaCard(titulo: String, valor: String, icone: ImageVector, cor: Color, modifier: Modifier) {
    Surface(color = CardDark, shape = RoundedCornerShape(12.dp),
        modifier = modifier, shadowElevation = 2.dp) {
        Column(modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(icone, null, tint = cor, modifier = Modifier.size(22.dp))
            Text(valor, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = cor)
            Text(titulo, fontSize = 10.sp, color = TextGray)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050A18)
@Composable
fun EstatisticasScreenPreview() {
    OrbitWatchTheme { EstatisticasScreen(onNavigateBack = {}) }
}