package com.example.android.popularmoviesstagetwo.controllers;

import com.example.android.popularmoviesstagetwo.models.Movie;
import com.example.android.popularmoviesstagetwo.models.MovieResponse;

import com.example.android.popularmoviesstagetwo.models.MovieReview;
import com.example.android.popularmoviesstagetwo.models.MovieReviewResponse;
import com.example.android.popularmoviesstagetwo.models.MovieTrailer;
import com.example.android.popularmoviesstagetwo.models.MovieTrailerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit Interface for TMDb API
 * The purpose of this interface is to define the endpoints, that include details of
 * request methods (e.g. GET, POST, etc.) and parameters.
 */

public interface MovieApiInterface {

    /**
     * Requests details of a specific movie by ID
     * @param id - as found in TMDB Movie List
     * @param apiKey - individual API key
     * @return {@link Movie} object
     */
    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    /**
     * Requests list of popular movies
     * @param apiKey - individual API key
     * @return List of movies sorted by preference (popular or top rated)
     */
    @GET("movie/{preference}")
    Call<MovieResponse> getMovieList(@Path("preference") String preference, @Query("api_key") String apiKey);

    /**
     * Requests list of trailers of a specific movie by ID
     * @param id - as found in TMDB Movie List
     * @param apiKey - individual API key
     * @return {@link MovieTrailer} object
     */
    @GET("movie/{id}/videos")
    Call<MovieTrailerResponse> getMovieTrailerList(@Path("id") int id, @Query("api_key") String apiKey);

    /**
     * Requests list of reviews of a specific movie by ID
     * @param id - as found in TMDB Movie List
     * @param apiKey - individual API key
     * @return {@link MovieReview} object
     */
    @GET("movie/{id}/reviews")
    Call<MovieReviewResponse> getMovieReviewList(@Path("id") int id, @Query("api_key") String apiKey);

}
