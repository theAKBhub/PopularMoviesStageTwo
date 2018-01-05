package com.example.android.popularmoviesstagetwo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.popularmoviesstagetwo.R;
import com.example.android.popularmoviesstagetwo.models.Movie;
import com.example.android.popularmoviesstagetwo.models.MovieReview;
import com.example.android.popularmoviesstagetwo.models.MovieReviewResponse;
import com.example.android.popularmoviesstagetwo.utils.Utils;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import java.util.List;

/**
 * {@link ReviewAdapter} creates a list of movie items to a {@link RecyclerView}
 */

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private MovieReviewResponse mMovieReviewResponse;
    private Movie mMovie;
    private List<MovieReview> mMovieReviewList;
    private Context mContext;


    /**
     * Initialize the dataset of the Adapter that contains the data to populate views to be used by RecyclerView
     * @param movieReviewResponse
     * @param movie
     */
    public ReviewAdapter(MovieReviewResponse movieReviewResponse, Movie movie) {
        mMovieReviewResponse = movieReviewResponse;
        mMovie = movie;
    }

    /**
     * Custom ViewHolder class for the list items
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expand_view)
        ExpandableTextView mExpandableView;
        @BindView(R.id.expandable_text)
        TextView mTextViewReview;
        @BindView(R.id.text_review_author)
        TextView mTextViewReviewAuthor;

        private ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Utils.setCustomTypeface(mContext, mTextViewReview);
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.review_list_item, viewGroup, false);
            return new ItemViewHolder(view);

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_header, viewGroup, false);
            return new HeaderViewHolder(view);

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
            headerView.mListHeader.setText(mContext.getString(R.string.label_header_review, mMovie.getTitle()));

        } else if (holder instanceof ItemViewHolder) {
            if (position < getItemCount()) {
                ItemViewHolder itemView = (ItemViewHolder) holder;
                MovieReview movieReview = mMovieReviewResponse.getMovieReviewList().get(position-1);
                itemView.mTextViewReviewAuthor.setText(mContext.
                        getString(R.string.label_review_by, movieReview.getReviewAuthor().trim()));
                itemView.mExpandableView.setText(movieReview.getReviewContent().replace("_", "").trim());
            }
        }
    }

    /**
     * Returns number of items in the fetched list
     * @return number of list items
     */
    @Override
    public int getItemCount() {
        return (mMovieReviewResponse == null) ? 0 : mMovieReviewResponse.getMovieReviewList().size() + 1;
    }

    /**
     * Method used to refresh the list once the Adapter is already created, to avoid creating a new Adapter
     * @param movieReviewResponse - the new movie set to be displayed
     */
    public void setReviewData(MovieReviewResponse movieReviewResponse) {
        mMovieReviewResponse = movieReviewResponse;
        mMovieReviewList = movieReviewResponse.getMovieReviewList();
        notifyDataSetChanged();
    }
}
