import com.android3.siegertpclient.data.team.Team
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.ui.base.BaseView

interface UserProfileContract {

    interface IUserProfileView : BaseView {

        fun showMyTeams(myTeams: List<Team>?)

        fun navigateToHomepageActivity()

        fun navigateToSettingActivity()

        fun navigateToTeamActivity()

        fun navigateToTournamentActivity()

        fun showMyTournaments(myTournaments : List<Tournament>?)
    }

    interface IUserProfilePresenter {

        fun onSettingBtnClicked()

        fun onHomeBtnClicked()

        fun onTournamentsRefresh()

        fun onTeamsRefresh()

        fun userTeamClicked(position: Int)

        fun userTournamentClicked(position : Int)
    }
}