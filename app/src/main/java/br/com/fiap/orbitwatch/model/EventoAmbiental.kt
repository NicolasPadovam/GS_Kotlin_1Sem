package br.com.fiap.orbitwatch.model

data class EventoAmbiental(
    val id: Int,
    val nome: String,
    val localizacao: String,
    val descricao: String,
    val risco: String,            // "CRÍTICO", "ALTO", "MÉDIO", "BAIXO"
    val temperatura: Double,
    val impacto: String,
    val status: String,           // "ATIVO", "MONITORADO", "CONTROLADO"
    val coordenadas: String,
    val ultimaAtualizacao: String
)