import com.android3.siegertpclient.data.team.Team
import com.android3.siegertpclient.data.tournament.Game
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.data.user.User
import com.android3.siegertpclient.ui.base.BaseView

interface TournamentContract {

    interface ITournamentView : BaseView {
        fun showCurrentTournamentDetails()

        fun setEditRights()

        fun disableEdits()

        fun showIncompleteInput()

        fun showSuccess(message: String)

        fun initParticipantAdapter()

        fun showSingleParticipants(participants: List<User>?)

        fun showTeamParticipants(participants: List<Team>?)

        fun showSchedules(schedules: List<Game>?)

        fun showGames(games: List<Game>?)

        fun navigateToHomepageActivity()
    }

    interface ITeamPresenter {
        fun allowEdits()

        fun onUpdateBtnClicked(
            tournamentName: String,
            registrationDeadline: String,
            startTime: String,
            endTime: String,
            location: String,
        )
        fun getCurrentTournament() : Tournament

        fun onHomeBtnClicked()

        fun onParticipantRefresh()

        fun onAddParticipantBtnClicked(participant: String)

        fun onScheduleRefresh()

        fun onGameRefresh()

        fun checkCreateGameRights()

        fun onCreateGameBtnClicked()

        fun onCancelTournamentBtnClicked(confirmCancel : String)

        fun isAdmin() : Boolean
    }
}