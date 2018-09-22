package subdua.dicoding.farid.footballscheduleapps.mvp.view

import subdua.dicoding.farid.footballscheduleapps.mvp.model.EventsModel
import subdua.dicoding.farid.footballscheduleapps.mvp.model.LeagueResponse

interface MatchView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showEventListPrev(data: List<EventsModel>)
    fun showEventListNext(data: List<EventsModel>)
}