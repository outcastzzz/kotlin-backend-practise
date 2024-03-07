package com.example.features.games

import com.example.database.games.Games
import com.example.database.games.mapToCreateGameResponse
import com.example.database.games.mapToGameDTO
import com.example.features.games.models.CreateGameRequest
import com.example.features.games.models.FetchGamesRequest
import com.example.utils.TokenCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GamesController(private val call: ApplicationCall) {

    suspend fun performSearch() {
        val request = call.receive<FetchGamesRequest>()
        val token = call.request.headers["Bearer-Authorization"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokenAdmin(token.orEmpty())) {
            if (request.searchQuery.isBlank()) {
                call.respond(Games.fetchAll())
            } else {
                call.respond(Games.fetchAll().filter { it.title.contains(request.searchQuery, ignoreCase = true) })
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun createGame() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenAdmin(token.orEmpty())) {
            val request = call.receive<CreateGameRequest>()
            val game = request.mapToGameDTO()
            Games.insert(game)
            call.respond(game.mapToCreateGameResponse())
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }
}