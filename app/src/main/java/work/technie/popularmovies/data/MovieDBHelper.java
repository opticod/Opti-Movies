package work.technie.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import work.technie.popularmovies.data.MovieContract.Bookmarks;
import work.technie.popularmovies.data.MovieContract.PopularMovies;
import work.technie.popularmovies.data.MovieContract.RatedMovies;
import work.technie.popularmovies.data.MovieContract.RevenueMovies;



public class MovieDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_POPULAR_TABLE = "CREATE TABLE " + PopularMovies.TABLE_NAME + " (" +
                PopularMovies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PopularMovies.PAGE + "  TEXT,"+
                PopularMovies.POSTER_PATH + "  TEXT,"+
                PopularMovies.ADULT + "  TEXT,"+
                PopularMovies.OVERVIEW + "  TEXT,"+
                PopularMovies.RELEASE_DATE + "  TEXT,"+
                PopularMovies.MOVIE_ID + "  TEXT,"+
                PopularMovies.ORIGINAL_TITLE + "  TEXT,"+
                PopularMovies.ORIGINAL_LANGUAGE + "  TEXT,"+
                PopularMovies.TITLE + "  TEXT,"+
                PopularMovies.BACKDROP_PATH + "  TEXT,"+
                PopularMovies.POPULARITY + "  TEXT,"+
                PopularMovies.VOTE_COUNT + "  TEXT,"+
                PopularMovies.VOTE_AVERAGE + "  TEXT);";

        final String SQL_CREATE_RATED_TABLE = "CREATE TABLE " + RatedMovies.TABLE_NAME + " (" +
                RatedMovies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RatedMovies.PAGE + "  TEXT,"+
                RatedMovies.POSTER_PATH + "  TEXT,"+
                RatedMovies.ADULT + "  TEXT,"+
                RatedMovies.OVERVIEW + "  TEXT,"+
                RatedMovies.RELEASE_DATE + "  TEXT,"+
                RatedMovies.MOVIE_ID + "  TEXT,"+
                RatedMovies.ORIGINAL_TITLE + "  TEXT,"+
                RatedMovies.ORIGINAL_LANGUAGE + "  TEXT,"+
                RatedMovies.TITLE + "  TEXT,"+
                RatedMovies.BACKDROP_PATH + "  TEXT,"+
                RatedMovies.POPULARITY + "  TEXT,"+
                RatedMovies.VOTE_COUNT + "  TEXT,"+
                RatedMovies.VOTE_AVERAGE + "  TEXT);";

        final String SQL_CREATE_REVENUE_TABLE = "CREATE TABLE " + RevenueMovies.TABLE_NAME + " (" +
                RevenueMovies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RevenueMovies.PAGE + "  TEXT,"+
                RevenueMovies.POSTER_PATH + "  TEXT,"+
                RevenueMovies.ADULT + "  TEXT,"+
                RevenueMovies.OVERVIEW + "  TEXT,"+
                RevenueMovies.RELEASE_DATE + "  TEXT,"+
                RevenueMovies.MOVIE_ID + "  TEXT,"+
                RevenueMovies.ORIGINAL_TITLE + "  TEXT,"+
                RevenueMovies.ORIGINAL_LANGUAGE + "  TEXT,"+
                RevenueMovies.TITLE + "  TEXT,"+
                RevenueMovies.BACKDROP_PATH + "  TEXT,"+
                RevenueMovies.POPULARITY + "  TEXT,"+
                RevenueMovies.VOTE_COUNT + "  TEXT,"+
                RevenueMovies.VOTE_AVERAGE + "  TEXT);";

        final String SQL_CREATE_BOOKMARKS_TABLE = "CREATE TABLE " + Bookmarks.TABLE_NAME + " (" +
                Bookmarks._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Bookmarks.PAGE + "  TEXT,"+
                Bookmarks.POSTER_PATH + "  TEXT,"+
                Bookmarks.ADULT + "  TEXT,"+
                Bookmarks.OVERVIEW + "  TEXT,"+
                Bookmarks.RELEASE_DATE + "  TEXT,"+
                Bookmarks.MOVIE_ID + "  TEXT,"+
                Bookmarks.ORIGINAL_TITLE + "  TEXT,"+
                Bookmarks.ORIGINAL_LANGUAGE + "  TEXT,"+
                Bookmarks.TITLE + "  TEXT,"+
                Bookmarks.BACKDROP_PATH + "  TEXT,"+
                Bookmarks.POPULARITY + "  TEXT,"+
                Bookmarks.VOTE_COUNT + "  TEXT,"+
                Bookmarks.VOTE_AVERAGE + "  TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_POPULAR_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_RATED_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REVENUE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_BOOKMARKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PopularMovies.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RatedMovies.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RevenueMovies.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Bookmarks.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}