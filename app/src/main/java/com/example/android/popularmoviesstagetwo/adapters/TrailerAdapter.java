package com.example.android.popularmoviesstagetwo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.popularmoviesstagetwo.R;
import com.example.android.popularmoviesstagetwo.models.Movie;
import com.example.android.popularmoviesstagetwo.models.MovieTrailer;
import com.example.android.popularmoviesstagetwo.models.MovieTrailerResponse;
import com.example.android.popularmoviesstagetwo.utils.Utils;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * {@link TrailerAdapter} creates a list of movie items to a {@link android.support.v7.widget.RecyclerView}
 */

public class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private MovieTrailerResponse mMovieTrailerResponse;
    private Movie mMovie;
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
    public TrailerAdapter(MovieTrailerResponse movieTrailerResponse, Movie movie,
            TrailerAdapterOnClickHandler clickHandler) {
        mMovieTrailerResponse = movieTrailerResponse;
        mMovie = movie;
        mClickHandler = clickHandler;
    }

    /**
     * Custom ViewHolder class for the list items
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_trailer_thumb)
        ImageView mImageViewTrailerThumb;
        @BindView(R.id.text_trailer_name)
        TextView mTextViewTrailerName;

        private ItemViewHolder(View view) {
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
            MovieTrailer movieTrailer = mMovieTrailerList.get(adapterPosition - 1);
            mClickHandler.onClick(movieTrailer);
        }
    }

    /**
     * Custom ViewHolder class for list header
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.header_text)
        TextView mListHeader;

        private HeaderViewHolder(View viewHeader) {
            super(viewHeader);
            ButterKnife.bind(this, viewHeader);
        }
    }

    /**
     * Return the view type to be inflated in the adapter
     * @param position in adapter
     * @return header type (if position is 0), item type otherwise
     */
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
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

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.trailer_list_item, viewGroup, false);
            return new TrailerAdapter.ItemViewHolder(view);

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_header, viewGroup, false);
            return new TrailerAdapter.HeaderViewHolder(view);

        } else {
            return null;
        }
    }

    /**
     * Method called by RecyclerView to display the data at the specified position
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerView = (HeaderViewHolder) holder;
            headerView.mListHeader.setText(mContext.getString(R.string.label_header_trailer, mMovie.getTitle()));

        } else if (holder instanceof ItemViewHolder) {
            if (position < getItemCount()) {
                ItemViewHolder itemView = (ItemViewHolder) holder;
                MovieTrailer movieTrailer = mMovieTrailerResponse.getMovieTrailerList().get(position-1);

                if (!Utils.isEmptyString(movieTrailer.getTrailerKey())) {
                    Picasso.with(mContext)
                            .load(movieTrailer.getVideoThumbImage(movieTrailer))
                            .placeholder(R.drawable.image_placeholder)
                            .error(R.drawable.no_image_placeholder)
                            .into(itemView.mImageViewTrailerThumb);
                }
                itemView.mTextViewTrailerName.setText(movieTrailer.getTrailerName().trim());
            }
        }
    }

    /**
     * Returns number of items in the fetched list
     * @return number of list items
     */
    @Override
    public int getItemCount() {
        return (mMovieTrailerResponse == null) ? 0 : mMovieTrailerResponse.getMovieTrailerList().size() + 1;
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
