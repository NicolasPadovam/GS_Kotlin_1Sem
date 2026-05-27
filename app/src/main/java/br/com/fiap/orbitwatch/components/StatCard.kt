package br.com.fiap.orbitwatch.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import br.com.fiap.orbitwatch.ui.theme.StarBlue

@Composable
fun StatCard(
    titulo: String,
    valor: String,
    icone: ImageVector,
    modifier: Modifier = Modifier,
    iconeTint: androidx.compose.ui.graphics.Color = StarBlue
) {
    ElevatedCard(
        modifier = modifier,
        colors   = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement   = Arrangement.spacedBy(8.dp),
            horizontalAlignment   = Alignment.Start
        ) {
            Icon(
                imageVector        = icone,
                contentDescription = null,
                tint               = iconeTint,
                modifier           = Modifier.size(28.dp)
            )
            Text(
                text  = valor,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text  = titulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}