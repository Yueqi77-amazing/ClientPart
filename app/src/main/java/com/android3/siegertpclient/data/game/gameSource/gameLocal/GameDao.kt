package com.android3.siegertpclient.data.game.gameSource.gameLocal

import androidx.room.*
import com.android3.siegertpclient.data.tournament.tournamentSource.tournamentLocal.Tournament

@Dao
interface GameDao {
    @Insert
    fun insertGame(vararg games: Game?)

    @Update
    fun updateGame(vararg games: Game?)

    @Delete
    fun deleteGame(vararg games: Game?)

    @Query("DELETE FROM Game")
    fun deleteAllGames()

    @get:Query("SELECT * FROM Game ORDER BY gameId DESC")
    val allGames: List<Tournament?>?

    @Query("SELECT * FROM Game WHERE gameId = :id")
    fun findGameById(id: Int): Tournament?
}