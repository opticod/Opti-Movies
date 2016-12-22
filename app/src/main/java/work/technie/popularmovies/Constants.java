package work.technie.popularmovies;

import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.data.MovieContract.TVDetails;

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


    public static final String[] MOVIE_DETAILS_COLUMNS_MIN = {
            MovieContract.MovieDetails.TABLE_NAME + "." + MovieContract.MovieDetails._ID,
            MovieContract.MovieDetails.MOVIE_ID
    };

    public static final String[] TV_DETAILS_COLUMNS_MIN = {
            MovieContract.TVDetails.TABLE_NAME + "." + MovieContract.TVDetails._ID,
            MovieContract.TVDetails.TV_ID
    };

    public static final String[] PEOPLE_DETAILS_COLUMNS_MIN = {
            MovieContract.People.TABLE_NAME + "." + MovieContract.People._ID,
            MovieContract.People.ID
    };


    public static final String[] CREATOR_COLUMNS = {
            MovieContract.TVCreator.TABLE_NAME + "." + MovieContract.TVCreator._ID,
            MovieContract.TVCreator.TV_ID,
            MovieContract.TVCreator.ID,
            MovieContract.TVCreator.NAME,
            MovieContract.TVCreator.PROFILE_PATH
    };

    public static final String[] TV_SEASONS_COLUMNS = {
            MovieContract.TVSeasons.TABLE_NAME + "." + MovieContract.TVSeasons._ID,
            MovieContract.TVSeasons.ID,
            MovieContract.TVSeasons.EPISODE_COUNT,
            MovieContract.TVSeasons.TV_ID,
            MovieContract.TVSeasons.SEASON_NUMBER,
            MovieContract.TVSeasons.POSTER_PATH
    };
    public static final String[] MOVIE_DETAILS_COLUMNS = {

            MovieContract.MovieDetails.TABLE_NAME + "." + MovieContract.MovieDetails._ID,
            MovieContract.MovieDetails.MOVIE_ID,
            MovieContract.MovieDetails.ADULT,
            MovieContract.MovieDetails.BACKDROP_PATH,
            MovieContract.MovieDetails.BUDGET,
            MovieContract.MovieDetails.HOMEPAGE,
            MovieContract.MovieDetails.ORIGINAL_LANGUAGE,
            MovieContract.MovieDetails.ORIGINAL_TITLE,
            MovieContract.MovieDetails.OVERVIEW,
            MovieContract.MovieDetails.POPULARITY,
            MovieContract.MovieDetails.POSTER_PATH,
            MovieContract.MovieDetails.RELEASE_DATE,
            MovieContract.MovieDetails.REVENUE,
            MovieContract.MovieDetails.RUNTIME,
            MovieContract.MovieDetails.STATUS,
            MovieContract.MovieDetails.TAGLINE,
            MovieContract.MovieDetails.TITLE,
            MovieContract.MovieDetails.VOTE_AVERAGE,
            MovieContract.MovieDetails.VOTE_COUNT,
            MovieContract.MovieDetails.FAVOURED,
            MovieContract.MovieDetails.SHOWED,
            MovieContract.MovieDetails.DOWNLOADED
    };
    public static final String[] CREW_COLUMNS = {

            MovieContract.Crew.TABLE_NAME + "." + MovieContract.Crew._ID,
            MovieContract.Crew.CREDIT_ID,
            MovieContract.Crew.DEPARTMENT,
            MovieContract.Crew.ID,
            MovieContract.Crew.JOB,
            MovieContract.Crew.NAME,
            MovieContract.Crew.PROFILE_PATH,
            MovieContract.Crew.MOVIE_ID
    };
    public static final String[] CAST_COLUMNS = {

            MovieContract.Cast._ID,
            MovieContract.Cast.CAST_ID,
            MovieContract.Cast.CHARACTER,
            MovieContract.Cast.CREDIT_ID,
            MovieContract.Cast.ID,
            MovieContract.Cast.NAME,
            MovieContract.Cast.ORDER,
            MovieContract.Cast.PROFILE_PATH,
            MovieContract.Cast.MOVIE_ID
    };
    public static final String[] TVCAST_COLUMNS = {

            MovieContract.TVCast._ID,
            MovieContract.TVCast.CHARACTER,
            MovieContract.TVCast.CREDIT_ID,
            MovieContract.TVCast.ID,
            MovieContract.TVCast.NAME,
            MovieContract.TVCast.ORDER,
            MovieContract.TVCast.PROFILE_PATH,
            MovieContract.TVCast.TV_ID
    };
    public static final String[] SIMILAR_MOVIE_COLUMNS = {

            MovieContract.SimilarMovies.TABLE_NAME + "." + MovieContract.SimilarMovies._ID,
            MovieContract.SimilarMovies.MOVIE_ID_OLD,
            MovieContract.SimilarMovies.PAGE,
            MovieContract.SimilarMovies.POSTER_PATH,
            MovieContract.SimilarMovies.ADULT,
            MovieContract.SimilarMovies.OVERVIEW,
            MovieContract.SimilarMovies.RELEASE_DATE,
            MovieContract.SimilarMovies.MOVIE_ID,
            MovieContract.SimilarMovies.ORIGINAL_TITLE,
            MovieContract.SimilarMovies.ORIGINAL_LANGUAGE,
            MovieContract.SimilarMovies.TITLE,
            MovieContract.SimilarMovies.BACKDROP_PATH,
            MovieContract.SimilarMovies.POPULARITY,
            MovieContract.SimilarMovies.VOTE_COUNT,
            MovieContract.SimilarMovies.VOTE_AVERAGE,
            MovieContract.SimilarMovies.FAVOURED,
            MovieContract.SimilarMovies.SHOWED,
            MovieContract.SimilarMovies.DOWNLOADED,
    };
    public static final String[] TV_SIMILAR_MOVIE_COLUMNS = {

            MovieContract.TVSimilar.TABLE_NAME + "." + MovieContract.TVSimilar._ID,
            MovieContract.TVSimilar.POSTER_PATH,
            MovieContract.TVSimilar.TV_ID,
            MovieContract.TVSimilar.ORIGINAL_NAME,
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
    public static final String[] TV_DETAILS_COLUMNS = {

            TVDetails.TABLE_NAME + "." + TVDetails._ID,
            TVDetails.TV_ID,
            TVDetails.BACKDROP_PATH,
            TVDetails.FIRST_AIR_DATE,
            TVDetails.HOMEPAGE,
            TVDetails.IN_PRODUCTION,
            TVDetails.LAST_AIR_DATE,
            TVDetails.NAME,
            TVDetails.NUMBER_OF_EPISODES,
            TVDetails.NUMBER_OF_SEASONS,
            TVDetails.ORIGINAL_LANG,
            TVDetails.ORIGINAL_NAME,
            TVDetails.OVERVIEW,
            TVDetails.POPULARITY,
            TVDetails.POSTER_PATH,
            TVDetails.STATUS,
            TVDetails.TYPE,
            TVDetails.VOTE_AVERAGE,
            TVDetails.VOTE_COUNT,
            TVDetails.FAVOURED
    };
    public static final String[] VIDEO_COLUMNS = {

            MovieContract.Videos.TABLE_NAME + "." + MovieContract.Videos._ID,
            MovieContract.Videos.TABLE_NAME + "." + MovieContract.Videos.VIDEO_ID,
            MovieContract.Videos.TABLE_NAME + "." + MovieContract.Videos.KEY,
            MovieContract.Videos.TABLE_NAME + "." + MovieContract.Videos.NAME,
            MovieContract.Videos.TABLE_NAME + "." + MovieContract.Videos.SITE,
            MovieContract.Videos.TABLE_NAME + "." + MovieContract.Videos.SIZE,
            MovieContract.Videos.TABLE_NAME + "." + MovieContract.Videos.TYPE,
            MovieContract.Videos.TABLE_NAME + "." + MovieContract.Videos.MOVIE_ID,
    };
    public static final String[] TV_VIDEO_COLUMNS = {

            MovieContract.TVVideos.TABLE_NAME + "." + MovieContract.TVVideos._ID,
            MovieContract.TVVideos.TABLE_NAME + "." + MovieContract.TVVideos.VIDEO_ID,
            MovieContract.TVVideos.TABLE_NAME + "." + MovieContract.TVVideos.KEY,
            MovieContract.TVVideos.TABLE_NAME + "." + MovieContract.TVVideos.NAME,
            MovieContract.TVVideos.TABLE_NAME + "." + MovieContract.TVVideos.SITE,
            MovieContract.TVVideos.TABLE_NAME + "." + MovieContract.TVVideos.SIZE,
            MovieContract.TVVideos.TABLE_NAME + "." + MovieContract.TVVideos.TYPE,
            MovieContract.TVVideos.TABLE_NAME + "." + MovieContract.TVVideos.TV_ID,
    };
    public static final String[] REVIEW_COLUMNS = {

            MovieContract.Reviews.TABLE_NAME + "." + MovieContract.Reviews._ID,
            MovieContract.Reviews.TABLE_NAME + "." + MovieContract.Reviews.PAGE,
            MovieContract.Reviews.TABLE_NAME + "." + MovieContract.Reviews.TOTAL_PAGE,
            MovieContract.Reviews.TABLE_NAME + "." + MovieContract.Reviews.TOTAL_RESULTS,
            MovieContract.Reviews.TABLE_NAME + "." + MovieContract.Reviews.ID_REVIEWS,
            MovieContract.Reviews.TABLE_NAME + "." + MovieContract.Reviews.AUTHOR,
            MovieContract.Reviews.TABLE_NAME + "." + MovieContract.Reviews.CONTENT,
            MovieContract.Reviews.TABLE_NAME + "." + MovieContract.Reviews.URL,
            MovieContract.Reviews.TABLE_NAME + "." + MovieContract.Reviews.MOVIE_ID

    };
    public static final String[] GENRE_COLUMNS = {

            MovieContract.Genres.TABLE_NAME + "." + MovieContract.Genres._ID,
            MovieContract.Genres.TABLE_NAME + "." + MovieContract.Genres.NAME,
            MovieContract.Genres.TABLE_NAME + "." + MovieContract.Genres.ID_GENRES,
            MovieContract.Genres.TABLE_NAME + "." + MovieContract.Genres.MOVIE_ID
    };
    public static final String[] TV_GENRE_COLUMNS = {

            MovieContract.TVGenres.TABLE_NAME + "." + MovieContract.TVGenres._ID,
            MovieContract.TVGenres.TABLE_NAME + "." + MovieContract.TVGenres.NAME,
            MovieContract.TVGenres.TABLE_NAME + "." + MovieContract.TVGenres.ID_GENRES,
            MovieContract.TVGenres.TABLE_NAME + "." + MovieContract.TVGenres.TV_ID
    };
    public static final String[] PEOPLE_COLUMNS = {

            MovieContract.People.TABLE_NAME + "." + MovieContract.People._ID,
            MovieContract.People.ADULT,
            MovieContract.People.BIOGRAPHY,
            MovieContract.People.BIRTHDAY,
            MovieContract.People.DEATHDAY,
            MovieContract.People.GENDER,
            MovieContract.People.HOMEPAGE,
            MovieContract.People.ID,
            MovieContract.People.NAME,
            MovieContract.People.PLACE_OF_BIRTH,
            MovieContract.People.POPULARITY,
            MovieContract.People.PROFILE_PATH
    };
    public static final String[] FAVOURITE_MOVIE_COLUMNS = {

            MovieContract.FavouritesMovies.TABLE_NAME + "." + MovieContract.FavouritesMovies._ID,
            MovieContract.FavouritesMovies.MOVIE_ID
    };
    public static final String[] FAVOURITE_TV_COLUMNS = {

            MovieContract.FavouritesTVs.TABLE_NAME + "." + MovieContract.FavouritesTVs._ID,
            MovieContract.FavouritesTVs.TV_ID
    };
    public static final String[] TV_NETWORKS_COLUMNS = {

            MovieContract.TVNetworks.TABLE_NAME + "." + MovieContract.TVNetworks._ID,
            MovieContract.TVNetworks.ID,
            MovieContract.TVNetworks.NAME,
            MovieContract.TVNetworks.TV_ID
    };
    public static final String[] TV_RUNTIME_EPISODES_COLUMNS = {

            MovieContract.TVEpisodeRuntime.TABLE_NAME + "." + MovieContract.TVEpisodeRuntime._ID,
            MovieContract.TVEpisodeRuntime.TV_ID,
            MovieContract.TVEpisodeRuntime.TIME
    };

    public static final String[] TV_SEASON_DETAILS_COLUMNS = {

            MovieContract.TVSeasonDetails.TABLE_NAME + "." + MovieContract.TVSeasonDetails._ID,
            MovieContract.TVSeasonDetails.SEASON_ID,
            MovieContract.TVSeasonDetails.AIR_DATE,
            MovieContract.TVSeasonDetails.NAME,
            MovieContract.TVSeasonDetails.OVERVIEW,
            MovieContract.TVSeasonDetails.SEASON__ID,
            MovieContract.TVSeasonDetails.POSTER_PATH,
            MovieContract.TVSeasonDetails.SEASON_NUMBER,
    };
    public static final String[] TV_EPISODE_COLUMNS = {

            MovieContract.TVEpisode.TABLE_NAME + "." + MovieContract.TVEpisode._ID,
            MovieContract.TVEpisode.SEASON_ID,
            MovieContract.TVEpisode.AIR_DATE,
            MovieContract.TVEpisode.EPISODE_NUMBER,
            MovieContract.TVEpisode.NAME,
            MovieContract.TVEpisode.OVERVIEW,
            MovieContract.TVEpisode.ID,
            MovieContract.TVEpisode.PRODUCTION_CODE,
            MovieContract.TVEpisode.SEASON_NUMBER,
            MovieContract.TVEpisode.STILL_PATH,
            MovieContract.TVEpisode.VOTE_AVERAGE,
            MovieContract.TVEpisode.VOTE_COUNT,
    };
    public static final String[] TV_EPISODE_CREW_COLUMNS = {

            MovieContract.TVEpisodeCrew.TABLE_NAME + "." + MovieContract.TVEpisodeCrew._ID,
            MovieContract.TVEpisodeCrew.EPISODE_ID,
            MovieContract.TVEpisodeCrew.ID,
            MovieContract.TVEpisodeCrew.CREDIT_ID,
            MovieContract.TVEpisodeCrew.NAME,
            MovieContract.TVEpisodeCrew.DEPARTMENT,
            MovieContract.TVEpisodeCrew.JOB,
            MovieContract.TVEpisodeCrew.PROFILE_PATH
    };
    public static final String[] TV_EPISODE_GUEST_COLUMNS = {

            MovieContract.TVEpisodeGuestStar.TABLE_NAME + "." + MovieContract.TVEpisodeGuestStar._ID,
            MovieContract.TVEpisodeGuestStar.EPISODE_ID,
            MovieContract.TVEpisodeGuestStar.ID,
            MovieContract.TVEpisodeGuestStar.NAME,
            MovieContract.TVEpisodeGuestStar.CREDIT_ID,
            MovieContract.TVEpisodeGuestStar.CHARACTER,
            MovieContract.TVEpisodeGuestStar.ORDER,
            MovieContract.TVEpisodeGuestStar.PROFILE_PATH
    };
    public static int TV_EPISODE_COL__ID = 0;
    public static int TV_EPISODE_COL_SEASON_ID = 1;
    public static int TV_EPISODE_COL_AIR_DATE = 2;
    public static int TV_EPISODE_COL_EPISODE_NUMBER = 3;
    public static int TV_EPISODE_COL_NAME = 4;
    public static int TV_EPISODE_COL_OVERVIEW = 5;
    public static int TV_EPISODE_COL_ID = 6;
    public static int TV_EPISODE_COL_PRODUCTION_CODE = 7;
    public static int TV_EPISODE_COL_SEASON_NUMBER = 8;
    public static int TV_EPISODE_COL_STILL_PATH = 9;
    public static int TV_EPISODE_COL_VOTE_AVERAGE = 10;
    public static int TV_EPISODE_COL_VOTE_COUNT = 11;
    public static int TV_SEASON_DETAILS_COL__ID = 0;
    public static int TV_SEASON_DETAILS_COL_SEASON_ID = 1;
    public static int TV_SEASON_DETAILS_COL_AIR_DATE = 2;
    public static int TV_SEASON_DETAILS_COL_NAME = 3;
    public static int TV_SEASON_DETAILS_COL_OVERVIEW = 4;
    public static int TV_SEASON_DETAILS_COL_SEASON__ID = 5;
    public static int TV_SEASON_DETAILS_COL_POSTER_PATH = 6;
    public static int TV_SEASON_DETAILS_COL_SEASON_NUMBER = 7;

    public static int TV_EPISODE_CREW_COL__ID = 0;
    public static int TV_EPISODE_CREW_COL_EPISODE_ID = 1;
    public static int TV_EPISODE_CREW_COL_ID = 2;
    public static int TV_EPISODE_CREW_COL_CREDIT_ID = 3;
    public static int TV_EPISODE_CREW_COL_NAME = 4;
    public static int TV_EPISODE_CREW_COL_DEPARTMENT = 5;
    public static int TV_EPISODE_CREW_COL_JOB = 6;
    public static int TV_EPISODE_CREW_COL_PROFILE_PATH = 7;
    public static int TV_EPISODE_GUEST_COL__ID = 0;
    public static int TV_EPISODE_GUEST_COL_EPISODE_ID = 1;
    public static int TV_EPISODE_GUEST_COL_ID = 2;
    public static int TV_EPISODE_GUEST_COL_NAME = 3;
    public static int TV_EPISODE_GUEST_COL_CREDIT_ID = 4;
    public static int TV_EPISODE_GUEST_COL_CHARACTER = 5;
    public static int TV_EPISODE_GUEST_COL_ORDER = 6;
    public static int TV_EPISODE_GUEST_COL_PROFILE_PATH = 7;


    public static int TV_SEASON_COL__ID = 0;
    public static int TV_SEASON_COL_ID = 1;
    public static int TV_SEASON_COL_EPISODE_COUNT = 2;
    public static int TV_SEASON_COL_TV_ID = 3;
    public static int TV_SEASON_COL_SEASON_NUMBER = 4;
    public static int TV_SEASON_COL_POSTER_PATH = 5;
    public static int CREATOR_COL__ID = 0;
    public static int CREATOR_COL_TV_ID = 1;
    public static int CREATOR_COL_ID = 2;
    public static int CREATOR_COL_NAME = 3;
    public static int CREATOR_COL_PROFILE_PATH = 4;
    public static int TV_CAST_COL__ID = 0;
    public static int TV_CAST_COL_CHARACTER = 1;
    public static int TV_CAST_COL_CREDIT_ID = 2;
    public static int TV_CAST_COL_ID = 3;
    public static int TV_CAST_COL_NAME = 4;
    public static int TV_CAST_COL_ORDER = 5;
    public static int TV_CAST_COL_PROFILE_PATH = 6;
    public static int TV_CAST_COL_MOVIE_ID = 7;
    public static int TV_SIMILAR_COL_ID = 0;
    public static int TV_SIMILAR_COL_POSTER_PATH = 1;
    public static int TV_SIMILAR_COL_TV_ID = 2;
    public static int TV_SIMILAR_COL_ORIGINAL_NAME = 3;
    public static int TV_DETAILS_COL_ID = 0;
    public static int TV_DETAILS_COL_TV_ID = 1;
    public static int TV_DETAILS_COL_BACKDROP_PATH = 2;
    public static int TV_DETAILS_COL_FIRST_AIR_DATE = 3;
    public static int TV_DETAILS_COL_HOMEPAGE = 4;
    public static int TV_DETAILS_COL_IN_PRODUCTION = 5;
    public static int TV_DETAILS_COL_LAST_AIR_DATE = 6;
    public static int TV_DETAILS_COL_NAME = 7;
    public static int TV_DETAILS_COL_NUMBER_OF_EPISODES = 8;
    public static int TV_DETAILS_COL_NUMBER_OF_SEASONS = 9;
    public static int TV_DETAILS_COL_ORIGINAL_LANG = 10;
    public static int TV_DETAILS_COL_ORIGINAL_NAME = 11;
    public static int TV_DETAILS_COL_OVERVIEW = 12;
    public static int TV_DETAILS_COL_POPULARITY = 13;
    public static int TV_DETAILS_COL_POSTER_PATH = 14;
    public static int TV_DETAILS_COL_STATUS = 15;
    public static int TV_DETAILS_COL_TYPE = 16;
    public static int TV_DETAILS_COL_VOTE_AVERAGE = 17;
    public static int TV_DETAILS_COL_VOTE_COUNT = 18;
    public static int TV_DETAILS_COL_FAVOURED = 19;
    public static int TV_RUNTIME_EPISODE_COL__ID = 0;
    public static int TV_RUNTIME_EPISODE_COL_TV_ID = 1;
    public static int TV_RUNTIME_EPISODE_COL_TIME = 2;

    public static int TV_NETWORKS_COL__ID = 0;
    public static int TV_NETWORKS_COL_ID = 1;
    public static int TV_NETWORKS_COL_NAME = 2;
    public static int TV_NETWORKS_COL_TV_ID = 3;

    public static int FAV_MOV_COL_ID = 0;
    public static int FAV_MOV_COL_MOVIE_ID = 1;

    public static int FAV_TV_COL_ID = 0;
    public static int FAV_TV_COL_MOVIE_ID = 1;

    public static int PEOPLE_COL__ID = 0;
    public static int PEOPLE_COL_ADULT = 1;
    public static int PEOPLE_COL_BIOGRAPHY = 2;
    public static int PEOPLE_COL_BIRTHDAY = 3;
    public static int PEOPLE_COL_DEATHDAY = 4;
    public static int PEOPLE_COL_GENDER = 5;
    public static int PEOPLE_COL_HOMEPAGE = 6;
    public static int PEOPLE_COL_ID = 7;
    public static int PEOPLE_COL_NAME = 8;
    public static int PEOPLE_COL_PLACE_OF_BIRTH = 9;
    public static int PEOPLE_COL_POPULARITY = 10;
    public static int PEOPLE_COL_PROFILE_PATH = 11;


    public static int MOV_DETAILS_COL_ID = 0;
    public static int MOV_DETAILS_COL_MOVIE_ID = 1;
    public static int MOV_DETAILS_COL_ADULT = 2;
    public static int MOV_DETAILS_COL_BACKDROP_PATH = 3;
    public static int MOV_DETAILS_COL_BUDGET = 4;
    public static int MOV_DETAILS_COL_HOMEPAGE = 5;
    public static int MOV_DETAILS_COL_ORIGINAL_LANGUAGE = 6;
    public static int MOV_DETAILS_COL_ORIGINAL_TITLE = 7;
    public static int MOV_DETAILS_COL_OVERVIEW = 8;
    public static int MOV_DETAILS_COL_POPULARITY = 9;
    public static int MOV_DETAILS_COL_POSTER_PATH = 10;
    public static int MOV_DETAILS_COL_RELEASE_DATE = 11;
    public static int MOV_DETAILS_COL_REVENUE = 12;
    public static int MOV_DETAILS_COL_RUNTIME = 13;
    public static int MOV_DETAILS_COL_STATUS = 14;
    public static int MOV_DETAILS_COL_TAGLINE = 15;
    public static int MOV_DETAILS_COL_TITLE = 16;
    public static int MOV_DETAILS_COL_VOTE_AVERAGE = 17;
    public static int MOV_DETAILS_COL_VOTE_COUNT = 18;
    public static int MOV_DETAILS_COL_FAVOURED = 19;
    public static int MOV_DETAILS_COL_SHOWED = 20;
    public static int MOV_DETAILS_COL_DOWNLOADED = 21;
    public static int CREW_COL__ID = 0;
    public static int CREW_COL_CREDIT_ID = 1;
    public static int CREW_COL_DEPARTMENT = 2;
    public static int CREW_COL_ID = 3;
    public static int CREW_COL_JOB = 4;
    public static int CREW_COL_NAME = 5;
    public static int CREW_COL_PROFILE_PATH = 6;
    public static int CREW_COL_MOVIE_ID = 7;
    public static int CAST_COL__ID = 0;
    public static int CAST_COL_CAST_ID = 1;
    public static int CAST_COL_CHARACTER = 2;
    public static int CAST_COL_CREDIT_ID = 3;
    public static int CAST_COL_ID = 4;
    public static int CAST_COL_NAME = 5;
    public static int CAST_COL_ORDER = 6;
    public static int CAST_COL_PROFILE_PATH = 7;
    public static int CAST_COL_MOVIE_ID = 8;
    public static int SIMILAR_MOV_COL_ID = 0;
    public static int SIMILAR_MOV_COL_MOVIE_ID_OLD = 1;
    public static int SIMILAR_MOV_COL_PAGE = 2;
    public static int SIMILAR_MOV_COL_POSTER_PATH = 3;
    public static int SIMILAR_MOV_COL_ADULT = 4;
    public static int SIMILAR_MOV_COL_OVERVIEW = 5;
    public static int SIMILAR_MOV_COL_RELEASE_DATE = 6;
    public static int SIMILAR_MOV_COL_MOVIE_ID = 7;
    public static int SIMILAR_MOV_COL_ORIGINAL_TITLE = 8;
    public static int SIMILAR_MOV_COL_ORIGINAL_LANG = 9;
    public static int SIMILAR_MOV_COL_TITLE = 10;
    public static int SIMILAR_MOV_COL_BACKDROP_PATH = 11;
    public static int SIMILAR_MOV_COL_POPULARITY = 12;
    public static int SIMILAR_MOV_COL_VOTE_COUNT = 13;
    public static int SIMILAR_MOV_COL_VOTE_AVERAGE = 14;
    public static int SIMILAR_MOV_COL_FAVOURED = 15;
    public static int SIMILAR_MOV_COL_SHOWED = 16;
    public static int SIMILAR_MOV_COL_DOWNLOADED = 17;

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

    public static int COL_VIDEOS_ID = 0;
    public static int COL_VIDEOS_VIDEO_ID = 1;
    public static int COL_VIDEOS_KEY = 2;
    public static int COL_VIDEOS_NAME = 3;
    public static int COL_VIDEOS_SITE = 4;
    public static int COL_VIDEOS_SIZE = 5;
    public static int COL_VIDEOS_TYPE = 6;
    public static int COL_VIDEOS_MOVIE_ID = 7;

    public static int COL_VIDEOS_TV_VIDEO_ID = 1;
    public static int COL_VIDEOS_TV_KEY = 2;
    public static int COL_VIDEOS_TV_NAME = 3;
    public static int COL_VIDEOS_TV_SITE = 4;
    public static int COL_VIDEOS_TV_SIZE = 5;
    public static int COL_VIDEOS_TV_TYPE = 6;
    public static int COL_VIDEOS_TV_ID = 7;

    public static int COL_REVIEW_ID = 0;
    public static int COL_REVIEW_PAGE = 1;
    public static int COL_REVIEW_TOTAL_PAGE = 2;
    public static int COL_REVIEW_TOTAL_RESULTS = 3;
    public static int COL_REVIEW_ID_REVIEWS = 4;
    public static int COL_REVIEW_AUTHOR = 5;
    public static int COL_REVIEW_CONTENT = 6;
    public static int COL_REVIEW_URL = 7;
    public static int COL_REVIEW_MOVIE_ID = 8;

    public static int COL_GENRE_ID = 0;
    public static int COL_GENRE_NAME = 1;
    public static int COL_GENRE_ID_GENRE = 3;
    public static int COL_GENRE_MOVIE_ID = 4;
    public static int COL_GENRE_TV_ID = 4;


}
