package br.com.fiap.orbitwatch.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary             = LightBlue,
    onPrimary           = SpaceBlack,
    primaryContainer    = AccentBlue,
    onPrimaryContainer  = TextWhite,
    secondary           = AccentBlue,
    onSecondary         = TextWhite,
    background          = SpaceBlack,
    onBackground        = TextWhite,
    surface             = DeepNavy,
    onSurface           = TextWhite,
    surfaceVariant      = CardDark,
    onSurfaceVariant    = TextGray,
    error               = CriticalRed,
    onError             = Color.White,
    outline             = TextGray
)

@Composable
fun OrbitWatchTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography  = OrbitTypography,
        content     = content
    )
}