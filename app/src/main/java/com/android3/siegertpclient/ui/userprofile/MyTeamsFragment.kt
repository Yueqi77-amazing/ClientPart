package com.android3.siegertpclient.ui.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android3.siegertpclient.R
import com.android3.siegertpclient.ui.homepage.TournamentOverviewCardRecyclerAdapter

class MyTeamsFragment : Fragment() {

    private val userProfilePresenter: UserProfilePresenter = UserProfilePresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        var view = inflater!!.inflate(R.layout.fragment_myteams, container, false)

        return view
    }

    fun showTeams() {
        TODO("Not yet implemented")
    }

    fun navigateToTeamActivity()  {
        TODO("Not yet implemented")
    }
}