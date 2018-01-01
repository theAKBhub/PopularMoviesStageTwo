package com.example.android.popularmoviesstagetwo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.popularmoviesstagetwo.R;
import com.example.android.popularmoviesstagetwo.models.MovieTrailer;
import com.example.android.popularmoviesstagetwo.models.MovieTrailerResponse;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * {@link MovieListAdapter} creates a list of movie items to a {@link android.support.v7.widget.RecyclerView}
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private MovieTrailerResponse mMovieTrailerResponse;
    private List<MovieTrailer> mMovieTrailerList;
    //private TrailerAdapterOnClickHandler mClickHandler;
    final private ListItemClickListener mOnClickListener;
    private int mNumberItems;
    private Context mContext;

    /**
     * Interface to receive onClick messages
     */
    /*public interface TrailerAdapterOnClickHandler {
        void onClick(MovieTrailer movieTrailer);
    }*/
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    /**
     * OnClick handler for the adapter that handles situation when a single item is clicked
     * @param numberOfItems Number of items to display in list
     * @param listener Listener for list item clicks
     */
    public TrailerAdapter(int numberOfItems, ListItemClickListener listener) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_trailer_thumb)
        ImageView mImageViewTrailerThumb;
        @BindView(R.id.text_trailer_name)
        TextView mTextViewTrailerName;

        private TrailerAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        /**
         * This method gets called when child view is clicked
         * @param view
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieTrailer movieTrailer = mMovieTrailerResponse.getMovieTrailerList().get(adapterPosition);
            mOnClickListener.onListItemClick(adapterPosition);
        }
    }

    /**
     * Method called when a new ViewHolder gets created in the event of RecyclerView being laid out.
     * This creates enough ViewHolders to fill up the screen and allow scrolling
     * @param viewGroup
     * @param viewType
     * @return A new MovieListAdapterViewHolder that holds the View for each list item
     */
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int listItemLayoutId = R.layout.trailer_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(listItemLayoutId, viewGroup, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    /**
     * Method used by RecyclerView to display the movie trailer thumbnail image and official name
     * @param trailerAdapterViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder trailerAdapterViewHolder, int position) {
        String moviePoster = "";
        String moviePosterUrl = "";

        if (position < getItemCount()) {
            MovieTrailer movieTrailer = mMovieTrailerResponse.getMovieTrailerList().get(position);
            /*moviePoster = movie.getPosterPath();
            if (!Utils.isEmptyString(moviePoster)) {
                moviePosterUrl = BuildConfig.MOVIE_POSTER_BASE_URL + moviePoster;
                Picasso.with(mContext)
                        .load(moviePosterUrl)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.no_image_placeholder)
                        .into(movieListAdapterViewHolder.mImageViewPoster);
            }*/
            Picasso.with(mContext)
                    .load("http://img.youtube.com/vi/OoI57NeMwCc/0.jpg")
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.no_image_placeholder)
                    .into(trailerAdapterViewHolder.mImageViewTrailerThumb);

            trailerAdapterViewHolder.mTextViewTrailerName.setText(movieTrailer.getTrailerName());
        }
    }

    /**
     * Returns number of items in the fetched movie trailer list
     * @return number of movie trailer items
     */
    @Override
    public int getItemCount() {
        return (mMovieTrailerResponse == null) ? 0 : mMovieTrailerResponse.getMovieTrailerList().size();
    }

    /**
     * Method used to refresh the movie list once the MovieListAdapter is
     * already created, to avoid creating a new MovieListAdapter
     * @param movieTrailerResponse - the new movie set to be displayed
     */
    public void setMovieData(MovieTrailerResponse movieTrailerResponse) {
        mMovieTrailerResponse = movieTrailerResponse;
        mMovieTrailerList = mMovieTrailerResponse.getMovieTrailerList();
        notifyDataSetChanged();
    }

}
