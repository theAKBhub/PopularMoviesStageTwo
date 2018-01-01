package com.example.android.popularmoviesstagetwo.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * A {@link MovieReviewResponse} object that contains a list of Movie Trailers
 * Created by aditibhattacharya on 28/12/2017.
 */

public class MovieReviewResponse {

    /**
     * {@link MovieResponse} Attributes
     * Each attribute has a corresponding @SerializedName that is needed for GSON
     * to map the JSON keys (from TMDB API) with the attributes of {@link MovieReviewResponse} object.
     */

    // Movie Review List
    @SerializedName("results")
    private List<MovieReview> mMovieReviewList;

    // Movie Id
    @SerializedName("id")
    private int mMovieId;

    // Page Number
    @SerializedName("page")
    private int mPage;


    /**
     * Empty constructor
     */
    public MovieReviewResponse() {}

    /**
     * Default Constructor - Constructs a new {@link MovieReviewResponse} object
     * @param movieReviewList
     * @param movieId
     * @param page
     */
    private MovieReviewResponse(List<MovieReview> movieReviewList, int movieId, int page) {
        mMovieReviewList = movieReviewList;
        mMovieId = movieId;
        mPage = page;
    }

    /** Getter method - Movie Review List */
    public List<MovieReview> getMovieReviewList() {
        return mMovieReviewList;
    }

    /** Setter method - Movie Review List */
    public void setMovieReviewList(List<MovieReview> movieReviewList) {
        mMovieReviewList = movieReviewList;
    }

    /** Getter method - Movie ID */
    public int getId() {
        return mMovieId;
    }

    /** Setter method - Movie ID */
    public void setId(int movieId) {
        mMovieId = movieId;
    }

    /** Getter method - Page */
    public int getPage() {
        return mPage;
    }

    /** Setter method - Page */
    public void setPage(int page) {
        mPage = page;
    }
}
