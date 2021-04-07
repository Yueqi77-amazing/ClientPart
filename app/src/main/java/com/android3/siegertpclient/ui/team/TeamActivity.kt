package com.android3.siegertpclient.ui.team

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android3.siegertpclient.R
import com.android3.siegertpclient.data.invitation.Invitation
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.data.user.User
import com.android3.siegertpclient.databinding.ActivityTeamBinding
import com.android3.siegertpclient.ui.base.BaseActivity
import com.android3.siegertpclient.ui.forgotpassword.ForgotPasswordActivity
import com.android3.siegertpclient.ui.homepage.HomepageActivity
import com.android3.siegertpclient.utils.LocalCache
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TeamActivity : BaseActivity(), TeamContract.ITeamView {
    private lateinit var binding: ActivityTeamBinding
    private lateinit var teamPresenter: TeamPresenter

    private val teamMemberFragment: TeamMemberFragment
    private val teamTournamentsFragment: TeamTournamentsFragment
    private val teamInvitationFragment: TeamInvitationFragment

    init {
        teamMemberFragment = TeamMemberFragment()
        teamTournamentsFragment = TeamTournamentsFragment()
        teamInvitationFragment = TeamInvitationFragment()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

            when (item.itemId) {
                R.id.navigation_team_member -> transaction.replace(
                    R.id.container_team_fragments,
                    teamMemberFragment
                )
                R.id.navigation_team_tournaments -> transaction.replace(
                    R.id.container_team_fragments,
                    teamTournamentsFragment
                )
                R.id.navigation_team_invitation -> transaction.replace(
                    R.id.container_team_fragments,
                    teamInvitationFragment
                )
                R.id.navigation_delete_team -> showDeleteAlert()
            }

            transaction.commit()

            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        teamPresenter = TeamPresenter(this)

        binding.bnvTeam.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container_team_fragments, teamMemberFragment)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        teamPresenter.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        teamPresenter.onDetach()
    }

    override fun navigateToTournamentActivity() {
        //Not implemented here
    }

    override fun navigateToHomepageActivity() {
        val intent = Intent(this@TeamActivity, HomepageActivity::class.java)
        startActivity(intent)
    }

    override fun showDeleteAlert() {
        val teamName = LocalCache.getCurrentTeamName(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
        val etDelete = dialogLayout.findViewById<EditText>(R.id.et_for_dialog)
        etDelete.hint = "Type DELETE to confirm"

        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Team")
            .setMessage("Are you sure you want to delete team $teamName? \n(Warning! This action cannot be undone)")
            .setNegativeButton("Cancel") {dialog, which ->
                //Do Nothing
            }
            .setPositiveButton("Confirm") {dialog, which ->
                teamPresenter?.onDeleteBtnClicked(etDelete.text.toString().trim { it <= ' ' })
            }
            .setView(dialogLayout)
            .show()
    }

    override fun showSuccess() {
        doToast("Your team has been successfully deleted")
    }

    override fun showMembers(teamMembers: List<User>?) {
        //Not implemented here
    }

    override fun showTournaments(tournaments: List<Tournament>?) {
        TODO("Not yet implemented")
    }

    override fun showInvitations(invitations: List<Invitation>?) {
        TODO("Not yet implemented")
    }

    override fun showProgress() {
        //Not implemented here
    }

    override fun hideProgress() {
        //Not implemented here
    }

    override fun showError(errorMessage: String) {
        doToast(errorMessage)
    }

    override fun showNoInternetConnection() {
        doToast("There's no internet connection to make the request.")
    }

    private fun doToast(message: String) {
        Toast.makeText(this@TeamActivity, message, Toast.LENGTH_LONG).show()
    }
}