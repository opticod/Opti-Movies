package work.technie.popularmovies;

import work.technie.popularmovies.data.MovieContract;

/**
 * Created by anupam on 13/12/16.
 */

public class Constants {
    public static final String[] MOVIE_COLUMNS = {

            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies._ID,
            MovieContract.Movies.PAGE,
            MovieContract.Movies.POSTER_PATH,
            MovieContract.Movies.ADULT,
            MovieContract.Movies.OVERVIEW,
            MovieContract.Movies.RELEASE_DATE,
            MovieContract.Movies.MOVIE_ID,
            MovieContract.Movies.ORIGINAL_TITLE,
            MovieContract.Movies.ORIGINAL_LANGUAGE,
            MovieContract.Movies.TITLE,
            MovieContract.Movies.BACKDROP_PATH,
            MovieContract.Movies.POPULARITY,
            MovieContract.Movies.VOTE_COUNT,
            MovieContract.Movies.VOTE_AVERAGE,
            MovieContract.Movies.FAVOURED,
            MovieContract.Movies.SHOWED,
            MovieContract.Movies.DOWNLOADED,
            MovieContract.Movies.MODE
    };
    public static final String[] MOVIE_COLUMNS_MIN = {
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies._ID
    };
    public static final String[] TV_COLUMNS_MIN = {
            MovieContract.TV.TABLE_NAME + "." + MovieContract.TV._ID
    };
    public static final String[] TV_COLUMNS = {

            MovieContract.TV.TABLE_NAME + "." + MovieContract.TV._ID,
            MovieContract.TV.PAGE,
            MovieContract.TV.POSTER_PATH,
            MovieContract.TV.OVERVIEW,
            MovieContract.TV.FIRST_AIR_DATE,
            MovieContract.TV.TV_ID,
            MovieContract.TV.ORIGINAL_NAME,
            MovieContract.TV.ORIGINAL_LANGUAGE,
            MovieContract.TV.NAME,
            MovieContract.TV.BACKDROP_PATH,
            MovieContract.TV.POPULARITY,
            MovieContract.TV.VOTE_COUNT,
            MovieContract.TV.VOTE_AVERAGE,
            MovieContract.TV.FAVOURED,
            MovieContract.TV.SHOWED,
            MovieContract.TV.DOWNLOADED,
            MovieContract.TV.MODE
    };
    private static final String[] FAVOURITE_MOVIE_COLUMNS = {

            MovieContract.Favourites.TABLE_NAME + "." + MovieContract.Favourites._ID,
            MovieContract.Favourites.PAGE,
            MovieContract.Favourites.POSTER_PATH,
            MovieContract.Favourites.ADULT,
            MovieContract.Favourites.OVERVIEW,
            MovieContract.Favourites.RELEASE_DATE,
            MovieContract.Favourites.MOVIE_ID,
            MovieContract.Favourites.ORIGINAL_TITLE,
            MovieContract.Favourites.ORIGINAL_LANGUAGE,
            MovieContract.Favourites.TITLE,
            MovieContract.Favourites.BACKDROP_PATH,
            MovieContract.Favourites.POPULARITY,
            MovieContract.Favourites.VOTE_COUNT,
            MovieContract.Favourites.VOTE_AVERAGE,
            MovieContract.Favourites.FAVOURED
    };
    public static int MOV_COL_ID = 0;
    public static int MOV_COL_PAGE = 1;
    public static int MOV_COL_POSTER_PATH = 2;
    public static int MOV_COL_ADULT = 3;
    public static int MOV_COL_OVERVIEW = 4;
    public static int MOV_COL_RELEASE_DATE = 5;
    public static int MOV_COL_MOVIE_ID = 6;
    public static int MOV_COL_ORIGINAL_TITLE = 7;
    public static int MOV_COL_ORIGINAL_LANG = 8;
    public static int MOV_COL_TITLE = 9;
    public static int MOV_COL_BACKDROP_PATH = 10;
    public static int MOV_COL_POPULARITY = 11;
    public static int MOV_COL_VOTE_COUNT = 12;
    public static int MOV_COL_VOTE_AVERAGE = 13;
    public static int MOV_COL_FAVOURED = 14;
    public static int MOV_COL_SHOWED = 15;
    public static int MOV_COL_DOWNLOADED = 16;
    public static int MOV_COL_MODE = 17;
    public static int TV_COL_ID = 0;
    public static int TV_COL_PAGE = 1;
    public static int TV_COL_POSTER_PATH = 2;
    public static int TV_COL_OVERVIEW = 3;
    public static int TV_COL_FIRST_AIR_DATE = 4;
    public static int TV_COL_TV_ID = 5;
    public static int TV_COL_ORIGINAL_NAME = 6;
    public static int TV_COL_ORIGINAL_LANG = 7;
    public static int TV_COL_NAME = 8;
    public static int TV_COL_BACKDROP_PATH = 9;
    public static int TV_COL_POPULARITY = 10;
    public static int TV_COL_VOTE_COUNT = 11;
    public static int TV_COL_VOTE_AVERAGE = 12;
    public static int TV_COL_FAVOURED = 13;
    public static int TV_COL_SHOWED = 14;
    public static int TV_COL_DOWNLOADED = 15;
    public static int TV_COL_MODE = 16;




}
