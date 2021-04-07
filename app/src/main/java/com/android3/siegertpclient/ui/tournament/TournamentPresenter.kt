package com.android3.siegertpclient.ui.tournament

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.android3.siegertpclient.data.invitation.invitationsource.InvitationRepo
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.data.tournament.TournamentDetail
import com.android3.siegertpclient.data.tournament.tournamentsource.TournamentRepo
import com.android3.siegertpclient.ui.base.BasePresenter
import com.android3.siegertpclient.utils.Constants
import com.android3.siegertpclient.utils.Constants.Companion.SINGLE
import com.android3.siegertpclient.utils.Constants.Companion.TEAM
import com.android3.siegertpclient.utils.LocalCache
import com.android3.siegertpclient.utils.OnlineChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TournamentPresenter(private val context: Context) :
    BasePresenter<TournamentContract.ITournamentView>(), TournamentContract.ITeamPresenter {
    private var onlineChecker = OnlineChecker(context)
    private var tournamentRepo = TournamentRepo(context)
    private var invitationRepo = InvitationRepo(context)

    override fun getCurrentTournament() : Tournament {
        return tournamentRepo.getCurrentTournament()
    }

    override fun allowEdits() {
        if (!isAdmin()) {
            view?.disableEdits()
        }
    }

    override fun onUpdateBtnClicked(
        tournamentName: String,
        registrationDeadline: String,
        startTime: String,
        endTime: String,
        location: String
    ) {
        view?.showProgress()

        when {
            TextUtils.isEmpty(tournamentName) or TextUtils.isEmpty(location) -> {
                view?.showIncompleteInput()
                view?.hideProgress()
            }
            !validDateDifference(startTime, endTime) -> {
                view?.showError("End date must ends after start date")
                view?.hideProgress()
            }
            !onlineChecker.isOnline() -> {
                view?.showNoInternetConnection()
                view?.hideProgress()
            }
            else -> {
                val oldTournament = tournamentRepo.getCurrentTournament()
                val oldTournamentDetail = oldTournament.tournamentDetail
                val newTournamentDetail = TournamentDetail(
                    oldTournamentDetail.adminId,
                    endTime,
                    location,
                    oldTournamentDetail.participantForm,
                    registrationDeadline,
                    startTime,
                    oldTournamentDetail.tournamentTypes,
                    oldTournamentDetail.typeOfGame
                )

                GlobalScope.launch(Dispatchers.IO) {
                    Log.d("CheckDetails", "Pr : You are in coroutine")
                    try {
                        Log.d("CheckDetails", "Pr : You finish update")
                        val tournament = tournamentRepo.updateTournamentDetail(
                            tournamentName,
                            newTournamentDetail
                        )
                        Log.d("CheckDetails", "Pr : You finish update")
                        if (tournament != null) {
                            Log.d("CheckDetails", "Pr : not null")
                            withContext(Dispatchers.Main) {
                                view?.showCurrentTournamentDetails()
                                view?.hideProgress()
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("CheckDetails", "Pr : You back in exception")
                        withContext(Dispatchers.Main) {
                            view?.showError(e.message.toString())
                            //view?.showError("Oops... It seems there's unexpected error. Please try again.")
                            view?.hideProgress()
                        }
                    }
                }
            }
        }
    }

    override fun onHomeBtnClicked() {
        view?.navigateToHomepageActivity()
    }

    override fun onParticipantRefresh() {
        val participantForm = tournamentRepo.getCurrentTournament().tournamentDetail.participantForm

        when {
            participantForm == SINGLE -> {
                handleSingleRefresh()
            }
            participantForm == TEAM -> {
                handleTeamRefresh()
            }
        }
    }

    private fun handleSingleRefresh() {
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val participants = tournamentRepo.getTournamentParticipantsUser()
                    if (participants != null) {
                        withContext(Dispatchers.Main) {
                            view?.showSingleParticipants(participants)
                            view?.hideProgress()
                        }
                    }
                    if (participants == null) {
                        withContext(Dispatchers.Main) {
                            view?.showError("It seems there's no participant yet.")
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

    private fun handleTeamRefresh() {
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val participants = tournamentRepo.getTournamentParticipantsTeam()
                    if (participants != null) {
                        withContext(Dispatchers.Main) {
                            view?.showTeamParticipants(participants)
                            view?.hideProgress()
                        }
                    }
                    if (participants == null) {
                        withContext(Dispatchers.Main) {
                            view?.showError("It seems there's no participant yet.")
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

    override fun onAddParticipantBtnClicked(participant: String) {
        val participantForm = getCurrentTournament().tournamentDetail.participantForm

        if (TextUtils.isEmpty(participant)) {
            view?.showIncompleteInput()
            view?.hideProgress()
            return
        }
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            try {
                when (participantForm) {
                    TEAM -> {
                        val inviteParticipant = invitationRepo.inviteTeam(participant)
                        if (inviteParticipant != null) {
                            withContext(Dispatchers.Main) {
                                view?.showSuccess("User :: $participant :: has been invited!")
                                view?.hideProgress()
                            }
                        }
                    }
                    SINGLE -> {
                        val inviteParticipant = invitationRepo.inviteUser(participant)
                        if (inviteParticipant != null) {
                            withContext(Dispatchers.Main) {
                                view?.showSuccess("Team :: $participant :: has been invited!")
                                view?.hideProgress()
                            }
                        }
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

    override fun onScheduleRefresh() {
        TODO("Not yet implemented")
    }

    override fun onGameRefresh() {
        TODO("Not yet implemented")
    }

    override fun checkCreateGameRights() {
        TODO("Not yet implemented")
    }

    override fun onCreateGameBtnClicked() {
        TODO("Not yet implemented")
    }

    override fun onCancelTournamentBtnClicked(confirmCancel : String) {
        view?.showProgress()

        if(!isAdmin()) {
            view?.showError("You have no permission to do the operation")
            view?.hideProgress()
            return
        }
        if(TextUtils.isEmpty(confirmCancel) or (confirmCancel != Constants.DELETE)) {
            view?.showError("Tournament deletion cancelled. Please type DELETE to complete")
            view?.hideProgress()
            return
        }
        if (!onlineChecker.isOnline()) {
            view?.showNoInternetConnection()
            view?.hideProgress()
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val deleteRequest = tournamentRepo.deleteTournament()
                if (deleteRequest != null) {
                    withContext(Dispatchers.Main) {
                        view?.showSuccess("Tournament has been successfully deleted")
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

    override fun isAdmin(): Boolean {
        val tournament = tournamentRepo.getCurrentTournament()
        val tournamentDetail = tournament.tournamentDetail
        val tournamentAdmin = tournamentDetail.adminId

        if (tournamentAdmin != LocalCache.getCurrentUserId(context)) {
            return false
        }

        return true
    }

    private fun validDateDifference(start: String, end: String): Boolean {
        val startYear = start.substring(0,4).toInt()
        val startMonth = start.substring(5,7).toInt()
        val startDate = start.substring(8,10).toInt()

        val endYear = end.substring(0,4).toInt()
        val endMonth = end.substring(5,7).toInt()
        val endDate = end.substring(8,10).toInt()

        if ((endYear - startYear) < 0) {
            return false
        }

        if (endYear - startYear == 0) {
            if(endMonth - startMonth < 0) {
                return false
            }
        }

        if (endYear - startYear == 0) {
            if(endMonth - startMonth == 0) {
                if(endDate - startDate < 0) {
                    return false
                }
            }
        }

        return true
    }
}