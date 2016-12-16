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
import android.view.LayoutInflater;
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
import work.technie.popularmovies.FetchDetailMovie;
import work.technie.popularmovies.R;
import work.technie.popularmovies.adapter.GenreMovieAdapter;
import work.technie.popularmovies.adapter.ReviewMovieAdapter;
import work.technie.popularmovies.adapter.TrailerMovieAdapter;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.utils.Utility;

import static work.technie.popularmovies.Constants.COL_GENRE_NAME;
import static work.technie.popularmovies.Constants.COL_TRAILER_SOURCE;
import static work.technie.popularmovies.Constants.GENRE_COLUMNS;
import static work.technie.popularmovies.Constants.MOVIE_COLUMNS;
import static work.technie.popularmovies.Constants.MOV_COL_BACKDROP_PATH;
import static work.technie.popularmovies.Constants.MOV_COL_DOWNLOADED;
import static work.technie.popularmovies.Constants.MOV_COL_MOVIE_ID;
import static work.technie.popularmovies.Constants.MOV_COL_ORIGINAL_LANG;
import static work.technie.popularmovies.Constants.MOV_COL_ORIGINAL_TITLE;
import static work.technie.popularmovies.Constants.MOV_COL_OVERVIEW;
import static work.technie.popularmovies.Constants.MOV_COL_POPULARITY;
import static work.technie.popularmovies.Constants.MOV_COL_POSTER_PATH;
import static work.technie.popularmovies.Constants.MOV_COL_RELEASE_DATE;
import static work.technie.popularmovies.Constants.MOV_COL_SHOWED;
import static work.technie.popularmovies.Constants.MOV_COL_VOTE_AVERAGE;
import static work.technie.popularmovies.Constants.REVIEW_COLUMNS;
import static work.technie.popularmovies.Constants.VIDEO_COLUMNS;


public class DetailActivityFragment extends Fragment implements LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private static final String MOVIE_SHARE_HASHTAG = " #PopularMovieApp #ByAnupam ";
    private static final int DETAIL_LOADER = 0;
    private static final int TRAILER_LOADER = 1;
    private static final int REVIEW_LOADER = 2;
    private static final int GENRE_LOADER = 3;

    public static String playTrailer = "https://www.youtube.com/watch?v=";
    private static String showed = "0";
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
        playTrailer = "https://www.youtube.com/watch?v=";
    }

    private void updateMovieList() {
        FetchDetailMovie weatherTask = new FetchDetailMovie(getActivity());
        weatherTask.execute(movie_Id);
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
                    getActivity().getContentResolver().delete(MovieContract.Videos.buildMoviesUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.Reviews.buildReviewsUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.Genres.buildGenresUriWithMovieId(movie_Id), null, null);
                    genre = "Genre : ";
                    updateMovieList();
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

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != movie_Id) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            switch (id) {
                case DETAIL_LOADER:
                    String sorting = Utility.getPreferredSorting(getActivity());
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.Movies.buildMoviesUriWithMovieId(movie_Id),
                            MOVIE_COLUMNS,
                            null,
                            null,
                            null
                    );
                case TRAILER_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.Videos.buildMoviesUriWithMovieId(movie_Id),
                            VIDEO_COLUMNS,
                            null,
                            null,
                            null
                    );
                case REVIEW_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.Reviews.buildReviewsUriWithMovieId(movie_Id),
                            REVIEW_COLUMNS,
                            null,
                            null,
                            null
                    );
                case GENRE_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.Genres.buildGenresUriWithMovieId(movie_Id),
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
                orgLang = data.getString(MOV_COL_ORIGINAL_LANG);

                orgTitle = data.getString(MOV_COL_ORIGINAL_TITLE);
                ((TextView) rootView.findViewById(R.id.orgTitle))
                        .setText(orgTitle);
//                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(orgTitle);

                overview = data.getString(MOV_COL_OVERVIEW);
                ((TextView) rootView.findViewById(R.id.overview))
                        .setText(overview);

                relDate = data.getString(MOV_COL_RELEASE_DATE);

                ((TextView) rootView.findViewById(R.id.relDate))
                        .setText("Release Date: " + relDate);

                postURL = data.getString(MOV_COL_POSTER_PATH);
                ImageView poster = (ImageView) rootView.findViewById(R.id.poster);
                Picasso
                        .with(getActivity())
                        .load(postURL)
                        .transform(new RoundedCornersTransformation(10, 10))
                        .fit()
                        .into(poster);

                movieId = data.getString(MOV_COL_MOVIE_ID);
                popularity = data.getString(MOV_COL_POPULARITY);
                double pop = Double.parseDouble(popularity);
                popularity = String.valueOf((double) Math.round(pop * 10d) / 10d);

                ((TextView) rootView.findViewById(R.id.popularity))
                        .setText("Popularity : " + popularity);

                votAvg = data.getString(MOV_COL_VOTE_AVERAGE);
                double vote = Double.parseDouble(votAvg);
                votAvg = String.valueOf((double) Math.round(vote * 10d) / 10d);
                ((TextView) rootView.findViewById(R.id.vote))
                        .setText(votAvg);
                backdropURL = data.getString(MOV_COL_BACKDROP_PATH);
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

                final String downloaded = data.getString(MOV_COL_DOWNLOADED);
                showed = data.getString(MOV_COL_SHOWED);
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
                break;
            case TRAILER_LOADER:
                int iter = 0;
                if (data.moveToFirst()) {
                    do {
                        trailerListAdapter.swapCursor(data);
                        iter++;
                        if (iter == 1) {
                            playTrailer += data.getString(COL_TRAILER_SOURCE);
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
