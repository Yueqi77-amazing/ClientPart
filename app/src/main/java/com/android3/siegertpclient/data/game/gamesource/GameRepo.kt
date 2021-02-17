package com.android3.siegertpclient.data.game.gamesource

import com.android3.siegertpclient.data.game.Game
import com.android3.siegertpclient.data.game.gamesource.gameRemote.GameRemoteDataSource
import com.android3.siegertpclient.utils.RestClient

class GameRepo : IGameDataSource {

    private val restClient = RestClient()
    private val gameService = restClient.getGameService()

    private val gameRemote = GameRemoteDataSource(gameService)

    fun getGameById(tournamentName : String, gameId : String, token : String) : Game {
        return gameRemote.getGameById(tournamentName, gameId, token)
    }

    fun updateGameById(tournamentName : String, gameId : String, game : Game, token : String) : Game {
        return gameRemote.updateGameById(tournamentName, gameId, game, token)
    }

    fun deleteGame(tournamentName : String, gameId : String, token : String) {
        gameRemote.deleteGame(tournamentName, gameId, token)
    }

}