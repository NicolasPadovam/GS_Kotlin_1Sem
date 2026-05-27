
package br.com.fiap.orbitwatch.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import br.com.fiap.orbitwatch.components.RiscoChip
import br.com.fiap.orbitwatch.ui.theme.*
import br.com.fiap.orbitwatch.viewmodel.EventosViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DetalhesScreen(
    eventoId: Int,
    onNavigateBack: () -> Unit,
    viewModel: EventosViewModel = viewModel()
) {
    val eventos    by viewModel.eventos.collectAsStateWithLifecycle()
    val evento     = eventos.find { it.id == eventoId }
    val snackbarHostState = remember { SnackbarHostState() }

    // Lista local de notificações enviadas nesta sessão
    var notificacoes by remember { mutableStateOf<List<String>>(emptyList()) }
    var jaNotificado by remember { mutableStateOf(viewModel.isNotificado(eventoId)) }


    Scaffold(
        topBar = {
            OrbitTopBar(
                titulo        = "Detalhes do Evento",
                mostrarVoltar = true,
                onVoltar      = onNavigateBack,
                acaoIcone     = Icons.Default.Satellite
            )
        },
        snackbarHost   = { SnackbarHost(snackbarHostState) },
        containerColor = NasaBlack
    ) { padding ->

        if (evento == null) {
            Box(
                modifier         = Modifier.fillMaxSize().background(NasaBlack).padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Evento não encontrado", color = NasaSubtle)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(NasaBlack)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Label de categoria
            Text(
                text          = "EVENTO AMBIENTAL",
                fontSize      = 10.sp,
                fontWeight    = FontWeight.Bold,
                color         = NasaBlue,
                letterSpacing = 2.sp
            )

            // Nome + chip de risco
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top
            ) {
                Text(
                    text       = evento.nome,
                    fontSize   = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color      = NasaWhite,
                    modifier   = Modifier.weight(1f),
                    lineHeight  = 30.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                RiscoChip(risco = evento.risco)
            }

            // Localização
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, null, tint = NasaBlue, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(evento.localizacao, fontSize = 13.sp, color = NasaSubtle)
            }

            // Divisor
            Box(Modifier.fillMaxWidth().height(1.dp).background(NasaCardGray))

            // Seção: Dados Ambientais
            NasaSectionTitle("DADOS AMBIENTAIS")
            NasaDetalheItem(Icons.Default.Thermostat, "Temperatura", "${"%.1f".format(evento.temperatura)}°C")
            NasaDetalheItem(Icons.Default.Description, "Descrição", evento.descricao)
            NasaDetalheItem(Icons.Default.Warning, "Impacto", evento.impacto)

            Box(Modifier.fillMaxWidth().height(1.dp).background(NasaCardGray))

            // Seção: Coordenadas
            NasaSectionTitle("COORDENADAS")
            NasaDetalheItem(Icons.Default.GpsFixed, "Posição GPS", evento.coordenadas)
            NasaDetalheItem(Icons.Default.AccessTime, "Última Atualização", evento.ultimaAtualizacao)

            Box(Modifier.fillMaxWidth().height(1.dp).background(NasaCardGray))

            // Seção: Ação
            NasaSectionTitle("AÇÃO")

            // Botão principal: Enviar Notificação à Defesa Civil
            Button(
                onClick = {
                    if (!jaNotificado) {
                        viewModel.notificarDefesaCivil(eventoId)
                        jaNotificado = true
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                enabled  = !jaNotificado,
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = if (jaNotificado) SafeGreen else CriticalRed,
                    disabledContainerColor = SafeGreen.copy(alpha = 0.8f)
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Icon(
                    if (jaNotificado) Icons.Default.CheckCircle else Icons.Default.NotificationAdd,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text          = if (jaNotificado) "NOTIFICAÇÃO ENVIADA" else "ENVIAR NOTIFICAÇÃO À DEFESA CIVIL",
                    fontSize      = 12.sp,
                    fontWeight    = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }

            // Lista de notificações enviadas (aparece apenas quando há itens)
            if (notificacoes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text          = "NOTIFICAÇÕES ENVIADAS",
                    fontSize      = 10.sp,
                    fontWeight    = FontWeight.Bold,
                    color         = NasaBlue,
                    letterSpacing = 2.sp
                )

                Surface(
                    color  = NasaDarkGray,
                    shape  = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier            = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        notificacoes.forEach { item ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint     = SafeGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text     = item,
                                    fontSize = 12.sp,
                                    color    = NasaOffWhite
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun NasaSectionTitle(titulo: String) {
    Text(
        text          = titulo,
        fontSize      = 10.sp,
        fontWeight    = FontWeight.Bold,
        color         = NasaBlue,
        letterSpacing = 2.sp
    )
}

@Composable
private fun NasaDetalheItem(
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    valor: String
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Icon(icone, null, tint = NasaSubtle, modifier = Modifier.size(16.dp).padding(top = 2.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(label, fontSize = 10.sp, color = NasaSubtle, letterSpacing = 0.5.sp)
            Text(valor, fontSize = 14.sp, color = NasaOffWhite, lineHeight = 20.sp)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0C10)
@Composable
fun DetalhesScreenPreview() {
    OrbitWatchTheme { DetalhesScreen(eventoId = 1, onNavigateBack = {}) }
}