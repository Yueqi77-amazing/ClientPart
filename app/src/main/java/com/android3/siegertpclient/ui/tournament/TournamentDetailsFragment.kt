package com.android3.siegertpclient.ui.tournament

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android3.siegertpclient.data.team.Team
import com.android3.siegertpclient.data.tournament.Game
import com.android3.siegertpclient.data.user.User
import com.android3.siegertpclient.databinding.FragmentTournamentdetailsBinding
import com.android3.siegertpclient.ui.homepage.HomepageActivity
import java.util.*

class TournamentDetailsFragment : Fragment() , TournamentContract.ITournamentView{
    private var _binding: FragmentTournamentdetailsBinding? = null
    private val binding get() = _binding!!

    private var tournamentPresenter: TournamentPresenter? = null

    private var day: Int? = null
    private var month: Int? = null
    private var year: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTournamentdetailsBinding.inflate(inflater, container, false)
        tournamentPresenter = TournamentPresenter(requireContext())

        showCurrentTournamentDetails()
        setEditRights()

        day = 0
        month = 0
        year = 0

        var tournamentNameEt = binding.etTournamentName

        binding.btnRegistrationDeadline.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    var monthCalibrate = monthOfYear + 1
                    val monthFix = if (monthCalibrate <= 10) "0$monthCalibrate" else monthCalibrate.toString()
                    val dayFix = if (dayOfMonth <= 10) "0$dayOfMonth" else dayOfMonth.toString()
                    binding.btnRegistrationDeadline.text = "$year-$monthFix-$dayFix"
                },
                year!!,
                month!!,
                day!!
            ).show()
        }

        binding.btnStartDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    var monthCalibrate = monthOfYear + 1
                    val monthFix = if (monthCalibrate <= 10) "0$monthCalibrate" else monthCalibrate.toString()
                    val dayFix = if (dayOfMonth <= 10) "0$dayOfMonth" else dayOfMonth.toString()
                    binding.btnStartDate.text = "$year-$monthFix-$dayFix"
                },
                year!!,
                month!!,
                day!!
            ).show()
        }

        binding.btnEndDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    var monthCalibrate = monthOfYear + 1
                    val monthFix = if (monthCalibrate <= 10) "0$monthCalibrate" else monthCalibrate.toString()
                    val dayFix = if (dayOfMonth <= 10) "0$dayOfMonth" else dayOfMonth.toString()
                    binding.btnEndDate.text = "$year-$monthFix-$dayFix"
                },
                year!!,
                month!!,
                day!!
            ).show()
        }

        var locationEt = binding.etLocation

        binding.btnUpdateDetails.setOnClickListener {
            val tournamentName = editTextTrimmer(tournamentNameEt)
            val registrationDeadline = binding.btnRegistrationDeadline.text.toString()
            val startTime = binding.btnStartDate.text.toString()
            val endTime = binding.btnEndDate.text.toString()
            val location = editTextTrimmer(locationEt)

            tournamentPresenter?.onUpdateBtnClicked(
                tournamentName,
                registrationDeadline,
                startTime,
                endTime,
                location,
            )
        }

        binding.btnHome.setOnClickListener {
            tournamentPresenter?.onHomeBtnClicked()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        tournamentPresenter?.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        tournamentPresenter?.onDetach()
        _binding = null
    }

    override fun showCurrentTournamentDetails() {
        val tournament = tournamentPresenter?.getCurrentTournament()
        val details = tournament!!.tournamentDetail

        binding.etTournamentName.setText(tournament!!.tournamentName)
        binding.tvTypeOfGame.text = details.typeOfGame
        binding.tvMatchType.text = tournament!!.type
        binding.tvTournamentType.text = details.tournamentTypes
        binding.tvParticipantForm.text = details.participantForm
        binding.btnRegistrationDeadline.text = details.registrationDeadline
        binding.btnStartDate.text = details.startTime
        binding.btnEndDate.text = details.endTime
        binding.etLocation.setText(details.location)
        binding.tvMaxPlayer.text = tournament!!.maxParticipantNumber.toString()
    }

    override fun setEditRights() {
        if (!tournamentPresenter!!.isAdmin()) {
            disableEdits()
        }
    }

    override fun disableEdits() {
        binding.etTournamentName.isEnabled = false
        binding.btnRegistrationDeadline.isEnabled = false
        binding.btnStartDate.isEnabled = false
        binding.btnEndDate.isEnabled = false
        binding.etLocation.isEnabled = false
        binding.btnUpdateDetails.isEnabled = false
    }

    override fun showIncompleteInput() {
        doToast("Please input all of the field")
    }

    override fun showSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun initParticipantAdapter() {
        TODO("Not yet implemented")
    }

    override fun showSingleParticipants(participants: List<User>?) {
        //Not implemented here
    }

    override fun showTeamParticipants(participants: List<Team>?) {
        //Not implemented here
    }

    override fun showSchedules(schedules: List<Game>?) {
        //Not implemented here
    }

    override fun showGames(games: List<Game>?) {
        //Not implemented here
    }

    override fun navigateToHomepageActivity() {
        val intent = Intent(activity, HomepageActivity::class.java)
        startActivity(intent)
    }

    override fun showProgress() {
        binding.pbRequest.visibility = View.VISIBLE
        binding.btnUpdateDetails.isEnabled = false
    }

    override fun hideProgress() {
        binding.pbRequest.visibility = View.GONE
        binding.btnUpdateDetails.isEnabled = true
    }

    override fun showError(errorMessage: String) {
        doToast(errorMessage)
    }

    override fun showNoInternetConnection() {
        doToast("There's no internet connection to make the request.")
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun editTextTrimmer(editText: EditText): String {
        return editText.text.toString().trim { it <= ' ' }
    }

    private fun doToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}