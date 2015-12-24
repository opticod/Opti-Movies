package work.technie.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract{

    public static final String CONTENT_AUTHORITY = "work.technie.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_TRAILERS = "trailers";
    public static final String PATH_REVIEWS = "reviews";
    public static final String PATH_GENRES = "genres";


    public static final class Movies implements BaseColumns{

        public static final String TABLE_NAME = "movies";

        public static final String PAGE = "page";
        public static final String  POSTER_PATH= "poster_path";
        public static final String  ADULT= "adult";
        public static final String  OVERVIEW= "overview";
        public static final String  RELEASE_DATE= "release_date";
        public static final String  MOVIE_ID= "id";
        public static final String  ORIGINAL_TITLE= "original_title";
        public static final String  ORIGINAL_LANGUAGE= "original_language";
        public static final String  TITLE= "title";
        public static final String  BACKDROP_PATH= "backdrop_path";
        public static final String  POPULARITY= "popularity";
        public static final String  VOTE_COUNT= "vote_count";
        public static final String  VOTE_AVERAGE= "vote_average";
        public static final String FAVOURED="favoured";
        public static final String SORT_BY="sort_by";


        public static final Uri CONTENT_URI =
                    BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static Uri buildMoviesUri(long id) {
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
    public static final class Trailers implements BaseColumns{

        public static final String TABLE_NAME = "trailers";

        public static final String NAME = "name";
        public static final String  SIZE= "size";
        public static final String  SOURCE= "source";
        public static final String  TYPE= "type";
        public static final String  MOVIE_ID= "id";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;

        public static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        //content://work....../trailers/MovieId
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
    public static final class Reviews implements BaseColumns{

        public static final String TABLE_NAME = "reviews";

        public static final String PAGE = "page";
        public static final String TOTAL_PAGE = "total_page";
        public static final String TOTAL_RESULTS = "total_results";
        public static final String  ID_REVIEWS= "id_reviews";
        public static final String  AUTHOR= "author";
        public static final String  CONTENT= "content";
        public static final String  URL= "url";
        public static final String  MOVIE_ID= "id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;

        public static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        //content://work....../reviews/MovieId
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
    public static final class Genres implements BaseColumns{

        public static final String TABLE_NAME = "genres";

        public static final String ID_GENRES = "id_genres";
        public static final String  NAME= "name";
        public static final String  MOVIE_ID= "movie_id";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GENRES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRES;

        public static Uri buildMoviesUri(long id) {
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

}
