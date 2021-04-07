package com.android3.siegertpclient.data.team.teamsource

import android.content.Context
import com.android3.siegertpclient.data.invitation.Invitation
import com.android3.siegertpclient.data.payload.ApiResponse
import com.android3.siegertpclient.data.team.Team
import com.android3.siegertpclient.data.team.teamsource.teamRemote.TeamRemoteDataSource
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.data.user.User
import com.android3.siegertpclient.utils.Constants.Companion.KEY_TEAM_ID
import com.android3.siegertpclient.utils.Constants.Companion.KEY_TEAM_NAME
import com.android3.siegertpclient.utils.LocalCache
import com.android3.siegertpclient.utils.PreferencesProvider

class TeamRepo(private val context: Context) {

    private val teamRemoteDataSource = TeamRemoteDataSource()
    private var localData = PreferencesProvider(context)

    suspend fun createNewTeam(name : String, password : String) : Team? {
        val response =
            teamRemoteDataSource.createNewTeam(LocalCache.getCurrentUserId(context)!!, name, password, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            localData.putCurrentTeam(response.body()!!)
            localData.putString(KEY_TEAM_NAME, response.body()!!.teamName)
            localData.putString(KEY_TEAM_ID, response.body()!!.teamId)
            return response.body()!!
        }
        return null
    }

    suspend fun getTeamByName(teamName : String) : Team? {
        val response =
            teamRemoteDataSource.getTeamByName(teamName, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            localData.putCurrentTeam(response.body()!!)
            return response.body()!!
        }
        return null
    }

    suspend fun getTeamById(teamId : String) : Team? {
        val response =
            teamRemoteDataSource.getTeamById(teamId, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            localData.putCurrentTeam(response.body()!!)
            return response.body()!!
        }
        return null
    }

    suspend fun getTeamTournaments() : List<Tournament>? {
        val response =
            teamRemoteDataSource.getTeamTournaments(LocalCache.getBearerToken(context)!!, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            return response.body()!!
        }
        return null
    }

    suspend fun getTeamInvitations() : List<Invitation>? {
        val response =
            teamRemoteDataSource.getTeamInvitations(LocalCache.getBearerToken(context)!!, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            return response.body()!!
        }
        return null
    }

    suspend fun deleteTeam() : ApiResponse? {
        val response =
            teamRemoteDataSource.deleteTeam(LocalCache.getCurrentTeamName(context)!!, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            return response.body()!!
        }
        return null
    }

    suspend fun getTeamMembers() : List<User>? {
        val response = teamRemoteDataSource.getTeamMembers(LocalCache.getCurrentTeamName(context)!!, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            return response.body()!!
        }
        return null
    }

    suspend fun kickTeamMember(teamName: String, memberId : String) : ApiResponse? {
        val response =
            teamRemoteDataSource.kickTeamMember(teamName, memberId, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            return response.body()!!
        }
        return null
    }

    suspend fun joinTeam(teamName : String, password : String) : ApiResponse? {
        val response =
            teamRemoteDataSource.joinTeam(teamName, password, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            return response.body()!!
        }
        return null
    }

    suspend fun quitTeam(teamName : String) : ApiResponse? {
        val response =
            teamRemoteDataSource.quitTeam(teamName, LocalCache.getBearerToken(context)!!)
        if (response.isSuccessful) {
            return response.body()!!
        }
        return null
    }

    fun getCurrentTeams() : Team? {
        return localData.getCurrentTeam()
    }

    fun getCurrentTeamsList() : List<Team> {
        return localData.getCurrentTeamList()
    }
}