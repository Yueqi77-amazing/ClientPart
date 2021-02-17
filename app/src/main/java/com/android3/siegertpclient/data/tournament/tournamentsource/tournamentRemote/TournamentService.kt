package com.android3.siegertpclient.data.tournament.tournamentsource.tournamentRemote

import com.android3.siegertpclient.data.game.Game
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.data.user.User
import com.android3.siegertpclient.utils.ParticipantFormUtil
import com.android3.siegertpclient.utils.TournamentTypesUtil
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface TournamentService {

    @POST("tournaments")//TODO change body
    fun createNewTournament(@Body tournament : Tournament,
                            @Header("currentUserId") ownUserId: String) : Response<Tournament>

    @GET("tournaments")
    fun getTournamentById(@Query("id") tourneyId : String,
                          @Header("currentUserId") ownUserId: String) : Response<Tournament>

    @GET("tournaments/{tournamentName}")
    fun getTournamentByName(@Path("tournamentName") tournamentName : String,
                            @Header("currentUserId") ownUserId: String) : Response<Tournament>

    @GET("tournaments/{tournamentName}/participants")
    fun getTournamentParticipants(@Path("tournamentName") tournamentName : String,
                                  @Header("currentUserId") ownUserId: String) : Response<Array<User>>

    @PUT("tournaments/{tournamentName}")
    fun updateTournamentDetailById(@Path("tournamentName") tournamentName : String,
                                   @Body participantForm: ParticipantFormUtil, adminId : String,
                                   tournamentTypes: TournamentTypesUtil, typeOfGame : String, location : String,
                                   registrationDeadline : Date, startTime : Date, endTime : Date,
                                   @Header("currentUserId") ownUserId: String) : Response<Tournament>

    @DELETE("tournaments/{tournamentName}")
    fun deleteTournament(@Path("tournamentName") tournamentName : String,
                         @Header("currentUserId") ownUserId: String) : Response<Boolean>

    @POST("tournaments/{tournamentName}")
    fun handleParticipation(@Path("tournamentName") tournamentName : String, @Body participate : Map<String, Boolean>,
                            @Header("currentUserId") ownUserId: String) : Response<Boolean>

    @GET("tournaments/{tournamentName}/games")
    fun getTournamentsGames(@Path("tournamentName") tournamentName : String,
                            @Header("currentUserId") ownUserId: String) : Response<Array<Game>>

    @POST("tournaments/{tournamentName}/games")
    fun createGames(@Path("tournamentName") tournamentName : String,
                    @Header("currentUserId") ownUserId: String) : Response<Array<Game>>
}