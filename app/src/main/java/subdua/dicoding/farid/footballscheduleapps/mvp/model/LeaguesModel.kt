package subdua.dicoding.farid.footballscheduleapps.mvp.model


data class LeaguesModel(val idLeague: String?, val strLeague: String?) {

    override fun toString(): String {
        return strLeague.toString()
    }
}
