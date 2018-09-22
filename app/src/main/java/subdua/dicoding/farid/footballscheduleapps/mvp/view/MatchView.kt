package subdua.dicoding.farid.footballscheduleapps.mvp.view

import subdua.dicoding.farid.footballscheduleapps.mvp.model.EventsItem
import subdua.dicoding.farid.footballscheduleapps.mvp.model.LeagueResponse

interface MatchView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showEventListPrev(data: List<EventsItem>)
    fun showEventListNext(data: List<EventsItem>)
}