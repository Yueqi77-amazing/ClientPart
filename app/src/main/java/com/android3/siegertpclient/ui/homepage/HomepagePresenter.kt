package com.android3.siegertpclient.ui.homepage

import android.content.Context
import android.text.TextUtils
import com.android3.siegertpclient.data.team.teamsource.TeamRepo
import com.android3.siegertpclient.data.tournament.tournamentsource.TournamentRepo
import com.android3.siegertpclient.data.user.usersource.UserRepo
import com.android3.siegertpclient.ui.base.BasePresenter
import com.android3.siegertpclient.utils.Constants
import com.android3.siegertpclient.utils.OnlineChecker
import com.android3.siegertpclient.utils.PreferencesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomepagePresenter(private val context: Context) :
    BasePresenter<HomepageContract.IHomepageView>(), HomepageContract.IHomepagePresenter {

    private var userRepo = UserRepo(context)
    private var teamRepo = TeamRepo(context)
    private var tournamentRepo = TournamentRepo(context)
    private var onlineChecker = OnlineChecker(context)
    private var localData = PreferencesProvider(context)

    override fun onCreateTournamentBtnClicked() {
        view?.navigateToCreateTournamentActivity()
    }

    override fun onCreateTeamBtnClicked() {
        view?.navigateToCreateTeamActivity()
    }


    override fun onUserBtnClicked() {
        view?.navigateToUserActivity()
    }

    override fun onFeedRefresh() {
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val tournOverviews = userRepo.getUsersTournaments()
                if (tournOverviews == null) {
                    withContext(Dispatchers.Main) {
                        view?.showError("It seems you haven't joined any tournament yet.")
                        view?.hideProgress()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        view?.showFeed(tournOverviews)
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

    override fun onJoinTeamBtnClicked(teamName: String, password: String) {
        view?.showProgress()

        when {
            TextUtils.isEmpty(teamName) or TextUtils.isEmpty(password) -> {
                view?.showIncompleteInput()
                view?.hideProgress()
            }
            !onlineChecker.isOnline() -> {
                view?.showNoInternetConnection()
                view?.hideProgress()
            }
            else -> {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val joinTeam = teamRepo.joinTeam(teamName, password)
                        if (joinTeam != null) {
                            withContext(Dispatchers.Main) {
                                view?.showSuccess("You have successfully joined $teamName. You can check your teams in your profile.")
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

    override fun onTournamentOverviewClicked(position: Int) {
        val savedTournaments = tournamentRepo.getCurrentTournamentList()
        val chosenTournament = savedTournaments[position]
        localData.putCurrentTournament(chosenTournament)
        localData.putString(Constants.KEY_TEAM_NAME, chosenTournament.tournamentName)
        localData.putString(Constants.KEY_TEAM_ID, chosenTournament.tournamentId)
        view?.navigateToTournamentActivity()
    }

    override fun onInvitationBtnClicked() {
        view?.navigateToInvitationActivity()
    }
}