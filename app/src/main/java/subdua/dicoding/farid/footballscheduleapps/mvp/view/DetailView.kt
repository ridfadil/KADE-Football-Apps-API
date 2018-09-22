package subdua.dicoding.farid.footballscheduleapps.mvp.view

import subdua.dicoding.farid.footballscheduleapps.mvp.model.TeamsItem


interface DetailView {

    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>)
}
