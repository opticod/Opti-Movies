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

package work.technie.popularmovies.fragment;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import work.technie.popularmovies.FetchTrailReview;
import work.technie.popularmovies.R;
import work.technie.popularmovies.adapter.GenreMovieAdapter;
import work.technie.popularmovies.adapter.ReviewMovieAdapter;
import work.technie.popularmovies.adapter.TrailerMovieAdapter;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.utils.Utility;


public class DetailActivityFragment extends Fragment implements LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private static final String MOVIE_SHARE_HASHTAG = " #PopularMovieApp #ByAnupam ";
    private static final int DETAIL_LOADER = 0;
    private static final int TRAILER_LOADER = 1;
    private static final int REVIEW_LOADER = 2;
    private static final int GENRE_LOADER = 3;
    private static final int FAVOURITE_LOADER = 4;
    private static final String[] MOVIE_COLUMNS = {

            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies._ID,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.PAGE,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.POSTER_PATH,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.ADULT,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.OVERVIEW,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.RELEASE_DATE,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.MOVIE_ID,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.ORIGINAL_TITLE,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.ORIGINAL_LANGUAGE,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.TITLE,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.BACKDROP_PATH,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.POPULARITY,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.VOTE_COUNT,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.VOTE_AVERAGE,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.FAVOURED,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.SHOWED,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.DOWNLOADED,
            MovieContract.Movies.TABLE_NAME + "." + MovieContract.Movies.SORT_BY

    };
    private static final String[] TRAILER_COLUMNS = {

            MovieContract.Trailers.TABLE_NAME + "." + MovieContract.Trailers._ID,
            MovieContract.Trailers.TABLE_NAME + "." + MovieContract.Trailers.NAME,
            MovieContract.Trailers.TABLE_NAME + "." + MovieContract.Trailers.SIZE,
            MovieContract.Trailers.TABLE_NAME + "." + MovieContract.Trailers.SOURCE,
            MovieContract.Trailers.TABLE_NAME + "." + MovieContract.Trailers.TYPE,
            MovieContract.Trailers.TABLE_NAME + "." + MovieContract.Trailers.MOVIE_ID
    };
    private static final String[] REVIEW_COLUMNS = {

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
    private static final String[] GENRE_COLUMNS = {

            MovieContract.Genres.TABLE_NAME + "." + MovieContract.Genres._ID,
            MovieContract.Genres.TABLE_NAME + "." + MovieContract.Genres.NAME,
            MovieContract.Genres.TABLE_NAME + "." + MovieContract.Genres.ID_GENRES,
            MovieContract.Genres.TABLE_NAME + "." + MovieContract.Genres.MOVIE_ID
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
            MovieContract.Favourites.FAVOURED,
            MovieContract.Favourites.SHOWED,
            MovieContract.Favourites.DOWNLOADED,
            MovieContract.Favourites.SORT_BY
    };
    public static String playTrailer = "https://www.youtube.com/watch?v=";
    public static int COL_ID = 0;
    public static int COL_PAGE = 1;
    public static int COL_POSTER_PATH = 2;
    public static int COL_ADULT = 3;
    public static int COL_OVERVIEW = 4;
    public static int COL_RELEASE_DATE = 5;
    public static int COL_MOVIE_ID = 6;
    public static int COL_ORIGINAL_TITLE = 7;
    public static int COL_ORIGINAL_LANG = 8;
    public static int COL_TITLE = 9;
    public static int COL_BACKDROP_PATH = 10;
    public static int COL_POPULARITY = 11;
    public static int COL_VOTE_COUNT = 12;
    public static int COL_VOTE_AVERAGE = 13;
    public static int COL_FAVOURED = 14;
    public static int COL_SHOWED = 15;
    public static int COL_DOWNLOADED = 16;
    public static int COL_SORT_BY = 17;
    public static int COL_TRAILER_ID = 0;
    public static int COL_TRAILER_NAME = 1;
    public static int COL_TRAILER_SIZE = 2;
    public static int COL_TRAILER_SOURCE = 3;
    public static int COL_TRAILER_TYPE = 4;
    public static int COL_TRAILER_MOVIE_ID = 5;
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
    private static String showed = "0";
    private static ContentValues movieValues;
    String orgLang;
    String orgTitle;
    String overview;
    String relDate;
    String postURL;
    String popularity;
    String votAvg;
    String favourite;
    String movieId;
    String backdropURL;
    String trailerName;
    String trailerSize;
    String trailerSource;
    String trailerType;
    String trailerID;
    private View rootView;
    private String movie_Id;
    private TrailerMovieAdapter trailerListAdapter;
    private ReviewMovieAdapter reviewListAdapter;
    private GenreMovieAdapter genreListAdapter;
    private ListView listViewTrailers;
    private ListView listViewReviews;
    private ListView listViewGenres;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String genre = "Genre : ";

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
        playTrailer = "https://www.youtube.com/watch?v=";
        movieValues = new ContentValues();
    }

    private void updateMovieList() {
        FetchTrailReview weatherTask = new FetchTrailReview(getActivity());
        weatherTask.execute(movie_Id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Activity mActivity = getActivity();
        Toolbar toolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);
            ((AppCompatActivity) mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            movie_Id = arguments.getString(Intent.EXTRA_TEXT);
        }
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        trailerListAdapter = new TrailerMovieAdapter(getActivity(), null, 0);
        listViewTrailers = (ListView) rootView.findViewById(R.id.listview_trailer);
        listViewTrailers.setAdapter(trailerListAdapter);

        reviewListAdapter = new ReviewMovieAdapter(getActivity(), null, 0);
        listViewReviews = (ListView) rootView.findViewById(R.id.listview_review);
        listViewReviews.setAdapter(reviewListAdapter);

        genreListAdapter = new GenreMovieAdapter(getActivity(), null, 0);
        listViewGenres = (ListView) rootView.findViewById(R.id.listview_genre);
        listViewGenres.setAdapter(genreListAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.detail_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utility.hasNetworkConnection(getActivity())) {
                    getActivity().getContentResolver().delete(MovieContract.Trailers.buildMoviesUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.Reviews.buildMoviesUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.Genres.buildMoviesUriWithMovieId(movie_Id), null, null);
                    genre = "Genre : ";
                    updateMovieList();
                    //Log.v(LOG_TAG, "refreshed");
                } else {
                    Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        FloatingActionButton play = (FloatingActionButton) rootView.findViewById(R.id.play);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playTrailer));
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        getLoaderManager().initLoader(TRAILER_LOADER, null, this);
        getLoaderManager().initLoader(REVIEW_LOADER, null, this);
        getLoaderManager().initLoader(GENRE_LOADER, null, this);
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    public void onSortingChanged() {
        String mI = movie_Id;
        if (null != mI) {
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
            getLoaderManager().restartLoader(TRAILER_LOADER, null, this);
            getLoaderManager().restartLoader(REVIEW_LOADER, null, this);
            getLoaderManager().restartLoader(GENRE_LOADER, null, this);
            getLoaderManager().restartLoader(FAVOURITE_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != movie_Id) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            switch (id) {
                case DETAIL_LOADER:
                    String sorting = Utility.getPreferredSorting(getActivity());
                    if (sorting.equalsIgnoreCase(getResources().getString(R.string.pref_sort_favourite))) {
                        return new CursorLoader(getActivity(),
                                MovieContract.Favourites.buildMovieUri(),
                                FAVOURITE_MOVIE_COLUMNS,
                                MovieContract.Favourites.MOVIE_ID + " = ?",
                                new String[]{movie_Id},
                                null);
                    }
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.Movies.buildMoviesUriWithMovieId(movie_Id),
                            MOVIE_COLUMNS,
                            null,
                            null,
                            null
                    );
                case FAVOURITE_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.Favourites.buildMovieUri(),
                            FAVOURITE_MOVIE_COLUMNS,
                            null,
                            null,
                            null
                    );
                case TRAILER_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.Trailers.buildMoviesUriWithMovieId(movie_Id),
                            TRAILER_COLUMNS,
                            null,
                            null,
                            null
                    );
                case REVIEW_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.Reviews.buildMoviesUriWithMovieId(movie_Id),
                            REVIEW_COLUMNS,
                            null,
                            null,
                            null
                    );
                case GENRE_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.Genres.buildMoviesUriWithMovieId(movie_Id),
                            GENRE_COLUMNS,
                            null,
                            null,
                            null
                    );
            }
        }
        return null;
    }

    private void hide() {
        rootView.findViewById(R.id.TrailerText).setVisibility(View.GONE);
        rootView.findViewById(R.id.ReviewsText).setVisibility(View.GONE);
        rootView.findViewById(R.id.listview_trailer).setVisibility(View.GONE);
        rootView.findViewById(R.id.listview_review).setVisibility(View.GONE);
        rootView.findViewById(R.id.hide).setVisibility(View.GONE);
        rootView.findViewById(R.id.show).setVisibility(View.VISIBLE);
    }

    private void show() {
        rootView.findViewById(R.id.TrailerText).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.ReviewsText).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.listview_trailer).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.listview_review).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.hide).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.show).setVisibility(View.GONE);
    }

    private void defaultShow() {
        rootView.findViewById(R.id.divisor).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.ten).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.share).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.play).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        swipeRefreshLayout.setRefreshing(false);
        //Log.v(LOG_TAG, "In onLoadFinished");
        if (!data.moveToFirst()) {
            return;
        }
        switch (loader.getId()) {
            case DETAIL_LOADER:
                defaultShow();
                orgLang = data.getString(COL_ORIGINAL_LANG);

                orgTitle = data.getString(COL_ORIGINAL_TITLE);
                ((TextView) rootView.findViewById(R.id.orgTitle))
                        .setText(orgTitle);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(orgTitle);

                overview = data.getString(COL_OVERVIEW);
                ((TextView) rootView.findViewById(R.id.overview))
                        .setText(overview);

                relDate = data.getString(COL_RELEASE_DATE);

                ((TextView) rootView.findViewById(R.id.relDate))
                        .setText("Release Date: " + relDate);

                postURL = data.getString(COL_POSTER_PATH);
                ImageView poster = (ImageView) rootView.findViewById(R.id.poster);
                Picasso
                        .with(getActivity())
                        .load(postURL)
                        .transform(new RoundedCornersTransformation(10, 10))
                        .fit()
                        .into(poster);

                movieId = data.getString(COL_MOVIE_ID);
                popularity = data.getString(COL_POPULARITY);
                double pop = Double.parseDouble(popularity);
                popularity = String.valueOf((double) Math.round(pop * 10d) / 10d);

                ((TextView) rootView.findViewById(R.id.popularity))
                        .setText("Popularity : " + popularity);

                votAvg = data.getString(COL_VOTE_AVERAGE);
                double vote = Double.parseDouble(votAvg);
                votAvg = String.valueOf((double) Math.round(vote * 10d) / 10d);
                ((TextView) rootView.findViewById(R.id.vote))
                        .setText(votAvg);
                backdropURL = data.getString(COL_BACKDROP_PATH);
                final ImageView backdrop = (ImageView) rootView.findViewById(R.id.backdropImg);
                Picasso
                        .with(getActivity())
                        .load(backdropURL)
                        .fit()
                        .centerCrop()
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(backdrop, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Picasso
                                        .with(getActivity())
                                        .load(backdropURL)
                                        .fit()
                                        .centerCrop()
                                        .error(R.mipmap.ic_launcher)
                                        .into(backdrop, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                            }

                                            @Override
                                            public void onError() {
                                                //Log.v("Error Loading Images", "'");
                                            }
                                        });
                            }
                        });
                backdrop.setAdjustViewBounds(true);

                final String downloaded = data.getString(COL_DOWNLOADED);
                showed = data.getString(COL_SHOWED);
                FloatingActionButton show;
                if (showed.equalsIgnoreCase("1")) {
                    rootView.findViewById(R.id.hide).setVisibility(View.VISIBLE);
                    show = (FloatingActionButton) rootView.findViewById(R.id.hide);
                    show();
                } else {
                    rootView.findViewById(R.id.show).setVisibility(View.VISIBLE);
                    show = (FloatingActionButton) rootView.findViewById(R.id.show);
                    hide();
                }
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContentValues sh = new ContentValues();
                        if (showed.equalsIgnoreCase("1")) {
                            sh.put(MovieContract.Movies.SHOWED, "0");
                            Toast.makeText(getContext(), "TRAILERS and REVIEWS hidden!", Toast.LENGTH_SHORT).show();
                            hide();
                        } else {
                            sh.put(MovieContract.Movies.SHOWED, "1");
                            if (downloaded.equalsIgnoreCase("0")) {
                                if (Utility.hasNetworkConnection(getActivity())) {
                                    updateMovieList();
                                    sh.put(MovieContract.Movies.DOWNLOADED, "1");
                                    Toast.makeText(getContext(), "TRAILERS and REVIEWS shown!", Toast.LENGTH_SHORT).show();
                                    show();
                                } else {
                                    Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        getContext().getContentResolver().update(
                                MovieContract.Movies.CONTENT_URI.buildUpon().appendPath(movieId).build(),
                                sh, null, new String[]{movieId});
                    }
                });

                FloatingActionButton play = (FloatingActionButton) rootView.findViewById(R.id.play);

                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (downloaded.equalsIgnoreCase("0")) {
                            ContentValues sh = new ContentValues();
                            if (Utility.hasNetworkConnection(getActivity())) {
                                updateMovieList();
                                sh.put(MovieContract.Movies.DOWNLOADED, "1");
                                getContext().getContentResolver().update(
                                        MovieContract.Movies.CONTENT_URI.buildUpon().appendPath(movieId).build(),
                                        sh, null, new String[]{movieId});
                                Toast.makeText(getContext(), "Click One More Time to Play!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playTrailer));
                            startActivity(intent);
                        }
                    }
                });
                FloatingActionButton share = (FloatingActionButton) rootView.findViewById(R.id.share);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (downloaded.equalsIgnoreCase("0")) {
                            ContentValues sh = new ContentValues();
                            if (Utility.hasNetworkConnection(getActivity())) {
                                updateMovieList();
                                sh.put(MovieContract.Movies.DOWNLOADED, "1");
                                getContext().getContentResolver().update(
                                        MovieContract.Movies.CONTENT_URI.buildUpon().appendPath(movieId).build(),
                                        sh, null, new String[]{movieId});
                                Toast.makeText(getContext(), "Click One More Time to Share!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT,
                                    orgTitle + "Watch : " + playTrailer + MOVIE_SHARE_HASHTAG);
                            intent.setType("text/plain");
                            startActivity(Intent.createChooser(intent, getString(R.string.share_links)));
                        }
                    }
                });
                if (movieValues.size() == 0) {
                    movieValues.put(MovieContract.Movies.PAGE, data.getString(COL_PAGE));
                    movieValues.put(MovieContract.Movies.POSTER_PATH, postURL);
                    movieValues.put(MovieContract.Movies.ADULT, data.getString(COL_ADULT));
                    movieValues.put(MovieContract.Movies.OVERVIEW, overview);
                    movieValues.put(MovieContract.Movies.RELEASE_DATE, relDate);
                    movieValues.put(MovieContract.Movies.MOVIE_ID, movie_Id);
                    movieValues.put(MovieContract.Movies.ORIGINAL_TITLE, orgTitle);
                    movieValues.put(MovieContract.Movies.ORIGINAL_LANGUAGE, orgLang);
                    movieValues.put(MovieContract.Movies.TITLE, data.getString(COL_TITLE));
                    movieValues.put(MovieContract.Movies.BACKDROP_PATH, backdropURL);
                    movieValues.put(MovieContract.Movies.POPULARITY, popularity);
                    movieValues.put(MovieContract.Movies.VOTE_COUNT, data.getString(COL_VOTE_COUNT));
                    movieValues.put(MovieContract.Movies.VOTE_AVERAGE, votAvg);
                    movieValues.put(MovieContract.Movies.FAVOURED, "1");
                    movieValues.put(MovieContract.Movies.SHOWED, data.getString(COL_SHOWED));
                    movieValues.put(MovieContract.Movies.DOWNLOADED, data.getString(COL_DOWNLOADED));
                    movieValues.put(MovieContract.Movies.SORT_BY, data.getString(COL_SORT_BY));
                }
                break;
            case FAVOURITE_LOADER:
                FloatingActionButton fab;
                boolean favoured = false;
                if (data.moveToFirst()) {
                    do {
                        if (data.getString(COL_MOVIE_ID).equalsIgnoreCase(movie_Id)) {
                            favoured = true;
                        }
                    }
                    while (data.moveToNext());
                }
                if (favoured) {
                    rootView.findViewById(R.id.bookmark).setVisibility(View.VISIBLE);
                    fab = (FloatingActionButton) rootView.findViewById(R.id.bookmark);
                } else {
                    rootView.findViewById(R.id.border_bookmark).setVisibility(View.VISIBLE);
                    fab = (FloatingActionButton) rootView.findViewById(R.id.border_bookmark);
                }
                final boolean finalFavoured = favoured;
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (finalFavoured) {
                            getActivity().getContentResolver().delete(MovieContract.Favourites.buildMoviesUriWithMovieId(movie_Id), null, null);
                            Toast.makeText(getContext(), "REMOVED FROM FAVOURITES!", Toast.LENGTH_SHORT).show();
                            rootView.findViewById(R.id.bookmark).setVisibility(View.GONE);
                            rootView.findViewById(R.id.border_bookmark).setVisibility(View.VISIBLE);
                        } else {
                            getActivity().getContentResolver().insert(MovieContract.Favourites.buildMovieUri(), movieValues);
                            Toast.makeText(getContext(), "ADDED TO FAVOURITES!", Toast.LENGTH_SHORT).show();
                            rootView.findViewById(R.id.bookmark).setVisibility(View.VISIBLE);
                            rootView.findViewById(R.id.border_bookmark).setVisibility(View.GONE);
                        }
                    }
                });
                break;
            case TRAILER_LOADER:
                int iter = 0;
                if (data.moveToFirst()) {
                    do {
                        trailerListAdapter.swapCursor(data);
                        iter++;
                        if (iter == 1) {
                            playTrailer += data.getString(DetailActivityFragment.COL_TRAILER_SOURCE);
                        }
                    }
                    while (data.moveToNext());
                }
                break;
            case REVIEW_LOADER:
                //Log.e(LOG_TAG, "Review");
                if (data.moveToFirst()) {
                    do {
                        reviewListAdapter.swapCursor(data);
                    }
                    while (data.moveToNext());
                }
                break;
            case GENRE_LOADER:
                if (data.moveToFirst()) {
                    do {
                        if (genre.length() < 9)
                            genre += data.getString(COL_GENRE_NAME) + " ";
                        genreListAdapter.swapCursor(data);
                    }
                    while (data.moveToNext());
                    TextView genreNames = ((TextView) rootView.findViewById(R.id.genreNames));
                    genreNames.setText(genre);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Loader");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        trailerListAdapter.swapCursor(null);
        reviewListAdapter.swapCursor(null);
        genreListAdapter.swapCursor(null);
    }
}
