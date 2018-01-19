package com.alifyz.popularmovies;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alifyz.popularmovies.Database.MoviesContract.MoviesEntry;
import com.squareup.picasso.Picasso;

public class MoviesDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        final TextView mMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        final TextView mMovieYear = (TextView) findViewById(R.id.tv_year);
        final TextView mMovieRatings = (TextView) findViewById(R.id.tv_ratings);
        final TextView mMovieDescription = (TextView) findViewById(R.id.tv_movie_description);
        final ImageView mMoviePoster = (ImageView) findViewById(R.id.iv_poster_details);
        final TextView mMovieDuration = (TextView) findViewById(R.id.tv_duration);

        ImageView mAddFavorite = (ImageView) findViewById(R.id.tv_add_favorite);

        mAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(MoviesEntry.COLUMN_TITLE, mMovieTitle.getText().toString());
                values.put(MoviesEntry.COLUMN_RELEASE_DATE, mMovieYear.getText().toString());
                values.put(MoviesEntry.COLUMN_RATING, mMovieRatings.getText().toString());
                values.put(MoviesEntry.COLUMN_DESCRIPTION, mMovieDescription.getText().toString());
                values.put(MoviesEntry.COLUMN_IMAGE, mMoviePoster.getDrawable().toString());
                values.put(MoviesEntry.COLUMN_DURATION, mMovieDuration.getText().toString());

                getContentResolver().insert(MoviesEntry.CONTENT_MOVIES, values);
            }
        });

        Intent movieInfo = getIntent();
        String title = movieInfo.getStringExtra("Title");
        String description = movieInfo.getStringExtra("Description");
        String year = movieInfo.getStringExtra("Year");
        String posterUrl = movieInfo.getStringExtra("PosterImage");
        String ratings = movieInfo.getStringExtra("Ratings") + "/10";

        mMovieTitle.setText(title);
        mMovieYear.setText(year.substring(0, 4));
        mMovieRatings.setText(ratings);
        mMovieDescription.setText(description);
        mMovieDuration.setText(getString(R.string.show_duration));
        Picasso.with(getApplicationContext()).load(posterUrl).into(mMoviePoster);

    }
}
