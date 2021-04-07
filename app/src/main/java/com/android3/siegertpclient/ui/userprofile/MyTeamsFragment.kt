package com.android3.siegertpclient.ui.userprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android3.siegertpclient.data.team.Team
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.databinding.FragmentMyteamsBinding
import com.android3.siegertpclient.ui.team.TeamActivity
import com.android3.siegertpclient.utils.recyclerviewadapters.TeamAdapter

class MyTeamsFragment : Fragment() , UserProfileContract.IUserProfileView,
    TeamAdapter.OnTeamItemClickListener {

    private var _binding: FragmentMyteamsBinding? = null
    private val binding get() = _binding!!

    private var userProfilePresenter: UserProfilePresenter? = null

    private val teamAdapter by lazy { TeamAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyteamsBinding.inflate(inflater, container, false)
        userProfilePresenter = UserProfilePresenter(requireContext())

        binding.rvMyTeams.adapter = teamAdapter

        userProfilePresenter?.onTeamsRefresh()

        binding.srlRvMyTeams.setOnRefreshListener {
            userProfilePresenter?.onTeamsRefresh()
        }

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        userProfilePresenter?.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        userProfilePresenter?.onDetach()
        _binding = null
    }

    override fun navigateToTeamActivity()  {
        val intent = Intent(activity, TeamActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToTournamentActivity() {
        //Not implemented here
    }

    override fun showMyTournaments(myTournaments: List<Tournament>?) {
        //Not implemented here
    }

    override fun showMyTeams(myTeams: List<Team>?) {
        if (myTeams != null) {
            teamAdapter.setData(myTeams)
        }
    }

    override fun navigateToHomepageActivity() {
        //Not implemented here
    }

    override fun navigateToSettingActivity() {
        //Not implemented here
    }

    override fun showProgress() {
        //Not needed for plain swipe refresh layout
    }

    override fun hideProgress() {
        binding.srlRvMyTeams.isRefreshing = false
    }

    override fun showError(errorMessage: String) {
        doToast(errorMessage)
    }

    override fun showNoInternetConnection() {
        doToast("There's no internet connection to make the request.")
    }

    override fun onTeamItemClick(position: Int) {
        userProfilePresenter?.userTeamClicked(position)
    }

    private fun doToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }


}