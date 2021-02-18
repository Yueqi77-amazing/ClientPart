package com.android3.siegertpclient.data.userdummy.usersource.userLocal

import androidx.room.*
import com.android3.siegertpclient.data.userdummy.NotificationList
import com.android3.siegertpclient.data.userdummy.TeamList
import com.android3.siegertpclient.data.userdummy.TournamentList
import com.android3.siegertpclient.data.userdummy.User


@Dao
interface UserDao {
    @Insert
    fun insertUsers(vararg users: User?)

    @Update
    fun updateUsers(vararg users: User?)

    @Delete
    fun deleteUsers(vararg users: User?)

    @Query("DELETE From User")
    fun deleteAllUsers()

    @get:Query("SELECT * FROM User")
    val allUsers: List<User?>?

    @Query("SELECT * FROM User WHERE id = :id")
    fun findUserById(id: Int): User?

    @Query("SELECT id FROM User")
    fun getAllUser(): List<String>

    @Query("SELECT team_list FROM User WHERE id = :id")
    fun getAllTeams(id: Int): TeamList

    @Query("SELECT tournament_list FROM User WHERE id = :id")
    fun getAllTournaments(id: Int): TournamentList


    @Query("SELECT notification_list FROM User WHERE id = :id")
    fun getAllNotifications(id: Int): NotificationList
}