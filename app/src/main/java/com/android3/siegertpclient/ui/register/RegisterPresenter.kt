package com.android3.siegertpclient.ui.register

import android.content.Context
import android.os.AsyncTask
import android.os.AsyncTask.execute
import android.util.Patterns
import com.android3.siegertpclient.data.user.usersource.UserRepo
import com.android3.siegertpclient.ui.base.BasePresenter
import com.android3.siegertpclient.data.user.User

class RegisterPresenter(context: Context) : BasePresenter<RegisterContract.IRegisterView>(), RegisterContract.IRegisterPresenter{

    private var userRepo = UserRepo()

    override fun onRegisterBtnClicked(
        email: String,
        password: String,
        retypePassword: String,
        surname: String,
        forename: String,
        username: String
    ) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view?.showErrorOnEmail("Email is not valid")
            return
        }
        if (password != retypePassword) {
            view?.showErrorOnPassword("Password doesn't match")
            return
        }

        val user = userRepo.register(email, password, username, forename, surname)
        if (user == null) {
            view?.showError("Register failed")
            return
        } else {
            view?.navigateToHomepageActivity()
        }
    }

    override fun onLoginTxtClicked() {
        view?.navigateToLoginActivity()
    }
}

