package com.example.android.popularmoviesstagetwo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmoviesstagetwo.data.MovieContract.MoviesEntry;

/**
 * Database Helper for Movies table. Manages database creation and version management.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "moviesDb.db";

    // Database version
    private static final int VERSION = 1;

    // Default Constructor
    MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * This method is called when the database is created for the first time
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String TYPE_TEXT = " TEXT";
        final String TYPE_INTEGER = " INTEGER";
        final String TYPE_TIMESTAMP = " TIMESTAMP";
        final String ATTR_PRIMARY_KEY = " PRIMARY KEY";
        final String ATTR_NOT_NULL = " NOT NULL";
        final String COMMA_SEP = ", ";

        // Create movies table
        final String SQL_CREATE_TABLE = "CREATE TABLE "  + MoviesEntry.TABLE_NAME + " (" +
                MoviesEntry._ID                     + TYPE_INTEGER      + ATTR_PRIMARY_KEY      + COMMA_SEP +
                MoviesEntry.COLUMN_MOVIE_ID         + TYPE_INTEGER      + ATTR_NOT_NULL         + COMMA_SEP +
                MoviesEntry.COLUMN_MOVIE_TITLE      + TYPE_TEXT         + ATTR_NOT_NULL         + COMMA_SEP +
                MoviesEntry.COLUMN_RELEASE_DATE     + TYPE_TEXT         + ATTR_NOT_NULL         + COMMA_SEP +
                MoviesEntry.COLUMN_LANGUAGE         + TYPE_TEXT         + ATTR_NOT_NULL         + COMMA_SEP +
                MoviesEntry.COLUMN_AVERAGE_VOTE     + TYPE_TEXT         + ATTR_NOT_NULL         + COMMA_SEP +
                MoviesEntry.COLUMN_OVERVIEW         + TYPE_TEXT         + ATTR_NOT_NULL         + COMMA_SEP +
                MoviesEntry.COLUMN_POSTER_PATH      + TYPE_TEXT         + ATTR_NOT_NULL         + COMMA_SEP +
                MoviesEntry.COLUMN_BACKDROP_PATH    + TYPE_TEXT         + ATTR_NOT_NULL         + COMMA_SEP +
                MoviesEntry.COLUMN_MOVIE_GENRES     + TYPE_TEXT         + ATTR_NOT_NULL         + COMMA_SEP +
                MoviesEntry.COLUMN_LAST_UPDATED     + TYPE_TIMESTAMP    + ATTR_NOT_NULL         + "  DEFAULT CURRENT_TIMESTAMP);";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

    }

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        final String SQL_COMMAND_DROP_TABLE = "DROP TABLE IF EXISTS ";

        sqLiteDatabase.execSQL(SQL_COMMAND_DROP_TABLE + MoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
