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

    static final int MOVIE = 100;
    static final int MOVIE_WITH_ID = 101;
    static final int VIDEOS = 200;
    static final int VIDEOS_WITH_ID = 201;
    static final int REVIEWS = 300;
    static final int REVIEWS_WITH_ID = 301;
    static final int GENRES = 400;
    static final int GENRES_WITH_ID = 401;
    static final int FAVOURITES_MOVIES = 500;
    static final int FAVOURITES_MOVIES_WITH_ID = 501;
    static final int SIMILAR_MOVIES = 700;
    static final int SIMILAR_MOVIES_WITH_ID = 701;
    static final int CASTS = 800;
    static final int CASTS_WITH_ID = 801;
    static final int PEOPLE = 900;
    static final int PEOPLE_WITH_ID = 901;
    static final int CREW = 1000;
    static final int CREW_WITH_ID = 1001;
    static final int MOVIE_DETAILS = 1100;
    static final int MOVIE_DETAILS_WITH_ID = 1101;


    static final int TV = 600;
    static final int TV_WITH_ID = 601;
    static final int FAVOURITES_TVS = 1200;
    static final int FAVOURITES_TVS_WITH_ID = 1201;
    static final int TV_DETAILS = 1300;
    static final int TV_DETAILS_WITH_ID = 1301;
    static final int TV_CREATOR = 1400;
    static final int TV_CREATOR_WITH_ID = 1401;
    static final int TV_EPISODE_RUNTIME = 1500;
    static final int TV_EPISODE_RUNTIME_WITH_ID = 1501;
    static final int TV_GENRE = 1600;
    static final int TV_GENRE_WITH_ID = 1601;
    static final int TV_NETWORKS = 1700;
    static final int TV_NETWORKS_WITH_ID = 1701;
    static final int TV_SEASONS = 1800;
    static final int TV_SEASONS_WITH_ID = 1801;
    static final int TV_VIDEOS = 1900;
    static final int TV_VIDEOS_WITH_ID = 1901;
    static final int TV_CAST = 2000;
    static final int TV_CAST_WITH_ID = 2001;
    static final int TV_SIMILAR = 2100;
    static final int TV_SIMILAR_WITH_ID = 2101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIES, MOVIE);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/*", MOVIE_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_VIDEOS, VIDEOS);
        matcher.addURI(authority, MovieContract.PATH_VIDEOS + "/*", VIDEOS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_REVIEWS, REVIEWS);
        matcher.addURI(authority, MovieContract.PATH_REVIEWS + "/*", REVIEWS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_GENRES, GENRES);
        matcher.addURI(authority, MovieContract.PATH_GENRES + "/*", GENRES_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES_MOVIES, FAVOURITES_MOVIES);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES_MOVIES + "/*", FAVOURITES_MOVIES_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_CASTS, CASTS);
        matcher.addURI(authority, MovieContract.PATH_CASTS + "/*", CASTS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_CREWS, CREW);
        matcher.addURI(authority, MovieContract.PATH_CREWS + "/*", CREW_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_SIMILAR_MOVIES, SIMILAR_MOVIES);
        matcher.addURI(authority, MovieContract.PATH_SIMILAR_MOVIES + "/*", SIMILAR_MOVIES_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_MOVIE_DETAILS, MOVIE_DETAILS);
        matcher.addURI(authority, MovieContract.PATH_MOVIE_DETAILS + "/*", MOVIE_DETAILS_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_PEOPLE, PEOPLE);
        matcher.addURI(authority, MovieContract.PATH_PEOPLE + "/*", PEOPLE_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_TV, TV);
        matcher.addURI(authority, MovieContract.PATH_TV + "/*", TV_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES_TV, FAVOURITES_TVS);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES_TV + "/*", FAVOURITES_TVS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_TV_DETAILS, TV_DETAILS);
        matcher.addURI(authority, MovieContract.PATH_TV_DETAILS + "/*", TV_DETAILS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_TV_CREATOR, TV_CREATOR);
        matcher.addURI(authority, MovieContract.PATH_TV_CREATOR + "/*", TV_CREATOR_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_TV_EPISODE_RUNTIME, TV_EPISODE_RUNTIME);
        matcher.addURI(authority, MovieContract.PATH_TV_EPISODE_RUNTIME + "/*", TV_EPISODE_RUNTIME_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_TV_GENRES, TV_GENRE);
        matcher.addURI(authority, MovieContract.PATH_TV_GENRES + "/*", TV_GENRE_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_TV_NETWORKS, TV_NETWORKS);
        matcher.addURI(authority, MovieContract.PATH_TV_NETWORKS + "/*", TV_NETWORKS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_TV_SEASONS, TV_SEASONS);
        matcher.addURI(authority, MovieContract.PATH_TV_SEASONS + "/*", TV_SEASONS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_TV_VIDEOS, TV_VIDEOS);
        matcher.addURI(authority, MovieContract.PATH_TV_VIDEOS + "/*", TV_VIDEOS_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_TV_CAST, TV_CAST);
        matcher.addURI(authority, MovieContract.PATH_TV_CAST + "/*", TV_CAST_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_TV_SIMILAR, TV_SIMILAR);
        matcher.addURI(authority, MovieContract.PATH_TV_SIMILAR + "/*", TV_SIMILAR_WITH_ID);

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

            case MOVIE:
                return MovieContract.Movies.CONTENT_TYPE;
            case MOVIE_WITH_ID:
                return MovieContract.Movies.CONTENT_ITEM_TYPE;
            case FAVOURITES_MOVIES:
                return MovieContract.FavouritesMovies.CONTENT_TYPE;
            case FAVOURITES_MOVIES_WITH_ID:
                return MovieContract.FavouritesMovies.CONTENT_ITEM_TYPE;
            case VIDEOS:
                return MovieContract.Videos.CONTENT_TYPE;
            case VIDEOS_WITH_ID:
                return MovieContract.Videos.CONTENT_ITEM_TYPE;
            case REVIEWS:
                return MovieContract.Reviews.CONTENT_TYPE;
            case REVIEWS_WITH_ID:
                return MovieContract.Reviews.CONTENT_ITEM_TYPE;
            case GENRES:
                return MovieContract.Genres.CONTENT_TYPE;
            case GENRES_WITH_ID:
                return MovieContract.Genres.CONTENT_ITEM_TYPE;
            case CASTS:
                return MovieContract.Cast.CONTENT_TYPE;
            case CASTS_WITH_ID:
                return MovieContract.Cast.CONTENT_ITEM_TYPE;
            case CREW:
                return MovieContract.Crew.CONTENT_TYPE;
            case CREW_WITH_ID:
                return MovieContract.Crew.CONTENT_ITEM_TYPE;
            case SIMILAR_MOVIES:
                return MovieContract.SimilarMovies.CONTENT_TYPE;
            case SIMILAR_MOVIES_WITH_ID:
                return MovieContract.SimilarMovies.CONTENT_ITEM_TYPE;
            case MOVIE_DETAILS:
                return MovieContract.MovieDetails.CONTENT_TYPE;
            case MOVIE_DETAILS_WITH_ID:
                return MovieContract.MovieDetails.CONTENT_ITEM_TYPE;


            case PEOPLE:
                return MovieContract.People.CONTENT_TYPE;
            case PEOPLE_WITH_ID:
                return MovieContract.People.CONTENT_ITEM_TYPE;

            case TV:
                return MovieContract.TV.CONTENT_TYPE;
            case TV_WITH_ID:
                return MovieContract.TV.CONTENT_ITEM_TYPE;
            case FAVOURITES_TVS:
                return MovieContract.FavouritesTVs.CONTENT_TYPE;
            case FAVOURITES_TVS_WITH_ID:
                return MovieContract.FavouritesTVs.CONTENT_ITEM_TYPE;
            case TV_DETAILS:
                return MovieContract.TVDetails.CONTENT_TYPE;
            case TV_DETAILS_WITH_ID:
                return MovieContract.TVDetails.CONTENT_ITEM_TYPE;
            case TV_CREATOR:
                return MovieContract.TVCreator.CONTENT_TYPE;
            case TV_CREATOR_WITH_ID:
                return MovieContract.TVCreator.CONTENT_ITEM_TYPE;
            case TV_EPISODE_RUNTIME:
                return MovieContract.TVEpisodeRuntime.CONTENT_TYPE;
            case TV_EPISODE_RUNTIME_WITH_ID:
                return MovieContract.TVEpisodeRuntime.CONTENT_ITEM_TYPE;
            case TV_GENRE:
                return MovieContract.TVGenres.CONTENT_TYPE;
            case TV_GENRE_WITH_ID:
                return MovieContract.TVGenres.CONTENT_ITEM_TYPE;
            case TV_NETWORKS:
                return MovieContract.TVNetworks.CONTENT_TYPE;
            case TV_NETWORKS_WITH_ID:
                return MovieContract.TVNetworks.CONTENT_ITEM_TYPE;
            case TV_SEASONS:
                return MovieContract.TVSeasons.CONTENT_TYPE;
            case TV_SEASONS_WITH_ID:
                return MovieContract.TVSeasons.CONTENT_ITEM_TYPE;
            case TV_VIDEOS:
                return MovieContract.TVVideos.CONTENT_TYPE;
            case TV_VIDEOS_WITH_ID:
                return MovieContract.TVVideos.CONTENT_ITEM_TYPE;
            case TV_CAST:
                return MovieContract.TVCast.CONTENT_TYPE;
            case TV_CAST_WITH_ID:
                return MovieContract.TVCast.CONTENT_ITEM_TYPE;
            case TV_SIMILAR:
                return MovieContract.TVSimilar.CONTENT_TYPE;
            case TV_SIMILAR_WITH_ID:
                return MovieContract.TVSimilar.CONTENT_ITEM_TYPE;

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
            case MOVIE_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Movies.TABLE_NAME,
                        projection,
                        MovieContract.Movies.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case FAVOURITES_MOVIES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.FavouritesMovies.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case FAVOURITES_MOVIES_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.FavouritesMovies.TABLE_NAME,
                        projection,
                        MovieContract.FavouritesMovies.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case VIDEOS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Videos.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case VIDEOS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Videos.TABLE_NAME,
                        projection,
                        MovieContract.Videos.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case REVIEWS: {
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
            case REVIEWS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Reviews.TABLE_NAME,
                        projection,
                        MovieContract.Reviews.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case GENRES: {
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
            case GENRES_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Genres.TABLE_NAME,
                        projection,
                        MovieContract.Genres.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case SIMILAR_MOVIES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.SimilarMovies.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case SIMILAR_MOVIES_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.SimilarMovies.TABLE_NAME,
                        projection,
                        MovieContract.SimilarMovies.MOVIE_ID_OLD + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case CASTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Cast.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case CASTS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Cast.TABLE_NAME,
                        projection,
                        MovieContract.Cast.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case PEOPLE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.People.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case PEOPLE_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.People.TABLE_NAME,
                        projection,
                        MovieContract.People.ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case CREW: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Crew.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case CREW_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Crew.TABLE_NAME,
                        projection,
                        MovieContract.Crew.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case MOVIE_DETAILS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieDetails.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MOVIE_DETAILS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieDetails.TABLE_NAME,
                        projection,
                        MovieContract.MovieDetails.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TV.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TV.TABLE_NAME,
                        projection,
                        MovieContract.TV.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case FAVOURITES_TVS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.FavouritesTVs.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case FAVOURITES_TVS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.FavouritesTVs.TABLE_NAME,
                        projection,
                        MovieContract.FavouritesTVs.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV_DETAILS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVDetails.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_DETAILS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVDetails.TABLE_NAME,
                        projection,
                        MovieContract.TVDetails.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV_CREATOR: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVCreator.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_CREATOR_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVCreator.TABLE_NAME,
                        projection,
                        MovieContract.TVCreator.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV_EPISODE_RUNTIME: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVEpisodeRuntime.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_EPISODE_RUNTIME_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVEpisodeRuntime.TABLE_NAME,
                        projection,
                        MovieContract.TVEpisodeRuntime.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV_GENRE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVGenres.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_GENRE_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVGenres.TABLE_NAME,
                        projection,
                        MovieContract.TVGenres.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV_NETWORKS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVNetworks.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_NETWORKS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVNetworks.TABLE_NAME,
                        projection,
                        MovieContract.TVNetworks.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV_SEASONS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVSeasons.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_SEASONS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVSeasons.TABLE_NAME,
                        projection,
                        MovieContract.TVSeasons.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV_VIDEOS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVVideos.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_VIDEOS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVVideos.TABLE_NAME,
                        projection,
                        MovieContract.TVVideos.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV_CAST: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVCast.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_CAST_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVCast.TABLE_NAME,
                        projection,
                        MovieContract.TVCast.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }

            case TV_SIMILAR: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVSimilar.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TV_SIMILAR_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TVSimilar.TABLE_NAME,
                        projection,
                        MovieContract.TVSimilar.TV_ID_OLD + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
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
                if (_id > 0)
                    returnUri = MovieContract.Movies.buildMoviesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FAVOURITES_MOVIES: {
                long _id = db.insert(MovieContract.FavouritesMovies.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.FavouritesMovies.buildMoviesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case VIDEOS: {
                long _id = db.insert(MovieContract.Videos.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.Videos.buildMoviesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case REVIEWS: {
                long _id = db.insert(MovieContract.Reviews.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.Reviews.buildReviewsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case GENRES: {
                long _id = db.insert(MovieContract.Genres.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.Genres.buildMoviesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case SIMILAR_MOVIES: {
                long _id = db.insert(MovieContract.SimilarMovies.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.SimilarMovies.buildSimilarMoviesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CASTS: {
                long _id = db.insert(MovieContract.Cast.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.Cast.buildCastUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CREW: {
                long _id = db.insert(MovieContract.Crew.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.Crew.buildCrewUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case PEOPLE: {
                long _id = db.insert(MovieContract.People.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.People.buildPeopleUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case MOVIE_DETAILS: {
                long _id = db.insert(MovieContract.MovieDetails.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.MovieDetails.buildMovieDetailsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case TV: {
                long _id = db.insert(MovieContract.TV.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TV.buildTVUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FAVOURITES_TVS: {
                long _id = db.insert(MovieContract.FavouritesTVs.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.FavouritesTVs.buildTVUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TV_DETAILS: {
                long _id = db.insert(MovieContract.TVDetails.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TVDetails.buildTVDetailsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TV_CREATOR: {
                long _id = db.insert(MovieContract.TVCreator.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TVCreator.buildTVCreatorUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TV_EPISODE_RUNTIME: {
                long _id = db.insert(MovieContract.TVEpisodeRuntime.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TVEpisodeRuntime.buildTVEpisodeUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TV_GENRE: {
                long _id = db.insert(MovieContract.TVGenres.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TVGenres.buildTVGenreUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TV_NETWORKS: {
                long _id = db.insert(MovieContract.TVNetworks.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TVNetworks.buildTVNetworksUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TV_SEASONS: {
                long _id = db.insert(MovieContract.TVSeasons.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TVSeasons.buildTVSeasonsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TV_VIDEOS: {
                long _id = db.insert(MovieContract.TVVideos.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TVVideos.buildTVVideosUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TV_CAST: {
                long _id = db.insert(MovieContract.TVCast.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TVCast.buildTVCastUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TV_SIMILAR: {
                long _id = db.insert(MovieContract.TVSimilar.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TVSimilar.buildSimilarTVUri(_id);
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
        if (null == selection) selection = "1";
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

            case FAVOURITES_MOVIES:
                rowsDeleted = db.delete(
                        MovieContract.FavouritesMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVOURITES_MOVIES_WITH_ID:
                rowsDeleted = db.delete(MovieContract.FavouritesMovies.TABLE_NAME,
                        MovieContract.FavouritesMovies.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case VIDEOS:
                rowsDeleted = db.delete(
                        MovieContract.Videos.TABLE_NAME, selection, selectionArgs);
                break;
            case VIDEOS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.Videos.TABLE_NAME,
                        MovieContract.Videos.MOVIE_ID + " = ?",
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
            case MOVIE_DETAILS:
                rowsDeleted = db.delete(
                        MovieContract.MovieDetails.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_DETAILS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.MovieDetails.TABLE_NAME,
                        MovieContract.MovieDetails.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case SIMILAR_MOVIES:
                rowsDeleted = db.delete(
                        MovieContract.SimilarMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case SIMILAR_MOVIES_WITH_ID:
                rowsDeleted = db.delete(MovieContract.SimilarMovies.TABLE_NAME,
                        MovieContract.SimilarMovies.MOVIE_ID_OLD + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case CASTS:
                rowsDeleted = db.delete(
                        MovieContract.Cast.TABLE_NAME, selection, selectionArgs);
                break;
            case CASTS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.Cast.TABLE_NAME,
                        MovieContract.Cast.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case CREW:
                rowsDeleted = db.delete(
                        MovieContract.Crew.TABLE_NAME, selection, selectionArgs);
                break;
            case CREW_WITH_ID:
                rowsDeleted = db.delete(MovieContract.Crew.TABLE_NAME,
                        MovieContract.Crew.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case PEOPLE:
                rowsDeleted = db.delete(
                        MovieContract.People.TABLE_NAME, selection, selectionArgs);
                break;
            case PEOPLE_WITH_ID:
                rowsDeleted = db.delete(MovieContract.People.TABLE_NAME,
                        MovieContract.People.ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV:
                rowsDeleted = db.delete(
                        MovieContract.TV.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TV.TABLE_NAME,
                        MovieContract.TV.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case FAVOURITES_TVS:
                rowsDeleted = db.delete(
                        MovieContract.FavouritesTVs.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVOURITES_TVS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.FavouritesTVs.TABLE_NAME,
                        MovieContract.FavouritesTVs.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV_DETAILS:
                rowsDeleted = db.delete(
                        MovieContract.TVDetails.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_DETAILS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TVDetails.TABLE_NAME,
                        MovieContract.TVDetails.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV_CREATOR:
                rowsDeleted = db.delete(
                        MovieContract.TVCreator.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_CREATOR_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TVCreator.TABLE_NAME,
                        MovieContract.TVCreator.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV_EPISODE_RUNTIME:
                rowsDeleted = db.delete(
                        MovieContract.TVEpisodeRuntime.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_EPISODE_RUNTIME_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TVEpisodeRuntime.TABLE_NAME,
                        MovieContract.TVEpisodeRuntime.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV_GENRE:
                rowsDeleted = db.delete(
                        MovieContract.TVGenres.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_GENRE_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TVGenres.TABLE_NAME,
                        MovieContract.TVGenres.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV_NETWORKS:
                rowsDeleted = db.delete(
                        MovieContract.TVNetworks.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_NETWORKS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TVNetworks.TABLE_NAME,
                        MovieContract.TVNetworks.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV_SEASONS:
                rowsDeleted = db.delete(
                        MovieContract.TVSeasons.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_SEASONS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TVSeasons.TABLE_NAME,
                        MovieContract.TVSeasons.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV_VIDEOS:
                rowsDeleted = db.delete(
                        MovieContract.TVVideos.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_VIDEOS_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TVVideos.TABLE_NAME,
                        MovieContract.TVVideos.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV_CAST:
                rowsDeleted = db.delete(
                        MovieContract.TVCast.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_CAST_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TVCast.TABLE_NAME,
                        MovieContract.TVCast.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case TV_SIMILAR:
                rowsDeleted = db.delete(
                        MovieContract.TVSimilar.TABLE_NAME, selection, selectionArgs);
                break;
            case TV_SIMILAR_WITH_ID:
                rowsDeleted = db.delete(MovieContract.TVSimilar.TABLE_NAME,
                        MovieContract.TVSimilar.TV_ID_OLD + " = ?",
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
            case FAVOURITES_MOVIES:
                rowsUpdated = db.update(MovieContract.FavouritesMovies.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case FAVOURITES_MOVIES_WITH_ID: {
                rowsUpdated = db.update(MovieContract.FavouritesMovies.TABLE_NAME,
                        values,
                        MovieContract.FavouritesMovies.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case VIDEOS:
                rowsUpdated = db.update(MovieContract.Videos.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case VIDEOS_WITH_ID: {
                rowsUpdated = db.update(MovieContract.Videos.TABLE_NAME,
                        values,
                        MovieContract.Videos.MOVIE_ID + " = ?",
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
            case MOVIE_DETAILS:
                rowsUpdated = db.update(MovieContract.MovieDetails.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case MOVIE_DETAILS_WITH_ID: {
                rowsUpdated = db.update(MovieContract.MovieDetails.TABLE_NAME,
                        values,
                        MovieContract.MovieDetails.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case CREW:
                rowsUpdated = db.update(MovieContract.Crew.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case CREW_WITH_ID: {
                rowsUpdated = db.update(MovieContract.Crew.TABLE_NAME,
                        values,
                        MovieContract.Crew.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case CASTS:
                rowsUpdated = db.update(MovieContract.Cast.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case CASTS_WITH_ID: {
                rowsUpdated = db.update(MovieContract.Cast.TABLE_NAME,
                        values,
                        MovieContract.Cast.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case PEOPLE:
                rowsUpdated = db.update(MovieContract.People.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case PEOPLE_WITH_ID: {
                rowsUpdated = db.update(MovieContract.People.TABLE_NAME,
                        values,
                        MovieContract.People.ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case SIMILAR_MOVIES:
                rowsUpdated = db.update(MovieContract.SimilarMovies.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case SIMILAR_MOVIES_WITH_ID: {
                rowsUpdated = db.update(MovieContract.SimilarMovies.TABLE_NAME,
                        values,
                        MovieContract.SimilarMovies.MOVIE_ID_OLD + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV:
                rowsUpdated = db.update(MovieContract.TV.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TV.TABLE_NAME,
                        values,
                        MovieContract.TV.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case FAVOURITES_TVS:
                rowsUpdated = db.update(MovieContract.FavouritesTVs.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case FAVOURITES_TVS_WITH_ID: {
                rowsUpdated = db.update(MovieContract.FavouritesTVs.TABLE_NAME,
                        values,
                        MovieContract.FavouritesTVs.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV_DETAILS:
                rowsUpdated = db.update(MovieContract.TVDetails.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_DETAILS_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TVDetails.TABLE_NAME,
                        values,
                        MovieContract.TVDetails.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV_CREATOR:
                rowsUpdated = db.update(MovieContract.TVCreator.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_CREATOR_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TVCreator.TABLE_NAME,
                        values,
                        MovieContract.TVCreator.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV_EPISODE_RUNTIME:
                rowsUpdated = db.update(MovieContract.TVEpisodeRuntime.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_EPISODE_RUNTIME_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TVEpisodeRuntime.TABLE_NAME,
                        values,
                        MovieContract.TVEpisodeRuntime.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV_GENRE:
                rowsUpdated = db.update(MovieContract.TVGenres.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_GENRE_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TVGenres.TABLE_NAME,
                        values,
                        MovieContract.TVGenres.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV_NETWORKS:
                rowsUpdated = db.update(MovieContract.TVNetworks.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_NETWORKS_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TVNetworks.TABLE_NAME,
                        values,
                        MovieContract.TVNetworks.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV_SEASONS:
                rowsUpdated = db.update(MovieContract.TVSeasons.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_SEASONS_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TVSeasons.TABLE_NAME,
                        values,
                        MovieContract.TVSeasons.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV_VIDEOS:
                rowsUpdated = db.update(MovieContract.TVVideos.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_VIDEOS_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TVVideos.TABLE_NAME,
                        values,
                        MovieContract.TVVideos.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV_CAST:
                rowsUpdated = db.update(MovieContract.TVCast.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_CAST_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TVCast.TABLE_NAME,
                        values,
                        MovieContract.TVCast.TV_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV_SIMILAR:
                rowsUpdated = db.update(MovieContract.TVSimilar.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TV_SIMILAR_WITH_ID: {
                rowsUpdated = db.update(MovieContract.TVSimilar.TABLE_NAME,
                        values,
                        MovieContract.TVSimilar.TV_ID_OLD + " = ?",
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

            case FAVOURITES_MOVIES:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.FavouritesMovies.TABLE_NAME, null, value);
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
            case VIDEOS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.Videos.TABLE_NAME, null, value);
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
            case SIMILAR_MOVIES:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.SimilarMovies.TABLE_NAME, null, value);
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
            case CASTS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.Cast.TABLE_NAME, null, value);
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
            case CREW:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.Crew.TABLE_NAME, null, value);
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
            case PEOPLE:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.People.TABLE_NAME, null, value);
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
            case MOVIE_DETAILS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.MovieDetails.TABLE_NAME, null, value);
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
            case TV:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TV.TABLE_NAME, null, value);
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
            case TV_DETAILS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TVDetails.TABLE_NAME, null, value);
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

            case TV_CREATOR:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TVCreator.TABLE_NAME, null, value);
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
            case TV_EPISODE_RUNTIME:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TVEpisodeRuntime.TABLE_NAME, null, value);
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
            case TV_GENRE:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TVGenres.TABLE_NAME, null, value);
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
            case TV_NETWORKS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TVNetworks.TABLE_NAME, null, value);
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
            case TV_SEASONS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TVSeasons.TABLE_NAME, null, value);
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
            case TV_VIDEOS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TVVideos.TABLE_NAME, null, value);
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
            case TV_CAST:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TVCast.TABLE_NAME, null, value);
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
            case TV_SIMILAR:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.TVSimilar.TABLE_NAME, null, value);
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
            case FAVOURITES_TVS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.FavouritesTVs.TABLE_NAME, null, value);
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

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}