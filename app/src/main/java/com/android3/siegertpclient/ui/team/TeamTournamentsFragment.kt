package com.android3.siegertpclient.ui.team

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android3.siegertpclient.data.invitation.Invitation
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.data.user.User
import com.android3.siegertpclient.databinding.FragmentTeamtournamentsBinding
import com.android3.siegertpclient.ui.homepage.HomepageActivity
import com.android3.siegertpclient.utils.LocalCache
import com.android3.siegertpclient.utils.recyclerviewadapters.TournamentAdapter

class TeamTournamentsFragment : Fragment(), TeamContract.ITeamView, TournamentAdapter.OnTournamentItemClickListener {
    private var _binding: FragmentTeamtournamentsBinding? = null
    private val binding get() = _binding!!

    private var teamPresenter: TeamPresenter? = null

    private val tournamentAdapter by lazy { TournamentAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamtournamentsBinding.inflate(inflater, container, false)
        teamPresenter = TeamPresenter(requireContext())

        binding.tvTeamName.text = "Team : " + LocalCache.getCurrentTeamName(requireContext())

        binding.rvTeamTournaments.adapter = tournamentAdapter

        teamPresenter?.onTournamentsRefresh()

        binding.srlRvTeamTournaments.setOnRefreshListener {
            teamPresenter?.onTournamentsRefresh()
        }

        binding.btnHome.setOnClickListener {
            teamPresenter?.onHomeBtnClicked()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        teamPresenter?.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        teamPresenter?.onDetach()
        _binding = null
    }

    override fun navigateToTournamentActivity() {
        TODO("Not yet implemented")
    }

    override fun navigateToHomepageActivity() {
        val intent = Intent(activity, HomepageActivity::class.java)
        startActivity(intent)
    }

    override fun showDeleteAlert() {
        //Not implemented here
    }

    override fun showSuccess() {
        TODO("Not yet implemented")
    }

    override fun showMembers(teamMembers: List<User>?) {
        //Not implemented here
    }

    override fun showTournaments(tournaments: List<Tournament>?) {
        if (tournaments != null) {
            tournamentAdapter.setData(tournaments)
        }
    }

    override fun showInvitations(invitations: List<Invitation>?) {
        TODO("Not yet implemented")
    }

    override fun showProgress() {
        //Not needed for plain swipe refresh layout
    }

    override fun hideProgress() {
        binding.srlRvTeamTournaments.isRefreshing = false
    }

    override fun showError(errorMessage: String) {
        doToast(errorMessage)
    }

    override fun showNoInternetConnection() {
        doToast("There's no internet connection to make the request.")
    }

    override fun onTournamentItemClick(position: Int) {
        teamPresenter?.onTournamentItemClicked(position)
    }

    private fun doToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}