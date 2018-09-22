package subdua.dicoding.farid.footballscheduleapps.api

import android.net.Uri
import subdua.dicoding.farid.footballscheduleapps.BuildConfig

object EndPointApi {

    fun getLeagueNext(id:String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("/eventsnextleague.php")
                .appendQueryParameter("id", id)
                .build()
                .toString()
    }

    fun getTeamDetails(id:String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("/lookupteam.php")
                .appendQueryParameter("id", id)
                .build()
                .toString()
    }

    fun getLeagueAll(): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("/all_leagues.php")
                .build()
                .toString()
    }

    fun getLeaguePrev(id:String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id", id)
                .build()
                .toString()
    }
}