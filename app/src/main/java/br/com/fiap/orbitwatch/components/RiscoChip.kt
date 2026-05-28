package br.com.fiap.orbitwatch.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.orbitwatch.ui.theme.*

@Composable
fun RiscoChip(risco: String, modifier: Modifier = Modifier) {
    val cor = when (risco) {
        "CRÍTICO" -> CriticalRed
        "ALTO"    -> WarningAmber
        "MÉDIO"   -> AccentBlue
        "BAIXO"   -> SafeGreen
        else      -> Color.Gray
    }
    Surface(
        color    = cor.copy(alpha = 0.20f),
        shape    = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        Text(
            text       = risco,
            color      = cor,
            fontSize   = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier   = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}