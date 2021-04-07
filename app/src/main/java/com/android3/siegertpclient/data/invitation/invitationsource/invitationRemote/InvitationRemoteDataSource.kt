package com.android3.siegertpclient.data.invitation.invitationsource.invitationRemote

import android.util.Log
import com.android3.siegertpclient.data.payload.ApiResponse
import com.android3.siegertpclient.utils.RestClient
import retrofit2.Response

class InvitationRemoteDataSource() {
    suspend fun createInvitation(
        senderId: String, recipientId: String, tournamentId: String,
        participantForm: String, token: String
    ): Response<ApiResponse> {
        val invitation = hashMapOf<String, String>()
        invitation["senderId"] = senderId
        invitation["recipientId"] = recipientId
        invitation["tournamentId"] = tournamentId
        invitation["participantForm"] = participantForm

        return RestClient.invitationService.createInvitation(invitation, token)
    }

    suspend fun handleInvitationAcceptation(
        invitationId: String,
        acceptB: Boolean,
        token: String
    ): Response<ApiResponse> {
        val accept = hashMapOf<String, Boolean>()
        accept["accept"] = acceptB
        return RestClient.invitationService.handleInvitationAcceptation(invitationId, accept, token)
    }

    suspend fun getRecipientIdByUsername(
        recipientUsername: String,
        token: String
    ): Response<Map<String, String>> {
        return RestClient.invitationService.getRecipientIdByUsername(recipientUsername, token)
    }

    suspend fun getRecipientIdByTeamName(
        recipientTeamname: String,
        token: String
    ): Response<Map<String, String>> {
        return RestClient.invitationService.getRecipientIdByUsername(recipientTeamname, token)
    }
}