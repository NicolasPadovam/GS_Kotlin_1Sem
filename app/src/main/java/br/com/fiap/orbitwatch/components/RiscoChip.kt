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
import br.com.fiap.orbitwatch.ui.theme.CriticalRed
import br.com.fiap.orbitwatch.ui.theme.OrbitBlue
import br.com.fiap.orbitwatch.ui.theme.SafeGreen
import br.com.fiap.orbitwatch.ui.theme.WarningAmber

@Composable
fun RiscoChip(risco: String, modifier: Modifier = Modifier) {
    val (bgColor, label) = when (risco) {
        "CRÍTICO"  -> Pair(CriticalRed, "CRÍTICO")
        "ALTO"     -> Pair(WarningAmber, "ALTO")
        "MÉDIO"    -> Pair(OrbitBlue, "MÉDIO")
        "BAIXO"    -> Pair(SafeGreen, "BAIXO")
        else       -> Pair(Color.Gray, risco)
    }

    Surface(
        color  = bgColor.copy(alpha = 0.20f),
        shape  = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        Text(
            text  = label,
            color = bgColor,
            fontSize   = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier   = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}