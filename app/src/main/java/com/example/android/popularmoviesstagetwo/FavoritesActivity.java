package com.example.android.popularmoviesstagetwo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.LoaderManager;
import androidx.core.content.AsyncTaskLoader;
import androidx.core.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.popularmoviesstagetwo.adapters.FavoritesAdapter;
import com.example.android.popularmoviesstagetwo.data.MovieContract.MoviesEntry;
import com.example.android.popularmoviesstagetwo.utils.Utils;

public class FavoritesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = FavoritesActivity.class.getSimpleName();
    private static final int LOADER_ID = 0;
    final Context mContext = this;
    private Toast mToast;

    private FavoritesAdapter mCursorAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTextViewEmptyList;
    private ImageView mImageViewEmptyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        mRecyclerView = findViewById(R.id.list_favorites);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCursorAdapter = new FavoritesAdapter(mContext);
        mRecyclerView.setAdapter(mCursorAdapter);

        mTextViewEmptyList = findViewById(R.id.text_empty_list);
        mImageViewEmptyList = findViewById(R.id.image_empty_list);


        // Method to recognize when an item is swiped to be deleted
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int numRowDeleted = 0;

                // get id of the list item to delete
                int id = (int) viewHolder.itemView.getTag();

                // build delete uri with row id appended in String format
                String stringId = Integer.toString(id);
                Uri uri = MoviesEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                // delete a single row using the uri
                numRowDeleted = getContentResolver().delete(uri, null, null);
                if (numRowDeleted == 1) {
                    Utils.showToastMessage(mContext, mToast, getString(R.string.info_delete_successful)).show();
                } else {
                    Utils.showToastMessage(mContext, mToast, getString(R.string.error_delete)).show();
                }

                // restart the loader to re-query for all tasks after a deletion
                getSupportLoaderManager().restartLoader(LOADER_ID, null, FavoritesActivity.this);
            }

        }).attachToRecyclerView(mRecyclerView);

        // Initialize the loader
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    /**
     * Instantiates and returns a new AsyncTaskLoader with the given ID
     * @param id
     * @param args
     * @return AsyncTaskLoader<Cursor>
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
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
                            null,
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
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
        if (mCursorAdapter.getItemCount() == 0) {
            mTextViewEmptyList.setText(getString(R.string.alert_no_favorites));
            mTextViewEmptyList.setVisibility(View.VISIBLE);
            mImageViewEmptyList.setVisibility(View.VISIBLE);
        } else {
            mTextViewEmptyList.setText("");
            mTextViewEmptyList.setVisibility(View.GONE);
            mImageViewEmptyList.setVisibility(View.GONE);
        }
    }

    /**
     * Method to handle when a previously created loader is being reset, and thus making its data unavailable.
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    /**
     * Method is called after this activity has been paused or restarted
     */
    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
