package com.android3.siegertpclient.data.invitation.invitationsource.invitationRemote

import com.android3.siegertpclient.data.payload.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface InvitationService {
    @POST("invitations")
    suspend fun createInvitation(
        @Body invitation: Map<String, String>,
        @Header("Authorization") token: String
    ): Response<ApiResponse>

    @POST("invitations/{id}")
    suspend fun handleInvitationAcceptation(
        @Path("id") invitationId: String, @Body accept: Map<String, Boolean>,
        @Header("Authorization") token: String
    ): Response<ApiResponse>
}