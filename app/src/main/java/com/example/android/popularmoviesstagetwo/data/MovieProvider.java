package com.example.android.popularmoviesstagetwo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.android.popularmoviesstagetwo.R;
import com.example.android.popularmoviesstagetwo.data.MovieContract.MoviesEntry;

/**
 * {@link ContentProvider} for Movies Database
 */

public class MovieProvider extends ContentProvider {

    // Tag for Log messages
    public static final String LOG_TAG = MovieProvider.class.getSimpleName();

    // URI matcher code for the content URI of all movie records
    private static final int CODE_MOVIES = 100;

    // URI matcher code for the content URI of a single movie record
    private static final int CODE_MOVIE_WITH_ID = 101;

    // Return value of ID when insert record fails
    private static final int ERROR_INSERT_ID = -1;

    // Database Helper object
    private MovieDbHelper mDbHelper;

    // UriMatcher object to match a content URI to a corresponding code
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private Context mContext;


    /**
     * Method to associate URI's with their int match
     * @return uriMatcher
     */
    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Matches for the task directory and a single item by ID.
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, CODE_MOVIES);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#", CODE_MOVIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDbHelper = new MovieDbHelper(mContext);
        return true;
    }


    /**
     * Function - QUERY
     * This method peforms query for given URI and load the cursor with results fetched from table.
     * The returned result can have multiple rows or a single row, depending on given URI.
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return Cursor object
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
            @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // Get instance of readable database
        SQLiteDatabase sqLiteDbReadable = mDbHelper.getReadableDatabase();

        // Cursor to hold the query result
        Cursor cursor;

        // Check if the uri matches to a specific URI CODE
        int match =  sUriMatcher.match(uri);

        switch (match) {
            case CODE_MOVIES:
                cursor =  sqLiteDbReadable.query(MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_MOVIE_WITH_ID:
                selection = MoviesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                cursor =  sqLiteDbReadable.query(MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException(mContext.getString(R.string.exception_unknown_uri, uri));
        }

        // Set notification URI on Cursor so it knows when to update in the event the data in cursor changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    /**
     * Function - INSERT
     * This method inserts records in the table
     * @param uri
     * @param contentValues
     * @return Uri
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        Uri returnUri;
        long id;

        // Get instance of writable database
        SQLiteDatabase sqLiteDbWritable = mDbHelper.getWritableDatabase();

        // Check if the uri matches to a specific URI CODE
        int match =  sUriMatcher.match(uri);

        switch (match) {
            case CODE_MOVIES:
                id = sqLiteDbWritable.insert(MoviesEntry.TABLE_NAME, null, contentValues);
                break;

            default:
                throw new UnsupportedOperationException(mContext.getString(R.string.exception_unknown_uri, uri));
        }

        // Check if ID is -1, which means record insert has failed
        if (id == ERROR_INSERT_ID) {
            Log.e(LOG_TAG, (mContext.getString(R.string.exception_insert_failed, uri)));
            return null;
        }

        returnUri = ContentUris.withAppendedId(MoviesEntry.CONTENT_URI, id);

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }


    /**
     * Function - DELETE
     * This method deletes records from the table
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return number of rows deleted
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
            @Nullable String[] selectionArgs) {

        // Get instance of writable database
        SQLiteDatabase sqLiteDbWritable = mDbHelper.getWritableDatabase();

        // Check if the uri matches to a specific URI CODE
        int match =  sUriMatcher.match(uri);

        int rowsDeleted;

        switch(match) {
            case CODE_MOVIES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = sqLiteDbWritable.delete(
                        MoviesEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case CODE_MOVIE_WITH_ID:
                // Delete a single row given by the ID in the URI
                selection = MoviesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = sqLiteDbWritable.delete(
                        MoviesEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException(mContext.getString(R.string.exception_unknown_uri, uri));
        }

        // Notify the ContentResolver of a change and return the number of items deleted
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection,
            String[] selectionArgs) {
        throw new UnsupportedOperationException(mContext.getString(R.string.exception_default_message));
    }


    /**
     * Method to determine type of URI used to query the table
     * @param uri
     * @return MIME type
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        // Check if the uri matches to a specific URI CODE
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case CODE_MOVIES:
                return MoviesEntry.CONTENT_LIST_TYPE;

            case CODE_MOVIE_WITH_ID:
                return MoviesEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException(mContext.getString(R.string.exception_default_message));
        }
    }
}
