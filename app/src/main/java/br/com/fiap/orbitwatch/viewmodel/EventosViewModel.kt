package br.com.fiap.orbitwatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.orbitwatch.data.EventoRepository
import br.com.fiap.orbitwatch.model.EventoAmbiental
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EventosViewModel : ViewModel() {

    private val _eventos = MutableStateFlow(EventoRepository.eventos)
    val eventos: StateFlow<List<EventoAmbiental>> = _eventos.asStateFlow()

    private val _filtroRisco = MutableStateFlow("TODOS")
    val filtroRisco: StateFlow<String> = _filtroRisco.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _showSnackbar = MutableStateFlow(false)
    val showSnackbar: StateFlow<Boolean> = _showSnackbar.asStateFlow()

    private val _eventosNotificados = MutableStateFlow<List<EventoAmbiental>>(emptyList())
    val eventosNotificados: StateFlow<List<EventoAmbiental>> = _eventosNotificados.asStateFlow()

    val eventosFiltrados: StateFlow<List<EventoAmbiental>> =
        combine(_eventos, _filtroRisco, _eventosNotificados) { lista, filtro, notificados ->
            when (filtro) {
                "TODOS"       -> lista
                "NOTIFICADOS" -> notificados
                else          -> lista.filter { it.risco == filtro }
            }
        }.stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = EventoRepository.eventos
        )

    fun setFiltro(filtro: String) {
        _filtroRisco.value = filtro
    }

    fun atualizarDados() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1_500L)
            _eventos.value   = EventoRepository.eventos.shuffled()
            _isLoading.value = false
            _showSnackbar.value = true
        }
    }

    fun onSnackbarDismissed() {
        _showSnackbar.value = false
    }

    fun notificarDefesaCivil(id: Int) {
        val evento = _eventos.value.find { it.id == id } ?: return
        if (_eventosNotificados.value.none { it.id == id }) {
            _eventosNotificados.value = _eventosNotificados.value + evento
        }
        _eventos.value = _eventos.value.map {
            if (it.id == id) it.copy(status = "MONITORADO") else it
        }
    }

    fun isNotificado(id: Int): Boolean =
        _eventosNotificados.value.any { it.id == id }
}