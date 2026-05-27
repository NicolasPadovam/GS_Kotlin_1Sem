package br.com.fiap.orbitwatch.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.fiap.orbitwatch.model.EventoAmbiental
import br.com.fiap.orbitwatch.ui.theme.*


@Composable
fun EventoCard(
    evento: EventoAmbiental,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bordaColor = when (evento.risco) {
        "CRÍTICO" -> CriticalRed
        "ALTO"    -> WarningAmber
        "MÉDIO"   -> OrbitBlue
        "BAIXO"   -> SafeGreen
        else      -> Color.Gray
    }

    ElevatedCard(
        onClick   = onClick,
        modifier  = modifier
            .fillMaxWidth()
            .border(
                width = 1.5.dp,
                color = bordaColor.copy(alpha = 0.6f),
                shape = RoundedCornerShape(12.dp)
            ),
        colors    = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text     = evento.nome,
                    style    = MaterialTheme.typography.titleMedium,
                    color    = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                RiscoChip(risco = evento.risco)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector        = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint               = StarBlue,
                    modifier           = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text  = evento.localizacao,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Default.Thermostat,
                        contentDescription = null,
                        tint               = WarningAmber,
                        modifier           = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text  = "${"%.1f".format(evento.temperatura)}°C",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    color = when (evento.status) {
                        "ATIVO"      -> CriticalRed.copy(alpha = 0.15f)
                        "MONITORADO" -> WarningAmber.copy(alpha = 0.15f)
                        else         -> SafeGreen.copy(alpha = 0.15f)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text  = evento.status,
                        color = when (evento.status) {
                            "ATIVO"      -> CriticalRed
                            "MONITORADO" -> WarningAmber
                            else         -> SafeGreen
                        },
                        style    = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}