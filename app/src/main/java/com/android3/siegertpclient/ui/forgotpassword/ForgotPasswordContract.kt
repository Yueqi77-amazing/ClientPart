import com.android3.siegertpclient.ui.base.BaseView

interface ForgotPasswordContract {

    interface IForgotPasswordView : BaseView {

        fun showErrorOnEmail(message: String)

        fun showSuccess(message: String)

        fun navigateToLoginActivity()

        fun navigateToHomepageActivity()
    }

    interface IForgotPasswordPresenter {

        fun onBackBtnClicked()

        fun onSendBtnClicked(email : String)
    }
}