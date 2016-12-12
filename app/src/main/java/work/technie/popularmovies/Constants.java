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
            MovieContract.Movies.FAVOURED
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
            MovieContract.TV.FAVOURED
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
}
