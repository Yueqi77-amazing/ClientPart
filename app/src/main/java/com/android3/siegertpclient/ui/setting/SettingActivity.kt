package com.android3.siegertpclient.ui.setting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android3.siegertpclient.R
import com.android3.siegertpclient.ui.base.BaseActivity
import com.android3.siegertpclient.ui.homepage.HomepageActivity
import com.android3.siegertpclient.ui.login.LoginActivity
import com.android3.siegertpclient.ui.userprofile.UserProfileActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class SettingActivity : BaseActivity() , SettingContract.ISettingView {

    private val settingPresenter: SettingPresenter = SettingPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val user = FirebaseAuth.getInstance().currentUser!!

        val backBtn: FloatingActionButton = findViewById(R.id.backButtonSetting)
        backBtn.setOnClickListener{
            settingPresenter.onBackBtnClicked()
        }

        val languageBt : Button = findViewById(R.id.changeLanguage)

        val firstnameEt : EditText = findViewById(R.id.changeFirstName)

        val lastnameEt: EditText = findViewById(R.id.changeLastName)

        val testEt: TextView = findViewById(R.id.tv_test_id)
        testEt.setText("The user is :: ".plus(user.uid))

        val saveBt : Button = findViewById(R.id.saveSettings)
        val logoutTv: TextView = findViewById(R.id.logout)
        logoutTv.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@SettingActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        settingPresenter.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        settingPresenter.onDetach()
    }

    override fun navigateToUserProfileActivity() {
        val upIntent = Intent(this, UserProfileActivity::class.java)
        startActivity(upIntent)
    }

    override fun showMessageOnPage(message: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToLoginActivity() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
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