package com.android3.siegertpclient.ui.login

import LoginContract
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import com.android3.siegertpclient.ui.base.BasePresenter
import com.android3.siegertpclient.utils.OnlineChecker
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginPresenter(private val context: Context) : BasePresenter<LoginContract.ILoginView>(),
    LoginContract.ILoginPresenter {

    private var onlineChecker = OnlineChecker(context)

    override fun onLoginBtnClicked(email: String, password: String) {
        view?.showProgress()

        when {
            TextUtils.isEmpty(email) or TextUtils.isEmpty(password) -> {
                view?.showIncompleteInput()
                view?.hideProgress()
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                view?.showErrorOnEmail()
                view?.hideProgress()
            }
            else -> {
                if (onlineChecker.isOnline()) {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener({ task ->
                            if (task.isSuccessful) {
                                val firebaseUser = task.result!!.user!!

                                firebaseUser.getIdToken(true).addOnCompleteListener({ task2 ->
                                    if (task2.isSuccessful()) {
                                        val token = task2.result!!.token!!
                                        val tokenBearer = "Bearer ".plus(token)
                                        view?.navigateToHomepageActivity(
                                            firebaseUser.uid,
                                            tokenBearer
                                        )
                                    } else {
                                        view?.hideProgress()
                                        view?.showError(task2.exception!!.message.toString())
                                    }
                                })
                            } else {
                                view?.hideProgress()
                                view?.showError(task.exception!!.message.toString())
                            }
                        })
                } else {
                    view?.hideProgress()
                    view?.showNoInternetConnection()
                }
            }
        }
    }

    override fun onForgotPasswordTextClicked() {
        view?.navigateToForgotPasswordActivity()
    }

    override fun onRegisterTextClicked() {
        view?.navigateToRegisterActivity()
    }
}