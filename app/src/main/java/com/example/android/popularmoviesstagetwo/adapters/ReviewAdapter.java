package com.example.android.popularmoviesstagetwo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.popularmoviesstagetwo.R;
import com.example.android.popularmoviesstagetwo.models.MovieReview;
import com.example.android.popularmoviesstagetwo.models.MovieReviewResponse;
import com.example.android.popularmoviesstagetwo.utils.Utils;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import java.util.List;

/**
 * {@link ReviewAdapter} creates a list of movie items to a {@link RecyclerView}
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();

    private MovieReviewResponse mMovieReviewResponse;
    private List<MovieReview> mMovieReviewList;
    private Context mContext;


    /**
     * Initialize the dataset of the Adapter that contains the data to populate views to be used by RecyclerView
     * @param movieReviewResponse
     */
    public ReviewAdapter(MovieReviewResponse movieReviewResponse) {
        mMovieReviewResponse = movieReviewResponse;
    }

    /**
     * Custom ViewHolder class for the adapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expand_view)
        ExpandableTextView mExpandableView;
        @BindView(R.id.expandable_text)
        TextView mTextViewReview;
        @BindView(R.id.text_review_author)
        TextView mTextViewReviewAuthor;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Utils.setCustomTypeface(mContext, mTextViewReview);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_list_item, viewGroup, false);
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
            MovieReview movieReview = mMovieReviewResponse.getMovieReviewList().get(position);
            viewHolder.mTextViewReviewAuthor.setText(mContext.
                    getString(R.string.label_review_by, movieReview.getReviewAuthor().trim()));
            viewHolder.mExpandableView.setText(movieReview.getReviewContent().replace("_", "").trim());
        }
    }

    /**
     * Returns number of items in the fetched list
     * @return number of list items
     */
    @Override
    public int getItemCount() {
        return (mMovieReviewResponse == null) ? 0 : mMovieReviewResponse.getMovieReviewList().size();
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
