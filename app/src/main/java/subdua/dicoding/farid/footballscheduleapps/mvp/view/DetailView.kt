package subdua.dicoding.farid.footballscheduleapps.mvp.view

import subdua.dicoding.farid.footballscheduleapps.mvp.model.TeamModel


interface DetailView {

    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(dataHomeTeam: List<TeamModel>, dataAwayTeam: List<TeamModel>)
}
