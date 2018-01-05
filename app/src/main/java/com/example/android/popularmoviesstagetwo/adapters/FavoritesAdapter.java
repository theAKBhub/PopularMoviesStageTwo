package com.example.android.popularmoviesstagetwo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.popularmoviesstagetwo.R;
import com.example.android.popularmoviesstagetwo.data.MovieContract.MoviesEntry;
import com.example.android.popularmoviesstagetwo.utils.BuildConfig;
import com.example.android.popularmoviesstagetwo.utils.Utils;
import com.squareup.picasso.Picasso;

/**
 * {@link FavoritesAdapter} creates a list of movie items to a {@link RecyclerView}
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ItemViewHolder> {

    private static final String LOG_TAG = FavoritesAdapter.class.getSimpleName();

    private Cursor mCursor;
    private Context mContext;


    /**
     * Initialize the Adapter that contains the data to populate views to be used by RecyclerView
     * @param context
     */
    public FavoritesAdapter(Context context) {
        mContext = context;
    }

    /**
     * Custom ViewHolder class for the list items
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_favorite_movie)
        ImageView mImageViewFavoriteMovie;
        @BindView(R.id.text_favorite_movie_name)
        TextView mTextViewFavoriteMovie;

        private ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Utils.setCustomTypeface(mContext, mTextViewFavoriteMovie);
        }
    }

    /**
     * Method called when a new ViewHolder gets created in the event of RecyclerView being laid out.
     * This creates enough ViewHolders to fill up the screen and allow scrolling
     * @param viewGroup
     * @param viewType
     * @return ViewHolder that holds the View for each list item
     */
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.favorites_list_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    /**
     * Method called by RecyclerView to display the data at the specified position
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String moviePosterUrl = "";

        // Get indices for the _id, movie id, movie title, and movie poster image columns
        int idIndex = mCursor.getColumnIndex(MoviesEntry._ID);
        int movieIdIndex = mCursor.getColumnIndex(MoviesEntry.COLUMN_MOVIE_ID);
        int movieTitleIndex = mCursor.getColumnIndex(MoviesEntry.COLUMN_MOVIE_TITLE);
        int moviePosterIndex = mCursor.getColumnIndex(MoviesEntry.COLUMN_POSTER_PATH);

        // Fetch the right location in the cursor
        mCursor.moveToPosition(position);

        // Fetch data
        final int rowId = mCursor.getInt(idIndex);
        final int movieId = mCursor.getInt(movieIdIndex);
        String movieTitle = mCursor.getString(movieTitleIndex);
        String moviePoster = mCursor.getString(moviePosterIndex);

        //Set values
        holder.itemView.setTag(rowId);
        holder.mTextViewFavoriteMovie.setText(movieTitle);

        if (!Utils.isEmptyString(moviePoster)) {
            moviePosterUrl = BuildConfig.MOVIE_POSTER_BASE_URL + moviePoster;
            Picasso.with(mContext)
                    .load(moviePosterUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.no_image_placeholder)
                    .into(holder.mImageViewFavoriteMovie);
        }
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor a newly updated Cursor
     */
    public Cursor swapCursor(Cursor cursor) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == cursor) {
            return null;
        }
        Cursor tempCursor = mCursor; // store old cursor value in a temporary variable
        mCursor = cursor; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return tempCursor;
    }


    /**
     * Returns number of items in the fetched list
     * @return number of list items
     */
    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }
}
