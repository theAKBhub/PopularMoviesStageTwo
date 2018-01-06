package com.example.android.popularmoviesstagetwo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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


public class DetailActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private static final String STATE_TAG_FAVORITE = "tag_favorite";

    public static Movie sCurrentMovie;
    final Context mContext = this;
    private Toast mToast;
    private static final int LOADER_ID = 0;

    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    private Menu mMenu;
    private MenuItem mMenuAddFavorite;
    private boolean mIsFavoriteMovie;


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

        // Initialize the loader only for the first time the activity is launched
        if (savedInstanceState != null) {
            mIsFavoriteMovie = savedInstanceState.getBoolean(STATE_TAG_FAVORITE);
        } else {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_TAG_FAVORITE, mIsFavoriteMovie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mIsFavoriteMovie = savedInstanceState.getBoolean(STATE_TAG_FAVORITE);
        }
    }


    /**
     * Inflate menu options
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_favorite, menu);
        mMenuAddFavorite = menu.getItem(0);
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
                if (mIsFavoriteMovie) {
                    Utils.showToastMessage(mContext, mToast, getString(R.string.info_already_favorite)).show();
                } else {
                    addToFavourite();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mMenuAddFavorite = menu.getItem(0);
        if (mIsFavoriteMovie) {
            mMenuAddFavorite.setIcon(R.drawable.star);
        } else {
            mMenuAddFavorite.setIcon(R.drawable.ic_star_outline);
        }
        return super.onPrepareOptionsMenu(menu);
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
            mIsFavoriteMovie = true;
            mMenuAddFavorite.setIcon(R.drawable.star);
        }
    }


    /**
     * Instantiates and returns a new AsyncTaskLoader with the given ID
     * @param id
     * @param args
     * @return AsyncTaskLoader<Cursor>
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final String selectionClause = "((" + MoviesEntry.COLUMN_MOVIE_ID + " = " + sCurrentMovie.getId() + "))";

        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mData = null;

            @Override
            protected void onStartLoading() {
                if (mData != null) {
                    // delivers any previously loaded data
                    deliverResult(mData);
                } else {
                    // force a new load
                    forceLoad();
                }
            }

            // performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(MoviesEntry.CONTENT_URI,
                            null,
                            selectionClause,
                            null,
                            MoviesEntry._ID);

                } catch (Exception e) {
                    Utils.showToastMessage(mContext, mToast, getString(R.string.error_favorites_load_failed)).show();
                    Log.e(LOG_TAG, getString(R.string.error_favorites_load_failed));
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    /**
     * Method to handle when a previously created loader has finished its load.
     * @param loader The Loader that has finished.
     * @param cursor The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            DatabaseUtils.dumpCursor(cursor);
            mIsFavoriteMovie = true;

            // method called to redraw menu
            invalidateOptionsMenu();

        } else {
            mIsFavoriteMovie = false;
        }
    }

    /**
     * Method to handle when a previously created loader is being reset, and thus making its data unavailable.
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
