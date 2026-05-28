package br.com.fiap.orbitwatch.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.orbitwatch.ui.theme.*

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SpaceBlack),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(horizontal = 36.dp)
        ) {
            // Ícone
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(AccentBlue, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Satellite,
                    contentDescription = null,
                    tint               = TextWhite,
                    modifier           = Modifier.size(50.dp)
                )
            }

            // Nome
            Text(
                text          = "OrbitWatch",
                fontSize      = 40.sp,
                fontWeight    = FontWeight.Bold,
                color         = TextWhite,
                letterSpacing = 2.sp
            )

            // Linha decorativa
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(3.dp)
                    .background(LightBlue, RoundedCornerShape(2.dp))
            )

            // Subtítulo
            Text(
                text       = "Monitoramento Ambiental por Satélite",
                fontSize   = 15.sp,
                fontWeight = FontWeight.Medium,
                color      = LightBlue,
                textAlign  = TextAlign.Center
            )

            // Descrição
            Text(
                text      = "Acompanhe eventos climáticos e ambientais " +
                        "em tempo real, identifique riscos e notifique " +
                        "a Defesa Civil com um toque.",
                fontSize  = 13.sp,
                color     = TextGray,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botão
            Button(
                onClick  = onNavigateToHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                shape  = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text          = "INICIAR MONITORAMENTO",
                    fontSize      = 13.sp,
                    fontWeight    = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color         = TextWhite
                )
            }
        }

        // Rodapé
        Text(
            text     = "Powered by NASA Earth Data  •  v1.0",
            fontSize = 11.sp,
            color    = TextGray,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050A18)
@Composable
fun SplashScreenPreview() {
    OrbitWatchTheme { SplashScreen(onNavigateToHome = {}) }
}