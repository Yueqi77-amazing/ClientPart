package com.android3.siegertpclient.utils

class Constants {
    companion object {
        //App REST API base URL
        const val BASE_URL = "https://sieger-teamthree.herokuapp.com/"

        //SharedPreference keys
        //Token & Session
        const val KEY_TOKEN = "KEY_TOKEN"
        const val IS_LOGGED_IN = "IS_LOGGED_IN"

        //User
        const val KEY_USER = "KEY_USER"
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_USERNAME = "KEY_USERNAME"
        const val KEY_FORENAME = "KEY_FORNAME"
        const val KEY_SURNAME = "KEY_SURNAME"

        //Tournament
        const val KEY_CURRENT_TOURNAMENT = "KEY_CURRENT_TOURNAMENT"
        const val KEY_TOURNAMENT_ID = "KEY_TOURNAMENT_ID"
        const val KEY_TOURNAMENT_NAME = "KEY_TOURNAMENT_NAME"

        //Team
        const val KEY_CURRENT_TEAM = "KEY_CURRENT_TEAM"
        const val KEY_TEAM_ID = "KEY_TEAM_ID"
        const val KEY_TEAM_NAME = "KEY_TEAM_NAME"
        const val DELETE = "DELETE"

        //Invitation
        const val KEY_INVITATION_ID = "KEY_INVITATION_ID"

        //ENUM
        const val MATCH_TYPE = "Match Type"
        const val KNOCK_OUT = "KnockOut"
        const val LEAGUE = "League"
        const val KNOCK_OUT_WITH_GROUP = "KnockOutWithGroup"
        const val TOURNAMENT_TYPE = "Tournament Type"
        const val PRIVATE = "PRIVATE"
        const val PARTICIPANT_FORM = "Participant Form"
        const val SINGLE = "SINGLE"
        const val TEAM = "TEAM"
        const val CHANGE_LANGUAGE = "Change Language"
        const val ENGLISH = "ENGLISH"

        //List
        const val KEY_USER_LIST = "KEY_USER_LIST"
        const val KEY_TEAM_LIST = "KEY_TEAM_LIST"
        const val KEY_TOURNAMENT_LIST = "KEY_TOURNAMENT_LIST"
        const val KEY_GAME_LIST = "KEY_GAME_LIST"
        const val KEY_INVITATION_LIST = "KEY_INVITATION_LIST"
    }
}