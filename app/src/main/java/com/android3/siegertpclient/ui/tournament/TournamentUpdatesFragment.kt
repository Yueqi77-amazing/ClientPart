package com.android3.siegertpclient.ui.tournament

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.android3.siegertpclient.R
import com.android3.siegertpclient.ui.homepage.HomepageActivity

class TournamentUpdatesFragment : Fragment(), TournamentContract.ITournamentView {

    private val tournamentPresenter: TournamentPresenter = TournamentPresenter()

    var deleteBtn: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        var view = inflater.inflate(R.layout.fragment_tournamentupdates, container, false)

        deleteBtn = view.findViewById<Button>(R.id.deleteTournamentBtn)
        deleteBtn?.setOnClickListener {
            tournamentPresenter.onDeleteTournamentBtnClicked()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        tournamentPresenter.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        tournamentPresenter.onDetach()
    }

    fun showUpdateLogs(logs: List<String>) {
        TODO("Not yet implemented")
    }

    override fun showTournamentDetailsFragment() {
        TODO("Not yet implemented")
    }

    override fun showTournamentParticipantsFragment() {
        TODO("Not yet implemented")
    }

    override fun showTournamentScheduleFragment() {
        TODO("Not yet implemented")
    }

    override fun showResultFragment() {
        TODO("Not yet implemented")
    }

    override fun showTournamentUpdatesFragment() {
        TODO("Not yet implemented")
    }

    override fun navigateToHomepageActivity() {
        val hIntent = Intent(activity, HomepageActivity::class.java)
        startActivity(hIntent)
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun showError(errorMessage: String) {
        TODO("Not yet implemented")
    }

    override fun showError(errorId: Int) {
        TODO("Not yet implemented")
    }

    override fun showNoInternetConnection() {
        TODO("Not yet implemented")
    }
}