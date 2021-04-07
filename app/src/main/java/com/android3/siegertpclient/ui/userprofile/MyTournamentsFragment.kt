package com.android3.siegertpclient.ui.userprofile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android3.siegertpclient.data.team.Team
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.databinding.FragmentMytournamentsBinding
import com.android3.siegertpclient.ui.homepage.HomepageActivity
import com.android3.siegertpclient.ui.setting.SettingsActivity
import com.android3.siegertpclient.ui.tournament.TournamentActivity
import com.android3.siegertpclient.utils.LocalCache
import com.android3.siegertpclient.utils.recyclerviewadapters.TournamentAdapter
import java.time.LocalDate

class MyTournamentsFragment : Fragment(), UserProfileContract.IUserProfileView,
    TournamentAdapter.OnTournamentItemClickListener {
    private var _binding: FragmentMytournamentsBinding? = null
    private val binding get() = _binding!!

    private var userProfilePresenter: UserProfilePresenter? = null

    private val tournamentAdapter by lazy { TournamentAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMytournamentsBinding.inflate(inflater, container, false)
        userProfilePresenter = UserProfilePresenter(requireContext())

        binding.tvUserData.text = "@" + LocalCache.getCurrentUsername(requireContext()) +
                " : " + LocalCache.getCurrentLastName(requireContext()) + ", " +
                LocalCache.getCurrentFirstName(requireContext())

        binding.rvMyTournaments.adapter = tournamentAdapter

        userProfilePresenter?.onTournamentsRefresh()

        binding.srlRvMyTournaments.setOnRefreshListener {
            userProfilePresenter?.onTournamentsRefresh()
        }

        binding.btnSetttings.setOnClickListener {
            userProfilePresenter?.onSettingBtnClicked()
        }

        binding.btnHome.setOnClickListener {
            userProfilePresenter?.onHomeBtnClicked()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        userProfilePresenter?.onAttach(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        userProfilePresenter?.onDetach()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        userProfilePresenter?.onDetach()
    }

    override fun showMyTeams(myTeams: List<Team>?) {
        //Not implemented here
    }

    override fun navigateToHomepageActivity() {
        val intent = Intent(activity, HomepageActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToSettingActivity() {
        val intent = Intent(activity, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToTeamActivity() {
        //Not implemented here
    }

    override fun navigateToTournamentActivity() {
        val intent = Intent(activity, TournamentActivity::class.java)
        startActivity(intent)
    }

    override fun showMyTournaments(myTournaments: List<Tournament>?) {
        if (myTournaments != null) {
            tournamentAdapter.setData(myTournaments)
        }
    }

    override fun showProgress() {
        //Not needed for plain swipe refresh layout
    }

    override fun hideProgress() {
        binding.srlRvMyTournaments.isRefreshing = false
    }

    override fun showError(errorMessage: String) {
        doToast(errorMessage)
    }

    override fun showNoInternetConnection() {
        doToast("There's no internet connection to make the request.")
    }

    override fun onTournamentItemClick(position: Int) {
        userProfilePresenter?.userTournamentClicked(position)
    }

    private fun doToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}