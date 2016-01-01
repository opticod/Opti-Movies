package work.technie.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import work.technie.popularmovies.data.MovieContract.Movies;
import work.technie.popularmovies.data.MovieContract.Trailers;
import work.technie.popularmovies.data.MovieContract.Genres;
import work.technie.popularmovies.data.MovieContract.Reviews;
import work.technie.popularmovies.data.MovieContract.Favourites;






public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE__TABLE = "CREATE TABLE " + Movies.TABLE_NAME + " (" +
                Movies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Movies.PAGE + "  TEXT,"+
                Movies.POSTER_PATH + "  TEXT,"+
                Movies.ADULT + "  TEXT,"+
                Movies.OVERVIEW + "  TEXT,"+
                Movies.RELEASE_DATE + "  TEXT,"+
                Movies.MOVIE_ID + "  TEXT,"+
                Movies.ORIGINAL_TITLE + "  TEXT,"+
                Movies.ORIGINAL_LANGUAGE + "  TEXT,"+
                Movies.TITLE + "  TEXT,"+
                Movies.BACKDROP_PATH + "  TEXT,"+
                Movies.POPULARITY + "  TEXT,"+
                Movies.VOTE_COUNT + "  TEXT,"+
                Movies.VOTE_AVERAGE + "  TEXT,"+
                Movies.FAVOURED + " INTEGER NOT NULL DEFAULT 0,"+
                Movies.SHOWED + " INTEGER NOT NULL DEFAULT 0,"+
                Movies.DOWNLOADED + " INTEGER NOT NULL DEFAULT 0,"+
                Movies.SORT_BY+" TEXT,"
                + "UNIQUE (" + Movies.MOVIE_ID + ") ON CONFLICT REPLACE)";
        final String SQL_CREATE__TABLE_FAVOURITES = "CREATE TABLE " + Favourites.TABLE_NAME + " (" +
                Favourites._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Favourites.PAGE + "  TEXT,"+
                Favourites.POSTER_PATH + "  TEXT,"+
                Favourites.ADULT + "  TEXT,"+
                Favourites.OVERVIEW + "  TEXT,"+
                Favourites.RELEASE_DATE + "  TEXT,"+
                Favourites.MOVIE_ID + "  TEXT,"+
                Favourites.ORIGINAL_TITLE + "  TEXT,"+
                Favourites.ORIGINAL_LANGUAGE + "  TEXT,"+
                Favourites.TITLE + "  TEXT,"+
                Favourites.BACKDROP_PATH + "  TEXT,"+
                Favourites.POPULARITY + "  TEXT,"+
                Favourites.VOTE_COUNT + "  TEXT,"+
                Favourites.VOTE_AVERAGE + "  TEXT,"+
                Favourites.FAVOURED + " INTEGER NOT NULL DEFAULT 0,"+
                Favourites.SHOWED + " INTEGER NOT NULL DEFAULT 0,"+
                Favourites.DOWNLOADED + " INTEGER NOT NULL DEFAULT 0,"+
                Favourites.SORT_BY+" TEXT,"
                + "UNIQUE (" + Favourites.MOVIE_ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_TRAILERS = "CREATE TABLE " + Trailers.TABLE_NAME + " (" +
                Trailers._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Trailers.NAME + "  TEXT,"+
                Trailers.SIZE + "  TEXT,"+
                Trailers.SOURCE + "  TEXT,"+
                Trailers.TYPE + "  TEXT,"+
                Trailers.MOVIE_ID + "  TEXT ,"+
                "UNIQUE (" + Trailers.SOURCE + ") ON CONFLICT REPLACE)";
        final String SQL_CREATE__TABLE_REVIEWS = "CREATE TABLE " + Reviews.TABLE_NAME + " (" +
                Reviews._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Reviews.PAGE + "  TEXT,"+
                Reviews.TOTAL_PAGE + "  TEXT,"+
                Reviews.TOTAL_RESULTS + "  TEXT,"+
                Reviews.ID_REVIEWS + "  TEXT,"+
                Reviews.AUTHOR + "  TEXT ,"+
                Reviews.CONTENT + "  TEXT,"+
                Reviews.URL + "  TEXT,"+
                Reviews.MOVIE_ID + "  TEXT )";
        final String SQL_CREATE__TABLE_GENRES = "CREATE TABLE " + Genres.TABLE_NAME + " (" +
                Genres._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Genres.NAME + "  TEXT,"+
                Genres.ID_GENRES + "  TEXT,"+
                Genres.MOVIE_ID + "  TEXT )";

        sqLiteDatabase.execSQL(SQL_CREATE__TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_FAVOURITES);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TRAILERS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_REVIEWS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_GENRES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
         sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Movies.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Favourites.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Trailers.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Reviews.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Genres.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}