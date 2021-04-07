import com.android3.siegertpclient.data.invitation.Invitation
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.data.user.User
import com.android3.siegertpclient.ui.base.BaseView

interface TeamContract {

    interface ITeamView : BaseView {
        fun navigateToTournamentActivity()

        fun navigateToHomepageActivity()

        fun showDeleteAlert()

        fun showSuccess()

        fun showMembers(teamMembers: List<User>?)

        fun showTournaments(tournaments: List<Tournament>?)

        fun showInvitations(invitations: List<Invitation>?)
    }

    interface ITeamPresenter {
        fun onMembersRefresh()

        fun onTournamentsRefresh()

        fun onInvitationRefresh()

        fun onAddBtnClicked()

        fun onHomeBtnClicked()

        fun onTournamentItemClicked(position : Int)

        fun onMemberItemClicked(position : Int)

        fun onInvitationItemClicked(position : Int)

        fun onDeleteBtnClicked(confirmDelete : String)
    }
}