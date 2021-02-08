import com.android3.siegertpclient.base.BaseView

interface ForgotPasswordContract {

    interface IForgotPasswordView : BaseView {

        fun showErrorOnEmail(message: String)
    }

    interface IForgotPasswordPresenter {

        fun onBackBtnClicked()

        fun onSendBtnClicked()
    }
}