package br.com.fiap.orbitwatch.data

import br.com.fiap.orbitwatch.model.EventoAmbiental

object EventoRepository {

    val eventos: List<EventoAmbiental> = listOf(
        EventoAmbiental(
            id = 1,
            nome = "Queimada na Amazônia",
            localizacao = "Pará, Brasil",
            descricao = "Foco de incêndio de grande extensão detectado por satélite na bacia amazônica, " +
                    "com risco de expansão para áreas de proteção ambiental.",
            risco = "CRÍTICO",
            temperatura = 48.5,
            impacto = "Destruição de biodiversidade, emissão massiva de CO₂ e impacto em comunidades indígenas.",
            status = "ATIVO",
            coordenadas = "-3.4653° S, 62.2159° O",
            ultimaAtualizacao = "26/05/2026 08:32"
        ),
        EventoAmbiental(
            id = 2,
            nome = "Tempestade no Atlântico Sul",
            localizacao = "Atlântico Sul",
            descricao = "Sistema de baixa pressão com ventos superiores a 120 km/h avançando em direção à costa brasileira.",
            risco = "ALTO",
            temperatura = 27.3,
            impacto = "Ondas de até 8 metros, risco de alagamento costeiro e interrupção de rotas marítimas.",
            status = "MONITORADO",
            coordenadas = "-30.0000° S, 40.0000° O",
            ultimaAtualizacao = "26/05/2026 06:15"
        ),
        EventoAmbiental(
            id = 3,
            nome = "Enchente em São Paulo",
            localizacao = "São Paulo, Brasil",
            descricao = "Chuvas acumuladas de 180mm em 24h causam inundações críticas em zonas residenciais e industriais.",
            risco = "CRÍTICO",
            temperatura = 22.1,
            impacto = "Desabrigados estimados em 4.200 pessoas; colapso de infraestrutura viária.",
            status = "ATIVO",
            coordenadas = "-23.5505° S, 46.6333° O",
            ultimaAtualizacao = "26/05/2026 09:00"
        ),
        EventoAmbiental(
            id = 4,
            nome = "Derretimento Polar Ártico",
            localizacao = "Ártico Central",
            descricao = "Extensão de gelo marinho 30% abaixo da média histórica. Aceleração detectada nos últimos 6 meses.",
            risco = "ALTO",
            temperatura = -2.8,
            impacto = "Elevação do nível do mar, alteração de correntes oceânicas globais.",
            status = "MONITORADO",
            coordenadas = "90.0000° N, 0.0000°",
            ultimaAtualizacao = "25/05/2026 23:50"
        ),
        EventoAmbiental(
            id = 5,
            nome = "Onda de Calor no Mediterrâneo",
            localizacao = "Sul da Europa",
            descricao = "Temperaturas recordes acima de 46°C registradas em Espanha, Itália e Grécia por 8 dias consecutivos.",
            risco = "MÉDIO",
            temperatura = 46.2,
            impacto = "Risco elevado de incêndios florestais e estresse hídrico na região.",
            status = "MONITORADO",
            coordenadas = "40.4168° N, 3.7038° O",
            ultimaAtualizacao = "25/05/2026 18:00"
        ),
        EventoAmbiental(
            id = 6,
            nome = "Desmatamento no Cerrado",
            localizacao = "Mato Grosso, Brasil",
            descricao = "Análise multitemporal revela perda de 12.400 km² de vegetação nativa no primeiro trimestre.",
            risco = "ALTO",
            temperatura = 38.9,
            impacto = "Comprometimento de nascentes, perda de habitat e emissões equivalentes a 200Mt de CO₂.",
            status = "ATIVO",
            coordenadas = "-12.6819° S, 56.9211° O",
            ultimaAtualizacao = "26/05/2026 07:40"
        ),
        EventoAmbiental(
            id = 7,
            nome = "Furacão no Caribe",
            localizacao = "Mar do Caribe",
            descricao = "Furacão categoria 4 com ventos sustentados de 220 km/h, projetado para atingir terra em 36 horas.",
            risco = "CRÍTICO",
            temperatura = 30.5,
            impacto = "Ameaça direta a 5 ilhas, potencial de 200.000 desabrigados.",
            status = "ATIVO",
            coordenadas = "17.2500° N, 72.5000° O",
            ultimaAtualizacao = "26/05/2026 10:05"
        ),
        EventoAmbiental(
            id = 8,
            nome = "Seca no Nordeste Brasileiro",
            localizacao = "Nordeste, Brasil",
            descricao = "Déficit hídrico acumulado de 60% em reservatórios estratégicos. Polígono da seca ampliado.",
            risco = "MÉDIO",
            temperatura = 39.4,
            impacto = "Comprometimento do abastecimento para 3,2 milhões de pessoas.",
            status = "MONITORADO",
            coordenadas = "-7.1200° S, 36.7200° O",
            ultimaAtualizacao = "25/05/2026 14:30"
        ),
        EventoAmbiental(
            id = 9,
            nome = "Aumento do Nível do Mar em Bangladesh",
            localizacao = "Bangladesh, Sul da Ásia",
            descricao = "Elevação de 3,2mm acima da média anual detectada nos deltas do Ganges-Brahmaputra.",
            risco = "ALTO",
            temperatura = 31.7,
            impacto = "Salinização de terras agrícolas e deslocamento potencial de populações costeiras.",
            status = "MONITORADO",
            coordenadas = "23.6850° N, 90.3563° L",
            ultimaAtualizacao = "25/05/2026 20:00"
        ),
        EventoAmbiental(
            id = 10,
            nome = "Tempestade de Areia no Saara",
            localizacao = "Norte da África",
            descricao = "Haboob de grande escala com visibilidade próxima de zero em raio de 600 km.",
            risco = "BAIXO",
            temperatura = 44.1,
            impacto = "Interrupção de voos, impacto na qualidade do ar em países vizinhos.",
            status = "CONTROLADO",
            coordenadas = "23.4162° N, 25.6628° L",
            ultimaAtualizacao = "25/05/2026 11:00"
        ),
        EventoAmbiental(
            id = 11,
            nome = "Deslizamento em Serra Gaúcha",
            localizacao = "Rio Grande do Sul, Brasil",
            descricao = "Deslizamento de massa após 72h de chuva contínua atinge área urbana de três municípios.",
            risco = "CRÍTICO",
            temperatura = 17.3,
            impacto = "Vítimas confirmadas, destruição de infraestrutura e isolamento de comunidades.",
            status = "ATIVO",
            coordenadas = "-29.1678° S, 51.1794° O",
            ultimaAtualizacao = "26/05/2026 09:45"
        ),
        EventoAmbiental(
            id = 12,
            nome = "Branqueamento de Coral na Austrália",
            localizacao = "Grande Barreira de Coral, Austrália",
            descricao = "Evento de branqueamento em massa afeta 65% dos recifes monitorados na temporada atual.",
            risco = "MÉDIO",
            temperatura = 29.8,
            impacto = "Colapso de ecossistemas marinhos e impacto no turismo estimado em AUD 6 bilhões.",
            status = "MONITORADO",
            coordenadas = "-18.2871° S, 147.6992° L",
            ultimaAtualizacao = "25/05/2026 16:20"
        )
    )

    fun getById(id: Int): EventoAmbiental? = eventos.find { it.id == id }

    fun getEstatisticas(): Map<String, Any> {
        val total = eventos.size
        val criticos = eventos.count { it.risco == "CRÍTICO" }
        val tempMedia = eventos.map { it.temperatura }.average()
        val regioes = eventos.map { it.localizacao.split(",").last().trim() }.distinct().size
        return mapOf(
            "total" to total,
            "criticos" to criticos,
            "tempMedia" to tempMedia,
            "regioes" to regioes
        )
    }
}