package alifyz.com.popseries.ui

import alifyz.com.popseries.R
import alifyz.com.popseries.adapter.CastAdapter
import alifyz.com.popseries.adapter.CommentAdapter
import alifyz.com.popseries.arch.DetailsContract
import alifyz.com.popseries.arch.DetailsPresenter
import alifyz.com.popseries.model.Credits
import alifyz.com.popseries.model.Reviews
import alifyz.com.popseries.model.SeriesModel
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details_comments.*
import kotlinx.android.synthetic.main.activity_details_crew.*
import kotlinx.android.synthetic.main.activity_details_header.*
import kotlinx.android.synthetic.main.item_details_comment.*

class DetailsActivity : AppCompatActivity(), DetailsContract.View {


    override lateinit var presenter: DetailsPresenter
    lateinit var rawJson : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        presenter = DetailsPresenter(this)
        rawJson = intent.getStringExtra("data")
    }

    override fun onStart() {
        super.onStart()
        presenter.setFlags(window)
        presenter.extractData(rawJson)
        presenter.loadAdditionalInformation(
                presenter.extractId(rawJson),
                "en-US",
                getString(R.string.appendToResponse))
    }

    override fun bindViews(seriesObject: SeriesModel.SeriesMetaData) {
        val posterImage = findViewById<ImageView>(R.id.poster)
        val posterUrl = getString(R.string.original_path)
                .plus(seriesObject.posterPath)


        val actionBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(actionBar)
        Glide.with(this)
                .load(posterUrl)
                .into(posterImage)

        rating_count.text = setRateCount(seriesObject)
        rating.rating = setRatings(seriesObject.voteAverage)
        series_title.text = seriesObject.originalName
        storyline_txt_description.text = seriesObject.overview
    }

    override fun setRatings(voteAverage: Double?) : Float {
        val vote = voteAverage?.div(10)?.times(5)
        return vote!!.toFloat()
    }

    override fun setRateCount(seriesDetail: SeriesModel.SeriesMetaData) =
            seriesDetail.voteCount!!.toString().plus(" Reviews")


    override fun showErrorScreen() {
        TODO("not implemented")
    }

    override fun setLoadingIndicator(active: Boolean) {
        if(active) {
            progress_crew.visibility = View.VISIBLE
            crews_title.visibility = View.INVISIBLE
            recycler_crew.visibility = View.INVISIBLE
        }else {
            progress_crew.visibility = View.GONE
            crews_title.visibility = View.VISIBLE
            recycler_crew.visibility = View.VISIBLE
        }
    }

    override fun setReviews(reviews: Reviews?) {
        recycler_comments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_comments.adapter = CommentAdapter(this, reviews!!)
    }

    override fun setEmptyReviews() {
        comment_section.visibility = View.GONE
    }

    override fun showEmptyContent() {
        TODO("not implemented")
    }

    override fun showOffline() {
        TODO("not implemented")
    }

    override fun showSavedAlert() {
        TODO("not implemented")
    }

    override fun setAdditionalViews(cast: Credits?) {
        recycler_crew.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_crew.adapter = CastAdapter(this, cast!!)
    }
}

