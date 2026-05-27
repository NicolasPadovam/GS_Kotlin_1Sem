package br.com.fiap.orbitwatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.orbitwatch.data.EventoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EstatisticasUiState(
    val totalEventos: Int     = 0,
    val eventosCriticos: Int  = 0,
    val totalRegioes: Int     = 0,
    val temperaturaMedia: Double = 0.0,
    val percentualCritico: Float = 0f,
    val isLoading: Boolean    = true
)

class EstatisticasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EstatisticasUiState())
    val uiState: StateFlow<EstatisticasUiState> = _uiState.asStateFlow()

    init {
        carregarEstatisticas()
    }

    private fun carregarEstatisticas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(1_200L) // simula loading inicial

            val stats = EventoRepository.getEstatisticas()
            val total    = stats["total"] as Int
            val criticos = stats["criticos"] as Int

            _uiState.value = EstatisticasUiState(
                totalEventos       = total,
                eventosCriticos    = criticos,
                totalRegioes       = stats["regioes"] as Int,
                temperaturaMedia   = stats["tempMedia"] as Double,
                percentualCritico  = if (total > 0) criticos.toFloat() / total else 0f,
                isLoading          = false
            )
        }
    }
}