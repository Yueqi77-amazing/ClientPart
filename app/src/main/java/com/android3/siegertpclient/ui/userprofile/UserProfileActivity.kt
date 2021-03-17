package com.android3.siegertpclient.ui.userprofile

import android.content.Intent
import android.os.Bundle
import com.android3.siegertpclient.R
import com.android3.siegertpclient.data.team.Team
import com.android3.siegertpclient.databinding.ActivityUserprofileBinding
import com.android3.siegertpclient.ui.base.BaseActivity
import com.android3.siegertpclient.ui.homepage.HomepageActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserProfileActivity : BaseActivity(), UserProfileContract.IUserProfileView {

    private lateinit var binding: ActivityUserprofileBinding
    private lateinit var userProfilePresenter: UserProfilePresenter

    private val myTeamsFragment: MyTeamsFragment
    private val myTournamentsFragment: MyTournamentsFragment

    init {
        myTeamsFragment = MyTeamsFragment()
        myTournamentsFragment = MyTournamentsFragment()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        when(item.itemId){
            R.id.navigation_my_tournament -> transaction.replace(R.id.container_userprofile_fragment, myTournamentsFragment)
            R.id.navigation_my_team -> transaction.replace(R.id.container_userprofile_fragment, myTeamsFragment)
        }

        transaction.commit()

        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userProfilePresenter = UserProfilePresenter(this)


        binding.navigationUser.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container_userprofile_fragment, myTournamentsFragment)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        userProfilePresenter.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        userProfilePresenter.onDetach()
    }

    override fun showMyTeams(myTeams: List<Team>?) {

    }

    override fun navigateToHomepageActivity() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToSettingActivity() {
        //Not implemented here
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