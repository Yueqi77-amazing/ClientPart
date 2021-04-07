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
import com.android3.siegertpclient.databinding.FragmentTeamInvitationBinding
import com.android3.siegertpclient.ui.tournament.TournamentActivity
import com.android3.siegertpclient.utils.recyclerviewadapters.InvitationAdapter

class TeamInvitationFragment : Fragment(), TeamContract.ITeamView, InvitationAdapter.OnInvitationItemClickListener {
    private var _binding: FragmentTeamInvitationBinding? = null
    private val binding get() = _binding!!

    private var teamPresenter: TeamPresenter? = null

    private val invitationAdapter by lazy { InvitationAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTeamInvitationBinding.inflate(inflater, container, false)
        teamPresenter = TeamPresenter(requireContext())

        binding.rvTeamInvitation.adapter = invitationAdapter

        teamPresenter?.onInvitationRefresh()

        binding.srlRvTeamInvitations.setOnRefreshListener {
            teamPresenter?.onInvitationRefresh()
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
        val intent = Intent(activity, TournamentActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToHomepageActivity() {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun showInvitations(invitations: List<Invitation>?) {
        if (invitations != null) {
            invitationAdapter.setData(invitations)
        }
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun showError(errorMessage: String) {
        doToast(errorMessage)
    }

    override fun showNoInternetConnection() {
        doToast("There's no internet connection to make the request.")
    }

    override fun onInvitationItemClick(position: Int) {
        teamPresenter?.onInvitationItemClicked(position)
    }

    private fun doToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}