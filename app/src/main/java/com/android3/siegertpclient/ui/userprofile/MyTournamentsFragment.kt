package com.android3.siegertpclient.ui.userprofile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android3.siegertpclient.data.team.Team
import com.android3.siegertpclient.databinding.FragmentMytournamentsBinding
import com.android3.siegertpclient.ui.homepage.HomepageActivity
import com.android3.siegertpclient.ui.homepage.TournamentOverviewCardRecyclerAdapter
import com.android3.siegertpclient.ui.setting.SettingsActivity

class MyTournamentsFragment : Fragment(), UserProfileContract.IUserProfileView {
    private var _binding: FragmentMytournamentsBinding? = null
    private val binding get() = _binding!!

    private var userProfilePresenter: UserProfilePresenter? = null

    var settingBtn: Button? = null
    var homeBtn: Button? = null
    var myTournamentsRecycler: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMytournamentsBinding.inflate(inflater, container, false)

        //var view = inflater!!.inflate(R.layout.fragment_mytournaments, container, false)

        userProfilePresenter = UserProfilePresenter(requireContext())

        val myTournamentRecycler = binding.recyclerMyTournaments
        //myTournamentsRecycler = view.findViewById<RecyclerView>(R.id.my_tournament_recycler)

        myTournamentsRecycler?.layoutManager = LinearLayoutManager(context)
        myTournamentsRecycler?.adapter = TournamentOverviewCardRecyclerAdapter()

        binding.buttonSettings.setOnClickListener{
            userProfilePresenter?.onSettingBtnClicked()
        }

        binding.buttonHome.setOnClickListener{
            userProfilePresenter?.onHomeBtnClicked()
        }

        //return view
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        userProfilePresenter?.onAttach(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        userProfilePresenter?.onDetach()
    }

    fun showTournaments() {
        TODO("Not yet implemented")
    }

    fun navigateToTournamentActivity()  {
        TODO("Not yet implemented")
    }

    override fun showMyTeams(myTeams: List<Team>?) {
        TODO("Not yet implemented")
    }

    override fun navigateToHomepageActivity() {
        val hIntent = Intent(activity, HomepageActivity::class.java)
        startActivity(hIntent)
    }

    override fun navigateToSettingActivity() {
        val sIntent = Intent(activity, SettingsActivity::class.java)
        startActivity(sIntent)
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

    override fun showNoInternetConnection() {
        TODO("Not yet implemented")
    }
}