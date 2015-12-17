package work.technie.popularmovies.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MovieProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mOpenHelper;

    static final int POPULAR = 100;
    static final int POPULAR_WITH_ID = 101;
    static final int REVENUE = 200;
    static final int REVENUE_WITH_ID = 201;
    static final int RATED = 300;
    static final int RATED_WITH_ID = 301;
    static final int BOOKMARKS = 400;
    static final int BOOKMARKS_WITH_ID = 401;

    static UriMatcher buildUriMatcher() {

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MovieContract.PATH_SORT_BY_POP, POPULAR);
        matcher.addURI(authority, MovieContract.PATH_SORT_BY_POP + "/*", POPULAR_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_SORT_BY_REV, REVENUE);
        matcher.addURI(authority, MovieContract.PATH_SORT_BY_POP + "/*", REVENUE_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_SORT_BY_VOTE, RATED);
        matcher.addURI(authority, MovieContract.PATH_SORT_BY_POP + "/*", RATED_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_BOOKMARKS, BOOKMARKS);
        matcher.addURI(authority, MovieContract.PATH_SORT_BY_POP + "/*", BOOKMARKS_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case POPULAR_WITH_ID:
                return MovieContract.PopularMovies.CONTENT_ITEM_TYPE;
            case POPULAR:
                return MovieContract.PopularMovies.CONTENT_TYPE;

            case RATED_WITH_ID:
                return MovieContract.RatedMovies.CONTENT_ITEM_TYPE;
            case RATED:
                return MovieContract.RatedMovies.CONTENT_TYPE;

            case REVENUE_WITH_ID:
                return MovieContract.RevenueMovies.CONTENT_ITEM_TYPE;
            case REVENUE:
                return MovieContract.RevenueMovies.CONTENT_TYPE;

            case BOOKMARKS_WITH_ID:
                return MovieContract.Bookmarks.CONTENT_ITEM_TYPE;
            case BOOKMARKS :
                return MovieContract.Bookmarks.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case POPULAR: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.PopularMovies.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case POPULAR_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.PopularMovies.TABLE_NAME,
                        projection,
                        MovieContract.PopularMovies.MOVIE_ID+" = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case REVENUE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.RevenueMovies.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case REVENUE_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.RevenueMovies.TABLE_NAME,
                        projection,
                        MovieContract.RevenueMovies.MOVIE_ID+" = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case RATED: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.RatedMovies.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case RATED_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.RatedMovies.TABLE_NAME,
                        projection,
                        MovieContract.RatedMovies.MOVIE_ID+" = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case BOOKMARKS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Bookmarks.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case BOOKMARKS_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Bookmarks.TABLE_NAME,
                        projection,
                        MovieContract.Bookmarks.MOVIE_ID+" = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case POPULAR: {
                long _id = db.insert(MovieContract.PopularMovies.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.PopularMovies.buildPopularUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case RATED: {
                long _id = db.insert(MovieContract.RatedMovies.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.RatedMovies.buildRatedUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case REVENUE: {
                long _id = db.insert(MovieContract.RevenueMovies.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.RevenueMovies.buildRevenueUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case BOOKMARKS: {
                long _id = db.insert(MovieContract.Bookmarks.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.Bookmarks.buildBookmarksUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case POPULAR:
                rowsDeleted = db.delete(
                        MovieContract.PopularMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case POPULAR_WITH_ID:
                rowsDeleted = db.delete(MovieContract.PopularMovies.TABLE_NAME,
                        MovieContract.PopularMovies.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
              /*  // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        MovieContract.PopularMovies.TABLE_NAME + "'");
*/
                break;
            case REVENUE:
                rowsDeleted = db.delete(
                        MovieContract.RevenueMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case RATED_WITH_ID:
                rowsDeleted = db.delete(MovieContract.RevenueMovies.TABLE_NAME,
                        MovieContract.RevenueMovies.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
              /*  // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        MovieContract.RevenueMovies.TABLE_NAME + "'");
*/
                break;
            case RATED:
                rowsDeleted = db.delete(
                        MovieContract.RatedMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case REVENUE_WITH_ID:
                rowsDeleted = db.delete(MovieContract.RevenueMovies.TABLE_NAME,
                        MovieContract.RevenueMovies.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
              /*  // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        MovieContract.RevenueMovies.TABLE_NAME + "'");
*/
                break;
            case BOOKMARKS:
                rowsDeleted = db.delete(
                        MovieContract.Bookmarks.TABLE_NAME, selection, selectionArgs);
                break;
            case BOOKMARKS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.Bookmarks.TABLE_NAME,
                        MovieContract.Bookmarks.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
              /*  // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        MovieContract.Bookmarks.TABLE_NAME + "'");
*/
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case POPULAR:
                rowsUpdated = db.update(MovieContract.PopularMovies.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case POPULAR_WITH_ID: {
                rowsUpdated = db.update(MovieContract.PopularMovies.TABLE_NAME,
                        values,
                        MovieContract.PopularMovies.MOVIE_ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case RATED:
                rowsUpdated = db.update(MovieContract.RatedMovies.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case RATED_WITH_ID: {
                rowsUpdated = db.update(MovieContract.RatedMovies.TABLE_NAME,
                        values,
                        MovieContract.RatedMovies.MOVIE_ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case REVENUE:
                rowsUpdated = db.update(MovieContract.RevenueMovies.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case REVENUE_WITH_ID: {
                rowsUpdated = db.update(MovieContract.RevenueMovies.TABLE_NAME,
                        values,
                        MovieContract.RevenueMovies.MOVIE_ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case BOOKMARKS:
                rowsUpdated = db.update(MovieContract.Bookmarks.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case BOOKMARKS_WITH_ID: {
                rowsUpdated = db.update(MovieContract.Bookmarks.TABLE_NAME,
                        values,
                        MovieContract.Bookmarks.MOVIE_ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case POPULAR:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.PopularMovies.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case RATED:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.RatedMovies.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case REVENUE:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.RevenueMovies.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case BOOKMARKS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.PopularMovies.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;


            default:
                return super.bulkInsert(uri, values);
        }
    }

    // This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}