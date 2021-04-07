package com.android3.siegertpclient.data.invitation.invitationsource

import android.content.Context
import android.util.Log
import com.android3.siegertpclient.data.invitation.Invitation
import com.android3.siegertpclient.data.invitation.invitationsource.invitationRemote.InvitationRemoteDataSource
import com.android3.siegertpclient.data.payload.ApiResponse
import com.android3.siegertpclient.utils.LocalCache
import com.android3.siegertpclient.utils.PreferencesProvider
import java.time.LocalDate

/**
 * Invitation repository that calls the remote and local data source.
 */
class InvitationRepo(private val context: Context) {
    private val invitationRemoteDataSource = InvitationRemoteDataSource()
    private var localData = PreferencesProvider(context)

    suspend fun inviteUser(recipientUsername: String): ApiResponse? {
        val currentTournament = localData.getCurrentTournament()!!

        val recipientIdResponse = invitationRemoteDataSource.getRecipientIdByUsername(
            recipientUsername,
            LocalCache.getBearerToken(context)!!
        )

        if (recipientIdResponse.isSuccessful) {
            val recipientId = recipientIdResponse.body()?.get("userId")

            val sendInvitation = invitationRemoteDataSource.createInvitation(LocalCache.getCurrentUserId(context)!!,
                recipientId!!,
                currentTournament.tournamentId,
                currentTournament.tournamentDetail.participantForm,
                LocalCache.getBearerToken(context)!!
            )
            if(sendInvitation.isSuccessful) {
                return sendInvitation.body()!!
            }
        }
        return null
    }

    suspend fun inviteTeam(recipientTeamName: String): ApiResponse? {
        val currentTournament = localData.getCurrentTournament()!!

        val recipientIdResponse = invitationRemoteDataSource.getRecipientIdByUsername(
            recipientTeamName,
            LocalCache.getBearerToken(context)!!
        )

        if (recipientIdResponse.isSuccessful) {
            val recipientId = recipientIdResponse.body()?.get("teamId")

            val sendInvitation = invitationRemoteDataSource.createInvitation(LocalCache.getCurrentUserId(context)!!,
                recipientId!!,
                currentTournament.tournamentId,
                currentTournament.tournamentDetail.participantForm,
                LocalCache.getBearerToken(context)!!
            )
            if(sendInvitation.isSuccessful) {
                return sendInvitation.body()!!
            }
        }
        return null
    }

    suspend fun handleInvitationAcceptation(invitationID: String, accept: Boolean): ApiResponse? {
        val response = invitationRemoteDataSource.handleInvitationAcceptation(
            invitationID,
            accept,
            LocalCache.getBearerToken(context)!!
        )
        if (response.isSuccessful) {
            return response.body()!!
        }
        return null
    }

    fun getCurrentInvitationList() : List<Invitation> {
        return localData.getCurrentInvitationList()
    }
}