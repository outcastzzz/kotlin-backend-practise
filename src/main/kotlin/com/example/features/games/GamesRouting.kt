package com.example.features.games

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGameRouting() {

    routing {
        post("/games/create") {
            GamesController(call).createGame()
        }

        post("/games/search") {
            GamesController(call).performSearch()
        }
    }
}