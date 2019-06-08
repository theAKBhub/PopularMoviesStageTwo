package com.example.android.popularmoviesstagetwo.utils;

/**
 * This class is used to store global constants used throughout the app
 *
 * Created by aditibhattacharya on 27/11/2017.
 */

public class BuildConfig {

    // TODO - add TMDb API Key
    public static final String API_KEY = "ENTER YOUR KEY HERE";

    // Number of columns to be displayed in Portrait Orientation
    public static final int GRID_COLUMNS_PORTRAIT = 4;

    // Number of columns to be displayed in Landscape Orientation
    public static final int GRID_COLUMNS_LANDSCAPE = 5;

    // TMDB Base URL
    public static final String TMDB_BASE_URL = "http://api.themoviedb.org/3/";

    // Movie Poster Base URL
    public final static String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    // Backdrop Poster Base URL
    public final static String BACKDROP_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500/";

    // Video Site Name
    public final static String VIDEO_SITE_NAME = "YouTube";

    // Video Site Base URL
    public static final String BASE_VIDEO_URL = "http://www.youtube.com/watch?v=%1$s";

    // Video Thumbnail Image Base URL
    public static final String BASE_VIDEO__THUMB_URL = "http://img.youtube.com/vi/%1$s/0.jpg";

    // Duration in Milliseconds after which connection times out
    public static final int DURATION_CONNECTION_TIMEOUT = 10000;

    // Intent Extra keys
    public static final String INTENT_EXTRA_KEY_MOVIE = "Movie";
    public static final String INTENT_EXTRA_KEY_TITLE = "ActionBar Title";
}
