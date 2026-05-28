package br.com.fiap.orbitwatch.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.fiap.orbitwatch.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrbitTopBar(
    titulo: String,
    mostrarVoltar: Boolean  = false,
    acaoIcone: ImageVector? = Icons.Default.Satellite,
    onVoltar: () -> Unit    = {},
    onAcaoClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text  = titulo,
                style = MaterialTheme.typography.titleLarge,
                color = TextWhite
            )
        },
        navigationIcon = {
            if (mostrarVoltar) {
                IconButton(onClick = onVoltar) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = LightBlue)
                }
            }
        },
        actions = {
            acaoIcone?.let {
                Icon(it, contentDescription = null, tint = LightBlue)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DeepNavy
        )
    )
}