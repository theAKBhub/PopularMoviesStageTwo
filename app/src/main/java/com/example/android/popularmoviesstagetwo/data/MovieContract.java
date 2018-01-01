package com.example.android.popularmoviesstagetwo.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Database schema for Favorites Movie Database
 */

public class MovieContract {

    // Empty Constructor
    private MovieContract() {}

    // ContentProvider Authority
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmoviesstagetwo";

    // ContentProvider Base Uri
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Path for the Movies directory. This is appended to the base URI for possible URIs
    public static final String PATH_MOVIES = "movies";

    // Inner class that defines the content of the MOVIES table
    public static final class MoviesEntry implements BaseColumns {

        //Content URI
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        /** MIME type of the {@link #CONTENT_URI} for a list of items */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        /** MIME type of the {@link #CONTENT_URI} for a single item */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        // Table name
        public static final String TABLE_NAME = "movies";

        // Column names
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_LANGUAGE = "original_language";
        public static final String COLUMN_AVERAGE_VOTE = "average_vote";
        public static final String COLUMN_OVERVIEW = "movie_overview";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_MOVIE_GENRES = "movie_genres";
        public static final String COLUMN_LAST_UPDATED = "last_updated";
    }

}
