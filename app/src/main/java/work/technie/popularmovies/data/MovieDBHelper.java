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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import work.technie.popularmovies.data.MovieContract.Cast;
import work.technie.popularmovies.data.MovieContract.Crew;
import work.technie.popularmovies.data.MovieContract.FavouritesMovies;
import work.technie.popularmovies.data.MovieContract.FavouritesTVs;
import work.technie.popularmovies.data.MovieContract.Genres;
import work.technie.popularmovies.data.MovieContract.MovieDetails;
import work.technie.popularmovies.data.MovieContract.Movies;
import work.technie.popularmovies.data.MovieContract.People;
import work.technie.popularmovies.data.MovieContract.Reviews;
import work.technie.popularmovies.data.MovieContract.SimilarMovies;
import work.technie.popularmovies.data.MovieContract.TV;
import work.technie.popularmovies.data.MovieContract.TVCast;
import work.technie.popularmovies.data.MovieContract.TVCreator;
import work.technie.popularmovies.data.MovieContract.TVDetails;
import work.technie.popularmovies.data.MovieContract.TVEpisodeRuntime;
import work.technie.popularmovies.data.MovieContract.TVGenres;
import work.technie.popularmovies.data.MovieContract.TVNetworks;
import work.technie.popularmovies.data.MovieContract.TVSeasons;
import work.technie.popularmovies.data.MovieContract.TVSimilar;
import work.technie.popularmovies.data.MovieContract.TVVideos;
import work.technie.popularmovies.data.MovieContract.Videos;


public class MovieDBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 2;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE__TABLE = "CREATE TABLE " + Movies.TABLE_NAME + " (" +
                Movies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Movies.PAGE + "  TEXT," +
                Movies.POSTER_PATH + "  TEXT," +
                Movies.ADULT + "  TEXT," +
                Movies.OVERVIEW + "  TEXT," +
                Movies.RELEASE_DATE + "  TEXT," +
                Movies.MOVIE_ID + "  TEXT," +
                Movies.ORIGINAL_TITLE + "  TEXT," +
                Movies.ORIGINAL_LANGUAGE + "  TEXT," +
                Movies.TITLE + "  TEXT," +
                Movies.BACKDROP_PATH + "  TEXT," +
                Movies.POPULARITY + "  TEXT," +
                Movies.VOTE_COUNT + "  TEXT," +
                Movies.VOTE_AVERAGE + "  TEXT," +
                Movies.FAVOURED + " INTEGER NOT NULL DEFAULT 0," +
                Movies.SHOWED + " INTEGER NOT NULL DEFAULT 0," +
                Movies.DOWNLOADED + " INTEGER NOT NULL DEFAULT 0," +
                Movies.MODE + " TEXT," +
                Movies.PREF_LANGUAGE + " TEXT," +
                Movies.PREF_ADULT + " TEXT," +
                Movies.PREF_REGION + " TEXT,"
                + "UNIQUE (" + Movies.MOVIE_ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_SIMILAR_MOVIES = "CREATE TABLE " + SimilarMovies.TABLE_NAME + " (" +
                SimilarMovies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SimilarMovies.MOVIE_ID_OLD + "  TEXT," +
                SimilarMovies.PAGE + "  TEXT," +
                SimilarMovies.POSTER_PATH + "  TEXT," +
                SimilarMovies.ADULT + "  TEXT," +
                SimilarMovies.OVERVIEW + "  TEXT," +
                SimilarMovies.RELEASE_DATE + "  TEXT," +
                SimilarMovies.MOVIE_ID + "  TEXT," +
                SimilarMovies.ORIGINAL_TITLE + "  TEXT," +
                SimilarMovies.ORIGINAL_LANGUAGE + "  TEXT," +
                SimilarMovies.TITLE + "  TEXT," +
                SimilarMovies.BACKDROP_PATH + "  TEXT," +
                SimilarMovies.POPULARITY + "  TEXT," +
                SimilarMovies.VOTE_COUNT + "  TEXT," +
                SimilarMovies.VOTE_AVERAGE + "  TEXT," +
                SimilarMovies.FAVOURED + " INTEGER NOT NULL DEFAULT 0," +
                SimilarMovies.SHOWED + " INTEGER NOT NULL DEFAULT 0," +
                SimilarMovies.DOWNLOADED + " INTEGER NOT NULL DEFAULT 0,"
                + "UNIQUE (" + SimilarMovies.MOVIE_ID_OLD + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_TV_SIMILAR = "CREATE TABLE " + TVSimilar.TABLE_NAME + " (" +
                TVSimilar._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TVSimilar.TV_ID_OLD + "  TEXT," +
                TVSimilar.PAGE + "  TEXT," +
                TVSimilar.POSTER_PATH + "  TEXT," +
                TVSimilar.OVERVIEW + "  TEXT," +
                TVSimilar.FIRST_AIR_DATE + "  TEXT," +
                TVSimilar.TV_ID + "  TEXT," +
                TVSimilar.ORIGINAL_NAME + "  TEXT," +
                TVSimilar.ORIGINAL_LANGUAGE + "  TEXT," +
                TVSimilar.NAME + "  TEXT," +
                TVSimilar.BACKDROP_PATH + "  TEXT," +
                TVSimilar.POPULARITY + "  TEXT," +
                TVSimilar.VOTE_COUNT + "  TEXT," +
                TVSimilar.VOTE_AVERAGE + "  TEXT," +
                TVSimilar.FAVOURED + " INTEGER NOT NULL DEFAULT 0,"
                + "UNIQUE (" + TVSimilar.TV_ID_OLD + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_MOVIE_DETAILS = "CREATE TABLE " + MovieDetails.TABLE_NAME + " (" +
                MovieDetails._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieDetails.MOVIE_ID + "  TEXT," +
                MovieDetails.ADULT + "  TEXT," +
                MovieDetails.BACKDROP_PATH + "  TEXT," +
                MovieDetails.BUDGET + "  TEXT," +
                MovieDetails.HOMEPAGE + "  TEXT," +
                MovieDetails.ORIGINAL_LANGUAGE + "  TEXT," +
                MovieDetails.ORIGINAL_TITLE + "  TEXT," +
                MovieDetails.OVERVIEW + "  TEXT," +
                MovieDetails.POPULARITY + "  TEXT," +
                MovieDetails.POSTER_PATH + "  TEXT," +
                MovieDetails.RELEASE_DATE + "  TEXT," +
                MovieDetails.REVENUE + "  TEXT," +
                MovieDetails.RUNTIME + "  TEXT," +
                MovieDetails.STATUS + "  TEXT," +
                MovieDetails.TAGLINE + "  TEXT," +
                MovieDetails.TITLE + "  TEXT," +
                MovieDetails.VOTE_AVERAGE + "  TEXT," +
                MovieDetails.VOTE_COUNT + "  TEXT," +
                MovieDetails.FAVOURED + " INTEGER NOT NULL DEFAULT 0," +
                MovieDetails.SHOWED + " INTEGER NOT NULL DEFAULT 0," +
                MovieDetails.DOWNLOADED + " INTEGER NOT NULL DEFAULT 0,"
                + "UNIQUE (" + MovieDetails.MOVIE_ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_TV_DETAILS = "CREATE TABLE " + TVDetails.TABLE_NAME + " (" +
                TVDetails._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TVDetails.TV_ID + "  TEXT," +
                TVDetails.BACKDROP_PATH + "  TEXT," +
                TVDetails.FIRST_AIR_DATE + "  TEXT," +
                TVDetails.HOMEPAGE + "  TEXT," +
                TVDetails.IN_PRODUCTION + "  TEXT," +
                TVDetails.LAST_AIR_DATE + "  TEXT," +
                TVDetails.NAME + "  TEXT," +
                TVDetails.NUMBER_OF_EPISODES + "  TEXT," +
                TVDetails.NUMBER_OF_SEASONS + "  TEXT," +
                TVDetails.ORIGINAL_LANG + "  TEXT," +
                TVDetails.ORIGINAL_NAME + "  TEXT," +
                TVDetails.OVERVIEW + "  TEXT," +
                TVDetails.POPULARITY + "  TEXT," +
                TVDetails.POSTER_PATH + "  TEXT," +
                TVDetails.STATUS + "  TEXT," +
                TVDetails.TYPE + "  TEXT," +
                TVDetails.VOTE_AVERAGE + "  TEXT," +
                TVDetails.VOTE_COUNT + "  TEXT," +
                TVDetails.FAVOURED + " INTEGER NOT NULL DEFAULT 0,"
                + "UNIQUE (" + TVDetails.TV_ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_CAST = "CREATE TABLE " + Cast.TABLE_NAME + " (" +
                Cast._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Cast.CAST_ID + "  TEXT," +
                Cast.CHARACTER + "  TEXT," +
                Cast.CREDIT_ID + "  TEXT," +
                Cast.ID + "  TEXT," +
                Cast.NAME + "  TEXT," +
                Cast.ORDER + "  TEXT," +
                Cast.PROFILE_PATH + "  TEXT," +
                Cast.MOVIE_ID + "  TEXT,"
                + "UNIQUE (" + Cast.MOVIE_ID + " , " + Cast.CAST_ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_TVCAST = "CREATE TABLE " + TVCast.TABLE_NAME + " (" +
                TVCast._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TVCast.CHARACTER + "  TEXT," +
                TVCast.CREDIT_ID + "  TEXT," +
                TVCast.ID + "  TEXT," +
                TVCast.NAME + "  TEXT," +
                TVCast.ORDER + "  TEXT," +
                TVCast.PROFILE_PATH + "  TEXT," +
                TVCast.TV_ID + "  TEXT,"
                + "UNIQUE (" + TVCast.TV_ID + " , " + TVCast.ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_TV_CREATOR = "CREATE TABLE " + TVCreator.TABLE_NAME + " (" +
                TVCreator._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TVCreator.TV_ID + "  TEXT," +
                TVCreator.ID + "  TEXT," +
                TVCreator.NAME + "  TEXT," +
                TVCreator.PROFILE_PATH + "  TEXT,"
                + "UNIQUE (" + TVCreator.TV_ID + " , " + TVCreator.ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_TV_EPISODE_RUNTIME = "CREATE TABLE " + TVEpisodeRuntime.TABLE_NAME + " (" +
                TVEpisodeRuntime._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TVEpisodeRuntime.TV_ID + "  TEXT," +
                TVEpisodeRuntime.TIME + "  TEXT,"
                + "UNIQUE (" + TVEpisodeRuntime.TV_ID + " , " + TVEpisodeRuntime.TIME + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_PEOPLE = "CREATE TABLE " + People.TABLE_NAME + " (" +
                People._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                People.ADULT + "  TEXT," +
                People.BIOGRAPHY + "  TEXT," +
                People.BIRTHDAY + "  TEXT," +
                People.DEATHDAY + "  TEXT," +
                People.GENDER + "  TEXT," +
                People.HOMEPAGE + "  TEXT," +
                People.ID + "  TEXT," +
                People.NAME + "  TEXT," +
                People.PLACE_OF_BIRTH + "  TEXT," +
                People.POPULARITY + "  TEXT," +
                People.PROFILE_PATH + "  TEXT,"
                + "UNIQUE (" + People.ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_CREW = "CREATE TABLE " + Crew.TABLE_NAME + " (" +
                Crew._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Crew.CREDIT_ID + "  TEXT," +
                Crew.DEPARTMENT + "  TEXT," +
                Crew.ID + "  TEXT," +
                Crew.JOB + "  TEXT," +
                Crew.NAME + "  TEXT," +
                Crew.PROFILE_PATH + "  TEXT," +
                Crew.MOVIE_ID + "  TEXT,"
                + "UNIQUE (" + Crew.CREDIT_ID + " , " + Crew.MOVIE_ID + ") ON CONFLICT REPLACE)";


        final String SQL_CREATE__TABLE_TV = "CREATE TABLE " + TV.TABLE_NAME + " (" +
                TV._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TV.PAGE + "  TEXT," +
                TV.POSTER_PATH + "  TEXT," +
                TV.OVERVIEW + "  TEXT," +
                TV.FIRST_AIR_DATE + "  TEXT," +
                TV.TV_ID + "  TEXT," +
                TV.ORIGINAL_NAME + "  TEXT," +
                TV.ORIGINAL_LANGUAGE + "  TEXT," +
                TV.NAME + "  TEXT," +
                TV.BACKDROP_PATH + "  TEXT," +
                TV.POPULARITY + "  TEXT," +
                TV.VOTE_COUNT + "  TEXT," +
                TV.VOTE_AVERAGE + "  TEXT," +
                TV.FAVOURED + " INTEGER NOT NULL DEFAULT 0," +
                TV.SHOWED + " INTEGER NOT NULL DEFAULT 0," +
                TV.DOWNLOADED + " INTEGER NOT NULL DEFAULT 0," +
                TV.MODE + " TEXT," +
                TV.PREF_LANGUAGE + " TEXT," +
                TV.PREF_ADULT + " TEXT," +
                TV.PREF_REGION + " TEXT,"
                + "UNIQUE (" + TV.TV_ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_FAVOURITES_MOVIES = "CREATE TABLE " + FavouritesMovies.TABLE_NAME + " (" +
                FavouritesMovies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavouritesMovies.MOVIE_ID + "  TEXT,"
                + "UNIQUE (" + FavouritesMovies.MOVIE_ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_FAVOURITES_TV = "CREATE TABLE " + FavouritesTVs.TABLE_NAME + " (" +
                FavouritesTVs._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavouritesTVs.TV_ID + "  TEXT,"
                + "UNIQUE (" + FavouritesTVs.TV_ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_VIDEOS = "CREATE TABLE " + Videos.TABLE_NAME + " (" +
                Videos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Videos.VIDEO_ID + "  TEXT," +
                Videos.KEY + "  TEXT," +
                Videos.NAME + "  TEXT," +
                Videos.SITE + "  TEXT," +
                Videos.SIZE + "  TEXT," +
                Videos.TYPE + "  TEXT," +
                Videos.MOVIE_ID + "  TEXT ," +
                "UNIQUE (" + Videos.MOVIE_ID + " , " + Videos.VIDEO_ID + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_REVIEWS = "CREATE TABLE " + Reviews.TABLE_NAME + " (" +
                Reviews._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Reviews.PAGE + "  TEXT," +
                Reviews.TOTAL_PAGE + "  TEXT," +
                Reviews.TOTAL_RESULTS + "  TEXT," +
                Reviews.ID_REVIEWS + "  TEXT," +
                Reviews.AUTHOR + "  TEXT ," +
                Reviews.CONTENT + "  TEXT," +
                Reviews.URL + "  TEXT," +
                Reviews.MOVIE_ID + "  TEXT )";

        final String SQL_CREATE__TABLE_GENRES = "CREATE TABLE " + Genres.TABLE_NAME + " (" +
                Genres._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Genres.NAME + "  TEXT," +
                Genres.ID_GENRES + "  TEXT," +
                Genres.MOVIE_ID + "  TEXT ," +
                "UNIQUE (" + Genres.MOVIE_ID + " , " + Genres.ID_GENRES + ") ON CONFLICT REPLACE)";


        final String SQL_CREATE__TABLE_TV_GENRES = "CREATE TABLE " + TVGenres.TABLE_NAME + " (" +
                TVGenres._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TVGenres.NAME + "  TEXT," +
                TVGenres.ID_GENRES + "  TEXT," +
                TVGenres.TV_ID + "  TEXT ," +
                "UNIQUE (" + TVGenres.TV_ID + " , " + TVGenres.ID_GENRES + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_TV_NETWORKS = "CREATE TABLE " + TVNetworks.TABLE_NAME + " (" +
                TVNetworks._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TVNetworks.ID + "  TEXT," +
                TVNetworks.NAME + "  TEXT," +
                TVNetworks.TV_ID + "  TEXT ," +
                "UNIQUE (" + TVNetworks.TV_ID + " , " + TVNetworks.NAME + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_TV_SEASONS = "CREATE TABLE " + TVSeasons.TABLE_NAME + " (" +
                TVSeasons._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TVSeasons.EPISODE_COUNT + "  TEXT," +
                TVSeasons.ID + "  TEXT," +
                TVSeasons.SEASON_NUMBER + "  TEXT," +
                TVSeasons.POSTER_PATH + "  TEXT," +
                TVSeasons.TV_ID + "  TEXT ," +
                "UNIQUE (" + TVSeasons.TV_ID + " , " + TVSeasons.SEASON_NUMBER + ") ON CONFLICT REPLACE)";

        final String SQL_CREATE__TABLE_TV_VIDEOS = "CREATE TABLE " + TVVideos.TABLE_NAME + " (" +
                TVVideos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TVVideos.VIDEO_ID + "  TEXT," +
                TVVideos.KEY + "  TEXT," +
                TVVideos.NAME + "  TEXT," +
                TVVideos.SITE + "  TEXT," +
                TVVideos.SIZE + "  TEXT," +
                TVVideos.TYPE + "  TEXT," +
                TVVideos.TV_ID + "  TEXT ," +
                "UNIQUE (" + TVVideos.TV_ID + " , " + TVVideos.VIDEO_ID + ") ON CONFLICT REPLACE)";

        sqLiteDatabase.execSQL(SQL_CREATE__TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_FAVOURITES_MOVIES);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_VIDEOS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_REVIEWS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_GENRES);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_MOVIE_DETAILS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_CAST);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_CREW);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_SIMILAR_MOVIES);

        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_PEOPLE);

        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TV);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_FAVOURITES_TV);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TV_DETAILS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TV_CREATOR);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TV_EPISODE_RUNTIME);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TV_GENRES);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TV_NETWORKS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TV_SEASONS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TV_VIDEOS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TVCAST);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_TV_SIMILAR);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Movies.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouritesMovies.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Videos.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Reviews.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Genres.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieDetails.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Cast.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Crew.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SimilarMovies.TABLE_NAME);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + People.TABLE_NAME);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TV.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouritesTVs.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVDetails.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVCreator.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVEpisodeRuntime.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVGenres.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVNetworks.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVSeasons.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVVideos.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVCast.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVSimilar.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}