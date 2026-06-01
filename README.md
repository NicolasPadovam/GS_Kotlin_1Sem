# 🛰️ OrbitWatch — Monitoramento Ambiental por Satélite

> **Global Solution 2025 — Indústria Espacial | FIAP**  
> Disciplina: Mobile Application Development with Kotlin  

---

## 📋 Sobre o Projeto

**OrbitWatch** é um aplicativo Android de monitoramento ambiental por satélite, desenvolvido como solução mobile para a Global Solution com tema **Indústria Espacial**.

O app simula uma central de monitoramento que recebe dados de satélites reais (GOES-18, Landsat-9, Terra/MODIS, Aqua/MODIS) para detectar e classificar eventos climáticos e ambientais em escala global — queimadas, furacões, enchentes, secas, desmatamento e outros fenômenos críticos. Cada evento é georreferenciado com coordenadas GPS, temperatura registrada pelo satélite, nível de risco e status operacional.

A funcionalidade central do app é o **envio de notificações à Defesa Civil**: ao entrar nos detalhes de qualquer evento, o usuário pode acionar um alerta oficial. O evento notificado muda de status e passa a aparecer no filtro exclusivo "NOTIFICADOS" na lista de eventos, simulando um fluxo real de resposta a emergências ambientais.

---

## 👥 Integrantes

| Nome | RM |
|------|----|
| Enzo Biloschycki Magalhães Cesar | RM 556736 |
| Nicolas Varella Barros Padovam | RM 556586 |
| Rebeca Berbert Barcelos | RM 556683 |

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão |
|---|---|
| Kotlin | 1.9.x |
| Jetpack Compose BOM | 2024.02.00 |
| Material Design 3 | via Compose BOM |
| Navigation Compose | 2.7.7 |
| ViewModel + StateFlow | Lifecycle 2.7.0 |
| Coroutines Android | 1.7.3 |
| minSdk | 26 (Android 8.0) |
| targetSdk | 34 (Android 14) |

---

## 📱 Telas do Aplicativo

O app conta com **5 telas** conectadas via Navigation Compose:

### 1. 🌌 Splash Screen
Tela de abertura com identidade visual do OrbitWatch. Exibe o ícone de satélite, o nome do app, o subtítulo "Monitoramento Ambiental por Satélite" e uma descrição resumida da proposta. O usuário inicia a sessão pelo botão **"INICIAR MONITORAMENTO"**, com o rodapé indicando a fonte de dados: `Powered by NASA Earth Data`.

```
Splash ──(botão)──► Home
```

---

### 2. 🏠 Home Screen — Painel de Controle
Tela principal com resumo em tempo real dos dados monitorados. Exibe um card com três métricas calculadas diretamente do repositório: total de eventos ativos, quantidade de eventos críticos e número de regiões distintas monitoradas. Dois botões direcionam para as seções principais do app.

```
Home ──► Eventos
Home ──► Estatísticas
```

---

### 3. 📡 Eventos Screen — Lista de Eventos
Lista completa dos eventos ambientais detectados por satélite, exibida em `LazyColumn` com `EventoCard` para cada item. Inclui:

- **Filtros por nível de risco** em `Row` scrollável horizontal: `TODOS`, `CRÍTICO`, `ALTO`, `MÉDIO`, `BAIXO` e **`NOTIFICADOS`** — este último exibe apenas os eventos para os quais o usuário já acionou a Defesa Civil, com contador dinâmico no chip.
- **FAB de atualização** com `CircularProgressIndicator` durante o loading e `Snackbar` de confirmação ao concluir.
- **Animação `fadeIn`/`fadeOut`** na lista durante a atualização dos dados.
- Estado vazio específico para o filtro `NOTIFICADOS`: exibe "Nenhuma notificação enviada ainda".

```
Eventos ──(clique no card)──► Detalhes
```

---

### 4. 🔍 Detalhes Screen — Detalhamento do Evento
Tela de detalhamento de um evento específico, recebendo o `eventoId` via argumento de navegação. Organizada em três seções:

- **Dados Ambientais:** temperatura registrada, descrição completa do fenômeno e impacto estimado.
- **Coordenadas:** posição GPS exata e horário da última atualização satelital.
- **Ação:** botão **"ENVIAR NOTIFICAÇÃO À DEFESA CIVIL"** em vermelho. Ao ser acionado, o evento muda de status para `MONITORADO`, o botão passa para verde com o texto "NOTIFICAÇÃO ENVIADA" e fica desabilitado. O evento também passa a aparecer no filtro `NOTIFICADOS` na tela anterior.

---

### 5. 📊 Estatísticas Screen — Visão Global
Painel analítico com métricas agregadas e dados orbitais detalhados por evento:

- **Grid de métricas globais:** Total de Eventos, Críticos e Regiões Monitoradas.
- **Índice de Criticidade:** `LinearProgressIndicator` mostrando o percentual de eventos em estado crítico sobre o total.
- **Cards por evento:** para cada um dos 12 eventos, exibe os dados orbitais (satélite responsável pela captura, horário da última leitura, precisão do sensor) e indicadores climáticos (umidade, velocidade do vento, índice de chuva).

---

## 🗺️ Fluxo de Navegação

```
┌──────────────┐
│  Splash      │
└──────┬───────┘
       │ botão "INICIAR MONITORAMENTO"
┌──────▼───────┐
│     Home     │
└──────┬───────┘
       │
  ┌────┴────────────────┐
  │                     │
┌─▼────────┐    ┌───────▼──────┐
│  Eventos │    │ Estatísticas │
└─┬────────┘    └──────────────┘
  │ clique no card
┌─▼────────┐
│ Detalhes │
└──────────┘
```

---

## 🏗️ Arquitetura

O projeto segue o padrão **MVVM** com separação clara de responsabilidades entre camadas:

```
br.com.fiap.orbitwatch/
├── model/
│   └── EventoAmbiental.kt           # Data class do domínio
├── data/
│   └── EventoAmbientalRepository.kt # 12 eventos mockados + métodos de consulta
├── navigation/
│   ├── NavRoutes.kt                  # Rotas tipadas (sealed class)
│   └── AppNavigation.kt             # NavHost com todos os destinations
├── viewmodel/
│   ├── EventosViewModel.kt           # Estado de eventos, filtros e notificações
│   └── EstatisticaViewModel.kt      # Estado de métricas e loading
├── screens/
│   ├── SplashScreen.kt
│   ├── HomeScreen.kt
│   ├── EventosScreen.kt
│   ├── DetalhesScreen.kt
│   └── EstatisticaScreen.kt
├── components/
│   ├── EventoCard.kt                # Card reutilizável de evento
│   ├── RiscoChip.kt                 # Chip colorido por nível de risco
│   ├── StatCard.kt                  # Card de métrica
│   └── OrbitTopBar.kt               # TopAppBar customizada
└── ui/theme/
    ├── Color.kt                     # Paleta espacial
    ├── Theme.kt                     # Tema Material 3
    └── Type.kt                      # Tipografia
```

---

## 🎨 Identidade Visual

O app utiliza um **dark theme espacial** com a seguinte paleta:

| Token | Cor | Uso |
|---|---|---|
| `SpaceBlack` | `#050A18` | Background principal |
| `CardDark` | `#0F2337` | Superfície dos cards |
| `AccentBlue` | `#1565C0` | Botões e FAB |
| `LightBlue` | `#4FC3F7` | Labels e ícones de destaque |
| `CriticalRed` | `#E53935` | Risco CRÍTICO e botão de notificação |
| `WarningAmber` | `#FFB300` | Risco ALTO |
| `SafeGreen` | `#43A047` | Risco BAIXO e notificação confirmada |

---

## 📦 Eventos Monitorados

O app exibe **12 eventos ambientais** com dados detalhados, classificados por nível de risco:

| # | Evento | Localização | Satélite | Risco | Status |
|---|--------|-------------|----------|-------|--------|
| 1 | Queimada na Amazônia | Pará, Brasil | GOES-18 | 🔴 CRÍTICO | ATIVO |
| 2 | Tempestade no Atlântico Sul | Atlântico Sul | Landsat-9 | 🟠 ALTO | MONITORADO |
| 3 | Enchente em São Paulo | São Paulo, Brasil | GOES-18 | 🔴 CRÍTICO | ATIVO |
| 4 | Derretimento Polar Ártico | Ártico Central | Landsat-9 | 🟠 ALTO | MONITORADO |
| 5 | Onda de Calor no Mediterrâneo | Sul da Europa | Terra/MODIS | 🟡 MÉDIO | MONITORADO |
| 6 | Desmatamento no Cerrado | Mato Grosso, Brasil | Landsat-9 | 🟠 ALTO | ATIVO |
| 7 | Furacão no Caribe | Mar do Caribe | GOES-18 | 🔴 CRÍTICO | ATIVO |
| 8 | Seca no Nordeste Brasileiro | Nordeste, Brasil | Terra/MODIS | 🟡 MÉDIO | MONITORADO |
| 9 | Aumento do Nível do Mar | Bangladesh | Landsat-9 | 🟠 ALTO | MONITORADO |
| 10 | Tempestade de Areia no Saara | Norte da África | Aqua/MODIS | 🟢 BAIXO | CONTROLADO |
| 11 | Deslizamento em Serra Gaúcha | Rio Grande do Sul, Brasil | GOES-18 | 🔴 CRÍTICO | ATIVO |
| 12 | Branqueamento de Coral | Grande Barreira de Coral, Austrália | Terra/MODIS | 🟡 MÉDIO | MONITORADO |


## 📲 Telas da aplicação



