package br.com.fiap.orbitwatch.screens

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
            .background(NasaWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 36.dp)
        ) {
            // Bloco azul com ícone (estilo NASA meatball)
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(NasaDarkBlue, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Satellite,
                    contentDescription = null,
                    tint               = NasaWhite,
                    modifier           = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Nome
            Text(
                text          = "OrbitWatch",
                fontSize      = 38.sp,
                fontWeight    = FontWeight.Bold,
                color         = NasaTextPrimary,
                letterSpacing = 1.sp
            )

            // Linha azul decorativa
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(3.dp)
                    .background(NasaBlue, RoundedCornerShape(2.dp))
            )

            // Subtítulo
            Text(
                text      = "Monitoramento Ambiental por Satélite",
                fontSize  = 15.sp,
                fontWeight = FontWeight.Medium,
                color     = NasaTextSecond,
                textAlign = TextAlign.Center
            )

            // Descrição
            Text(
                text      = "Acompanhe eventos climáticos e ambientais " +
                        "em tempo real, identifique riscos e notifique " +
                        "a Defesa Civil com um toque.",
                fontSize  = 13.sp,
                color     = NasaSubtle,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botão NASA style
            Button(
                onClick  = onNavigateToHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NasaBlue),
                shape  = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text          = "INICIAR MONITORAMENTO",
                    fontSize      = 13.sp,
                    fontWeight    = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color         = NasaWhite
                )
            }
        }

        // Rodapé
        Column(
            modifier            = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 36.dp)
                    .background(NasaCardWhite)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text     = "Powered by NASA Earth Data  •  v1.0",
                fontSize = 11.sp,
                color    = NasaSubtle
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SplashScreenPreview() {
    OrbitWatchTheme { SplashScreen(onNavigateToHome = {}) }
}