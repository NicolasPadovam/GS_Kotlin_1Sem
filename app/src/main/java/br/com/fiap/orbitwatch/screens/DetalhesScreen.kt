package br.com.fiap.orbitwatch.screens.detalhes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
    viewModel: EventosViewModel
) {
    val eventos      by viewModel.eventos.collectAsStateWithLifecycle()
    val evento       = eventos.find { it.id == eventoId }
    val snackbarHost = remember { SnackbarHostState() }
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
        snackbarHost   = { SnackbarHost(snackbarHost) },
        containerColor = SpaceBlack
    ) { padding ->

        if (evento == null) {
            Box(
                modifier         = Modifier
                    .fillMaxSize()
                    .background(SpaceBlack)
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Evento não encontrado", color = TextGray)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SpaceBlack)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Label
            Text(
                text          = "EVENTO AMBIENTAL",
                fontSize      = 10.sp,
                fontWeight    = FontWeight.Bold,
                color         = LightBlue,
                letterSpacing = 2.sp
            )

            // Nome + risco
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top
            ) {
                Text(
                    text       = evento.nome,
                    fontSize   = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextWhite,
                    modifier   = Modifier.weight(1f),
                    lineHeight  = 30.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                RiscoChip(risco = evento.risco)
            }

            // Localização
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, null, tint = LightBlue, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(evento.localizacao, fontSize = 13.sp, color = TextGray)
            }

            HorizontalDivider(color = CardDark2)

            // Dados Ambientais
            SecaoTitulo("DADOS AMBIENTAIS")
            DetalheItem(Icons.Default.Thermostat,  "Temperatura", "${"%.1f".format(evento.temperatura)}°C")
            DetalheItem(Icons.Default.Description, "Descrição",   evento.descricao)
            DetalheItem(Icons.Default.Warning,     "Impacto",     evento.impacto)

            HorizontalDivider(color = CardDark2)

            // Coordenadas
            SecaoTitulo("COORDENADAS")
            DetalheItem(Icons.Default.GpsFixed,   "Posição GPS",        evento.coordenadas)
            DetalheItem(Icons.Default.AccessTime, "Última Atualização", evento.ultimaAtualizacao)

            HorizontalDivider(color = CardDark2)

            // Ação
            SecaoTitulo("AÇÃO")

            Button(
                onClick = {
                    if (!jaNotificado) {
                        viewModel.notificarDefesaCivil(eventoId)
                        jaNotificado = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = !jaNotificado,
                colors  = ButtonDefaults.buttonColors(
                    containerColor         = if (jaNotificado) SafeGreen else CriticalRed,
                    disabledContainerColor = SafeGreen.copy(alpha = 0.8f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector        = if (jaNotificado) Icons.Default.CheckCircle
                    else Icons.Default.NotificationAdd,
                    contentDescription = null,
                    modifier           = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text       = if (jaNotificado) "NOTIFICAÇÃO ENVIADA"
                    else "ENVIAR NOTIFICAÇÃO À DEFESA CIVIL",
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextWhite
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SecaoTitulo(titulo: String) {
    Text(
        text          = titulo,
        fontSize      = 10.sp,
        fontWeight    = FontWeight.Bold,
        color         = LightBlue,
        letterSpacing = 2.sp
    )
}

@Composable
private fun DetalheItem(
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    valor: String
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Icon(icone, null, tint = TextGray, modifier = Modifier.size(16.dp).padding(top = 2.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(label, fontSize = 10.sp, color = TextGray, letterSpacing = 0.5.sp)
            Text(valor,  fontSize = 14.sp, color = TextWhite, lineHeight = 20.sp)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050A18)
@Composable
fun DetalhesScreenPreview() {
    OrbitWatchTheme {
        DetalhesScreen(eventoId = 1, onNavigateBack = {}, viewModel = viewModel())
    }
}