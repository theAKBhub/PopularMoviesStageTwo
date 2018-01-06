package com.example.android.popularmoviesstagetwo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.popularmoviesstagetwo.adapters.FragmentAdapter;
import com.example.android.popularmoviesstagetwo.data.MovieContract.MoviesEntry;
import com.example.android.popularmoviesstagetwo.models.Movie;
import com.example.android.popularmoviesstagetwo.utils.BuildConfig;
import com.example.android.popularmoviesstagetwo.utils.Utils;


public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static Movie sCurrentMovie;
    final Context mContext = this;
    private Toast mToast;

    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Retrieve Intent Extras
        Intent intent = getIntent();
        if (intent.getParcelableExtra(BuildConfig.INTENT_EXTRA_KEY_MOVIE) != null) {
            sCurrentMovie = intent.getParcelableExtra(BuildConfig.INTENT_EXTRA_KEY_MOVIE);
        }
        if (intent.getExtras() != null) {
            setTitle(intent.getStringExtra(BuildConfig.INTENT_EXTRA_KEY_TITLE));
        }

        // Set up Tab Layout and ViewPager
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), DetailActivity.this));
        mTabLayout.setupWithViewPager(viewPager);







    }

    /**
     * Inflate menu options
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_favorite, menu);
        return true;
    }

    /**
     * Method to handle actions when individual menu item is clicked
     * @param item
     * @return true/false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_favorite:
                addToFavourite();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to add movie as favorite
     */
    public void addToFavourite() {
        Uri uri;

        ContentValues values = new ContentValues();
        values.put(MoviesEntry.COLUMN_MOVIE_ID, sCurrentMovie.getId());
        values.put(MoviesEntry.COLUMN_MOVIE_TITLE, sCurrentMovie.getTitle());
        values.put(MoviesEntry.COLUMN_POSTER_PATH, sCurrentMovie.getPosterPath());

        try {
            uri = getContentResolver().insert(MoviesEntry.CONTENT_URI, values);
        } catch (IllegalArgumentException iae) {
            uri = null;
            Log.e(LOG_TAG, iae.toString());
        }

        // If the row ID is -1, then there was an error with insertion.
        // Otherwise, the insertion was successful and we can display a toast with the row ID.
        if (uri == null) {
            Utils.showToastMessage(mContext, mToast, getString(R.string.error_insert)).show();
        } else {
            Utils.showToastMessage(mContext, mToast, getString(R.string.info_insert_successful)).show();
        }

    }

}
