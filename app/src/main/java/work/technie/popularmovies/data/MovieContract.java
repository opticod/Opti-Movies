package work.technie.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract{

    public static final String CONTENT_AUTHORITY = "work.technie.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SORT_BY_POP = "popular";
    public static final String PATH_SORT_BY_REV = "revenue";
    public static final String PATH_SORT_BY_VOTE = "rated";
    public static final String PATH_BOOKMARKS = "bookmarks";


    public static final class PopularMovies implements BaseColumns{

        public static final String TABLE_NAME = "popular";

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


        public static final Uri CONTENT_URI =
                    BASE_CONTENT_URI.buildUpon().appendPath(PATH_SORT_BY_POP).build();

        public static final String CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SORT_BY_POP;
        public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SORT_BY_POP;

        public static Uri buildPopularUri(long id) {
                return ContentUris.withAppendedId(CONTENT_URI, id);

        }
        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }
    public static final class RevenueMovies implements BaseColumns{

        public static final String TABLE_NAME = "revenue";

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


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SORT_BY_REV).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SORT_BY_REV;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SORT_BY_REV;

        public static Uri buildRevenueUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);

        }
    }
    public static final class RatedMovies implements BaseColumns{

        public static final String TABLE_NAME = "rated";

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


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SORT_BY_VOTE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SORT_BY_VOTE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SORT_BY_VOTE;

        public static Uri buildRatedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);

        }
    }public static final class Bookmarks implements BaseColumns{

        public static final String TABLE_NAME = "bookmarks";

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


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOOKMARKS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKMARKS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKMARKS;

        public static Uri buildBookmarksUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);

        }
    }

}
