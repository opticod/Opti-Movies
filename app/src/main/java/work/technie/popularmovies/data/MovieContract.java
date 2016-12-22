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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "work.technie.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_VIDEOS = "videos";
    public static final String PATH_SIMILAR_MOVIES = "similar_movies";
    public static final String PATH_CASTS = "casts";
    public static final String PATH_CREWS = "crews";
    public static final String PATH_REVIEWS = "reviews";
    public static final String PATH_GENRES = "genres";
    public static final String PATH_MOVIE_DETAILS = "movie_details";
    public static final String PATH_FAVOURITES_MOVIES = "favourites_movies";

    public static final String PATH_PEOPLE = "people";

    public static final String PATH_TV = "tv";
    public static final String PATH_TV_DETAILS = "tv_details";
    public static final String PATH_FAVOURITES_TV = "favourites_tv";
    public static final String PATH_TV_CREATOR = "tv_creator";
    public static final String PATH_TV_EPISODE_RUNTIME = "tv_episode_runtime";
    public static final String PATH_TV_GENRES = "tv_genres";
    public static final String PATH_TV_NETWORKS = "tv_networks";
    public static final String PATH_TV_SEASONS = "tv_seasons";
    public static final String PATH_TV_VIDEOS = "tv_videos";
    public static final String PATH_TV_CAST = "tv_cast";
    public static final String PATH_TV_SIMILAR = "tv_similar";

    public static final String PATH_TV_EPISODE_CREW = "tv_episode_crew";
    public static final String PATH_TV_EPISODE_GUEST_STAR = "tv_episode_guest_star";
    public static final String PATH_TV_EPISODE = "tv_episode";
    public static final String PATH_TV_SEASON_DETAILS = "tv_season_details";


    public static final class Movies implements BaseColumns {

        public static final String TABLE_NAME = "movies";

        public static final String PAGE = "page";
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String MOVIE_ID = "id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String FAVOURED = "favoured";
        public static final String SHOWED = "shown";
        public static final String DOWNLOADED = "downloaded";
        public static final String MODE = "mode";
        public static final String PREF_LANGUAGE = "pref_lang";
        public static final String PREF_ADULT = "pref_adult";
        public static final String PREF_REGION = "pref_region";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../movies/MovieId
        public static Uri buildMoviesUriWithMovieId(String MovieId) {
            return CONTENT_URI.buildUpon().appendPath(MovieId).build();
        }

        public static Uri buildMovieUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

    public static final class SimilarMovies implements BaseColumns {

        public static final String TABLE_NAME = "similar_movies";
        public static final String MOVIE_ID_OLD = "id_old";
        public static final String PAGE = "page";
        public static final String POSTER_PATH = "poster_path";
        public static final String ADULT = "adult";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String MOVIE_ID = "id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String FAVOURED = "favoured";
        public static final String SHOWED = "shown";
        public static final String DOWNLOADED = "downloaded";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SIMILAR_MOVIES).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SIMILAR_MOVIES;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SIMILAR_MOVIES;

        static Uri buildSimilarMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../similar_movies/MovieId
        public static Uri buildSimilarMoviesUriWithMovieId(String MovieId) {
            return CONTENT_URI.buildUpon().appendPath(MovieId).build();
        }

        public static Uri buildSimilarMovieUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

    public static final class MovieDetails implements BaseColumns {

        public static final String TABLE_NAME = "movie_details";

        public static final String MOVIE_ID = "id";
        public static final String ADULT = "adult";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String BUDGET = "budget";
        public static final String HOMEPAGE = "homepage";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String OVERVIEW = "overview";
        public static final String POPULARITY = "popularity";
        public static final String POSTER_PATH = "poster_path";
        public static final String RELEASE_DATE = "release_date";
        public static final String REVENUE = "revenue";
        public static final String RUNTIME = "runtime";
        public static final String STATUS = "status";
        public static final String TAGLINE = "tagline";
        public static final String TITLE = "title";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";
        public static final String FAVOURED = "favoured";
        public static final String SHOWED = "shown";
        public static final String DOWNLOADED = "downloaded";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_DETAILS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_DETAILS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_DETAILS;

        static Uri buildMovieDetailsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../movie_details/MovieId
        public static Uri buildMovieDetailsUriWithMovieId(String MovieId) {
            return CONTENT_URI.buildUpon().appendPath(MovieId).build();
        }

        public static Uri buildMovieDetailsUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVDetails implements BaseColumns {

        public static final String TABLE_NAME = "tv_details";

        public static final String TV_ID = "id";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String FIRST_AIR_DATE = "first_air_date";
        public static final String HOMEPAGE = "homepage";
        public static final String IN_PRODUCTION = "in_production";
        public static final String LAST_AIR_DATE = "last_air_date";
        public static final String NAME = "name";
        public static final String NUMBER_OF_EPISODES = "number_of_episodes";
        public static final String NUMBER_OF_SEASONS = "number_of_seasons";
        public static final String ORIGINAL_LANG = "original_language";
        public static final String ORIGINAL_NAME = "original_name";
        public static final String OVERVIEW = "overview";
        public static final String POPULARITY = "popularity";
        public static final String POSTER_PATH = "poster_path";
        public static final String STATUS = "status";
        public static final String TYPE = "type";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";
        public static final String FAVOURED = "favoured";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_DETAILS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_DETAILS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_DETAILS;

        static Uri buildTVDetailsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_details/tvId
        public static Uri buildTVDetailsUriWithTvId(String TVId) {
            return CONTENT_URI.buildUpon().appendPath(TVId).build();
        }

        public static Uri buildTVDetailsUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVSeasonDetails implements BaseColumns {

        public static final String TABLE_NAME = "tv_season_details";

        public static final String SEASON_ID = "season_id";
        public static final String AIR_DATE = "air_date";
        public static final String NAME = "name";
        public static final String OVERVIEW = "overview";
        public static final String SEASON__ID = "season__id";
        public static final String POSTER_PATH = "poster_path";
        public static final String SEASON_NUMBER = "season_number";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_SEASON_DETAILS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SEASON_DETAILS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SEASON_DETAILS;

        static Uri buildTVSeasonDetailsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_season_details/seasonId
        public static Uri buildSeasonDetailsUriWithSeasonId(String seasonId) {
            return CONTENT_URI.buildUpon().appendPath(seasonId).build();
        }

        public static Uri buildSeasonDetailsUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVEpisode implements BaseColumns {

        public static final String TABLE_NAME = "tv_episode";

        public static final String SEASON_ID = "season_id";
        public static final String AIR_DATE = "air_date";
        public static final String EPISODE_NUMBER = "episode_number";
        public static final String NAME = "name";
        public static final String OVERVIEW = "overview";
        public static final String ID = "id";
        public static final String PRODUCTION_CODE = "production_code";
        public static final String SEASON_NUMBER = "season_number";
        public static final String STILL_PATH = "still_path";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_EPISODE).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_EPISODE;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_EPISODE;

        static Uri buildTVEpisodeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_episode/seasonId
        public static Uri buildEpisodeUriWithSeasonId(String seasonId) {
            return CONTENT_URI.buildUpon().appendPath(seasonId).build();
        }

        public static Uri buildEpisodeUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVEpisodeCrew implements BaseColumns {

        public static final String TABLE_NAME = "tv_episode_crew";

        public static final String EPISODE_ID = "episode_id";
        public static final String ID = "id";
        public static final String CREDIT_ID = "credit_id";
        public static final String NAME = "name";
        public static final String DEPARTMENT = "department";
        public static final String JOB = "job";
        public static final String PROFILE_PATH = "profile_path";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_EPISODE_CREW).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_EPISODE_CREW;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_EPISODE_CREW;

        static Uri buildTVEpisodeCrewUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_episode_crew/episodeId
        public static Uri buildEpisodeCrewUriWithEpisodeId(String episodeId) {
            return CONTENT_URI.buildUpon().appendPath(episodeId).build();
        }

        public static Uri buildEpisodeCrewUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }


    public static final class TVEpisodeGuestStar implements BaseColumns {

        public static final String TABLE_NAME = "tv_episode_guest_star";

        public static final String EPISODE_ID = "episode_id";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CREDIT_ID = "credit_id";
        public static final String CHARACTER = "character";
        public static final String ORDER = "order_guest";
        public static final String PROFILE_PATH = "profile_path";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_EPISODE_GUEST_STAR).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_EPISODE_GUEST_STAR;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_EPISODE_GUEST_STAR;

        static Uri buildTVEpisodeGuestStarUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_episode_guest_star/episodeId
        public static Uri buildEpisodeGuestStarUriWithEpisodeId(String episodeId) {
            return CONTENT_URI.buildUpon().appendPath(episodeId).build();
        }

        public static Uri buildEpisodeGuestStarUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVCreator implements BaseColumns {

        public static final String TABLE_NAME = "tv_creator";

        public static final String TV_ID = "tv_id";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PROFILE_PATH = "profile_path";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_CREATOR).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_CREATOR;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_CREATOR;

        static Uri buildTVCreatorUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_creator/tv_id
        public static Uri buildTVCreatorUriWithTVId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static Uri buildTVCreatorUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVEpisodeRuntime implements BaseColumns {

        public static final String TABLE_NAME = "tv_episode_runtime";

        public static final String TV_ID = "tv_id";
        public static final String TIME = "time";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_EPISODE_RUNTIME).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_EPISODE_RUNTIME;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_EPISODE_RUNTIME;

        static Uri buildTVEpisodeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_episode_runtime/tv_id
        public static Uri buildTVEpisodeRuntimeUriWithTVId(String tv_id) {
            return CONTENT_URI.buildUpon().appendPath(tv_id).build();
        }

        public static Uri buildTVEpisodeRuntimeUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVGenres implements BaseColumns {

        public static final String TABLE_NAME = "tv_genres";

        public static final String ID_GENRES = "id_genres";
        public static final String NAME = "name";
        public static final String TV_ID = "tv_id";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_GENRES).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_GENRES;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_GENRES;

        static Uri buildTVGenreUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../genres/tvId
        public static Uri buildGenresUriWithTVId(String tvId) {
            return CONTENT_URI.buildUpon().appendPath(tvId).build();
        }

        public static Uri buildGenreUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVNetworks implements BaseColumns {

        public static final String TABLE_NAME = "tv_networks";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String TV_ID = "tv_id";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_NETWORKS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_NETWORKS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_NETWORKS;

        static Uri buildTVNetworksUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_networks/tvId
        public static Uri buildTVNetworksUriWithTVId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static Uri buildGenreUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVSeasons implements BaseColumns {

        public static final String TABLE_NAME = "tv_seasons";

        public static final String ID = "id";
        public static final String EPISODE_COUNT = "episode_count";
        public static final String TV_ID = "tv_id";
        public static final String SEASON_NUMBER = "season_number";
        public static final String POSTER_PATH = "poster_path";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_SEASONS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SEASONS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SEASONS;

        static Uri buildTVSeasonsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_seasons/tvId
        public static Uri buildTVSeasonsUriWithTVId(String tvId) {
            return CONTENT_URI.buildUpon().appendPath(tvId).build();
        }

        public static Uri buildGenreUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVVideos implements BaseColumns {

        public static final String TABLE_NAME = "tv_videos";

        public static final String VIDEO_ID = "id";
        public static final String KEY = "key";
        public static final String NAME = "name";
        public static final String SITE = "site";
        public static final String SIZE = "size";
        public static final String TYPE = "type";
        public static final String TV_ID = "tv_id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_VIDEOS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_VIDEOS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_VIDEOS;

        static Uri buildTVVideosUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_videos/TvId
        public static Uri buildTVUriWithTVId(String tvId) {
            return CONTENT_URI.buildUpon().appendPath(tvId).build();
        }

        public static Uri buildTVVideosUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TVSimilar implements BaseColumns {

        public static final String TABLE_NAME = "similar_tv";
        public static final String TV_ID_OLD = "id_old";
        public static final String PAGE = "page";
        public static final String POSTER_PATH = "poster_path";
        public static final String OVERVIEW = "overview";
        public static final String FIRST_AIR_DATE = "first_air_date";
        public static final String TV_ID = "id";
        public static final String ORIGINAL_NAME = "original_name";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String NAME = "name";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String FAVOURED = "favoured";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_SIMILAR).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SIMILAR;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SIMILAR;

        static Uri buildSimilarTVUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../similar_tv/MovieId
        public static Uri buildSimilarTVUriWithTVId(String TVId) {
            return CONTENT_URI.buildUpon().appendPath(TVId).build();
        }

        public static Uri buildSimilarTVUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class TVCast implements BaseColumns {

        public static final String TABLE_NAME = "tv_cast";

        public static final String CHARACTER = "character";
        public static final String CREDIT_ID = "credit_id";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ORDER = "order_cast";
        public static final String PROFILE_PATH = "profile_path";
        public static final String TV_ID = "tv_id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_CAST).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_CAST;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_CAST;

        static Uri buildTVCastUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv_casts/TvId
        public static Uri buildCastsUriWithTVId(String TVId) {
            return CONTENT_URI.buildUpon().appendPath(TVId).build();
        }

        public static Uri buildCastUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }


    public static final class Cast implements BaseColumns {

        public static final String TABLE_NAME = "cast";

        public static final String CAST_ID = "cast_id";
        public static final String CHARACTER = "character";
        public static final String CREDIT_ID = "credit_id";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ORDER = "order_cast";
        public static final String PROFILE_PATH = "profile_path";
        public static final String MOVIE_ID = "movie_id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CASTS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CASTS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CASTS;

        static Uri buildCastUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../casts/MovieId
        public static Uri buildCastsUriWithMovieId(String MovieId) {
            return CONTENT_URI.buildUpon().appendPath(MovieId).build();
        }

        public static Uri buildCastUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class People implements BaseColumns {

        public static final String TABLE_NAME = "people";

        public static final String ADULT = "adult";
        public static final String BIOGRAPHY = "biography";
        public static final String BIRTHDAY = "birthday";
        public static final String DEATHDAY = "deathday";
        public static final String GENDER = "gender";
        public static final String HOMEPAGE = "homepage";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PLACE_OF_BIRTH = "place_of_birth";
        public static final String POPULARITY = "popularity";
        public static final String PROFILE_PATH = "profile_path";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PEOPLE).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PEOPLE;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PEOPLE;

        static Uri buildPeopleUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../people/peopleId
        public static Uri buildPeopleUriWithPeopleId(String peopleId) {
            return CONTENT_URI.buildUpon().appendPath(peopleId).build();
        }

        public static Uri buildPersonUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class Crew implements BaseColumns {

        public static final String TABLE_NAME = "crew";

        public static final String CREDIT_ID = "credit_id";
        public static final String DEPARTMENT = "department";
        public static final String ID = "id";
        public static final String JOB = "job";
        public static final String NAME = "name";
        public static final String PROFILE_PATH = "profile_path";
        public static final String MOVIE_ID = "movie_id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CREWS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CREWS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CREWS;

        static Uri buildCrewUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../crews/MovieId
        public static Uri buildCrewsUriWithMovieId(String MovieId) {
            return CONTENT_URI.buildUpon().appendPath(MovieId).build();
        }

        public static Uri buildCrewUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class TV implements BaseColumns {

        public static final String TABLE_NAME = "tv";

        public static final String PAGE = "page";
        public static final String POSTER_PATH = "poster_path";
        public static final String OVERVIEW = "overview";
        public static final String FIRST_AIR_DATE = "first_air_date";
        public static final String TV_ID = "id";
        public static final String ORIGINAL_NAME = "original_name";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String NAME = "name";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String FAVOURED = "favoured";
        public static final String SHOWED = "shown";
        public static final String DOWNLOADED = "downloaded";
        public static final String MODE = "mode";
        public static final String PREF_LANGUAGE = "pref_lang";
        public static final String PREF_ADULT = "pref_adult";
        public static final String PREF_REGION = "pref_region";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV;

        static Uri buildTVUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../tv/TVid
        public static Uri buildTVUriWithTVId(String tvId) {
            return CONTENT_URI.buildUpon().appendPath(tvId).build();
        }

        public static Uri buildTVUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class FavouritesMovies implements BaseColumns {

        public static final String TABLE_NAME = "favourites_movies";
        public static final String MOVIE_ID = "id";

        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES_MOVIES).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITES_MOVIES;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITES_MOVIES;

        static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../favourites_movies/MovieId
        public static Uri buildMoviesUriWithMovieId(String MovieId) {
            return CONTENT_URI.buildUpon().appendPath(MovieId).build();
        }

        public static Uri buildMovieUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class FavouritesTVs implements BaseColumns {

        public static final String TABLE_NAME = "favourites_tv";
        public static final String TV_ID = "id";

        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES_TV).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITES_TV;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITES_TV;

        static Uri buildTVUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../favourites_tv/tvId
        public static Uri buildMoviesUriWithTvId(String tvId) {
            return CONTENT_URI.buildUpon().appendPath(tvId).build();
        }

        public static Uri buildTVUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class Videos implements BaseColumns {

        public static final String TABLE_NAME = "videos";

        public static final String VIDEO_ID = "id";
        public static final String KEY = "key";
        public static final String NAME = "name";
        public static final String SITE = "site";
        public static final String SIZE = "size";
        public static final String TYPE = "type";
        public static final String MOVIE_ID = "movie_id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEOS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIDEOS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIDEOS;

        static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../videos/MovieId
        public static Uri buildMoviesUriWithMovieId(String MovieId) {
            return CONTENT_URI.buildUpon().appendPath(MovieId).build();
        }

        public static Uri buildVideosUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

    public static final class Reviews implements BaseColumns {

        public static final String TABLE_NAME = "reviews";

        public static final String PAGE = "page";
        public static final String TOTAL_PAGE = "total_page";
        public static final String TOTAL_RESULTS = "total_results";
        public static final String ID_REVIEWS = "id_reviews";
        public static final String AUTHOR = "author";
        public static final String CONTENT = "content";
        public static final String URL = "url";
        public static final String MOVIE_ID = "movie_id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;

        static Uri buildReviewsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../reviews/MovieId
        public static Uri buildReviewsUriWithMovieId(String MovieId) {
            return CONTENT_URI.buildUpon().appendPath(MovieId).build();
        }

        public static Uri buildReviewUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

    public static final class Genres implements BaseColumns {

        public static final String TABLE_NAME = "genres";

        public static final String ID_GENRES = "id_genres";
        public static final String NAME = "name";
        public static final String MOVIE_ID = "movie_id";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GENRES).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRES;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRES;

        static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../genres/MovieId
        public static Uri buildGenresUriWithMovieId(String MovieId) {
            return CONTENT_URI.buildUpon().appendPath(MovieId).build();
        }

        public static Uri buildGenreUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

}
