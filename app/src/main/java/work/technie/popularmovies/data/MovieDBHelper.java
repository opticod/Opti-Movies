package work.technie.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import work.technie.popularmovies.data.MovieContract.Movies;



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
                Movies.FAVOURED + " INTEGER NOT NULL DEFAULT 0,"
                + "UNIQUE (" + Movies.MOVIE_ID + ") ON CONFLICT REPLACE)";

        sqLiteDatabase.execSQL(SQL_CREATE__TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
         sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Movies.TABLE_NAME);
         onCreate(sqLiteDatabase);
    }
}