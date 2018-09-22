package subdua.dicoding.farid.footballscheduleapps

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import subdua.dicoding.farid.footballscheduleapps.adapter.MatchAdapter
import subdua.dicoding.farid.footballscheduleapps.mvp.model.EventsModel
import subdua.dicoding.farid.footballscheduleapps.mvp.model.LeagueResponse
import subdua.dicoding.farid.footballscheduleapps.mvp.model.LeaguesModel
import subdua.dicoding.farid.footballscheduleapps.mvp.presenter.MatchPresenter
import subdua.dicoding.farid.footballscheduleapps.mvp.view.MatchView
import subdua.dicoding.farid.footballscheduleapps.utils.invisible
import subdua.dicoding.farid.footballscheduleapps.utils.visible

class MatchActivity : AppCompatActivity(), MatchView {

    lateinit var presenter: MatchPresenter
    lateinit var adapter: MatchAdapter

    lateinit var spinner: Spinner
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var emptyDataView: LinearLayout

    lateinit var league: LeaguesModel

    var events: MutableList<EventsModel> = mutableListOf()

    private val ID_BNV = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
        setupEnv()
    }

    override fun showLoading() {
        progressBar.visible()
        recyclerView.invisible()
        emptyDataView.invisible()
    }

    override fun showEmptyData() {
        progressBar.invisible()
        recyclerView.invisible()
        emptyDataView.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        recyclerView.visible()
        emptyDataView.invisible()
    }



    override fun showLeagueList(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as LeaguesModel

                when (presenter.match) {
                    1 -> presenter.getEventsPrev(league.idLeague!!)
                    2 -> presenter.getEventsNext(league.idLeague!!)
                }
            }
        }
    }

    override fun showEventListPrev(data: List<EventsModel>) {
        showEventListData(data)
    }

    override fun showEventListNext(data: List<EventsModel>) {
        showEventListData(data)
    }

    fun itemClicked(item: EventsModel) {
        startActivity<DetailActivity>(INTENT_DETAIL to item)
    }

    fun setupLayout() {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.LTGRAY

                spinner = spinner {
                    padding = dip(16)
                    minimumHeight = dip(80)
                }
            }

            relativeLayout {
                emptyDataView = linearLayout {
                    orientation = LinearLayout.VERTICAL

                    imageView {
                        setImageResource(R.drawable.logo)
                    }

                    textView {
                        gravity = Gravity.CENTER
                        padding = dip(8)
                        text = "No Data Provided"
                    }
                }.lparams {
                    centerInParent()
                }

                recyclerView = recyclerView {
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(matchParent, matchParent) {
                    topOf(ID_BNV)
                }

                progressBar = progressBar {
                    indeterminateDrawable.setColorFilter(
                            ContextCompat.getColor(ctx, R.color.colorPrimary),
                            PorterDuff.Mode.SRC_IN
                    )
                }.lparams {
                    centerInParent()
                }

                bottomNavigationView {
                    id = ID_BNV
                    backgroundColor = Color.WHITE


                    menu.apply {
                        add("Match")
                                .setOnMenuItemClickListener {
                                    presenter.getEventsPrev(league.idLeague!!)
                                    false
                                }

                        add("Next Match")
                                .setOnMenuItemClickListener {
                                    presenter.getEventsNext(league.idLeague!!)
                                    false
                                }
                    }
                }.lparams(matchParent, wrapContent) {
                    alignParentBottom()
                }
            }
        }
    }

    fun setupEnv() {
        presenter = MatchPresenter(this)
        adapter = MatchAdapter(events, { item: EventsModel -> itemClicked(item) })

        presenter.getLeagueAll()
        recyclerView.adapter = adapter
    }

    fun showEventListData(data: List<EventsModel>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(0)
    }
}
