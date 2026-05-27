// br/com/fiap/orbitwatch/ui/theme/Theme.kt

package br.com.fiap.orbitwatch.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val NasaColorScheme = lightColorScheme(
    primary             = NasaBlue,
    onPrimary           = NasaWhite,
    primaryContainer    = NasaDarkBlue,
    onPrimaryContainer  = NasaWhite,
    secondary           = NasaDarkBlue,
    onSecondary         = NasaWhite,
    background          = NasaWhite,
    onBackground        = NasaTextPrimary,
    surface             = NasaWhite,
    onSurface           = NasaTextPrimary,
    surfaceVariant      = NasaLightGray,
    onSurfaceVariant    = NasaTextSecond,
    error               = CriticalRed,
    onError             = NasaWhite,
    outline             = NasaSubtle
)

@Composable
fun OrbitWatchTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NasaColorScheme,
        typography  = OrbitTypography,
        content     = content
    )
}