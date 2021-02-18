package com.android3.siegertpclient.data.userdummy.usersource.userRemote

import com.android3.siegertpclient.data.invitation.Invitation
import com.android3.siegertpclient.data.userdummy.TeamList
import com.android3.siegertpclient.data.userdummy.TournamentList
import com.android3.siegertpclient.data.userdummy.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface UserService {

    @POST("users")
    fun createNewUser(@Body username: String, surname: String, firstName: String, userId : String) : Response<User>

    @GET("users/{username}")
    fun getUserByUsername(@Path("username") username: String,
                          @Header("currentUserId") ownUserId: String) : Response<User>

    @GET("users")
    fun getUserById (@Query("id") id : String,
                     @Header("currentUserId") ownUserId: String) : Response<User>

    @GET("users/{username}/tournaments")
    fun getUsersTournaments (@Path("username") username: String,
                             @Header("currentUserId") ownUserId: String) : Response<TournamentList>

    @GET("users/{username}/teams")
    fun getUserTeams(@Path("username") username: String,
                     @Header("currentUserId") ownUserId: String) : Response<TeamList>

    @GET("users/{username}/invitations")
    fun getUserInvitations(@Path("username") username: String,
                           @Header("currentUserId") ownUserId: String) : Response<Array<Invitation>>

    @PUT("users/{username}")
    fun updateUserDetails(@Path("username") oldUsername: String,
                          @Body newUsername : String, @Body forename : String,
                          @Body surname : String, @Header("currentUserId") ownUserId: String)

}