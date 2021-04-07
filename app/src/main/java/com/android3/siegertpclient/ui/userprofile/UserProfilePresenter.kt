package com.android3.siegertpclient.ui.userprofile

import android.content.Context
import android.util.Log
import com.android3.siegertpclient.data.team.teamsource.TeamRepo
import com.android3.siegertpclient.data.tournament.tournamentsource.TournamentRepo
import com.android3.siegertpclient.data.user.usersource.UserRepo
import com.android3.siegertpclient.ui.base.BasePresenter
import com.android3.siegertpclient.utils.Constants.Companion.KEY_TEAM_ID
import com.android3.siegertpclient.utils.Constants.Companion.KEY_TEAM_NAME
import com.android3.siegertpclient.utils.Constants.Companion.KEY_TOURNAMENT_ID
import com.android3.siegertpclient.utils.Constants.Companion.KEY_TOURNAMENT_NAME
import com.android3.siegertpclient.utils.OnlineChecker
import com.android3.siegertpclient.utils.PreferencesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfilePresenter(private val context: Context) :
    BasePresenter<UserProfileContract.IUserProfileView>(),
    UserProfileContract.IUserProfilePresenter {

    private var onlineChecker = OnlineChecker(context)

    private var userRepo = UserRepo(context)
    private var teamRepo = TeamRepo(context)
    private var tournamentRepo = TournamentRepo(context)

    private var localData = PreferencesProvider(context)

    override fun userTeamClicked(position: Int) {
        val savedTeam = teamRepo.getCurrentTeamsList()
        val chosenTeam = savedTeam[position]
        localData.putCurrentTeam(chosenTeam)
        localData.putString(KEY_TEAM_NAME, chosenTeam.teamName)
        localData.putString(KEY_TEAM_ID, chosenTeam.teamId)
        view?.navigateToTeamActivity()
    }

    override fun userTournamentClicked(position: Int) {
        val savedTournaments = tournamentRepo.getCurrentTournamentList()
        val chosenTournament = savedTournaments[position]
        localData.putCurrentTournament(chosenTournament)
        localData.putString(KEY_TOURNAMENT_NAME, chosenTournament.tournamentName)
        localData.putString(KEY_TOURNAMENT_ID, chosenTournament.tournamentId)
        view?.navigateToTournamentActivity()
    }

    override fun onSettingBtnClicked() {
        view?.navigateToSettingActivity()
    }

    override fun onHomeBtnClicked() {
        view?.navigateToHomepageActivity()
    }

    override fun onTournamentsRefresh() {
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val tournaments = userRepo.getUsersTournaments()
                if (tournaments == null) {
                    withContext(Dispatchers.Main) {
                        view?.showError("It seems this team hasn't joined any tournament yet.")
                        view?.hideProgress()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        view?.showMyTournaments(tournaments)
                        view?.hideProgress()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.showError("Oops... It seems there's unexpected error. Please try again.")
                    view?.hideProgress()
                }
            }
        }
    }

    override fun onTeamsRefresh() {
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val myTeams = userRepo.getUserTeams()
                    if (myTeams != null) {
                        withContext(Dispatchers.Main) {
                            view?.showMyTeams(myTeams)
                            view?.hideProgress()
                        }
                    }
                    if (myTeams == null) {
                        withContext(Dispatchers.Main) {
                            view?.showError("It seems you haven't joined any team yet.")
                            view?.hideProgress()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        view?.showError("Oops... It seems there's unexpected error. Please try again.")
                        view?.hideProgress()
                    }
                }
            }
        }
    }
}