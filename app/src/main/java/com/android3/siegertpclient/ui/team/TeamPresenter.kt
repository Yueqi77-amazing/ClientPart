package com.android3.siegertpclient.ui.team

import android.content.Context
import android.text.TextUtils
import com.android3.siegertpclient.data.invitation.invitationsource.InvitationRepo
import com.android3.siegertpclient.data.team.teamsource.TeamRepo
import com.android3.siegertpclient.data.tournament.tournamentsource.TournamentRepo
import com.android3.siegertpclient.data.user.usersource.UserRepo
import com.android3.siegertpclient.ui.base.BasePresenter
import com.android3.siegertpclient.utils.Constants
import com.android3.siegertpclient.utils.Constants.Companion.DELETE
import com.android3.siegertpclient.utils.OnlineChecker
import com.android3.siegertpclient.utils.PreferencesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamPresenter(private val context: Context) : BasePresenter<TeamContract.ITeamView>(), TeamContract.ITeamPresenter {

    private var onlineChecker = OnlineChecker(context)
    private var localData = PreferencesProvider(context)
    private var tournamentRepo = TournamentRepo(context)
    private var teamRepo = TeamRepo(context)
    private var userRepo = UserRepo(context)
    private var invitationRepo = InvitationRepo(context)

    override fun onMembersRefresh() {
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val teamMembers = teamRepo.getTeamMembers()
                    if (teamMembers != null) {
                        withContext(Dispatchers.Main) {
                            view?.showMembers(teamMembers)
                            view?.hideProgress()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        view?.showError(e.message.toString())
                        view?.showMembers(null)
                        //view?.showError("Oops... It seems there's unexpected error. Please try again.")
                        view?.hideProgress()
                    }
                }
            }
        }
    }

    override fun onTournamentsRefresh() {
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val tournaments = teamRepo.getTeamTournaments()
                    if (tournaments != null) {
                        withContext(Dispatchers.Main) {
                            view?.showTournaments(tournaments)
                            view?.hideProgress()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        view?.showError(e.message.toString())
                        view?.showTournaments(null)
                        //view?.showError("Oops... It seems there's unexpected error. Please try again.")
                        view?.hideProgress()
                    }
                }
            }
        }
    }

    override fun onInvitationRefresh() {
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val invitations = teamRepo.getTeamInvitations()
                    if (invitations != null) {
                        withContext(Dispatchers.Main) {
                            view?.showInvitations(invitations)
                            view?.hideProgress()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        view?.showError(e.message.toString())
                        view?.showInvitations(null)
                        //view?.showError("Oops... It seems there's unexpected error. Please try again.")
                        view?.hideProgress()
                    }
                }
            }
        }
    }

    override fun onAddBtnClicked() {
        TODO("Not yet implemented")
    }

    override fun onHomeBtnClicked() {
        view?.navigateToHomepageActivity()
    }

    override fun onTournamentItemClicked(position: Int) {
        val savedTournaments = tournamentRepo.getCurrentTournamentList()!!

        val chosenTeamName = savedTournaments[position].tournamentName
        localData.putString(Constants.KEY_TOURNAMENT_NAME, chosenTeamName)
        view?.navigateToTournamentActivity()
    }

    override fun onMemberItemClicked(position: Int) {
        val savedMembers = userRepo.getCurrentUserList()!!

        val chosenUsername = savedMembers[position].username
        localData.putString(Constants.KEY_TOURNAMENT_NAME, chosenUsername)
        view?.navigateToTournamentActivity()
    }

    override fun onInvitationItemClicked(position: Int) {
        val savedInvitations = invitationRepo.getCurrentInvitationList()!!

        val tournamentName = savedInvitations[position].tournamentName
        GlobalScope.launch(Dispatchers.IO) {
            val tournament = tournamentRepo.getTournamentByName(tournamentName)
            localData.putString(Constants.KEY_TOURNAMENT_NAME, tournament?.tournamentName!!)
            withContext(Dispatchers.Main) {
                view?.navigateToTournamentActivity()
            }
        }
    }

    override fun onDeleteBtnClicked(confirmDelete : String) {
        when {
            TextUtils.isEmpty(confirmDelete) or (confirmDelete != DELETE) -> {
                view?.showError("Team deletion cancelled. Please type DELETE to complete")
                view?.hideProgress()
            }
            !onlineChecker.isOnline() -> {
                view?.showNoInternetConnection()
                view?.hideProgress()
            }
            else -> {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val teamMembers = teamRepo.deleteTeam()
                        if (teamMembers != null) {
                            withContext(Dispatchers.Main) {
                                view?.showSuccess()
                                view?.hideProgress()
                                view?.navigateToHomepageActivity()
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

}