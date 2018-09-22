package subdua.dicoding.farid.footballscheduleapps

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import subdua.dicoding.farid.footballscheduleapps.mvp.model.EventsItem
import subdua.dicoding.farid.footballscheduleapps.mvp.model.TeamsItem
import subdua.dicoding.farid.footballscheduleapps.mvp.presenter.DetailPresenter
import subdua.dicoding.farid.footballscheduleapps.mvp.view.DetailView
import subdua.dicoding.farid.footballscheduleapps.utils.DateTime
import subdua.dicoding.farid.footballscheduleapps.utils.invisible
import subdua.dicoding.farid.footballscheduleapps.utils.visible

/*class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}*/



const val INTENT_DETAIL = "INTENT_DETAIL"

class DetailActivity : AppCompatActivity(), DetailView {

    lateinit var presenter: DetailPresenter

    lateinit var progressView: ProgressBar
    lateinit var dataView: ScrollView

    lateinit var imgHomeBadge: ImageView
    lateinit var imgAwayBadge: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val item = intent.getParcelableExtra<EventsItem>(INTENT_DETAIL)

        setupLayout(item)
        setupEnv(item)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressView.visible()
        dataView.invisible()
    }

    override fun hideLoading() {
        progressView.invisible()
        dataView.visible()
    }

    override fun showTeamDetails(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>) {
        Picasso.get()
                .load(dataHomeTeam[0].strTeamBadge)
                .into(imgHomeBadge)

        Picasso.get()
                .load(dataAwayTeam[0].strTeamBadge)
                .into(imgAwayBadge)
    }

    fun setupLayout(item: EventsItem) {
        relativeLayout {
            dataView = scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    // date
                    textView {
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = DateTime.getLongDate(item.dateEvent!!)
                    }

                    // score
                    linearLayout {
                        padding = dip(16)
                        gravity = Gravity.CENTER

                        textView {
                            textSize = 48f
                            setTypeface(null, Typeface.BOLD)
                            text = item.intHomeScore
                        }

                        textView {
                            padding = dip(16)
                            textSize = 24f
                            text = "vs"
                        }

                        textView {
                            textSize = 48f
                            setTypeface(null, Typeface.BOLD)
                            text = item.intAwayScore
                        }
                    }

                    // team
                    linearLayout {
                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            imgHomeBadge = imageView() {
                            }.lparams {
                                width = dip(100)
                                height = dip(100)
                                gravity = Gravity.CENTER
                            }

                            textView {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                textSize = 24f
                                setTypeface(null, Typeface.BOLD)
                                text = item.strHomeTeam
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = item.strHomeFormation
                            }
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            imgAwayBadge = imageView() {
                            }.lparams {
                                width = dip(100)
                                height = dip(100)
                                gravity = Gravity.CENTER
                            }

                            textView {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                textSize = 24f
                                setTypeface(null, Typeface.BOLD)
                                text = item.strAwayTeam
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = item.strAwayFormation
                            }
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    view {
                        backgroundColor = Color.LTGRAY
                    }.lparams(matchParent, dip(1)) {
                        topMargin = dip(8)
                    }

                    // goals
                    linearLayout {
                        topPadding = dip(8)

                        textView {
                            text = item.strHomeGoalDetails
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Goals"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = item.strAwayGoalDetails
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // shots
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = item.intHomeShots
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Shots"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = item.intAwayShots
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    view {
                        backgroundColor = Color.LTGRAY
                    }.lparams(matchParent, dip(1)) {
                        topMargin = dip(8)
                    }

                    // lineups
                    textView {
                        topPadding = dip(8)
                        gravity = Gravity.CENTER
                        textSize = 18f
                        setTypeface(null, Typeface.BOLD)
                        text = "Lineups"
                    }

                    // goal keeper
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = item.strHomeLineupGoalkeeper
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Goal Keeper"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = item.strAwayLineupGoalkeeper
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // defense
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = item.strHomeLineupDefense
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Defense"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = item.strAwayLineupDefense
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // midfield
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = item.strHomeLineupMidfield
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Midfield"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = item.strAwayLineupMidfield
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // forward
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = item.strHomeLineupForward
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Forward"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = item.strAwayLineupForward
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // substitutes
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = item.strHomeLineupSubstitutes
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Substitutes"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = item.strAwayLineupSubstitutes
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }
            }

            progressView = progressBar {
                indeterminateDrawable.setColorFilter(
                        ContextCompat.getColor(ctx, R.color.colorPrimary),
                        PorterDuff.Mode.SRC_IN
                )
            }.lparams {
                centerInParent()
            }
        }
    }

    fun setupEnv(item: EventsItem) {
        presenter = DetailPresenter(this)
        presenter.getTeamDetails(item.idHomeTeam!!, item.idAwayTeam!!)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"
    }
}



