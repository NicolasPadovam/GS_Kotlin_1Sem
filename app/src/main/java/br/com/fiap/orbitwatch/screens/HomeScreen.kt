package br.com.fiap.orbitwatch.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.orbitwatch.components.OrbitTopBar
import br.com.fiap.orbitwatch.data.EventoRepository
import br.com.fiap.orbitwatch.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToEventos: () -> Unit,
    onNavigateToEstatisticas: () -> Unit
) {
    val stats    = EventoRepository.getEstatisticas()
    val total    = stats["total"] as Int
    val criticos = stats["criticos"] as Int
    val regioes  = stats["regioes"] as Int   // ← substituiu tempMedia

    Scaffold(
        topBar         = { OrbitTopBar(titulo = "OrbitWatch", mostrarVoltar = false) },
        containerColor = NasaWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(NasaWhite)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Label seção
            Text(
                text          = "PAINEL DE CONTROLE",
                fontSize      = 10.sp,
                fontWeight    = FontWeight.Bold,
                color         = NasaBlue,
                letterSpacing = 2.sp
            )
            Text(
                text       = "Monitoramento\nAmbiental Global",
                fontSize   = 24.sp,
                fontWeight = FontWeight.Bold,
                color      = NasaTextPrimary,
                lineHeight  = 32.sp
            )

            // Divider azul
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(NasaBlue)
            )

            // Card de resumo
            Surface(
                color    = NasaLightGray,
                shape    = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ResumoItem(
                        label = "Eventos",
                        valor = "$total",
                        cor   = NasaTextPrimary
                    )
                    VerticalDivider(modifier = Modifier.height(48.dp))
                    ResumoItem(
                        label = "Críticos",
                        valor = "$criticos",
                        cor   = CriticalRed
                    )
                    VerticalDivider(modifier = Modifier.height(48.dp))
                    ResumoItem(
                        label = "Regiões",      // ← alterado
                        valor = "$regioes",
                        cor   = NasaBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text          = "ACESSO RÁPIDO",
                fontSize      = 10.sp,
                fontWeight    = FontWeight.Bold,
                color         = NasaSubtle,
                letterSpacing = 2.sp
            )

            NasaButton(
                texto   = "MONITORAR EVENTOS",
                icone   = Icons.Default.Radar,
                onClick = onNavigateToEventos
            )
            NasaButton(
                texto    = "VER ESTATÍSTICAS",
                icone    = Icons.Default.BarChart,
                onClick  = onNavigateToEstatisticas,
                outlined = true
            )
        }
    }
}

@Composable
private fun ResumoItem(
    label: String,
    valor: String,
    cor: androidx.compose.ui.graphics.Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = valor, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = cor)
        Text(text = label, fontSize = 11.sp, color = NasaSubtle)
    }
}

@Composable
private fun NasaButton(
    texto: String,
    icone: ImageVector,
    onClick: () -> Unit,
    outlined: Boolean = false
) {
    if (outlined) {
        OutlinedButton(
            onClick  = onClick,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape    = RoundedCornerShape(4.dp),
            border   = androidx.compose.foundation.BorderStroke(1.5.dp, NasaBlue)
        ) {
            Icon(icone, null, tint = NasaBlue, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(texto, fontSize = 13.sp, fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp, color = NasaBlue)
        }
    } else {
        Button(
            onClick  = onClick,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = NasaBlue),
            shape    = RoundedCornerShape(4.dp)
        ) {
            Icon(icone, null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(texto, fontSize = 13.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun HomeScreenPreview() {
    OrbitWatchTheme {
        HomeScreen(onNavigateToEventos = {}, onNavigateToEstatisticas = {})
    }
}