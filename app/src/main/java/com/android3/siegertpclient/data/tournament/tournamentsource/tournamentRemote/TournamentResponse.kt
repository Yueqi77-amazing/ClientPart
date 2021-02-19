package com.android3.siegertpclient.data.tournament.tournamentsource.tournamentRemote

import com.android3.siegertpclient.utils.ParticipantFormUtil
import com.android3.siegertpclient.utils.TournamentState
import com.android3.siegertpclient.utils.TournamentTypesUtil
import java.util.*

public data class TournamentResponse (
    val tournamentId : String,
    val tournamentDetail : TournamentDetail,
    val gameList : List<String>,
    val participantList : List<String>,
    val tournamentName : String,
    val maxParticipantNumber : Int,
    val type : String,
    val currentState : TournamentState,
    val open : Boolean,
    val leagueTable: LeagueTable
)

data class TournamentDetail (
    val participantForm : ParticipantFormUtil,
    val adminId : String,
    val tournamentTypes : TournamentTypesUtil,
    val typeOfGame : String,
    val location : String,
    val registrationDeadline : Date,
    val startTime : Date,
    val endTime : Date
)