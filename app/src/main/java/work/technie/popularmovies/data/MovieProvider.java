/*
 * Copyright (C) 2016 Anupam Das
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mOpenHelper;

    static final int MOVIE = 100;
    static final int MOVIE_WITH_ID = 101;

    static final int TRAILERS=200;
    static final int TRAILERS_WITH_ID=201;

    static final int REVIEWS=300;
    static final int REVIEWS_WITH_ID=301;

    static final int GENRES=400;
    static final int GENRES_WITH_ID=401;

    static final int FAVOURITES=500;
    static final int FAVOURITES_WITH_ID=501;


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIES, MOVIE);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/*", MOVIE_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_TRAILERS, TRAILERS);
        matcher.addURI(authority, MovieContract.PATH_TRAILERS + "/*", TRAILERS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_REVIEWS, REVIEWS);
        matcher.addURI(authority, MovieContract.PATH_REVIEWS + "/*", REVIEWS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_GENRES, GENRES);
        matcher.addURI(authority, MovieContract.PATH_GENRES + "/*", GENRES_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES, FAVOURITES);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES + "/*", FAVOURITES_WITH_ID);

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

            case MOVIE :
            return MovieContract.Movies.CONTENT_TYPE;
            case MOVIE_WITH_ID:
                return MovieContract.Movies.CONTENT_ITEM_TYPE;
            case FAVOURITES :
                return MovieContract.Favourites.CONTENT_TYPE;
            case FAVOURITES_WITH_ID:
                return MovieContract.Favourites.CONTENT_ITEM_TYPE;
            case TRAILERS :
                return MovieContract.Trailers.CONTENT_TYPE;
            case TRAILERS_WITH_ID:
                return MovieContract.Trailers.CONTENT_ITEM_TYPE;
            case REVIEWS :
                return MovieContract.Reviews.CONTENT_TYPE;
            case REVIEWS_WITH_ID:
                return MovieContract.Reviews.CONTENT_ITEM_TYPE;
            case GENRES :
                return MovieContract.Genres.CONTENT_TYPE;
            case GENRES_WITH_ID:
                return MovieContract.Genres.CONTENT_ITEM_TYPE;
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
            case MOVIE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Movies.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MOVIE_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Movies.TABLE_NAME,
                        projection,
                        MovieContract.Movies.MOVIE_ID+" = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case FAVOURITES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Favourites.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case FAVOURITES_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Favourites.TABLE_NAME,
                        projection,
                        MovieContract.Favourites.MOVIE_ID+" = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case TRAILERS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Trailers.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TRAILERS_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Trailers.TABLE_NAME,
                        projection,
                        MovieContract.Trailers.MOVIE_ID+" = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }case REVIEWS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Reviews.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case REVIEWS_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Reviews.TABLE_NAME,
                        projection,
                        MovieContract.Reviews.MOVIE_ID+" = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }case GENRES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Genres.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case GENRES_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Genres.TABLE_NAME,
                        projection,
                        MovieContract.Genres.MOVIE_ID+" = ?",
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
            case MOVIE: {
                long _id = db.insert(MovieContract.Movies.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.Movies.buildMoviesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FAVOURITES: {
                long _id = db.insert(MovieContract.Favourites.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.Favourites.buildMoviesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TRAILERS: {
                long _id = db.insert(MovieContract.Trailers.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.Trailers.buildMoviesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }case REVIEWS: {
                long _id = db.insert(MovieContract.Reviews.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.Reviews.buildMoviesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }case GENRES: {
                long _id = db.insert(MovieContract.Genres.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.Genres.buildMoviesUri(_id);
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
            case MOVIE:
                rowsDeleted = db.delete(
                        MovieContract.Movies.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_WITH_ID:
                rowsDeleted = db.delete(MovieContract.Movies.TABLE_NAME,
                        MovieContract.Movies.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case FAVOURITES:
                rowsDeleted = db.delete(
                        MovieContract.Favourites.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVOURITES_WITH_ID:
                rowsDeleted = db.delete(MovieContract.Favourites.TABLE_NAME,
                        MovieContract.Favourites.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TRAILERS:
                rowsDeleted = db.delete(
                        MovieContract.Trailers.TABLE_NAME, selection, selectionArgs);
                break;
            case TRAILERS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.Trailers.TABLE_NAME,
                        MovieContract.Trailers.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case REVIEWS:
                rowsDeleted = db.delete(
                        MovieContract.Reviews.TABLE_NAME, selection, selectionArgs);
                break;
            case REVIEWS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.Reviews.TABLE_NAME,
                        MovieContract.Reviews.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case GENRES:
                rowsDeleted = db.delete(
                        MovieContract.Genres.TABLE_NAME, selection, selectionArgs);
                break;
            case GENRES_WITH_ID:
                rowsDeleted = db.delete(MovieContract.Genres.TABLE_NAME,
                        MovieContract.Genres.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
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
            case MOVIE:
                rowsUpdated = db.update(MovieContract.Movies.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case MOVIE_WITH_ID: {
                rowsUpdated = db.update(MovieContract.Movies.TABLE_NAME,
                        values,
                        MovieContract.Movies.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case FAVOURITES:
                rowsUpdated = db.update(MovieContract.Favourites.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case FAVOURITES_WITH_ID: {
                rowsUpdated = db.update(MovieContract.Favourites.TABLE_NAME,
                        values,
                        MovieContract.Favourites.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
                case TRAILERS:
                    rowsUpdated = db.update(MovieContract.Trailers.TABLE_NAME, values, selection,
                            selectionArgs);
                    break;
                case TRAILERS_WITH_ID: {
                    rowsUpdated = db.update(MovieContract.Trailers.TABLE_NAME,
                            values,
                            MovieContract.Trailers.MOVIE_ID + " = ?",
                            new String[]{String.valueOf(ContentUris.parseId(uri))});
                    break;
                }
                    case REVIEWS:
                        rowsUpdated = db.update(MovieContract.Reviews.TABLE_NAME, values, selection,
                                selectionArgs);
                        break;
                    case REVIEWS_WITH_ID: {
                        rowsUpdated = db.update(MovieContract.Reviews.TABLE_NAME,
                                values,
                                MovieContract.Reviews.MOVIE_ID + " = ?",
                                new String[]{String.valueOf(ContentUris.parseId(uri))});
                        break;
                    }
                        case GENRES:
                            rowsUpdated = db.update(MovieContract.Genres.TABLE_NAME, values, selection,
                                    selectionArgs);
                            break;
                        case GENRES_WITH_ID: {
                            rowsUpdated = db.update(MovieContract.Genres.TABLE_NAME,
                                    values,
                                    MovieContract.Genres.MOVIE_ID + " = ?",
                                    new String[]{String.valueOf(ContentUris.parseId(uri))});
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
            case MOVIE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.Movies.TABLE_NAME, null, value);
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
            case FAVOURITES:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.Favourites.TABLE_NAME, null, value);
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
            case TRAILERS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.Trailers.TABLE_NAME, null, value);
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
            case REVIEWS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.Reviews.TABLE_NAME, null, value);
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
            case GENRES:
                db.beginTransaction();
                 returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.Genres.TABLE_NAME, null, value);
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
    }  @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}