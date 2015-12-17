package work.technie.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract{

    public static final String CONTENT_AUTHORITY = "work.technie.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

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

        public static final Uri CONTENT_URI =
                    BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static Uri buildPopularUri(long id) {
                return ContentUris.withAppendedId(CONTENT_URI, id);

        }
        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

}
