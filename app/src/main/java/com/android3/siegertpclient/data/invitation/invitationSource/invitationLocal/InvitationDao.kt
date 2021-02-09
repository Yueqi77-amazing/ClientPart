package com.android3.siegertpclient.data.invitation.invitationSource.invitationLocal

import androidx.room.*

@Dao
interface InvitationDao {
    @Insert
    fun insertInvitation(vararg invitations: Invitation?)

    @Update
    fun updateInvitation(vararg invitations: Invitation?)

    @Delete
    fun deleteInvitation(vararg invitations: Invitation?)

    @Query("DELETE FROM Invitation")
    fun deleteAllInvitations()

    @get:Query("SELECT * FROM Invitation ORDER BY invitationId DESC")
    val allInvitations: List<Invitation?>?

    @Query("SELECT * FROM Invitation WHERE sender_name = :name")
    fun findInvitationBySenderName(name: String?): Invitation?
}