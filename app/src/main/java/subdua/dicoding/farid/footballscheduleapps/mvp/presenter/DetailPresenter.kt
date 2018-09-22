package subdua.dicoding.farid.footballscheduleapps.mvp.presenter

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import subdua.dicoding.farid.footballscheduleapps.api.ApiRepository
import subdua.dicoding.farid.footballscheduleapps.api.EndPointApi
import subdua.dicoding.farid.footballscheduleapps.mvp.model.TeamDetailResponse
import subdua.dicoding.farid.footballscheduleapps.mvp.view.DetailView

class DetailPresenter(val view: DetailView) {

    val apiRepository = ApiRepository()
    val gson = Gson()

    fun getTeamDetails(idHomeTeam: String, idAwayTeam: String) {
        view.showLoading()

        doAsync {
            val dataHomeTeam = gson.fromJson(apiRepository
                    .doRequest(EndPointApi.getTeamDetails(idHomeTeam)),
                    TeamDetailResponse::class.java
            )

            val dataAwayTeam = gson.fromJson(apiRepository
                    .doRequest(EndPointApi.getTeamDetails(idAwayTeam)),
                    TeamDetailResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamDetails(dataHomeTeam.teams!!, dataAwayTeam.teams!!)
            }
        }
    }
}