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
import com.example.android.popularmoviesstagetwo.utils.Utils;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * {@link TrailerAdapter} creates a list of movie items to a {@link android.support.v7.widget.RecyclerView}
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();

    private MovieTrailerResponse mMovieTrailerResponse;
    private List<MovieTrailer> mMovieTrailerList;
    private TrailerAdapter.TrailerAdapterOnClickHandler mClickHandler;
    private Context mContext;


    /**
     * Interface to receive onClick messages
     */
    public interface TrailerAdapterOnClickHandler {
        void onClick(MovieTrailer movieTrailer);
    }

    /**
     * Initialize the dataset of the Adapter that contains the data to populate views to be used by RecyclerView
     * @param movieTrailerResponse
     */
    public TrailerAdapter(MovieTrailerResponse movieTrailerResponse, TrailerAdapterOnClickHandler clickHandler) {
        mMovieTrailerResponse = movieTrailerResponse;
        mClickHandler = clickHandler;
    }

    /**
     * Custom ViewHolder class for the adapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_trailer_thumb)
        ImageView mImageViewTrailerThumb;
        @BindView(R.id.text_trailer_name)
        TextView mTextViewTrailerName;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Utils.setCustomTypeface(mContext, mTextViewTrailerName);
            view.setOnClickListener(this);
        }

        /**
         * This method gets called when child view is clicked
         * @param view
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieTrailer movieTrailer = mMovieTrailerList.get(adapterPosition);
            mClickHandler.onClick(movieTrailer);
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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.trailer_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * Method used by RecyclerView to display the movie trailer thumbnail image and official name
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (position < getItemCount()) {
            MovieTrailer movieTrailer = mMovieTrailerResponse.getMovieTrailerList().get(position);

            if (!Utils.isEmptyString(movieTrailer.getTrailerKey())) {
                Picasso.with(mContext)
                        .load(movieTrailer.getVideoThumbImage(movieTrailer))
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.no_image_placeholder)
                        .into(viewHolder.mImageViewTrailerThumb);
            }
            viewHolder.mTextViewTrailerName.setText(movieTrailer.getTrailerName().trim());
        }
    }

    /**
     * Returns number of items in the fetched list
     * @return number of list items
     */
    @Override
    public int getItemCount() {
        return (mMovieTrailerResponse == null) ? 0 : mMovieTrailerResponse.getMovieTrailerList().size();
    }

    /**
     * Method used to refresh the list once the Adapter is already created, to avoid creating a new Adapter
     * @param movieTrailerResponse - the new movie set to be displayed
     */
    public void setTrailerData(MovieTrailerResponse movieTrailerResponse) {
        mMovieTrailerResponse = movieTrailerResponse;
        mMovieTrailerList = movieTrailerResponse.getMovieTrailerList();
        notifyDataSetChanged();
    }
}
