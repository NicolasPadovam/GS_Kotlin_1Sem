package br.com.fiap.orbitwatch.screens

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
            OrbitTopBar(
                titulo        = "Estatísticas",
                mostrarVoltar = true,
                onVoltar      = onNavigateBack,
                acaoIcone     = Icons.Default.BarChart
            )
        },
        containerColor = NasaWhite
    ) { padding ->

        if (uiState.isLoading) {
            Box(
                modifier         = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = NasaBlue)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Carregando dados...", color = NasaSubtle, fontSize = 13.sp)
                }
            }
            return@Scaffold
        }

        LazyColumn(
            modifier            = Modifier
                .fillMaxSize()
                .background(NasaWhite)
                .padding(padding),
            contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // ── Cabeçalho ──
            item {
                Text(
                    text          = "VISÃO GLOBAL",
                    fontSize      = 10.sp,
                    fontWeight    = FontWeight.Bold,
                    color         = NasaBlue,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text       = "Dados por Evento",
                    fontSize   = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color      = NasaTextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(NasaBlue)
                )
            }

            // ── Cards de métricas globais ──
            item {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricaCard(
                        titulo = "Total",
                        valor  = "${uiState.totalEventos}",
                        icone  = Icons.Default.Public,
                        cor    = NasaBlue,
                        modifier = Modifier.weight(1f)
                    )
                    MetricaCard(
                        titulo = "Críticos",
                        valor  = "${uiState.eventosCriticos}",
                        icone  = Icons.Default.Warning,
                        cor    = CriticalRed,
                        modifier = Modifier.weight(1f)
                    )
                    MetricaCard(
                        titulo = "Regiões",
                        valor  = "${uiState.totalRegioes}",
                        icone  = Icons.Default.Map,
                        cor    = SafeGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ── Índice de criticidade ──
            item {
                Surface(
                    color    = NasaLightGray,
                    shape    = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Índice de Criticidade",
                                fontWeight = FontWeight.Bold,
                                color      = NasaTextPrimary,
                                fontSize   = 14.sp
                            )
                            Text(
                                "${"%.0f".format(uiState.percentualCritico * 100)}%",
                                fontWeight = FontWeight.Bold,
                                color      = CriticalRed,
                                fontSize   = 14.sp
                            )
                        }
                        LinearProgressIndicator(
                            progress         = { uiState.percentualCritico },
                            modifier         = Modifier.fillMaxWidth().height(6.dp),
                            color            = CriticalRed,
                            trackColor       = NasaCardWhite
                        )
                        Text(
                            "${uiState.eventosCriticos} de ${uiState.totalEventos} eventos em estado crítico",
                            fontSize = 12.sp,
                            color    = NasaSubtle
                        )
                    }
                }
            }

            // ── Separador ──
            item {
                Text(
                    text          = "DETALHES POR EVENTO",
                    fontSize      = 10.sp,
                    fontWeight    = FontWeight.Bold,
                    color         = NasaBlue,
                    letterSpacing = 2.sp
                )
            }

            // ── Card por evento com dados orbitais e climáticos ──
            items(EventoRepository.eventos) { evento ->
                EventoEstatisticaCard(evento = evento)
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun EventoEstatisticaCard(evento: EventoAmbiental) {
    // Dados orbitais simulados baseados no evento
    val satelite       = when (evento.risco) {
        "CRÍTICO" -> "GOES-18"
        "ALTO"    -> "Landsat-9"
        "MÉDIO"   -> "Terra/MODIS"
        else      -> "Aqua/MODIS"
    }
    val precisao       = when (evento.risco) {
        "CRÍTICO" -> "98.2%"
        "ALTO"    -> "95.7%"
        "MÉDIO"   -> "91.3%"
        else      -> "87.6%"
    }
    val umidade        = "${(40..95).random()}%"
    val vento          = "${(15..180).random()} km/h"
    val pluviometrico  = "${"%.1f".format((0..250).random().toDouble())} mm"

    Surface(
        color    = NasaWhite,
        shape    = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 2.dp,
        border   = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = when (evento.risco) {
                "CRÍTICO" -> CriticalRed.copy(alpha = 0.4f)
                "ALTO"    -> WarningAmber.copy(alpha = 0.4f)
                "MÉDIO"   -> NasaBlue.copy(alpha = 0.3f)
                else      -> SafeGreen.copy(alpha = 0.3f)
            }
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Nome do evento
            Text(
                text       = evento.nome,
                fontSize   = 14.sp,
                fontWeight = FontWeight.Bold,
                color      = NasaTextPrimary
            )
            Text(
                text     = evento.localizacao,
                fontSize = 11.sp,
                color    = NasaSubtle
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Dados Orbitais
            SubSectionLabel(label = "DADOS ORBITAIS")
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrbitalChip(icone = Icons.Default.Satellite,    label = "Satélite",  valor = satelite,                modifier = Modifier.weight(1f))
                OrbitalChip(icone = Icons.Default.AccessTime,   label = "Últ. Leitura", valor = evento.ultimaAtualizacao.takeLast(5), modifier = Modifier.weight(1f))
                OrbitalChip(icone = Icons.Default.GpsFixed,     label = "Precisão",  valor = precisao,               modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Indicadores Climáticos
            SubSectionLabel(label = "INDICADORES CLIMÁTICOS")
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrbitalChip(icone = Icons.Default.WaterDrop,    label = "Umidade",   valor = umidade,               modifier = Modifier.weight(1f))
                OrbitalChip(icone = Icons.Default.Air,          label = "Vento",     valor = vento,                 modifier = Modifier.weight(1f))
                OrbitalChip(icone = Icons.Default.Umbrella,     label = "Chuva",     valor = pluviometrico,         modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun SubSectionLabel(label: String) {
    Text(
        text          = label,
        fontSize      = 9.sp,
        fontWeight    = FontWeight.Bold,
        color         = NasaBlue,
        letterSpacing = 1.5.sp
    )
}

@Composable
private fun OrbitalChip(
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color    = NasaLightGray,
        shape    = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Column(
            modifier            = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icone, null, tint = NasaBlue, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.height(3.dp))
            Text(valor, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NasaTextPrimary)
            Text(label, fontSize = 9.sp, color = NasaSubtle)
        }
    }
}

@Composable
private fun MetricaCard(
    titulo: String,
    valor: String,
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    cor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color    = NasaLightGray,
        shape    = RoundedCornerShape(8.dp),
        modifier = modifier,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier            = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icone, null, tint = cor, modifier = Modifier.size(22.dp))
            Text(valor, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = cor)
            Text(titulo, fontSize = 10.sp, color = NasaSubtle)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun EstatisticasScreenPreview() {
    OrbitWatchTheme { EstatisticasScreen(onNavigateBack = {}) }
}