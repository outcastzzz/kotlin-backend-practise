package com.example.features.games.models

import kotlinx.serialization.Serializable

@Serializable
data class FetchGamesRequest(
    val searchQuery: String
)