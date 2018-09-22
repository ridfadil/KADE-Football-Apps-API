package subdua.dicoding.farid.footballscheduleapps.mvp.presenter

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import subdua.dicoding.farid.footballscheduleapps.api.ApiRepository
import subdua.dicoding.farid.footballscheduleapps.api.EndPointApi
import subdua.dicoding.farid.footballscheduleapps.mvp.model.EventResponse
import subdua.dicoding.farid.footballscheduleapps.mvp.model.LeagueResponse
import subdua.dicoding.farid.footballscheduleapps.mvp.view.MatchView

class MatchPresenter(val view: MatchView) {

    val apiRepository = ApiRepository()
    val gson = Gson()

    var match = 1

    fun getLeagueAll() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(EndPointApi.getLeagueAll()),
                    LeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showLeagueList(data)
            }
        }
    }

    fun getEventsPrev(id: String) {
        match = 1
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(EndPointApi.getLeaguePrev(id)),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showEventListPrev(data.events!!)
                } catch (e: NullPointerException) {
                    view.showEmptyData()
                }
            }
        }
    }

    fun getEventsNext(id: String) {
        match = 2
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(EndPointApi.getLeagueNext(id)),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showEventListPrev(data.events!!)
                } catch (e: NullPointerException) {
                    view.showEmptyData()
                }
            }
        }
    }
}
