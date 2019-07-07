package com.example.android.popularmoviesstagetwo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.popularmoviesstagetwo.DetailActivity;
import com.example.android.popularmoviesstagetwo.R;
import com.example.android.popularmoviesstagetwo.models.Movie;
import com.example.android.popularmoviesstagetwo.utils.BuildConfig;
import com.example.android.popularmoviesstagetwo.utils.Utils;
import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();


    private final Movie mMovie = DetailActivity.sCurrentMovie;
    private Animation mShowAnimation;
    private DetailActivity mParentActivity;

    private View mViewFragmentDetail;
    @BindView(R.id.image_movie)             ImageView mImageViewMoviePoster;
    @BindView(R.id.text_movie_title)        TextView mTextViewMovieTitle;
    @BindView(R.id.text_movie_date)         TextView mTextViewMovieDate;
    @BindView(R.id.text_movie_language)     TextView mTextViewMovieLanguage;
    @BindView(R.id.text_movie_genre)        TextView mTextViewMovieGenre;
    @BindView(R.id.text_movie_vote)         TextView mTextViewMovieVote;
    @BindView(R.id.text_movie_overview)     TextView mTextViewMovieOverview;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        mParentActivity = (DetailActivity) getActivity();

        // Inflate view object
        mViewFragmentDetail = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialize views
        initializeUI();
        setCustomFont();

        // Display movie data
        displayMovieData();

        return mViewFragmentDetail;
    }

    /**
     * Method to initialize UI elements
     */
    public void initializeUI() {
        ButterKnife.bind(this, mViewFragmentDetail);
        mShowAnimation = AnimationUtils.loadAnimation(mParentActivity, R.anim.poster_anim);
    }

    /**
     * Method to set custom font for views
     */
    public void setCustomFont() {
        Utils.setCustomTypeface(mParentActivity, mTextViewMovieDate);
        Utils.setCustomTypeface(mParentActivity, mTextViewMovieLanguage);
        Utils.setCustomTypeface(mParentActivity, mTextViewMovieGenre);
        Utils.setCustomTypeface(mParentActivity, mTextViewMovieVote);
        Utils.setCustomTypeface(mParentActivity, mTextViewMovieOverview);
    }

    /**
     * Method that controls display of movie data
     */
    public void displayMovieData() {
        int [] genreIds = mMovie.getGenreIds();

        // Display Backdrop Poster Image
        Picasso.with(mParentActivity)
                .load(BuildConfig.BACKDROP_POSTER_BASE_URL + mMovie.getBackdropPath())
                .placeholder(R.drawable.backdrop_image_placeholder)
                .error(R.drawable.backdrop_image_placeholder)
                .into(mImageViewMoviePoster);
        mImageViewMoviePoster.startAnimation(mShowAnimation);

        // Display Movie Title
        mTextViewMovieTitle.setText(mMovie.getTitle());

        // Display Movie Release Date
        mTextViewMovieDate.setText(Utils.getFormattedDate(mParentActivity, mMovie.getReleaseDate()));

        // Display Movie Language
        mTextViewMovieLanguage.setText(Utils.getLanguageName(mMovie.getLanguage()));

        // Display Movie Genre
        mTextViewMovieGenre.setText(mMovie.getGenres(genreIds));

        // Display Average Vote
        mTextViewMovieVote.setText(getString(R.string.label_vote_display, mMovie.getVote()));

        // Display Plot Synopsis
        mTextViewMovieOverview.setText(mMovie.getOverview());
    }
}
