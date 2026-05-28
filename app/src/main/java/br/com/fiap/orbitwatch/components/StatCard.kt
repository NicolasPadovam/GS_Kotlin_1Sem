package br.com.fiap.orbitwatch.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import br.com.fiap.orbitwatch.ui.theme.*

@Composable
fun StatCard(
    titulo: String,
    valor: String,
    icone: ImageVector,
    modifier: Modifier = Modifier,
    iconeTint: Color = LightBlue
) {
    ElevatedCard(
        modifier  = modifier,
        colors    = CardDefaults.elevatedCardColors(containerColor = CardDark),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape     = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier            = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(icone, null, tint = iconeTint, modifier = Modifier.size(28.dp))
            Text(valor, style = MaterialTheme.typography.displaySmall, color = TextWhite)
            Text(titulo, style = MaterialTheme.typography.bodyMedium, color = TextGray)
        }
    }
}