package com.android3.siegertpclient.ui.tournament

import android.content.Context
import android.text.TextUtils
import com.android3.siegertpclient.data.tournament.TournamentDetail
import com.android3.siegertpclient.data.tournament.tournamentsource.TournamentRepo
import com.android3.siegertpclient.ui.base.BasePresenter
import com.android3.siegertpclient.utils.LocalCache
import com.android3.siegertpclient.utils.OnlineChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TournamentPresenter(private val context: Context) : BasePresenter<TournamentContract.ITournamentView>(), TournamentContract.ITeamPresenter {
    private var onlineChecker = OnlineChecker(context)
    private var tournamentRepo = TournamentRepo(context)

    override fun initTournamentDetails() {
        val tournament = tournamentRepo.getCurrentTournament()
        val tournamentDetail = tournament.tournamentDetail

        val tournamentName = tournament.tournamentName
        val typeOfGame = tournamentDetail.typeOfGame
        val matchType = tournament.type
        val tournamentType = tournamentDetail.tournamentTypes
        val participantForm = tournamentDetail.participantForm
        val registrationDeadline = tournamentDetail.registrationDeadline
        val startDate = tournamentDetail.startTime
        val endDate = tournamentDetail.endTime
        val location = tournamentDetail.location
        val maxPlayer = tournament.maxParticipantNumber

        view?.showCurrentTournamentDetails(tournamentName, typeOfGame, matchType, tournamentType, participantForm, registrationDeadline, startDate, endDate, location, maxPlayer)
    }

    override fun checkEditRights() {
        if(!isAdmin()) {
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
                val newTournamentDetail = TournamentDetail(oldTournamentDetail.adminId, endTime, location, oldTournamentDetail.participantForm, registrationDeadline, startTime, oldTournamentDetail.tournamentTypes, oldTournamentDetail.typeOfGame)

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val tournament = tournamentRepo.updateTournamentDetail(tournamentName, newTournamentDetail)
                        if (tournament != null) {
                            withContext(Dispatchers.Main) {
                                initTournamentDetails()
                                view?.hideProgress()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            view?.hideProgress()
                            view?.showError("Oops... It seems there's unexpected error. Please try again.")
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
        val tournament = tournamentRepo.getCurrentTournament()
        val participantForm = tournament.tournamentDetail.participantForm

        when {
            participantForm == "SINGLE" -> {
                handleSigleRefresh()
            }
            participantForm == "TEAM" -> {
                handleTeamRefresh()
            }
        }
    }

    private fun handleSigleRefresh() {
        TODO("Not yet implemented")
    }

    private fun handleTeamRefresh() {
        TODO("Not yet implemented")
    }

    override fun onAddParticipantBtnClicked() {
        TODO("Not yet implemented")
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

    override fun onCancelTournamentBtnClicked() {
        TODO("Not yet implemented")
    }

    override fun isAdmin() : Boolean{
        val tournament = tournamentRepo.getCurrentTournament()
        val tournamentDetail = tournament.tournamentDetail
        val tournamentAdmin = tournamentDetail.adminId

        if (tournamentAdmin != LocalCache.getCurrentUserId(context)) {
            return false
        }

        return true
    }

    private fun validDateDifference(start: String, end: String): Boolean {
        val startYear = start.substring(0,3).toInt()
        val startMonth = start.substring(5,6).toInt()
        val startDate = start.substring(8,9).toInt()

        val endYear = end.substring(0,3).toInt()
        val endMonth = end.substring(5,6).toInt()
        val endDate = end.substring(8,9).toInt()

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