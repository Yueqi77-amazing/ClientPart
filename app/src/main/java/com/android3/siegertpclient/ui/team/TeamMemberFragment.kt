package com.android3.siegertpclient.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android3.siegertpclient.data.invitation.Invitation
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.data.user.User
import com.android3.siegertpclient.databinding.FragmentTeamMemberBinding
import com.android3.siegertpclient.utils.recyclerviewadapters.UserAdapter

class TeamMemberFragment : Fragment(), TeamContract.ITeamView, UserAdapter.OnUserItemClickListener {
    private var _binding: FragmentTeamMemberBinding? = null
    private val binding get() = _binding!!

    private var teamPresenter: TeamPresenter? = null

    private val userAdapter by lazy { UserAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamMemberBinding.inflate(inflater, container, false)
        teamPresenter = TeamPresenter(requireContext())

        binding.rvTeamMember.adapter = userAdapter

        teamPresenter?.onMembersRefresh()

        binding.srlRvTeamMember.setOnRefreshListener {
            teamPresenter?.onMembersRefresh()
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
        TODO("Not yet implemented")
    }

    override fun showDeleteAlert() {
        TODO("Not yet implemented")
    }

    override fun showSuccess() {
        TODO("Not yet implemented")
    }

    override fun showMembers(teamMembers: List<User>?) {
        if (teamMembers != null) {
            userAdapter.setData(teamMembers)
        }
    }

    override fun showTournaments(tournaments: List<Tournament>?) {
        TODO("Not yet implemented")
    }

    override fun showInvitations(invitations: List<Invitation>?) {
        TODO("Not yet implemented")
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        binding.srlRvTeamMember.isRefreshing = false
    }

    override fun showError(errorMessage: String) {
        doToast(errorMessage)
    }

    override fun showNoInternetConnection() {
        doToast("There's no internet connection to make the request.")
    }

    private fun doToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onUserItemClick(position: Int) {
        teamPresenter?.onMemberItemClicked(position)
    }
}