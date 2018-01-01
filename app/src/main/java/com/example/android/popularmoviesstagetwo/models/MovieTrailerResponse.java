package com.example.android.popularmoviesstagetwo.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * A {@link MovieTrailerResponse} object that contains a list of Movie Trailers
 * Created by aditibhattacharya on 28/12/2017.
 */

public class MovieTrailerResponse {

    /**
     * {@link MovieResponse} Attributes
     * Each attribute has a corresponding @SerializedName that is needed for GSON
     * to map the JSON keys (from TMDB API) with the attributes of {@link MovieTrailerResponse} object.
     */

    // Movie Trailer List
    @SerializedName("results")
    private List<MovieTrailer> mMovieTrailerList;

    // Movie Id
    @SerializedName("id")
    private int mMovieId;


    /**
     * Empty constructor
     */
    public MovieTrailerResponse() {}

    /**
     * Default Constructor - Constructs a new {@link MovieTrailerResponse} object
     * @param movieTrailerList
     * @param movieId
     */
    private MovieTrailerResponse(List<MovieTrailer> movieTrailerList, int movieId) {
        mMovieTrailerList = movieTrailerList;
        mMovieId = movieId;
    }

    /** Getter method - Movie Trailer List */
    public List<MovieTrailer> getMovieTrailerList() {
        return mMovieTrailerList;
    }

    /** Setter method - Movie Trailer List */
    public void setMovieTrailerList(List<MovieTrailer> movieTrailerList) {
        mMovieTrailerList = movieTrailerList;
    }

    /** Getter method - Movie ID */
    public int getId() {
        return mMovieId;
    }

    /** Setter method - Movie ID */
    public void setId(int movieId) {
        mMovieId = movieId;
    }
}
