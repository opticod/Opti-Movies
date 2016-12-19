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
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.FetchDetail;
import work.technie.popularmovies.R;
import work.technie.popularmovies.adapter.CastMovieAdapter;
import work.technie.popularmovies.adapter.CrewMovieAdapter;
import work.technie.popularmovies.adapter.GenreMovieAdapter;
import work.technie.popularmovies.adapter.ReviewMovieAdapter;
import work.technie.popularmovies.adapter.SimilarMovieArrayAdapter;
import work.technie.popularmovies.adapter.VideoMovieAdapter;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.utils.AsyncResponse;
import work.technie.popularmovies.utils.PaletteTransformation;
import work.technie.popularmovies.utils.RoundedTransformation;
import work.technie.popularmovies.utils.Utility;

import static work.technie.popularmovies.Constants.CAST_COLUMNS;
import static work.technie.popularmovies.Constants.COL_VIDEOS_KEY;
import static work.technie.popularmovies.Constants.CREW_COLUMNS;
import static work.technie.popularmovies.Constants.GENRE_COLUMNS;
import static work.technie.popularmovies.Constants.MOVIE_DETAILS_COLUMNS;
import static work.technie.popularmovies.Constants.MOV_COL_BACKDROP_PATH;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_BUDGET;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_HOMEPAGE;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_ORIGINAL_LANGUAGE;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_ORIGINAL_TITLE;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_OVERVIEW;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_POSTER_PATH;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_RELEASE_DATE;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_REVENUE;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_RUNTIME;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_STATUS;
import static work.technie.popularmovies.Constants.MOV_DETAILS_COL_VOTE_AVERAGE;
import static work.technie.popularmovies.Constants.REVIEW_COLUMNS;
import static work.technie.popularmovies.Constants.SIMILAR_MOVIE_COLUMNS;
import static work.technie.popularmovies.Constants.VIDEO_COLUMNS;


public class DetailActivityFragment extends Fragment implements LoaderCallbacks<Cursor>, AsyncResponse {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private static final String MOVIE_SHARE_HASHTAG = " #PopularMovieApp";
    private static final int MOVIE_DETAILS_LOADER = 0;
    private final String DETAIL_FRAGMENT_TAG = "DFTAG";

    private View rootView;
    private String movie_Id;
    private Fragment current;
    private String orgTitle;
    private CrewMovieAdapter crewListAdapter;
    private CastMovieAdapter castListAdapter;
    private SimilarMovieArrayAdapter similarMovieListAdapter;
    private VideoMovieAdapter videoListAdapter;
    private ReviewMovieAdapter reviewListAdapter;
    private GenreMovieAdapter genreListAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean reLoadPoster;

    private void updateDetailList() {
        FetchDetail fetchTask = new FetchDetail(getActivity());
        fetchTask.response = this;
        fetchTask.execute(movie_Id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();

        Bitmap imageBitmap = null;
        String transitionName = "";
        reLoadPoster = false;

        if (arguments != null) {
            movie_Id = arguments.getString(Intent.EXTRA_TEXT);
            transitionName = arguments.getString("TRANS_NAME");
            imageBitmap = arguments.getParcelable("POSTER_IMAGE");
        }
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootView.findViewById(R.id.poster).setTransitionName(transitionName);
        }

        if (imageBitmap != null) {
            ((ImageView) rootView.findViewById(R.id.poster)).setImageBitmap(imageBitmap);
        } else {
            reLoadPoster = true;
        }

        current = this;

        loadData(rootView);


        return rootView;
    }

    public void populateAdapters() {

        Activity mActivity = getActivity();
        if (mActivity != null) {
            LinearLayoutManager crewLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            crewListAdapter = new CrewMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.Crew.buildCrewsUriWithMovieId(movie_Id),
                    CREW_COLUMNS,
                    null,
                    null,
                    null
            ));

            RecyclerView recyclerViewCrew = (RecyclerView) rootView.findViewById(R.id.recyclerview_crew);
            recyclerViewCrew.setAdapter(crewListAdapter);
            recyclerViewCrew.setLayoutManager(crewLayoutManager);

            if (crewListAdapter.getItemCount() == 0) {
                recyclerViewCrew.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_crew).setVisibility(View.VISIBLE);
            } else {
                recyclerViewCrew.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_crew).setVisibility(View.GONE);
            }

            LinearLayoutManager castLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            SnapHelper castSnapHelper = new LinearSnapHelper();
            castListAdapter = new CastMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.Cast.buildCastsUriWithMovieId(movie_Id),
                    CAST_COLUMNS,
                    null,
                    null,
                    " CAST ( " + MovieContract.Cast.ORDER + " AS REAL ) ASC"
            ));
            RecyclerView recyclerViewCast = (RecyclerView) rootView.findViewById(R.id.recyclerview_cast);
            recyclerViewCast.setAdapter(castListAdapter);
            recyclerViewCast.setLayoutManager(castLayoutManager);
            castSnapHelper.attachToRecyclerView(recyclerViewCast);

            if (castListAdapter.getItemCount() == 0) {
                recyclerViewCast.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_cast).setVisibility(View.VISIBLE);
            } else {
                recyclerViewCast.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_cast).setVisibility(View.GONE);
            }

            LinearLayoutManager similarLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            similarMovieListAdapter = new SimilarMovieArrayAdapter(mActivity.getContentResolver().query(
                    MovieContract.SimilarMovies.buildSimilarMoviesUriWithMovieId(movie_Id),
                    SIMILAR_MOVIE_COLUMNS,
                    null,
                    null,
                    null
            ));
            RecyclerView recyclerViewSimilarMovies = (RecyclerView) rootView.findViewById(R.id.recyclerview_similar_movies);
            recyclerViewSimilarMovies.setAdapter(similarMovieListAdapter);
            recyclerViewSimilarMovies.setLayoutManager(similarLayoutManager);

            if (similarMovieListAdapter.getItemCount() == 0) {
                recyclerViewSimilarMovies.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_similar_movies).setVisibility(View.VISIBLE);
            } else {
                recyclerViewSimilarMovies.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_similar_movies).setVisibility(View.GONE);
            }

            LinearLayoutManager videoLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            videoListAdapter = new VideoMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.Videos.buildMoviesUriWithMovieId(movie_Id),
                    VIDEO_COLUMNS,
                    null,
                    null,
                    null
            ));

            RecyclerView recyclerViewVideos = (RecyclerView) rootView.findViewById(R.id.recyclerview_videos);
            recyclerViewVideos.setAdapter(videoListAdapter);
            recyclerViewVideos.setLayoutManager(videoLayoutManager);

            if (videoListAdapter.getItemCount() == 0) {
                recyclerViewVideos.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_video).setVisibility(View.VISIBLE);
            } else {
                recyclerViewVideos.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_video).setVisibility(View.GONE);
            }

            LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
            reviewListAdapter = new ReviewMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.Reviews.buildReviewsUriWithMovieId(movie_Id),
                    REVIEW_COLUMNS,
                    null,
                    null,
                    null
            ));

            RecyclerView recyclerViewReviews = (RecyclerView) rootView.findViewById(R.id.recyclerview_review);
            recyclerViewReviews.setAdapter(reviewListAdapter);
            recyclerViewReviews.setLayoutManager(reviewLayoutManager);

            if (reviewListAdapter.getItemCount() == 0) {
                recyclerViewReviews.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_review).setVisibility(View.VISIBLE);
            } else {
                recyclerViewReviews.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_review).setVisibility(View.GONE);
            }


            GridLayoutManager genreLayoutManager = new GridLayoutManager(mActivity, 2);
            SnapHelper genreSnapHelper = new LinearSnapHelper();
            genreListAdapter = new GenreMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.Genres.buildGenresUriWithMovieId(movie_Id),
                    GENRE_COLUMNS,
                    null,
                    null,
                    null
            ));

            RecyclerView recyclerViewGenres = (RecyclerView) rootView.findViewById(R.id.recyclerview_genre);
            recyclerViewGenres.setAdapter(genreListAdapter);
            recyclerViewGenres.setLayoutManager(genreLayoutManager);
            genreSnapHelper.attachToRecyclerView(recyclerViewGenres);


            if (genreListAdapter.getItemCount() == 0) {
                recyclerViewGenres.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_genre).setVisibility(View.VISIBLE);
            } else {
                recyclerViewGenres.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_genre).setVisibility(View.GONE);
            }

            similarMovieListAdapter.setOnClickListener(new SimilarMovieArrayAdapter.SetOnClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    Cursor cursor = similarMovieListAdapter.getCursor();
                    cursor.moveToPosition(position);
                    Activity mActivity = getActivity();

                    DetailActivityFragment fragment = new DetailActivityFragment();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        current.setSharedElementReturnTransition(TransitionInflater.from(
                                mActivity).inflateTransition(R.transition.change_image_trans));
                        current.setExitTransition(TransitionInflater.from(
                                mActivity).inflateTransition(android.R.transition.fade));

                        fragment.setSharedElementEnterTransition(TransitionInflater.from(
                                mActivity).inflateTransition(R.transition.change_image_trans));
                        fragment.setEnterTransition(TransitionInflater.from(
                                mActivity).inflateTransition(android.R.transition.fade));
                    }

                    Bundle arguments = new Bundle();
                    arguments.putString(Intent.EXTRA_TEXT, cursor.getString(Constants.SIMILAR_MOV_COL_MOVIE_ID));
                    fragment.setArguments(arguments);
                    FragmentManager fragmentManager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.frag_container, fragment, DETAIL_FRAGMENT_TAG)
                            .addToBackStack(null)
                            .commit();
                }
            });

            videoListAdapter.setOnClickListener(new VideoMovieAdapter.SetOnClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    Cursor cursor = videoListAdapter.getCursor();
                    cursor.moveToPosition(position);
                    Activity mActivity = getActivity();
                    final String source = cursor.getString(COL_VIDEOS_KEY);
                    final String trailerUrl = "https://www.youtube.com/watch?v=" + source;

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                    mActivity.startActivity(intent);
                }
            });

            String firstVideoLink = "https://www.youtube.com/watch?v=";
            Cursor mCursor = mActivity.getContentResolver().query(
                    MovieContract.Videos.buildMoviesUriWithMovieId(movie_Id),
                    VIDEO_COLUMNS,
                    null,
                    null,
                    null
            );
            if (mCursor != null) {
                mCursor.moveToFirst();
                firstVideoLink += mCursor.getString(Constants.COL_VIDEOS_KEY);
                mCursor.close();
            }

            FloatingActionButton play = (FloatingActionButton) rootView.findViewById(R.id.play);

            final String finalFirstVideoLink = firstVideoLink;
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalFirstVideoLink));
                    startActivity(intent);
                }
            });


            FloatingActionButton share = (FloatingActionButton) rootView.findViewById(R.id.share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT,
                            orgTitle + " Watch : " + finalFirstVideoLink + MOVIE_SHARE_HASHTAG);
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, getString(R.string.share_links)));
                }
            });

        }
    }

    public void loadData(final View rootView) {

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.detail_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utility.hasNetworkConnection(getActivity())) {
                    getActivity().getContentResolver().delete(MovieContract.Videos.buildMoviesUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.Reviews.buildReviewsUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.Genres.buildGenresUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.SimilarMovies.buildSimilarMoviesUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.Crew.buildCrewsUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.Cast.buildCastsUriWithMovieId(movie_Id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.MovieDetails.buildMovieDetailsUriWithMovieId(movie_Id), null, null);
                    updateDetailList();
                } else {
                    Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        int count;

        Uri Uri = MovieContract.MovieDetails.buildMovieDetailsUri();
        count = getActivity().getContentResolver().query(Uri, Constants.MOVIE_DETAILS_COLUMNS_MIN,
                MovieContract.MovieDetails.MOVIE_ID + " = ? ",
                new String[]{movie_Id},
                null).getCount();

        if (count == 0) {
            if (!Utility.hasNetworkConnection(getActivity())) {
                Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_LONG).show();
            } else {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
                updateDetailList();
            }
        } else {
            populateAdapters();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        getLoaderManager().initLoader(MOVIE_DETAILS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != movie_Id) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            switch (id) {
                case MOVIE_DETAILS_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.MovieDetails.buildMovieDetailsUriWithMovieId(movie_Id),
                            MOVIE_DETAILS_COLUMNS,
                            null,
                            null,
                            null
                    );
            }
        }
        return null;
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
        if (!data.moveToFirst()) {
            return;
        }
        switch (loader.getId()) {
            case MOVIE_DETAILS_LOADER:
                defaultShow();

                String votAvg = data.getString(MOV_DETAILS_COL_VOTE_AVERAGE);
                double vote = Double.parseDouble(votAvg);
                votAvg = String.valueOf((double) Math.round(vote * 10d) / 10d);
                ((TextView) rootView.findViewById(R.id.vote))
                        .setText(votAvg);
                String backdropURL = data.getString(MOV_COL_BACKDROP_PATH);
                final ImageView backdrop = (ImageView) rootView.findViewById(R.id.backdropImg);

                Picasso.with(getActivity())
                        .load(backdropURL)
                        .fit().centerCrop()
                        .transform(PaletteTransformation.instance())
                        .into(backdrop, new Callback.EmptyCallback() {
                            @Override
                            public void onSuccess() {
                                Bitmap bitmap = ((BitmapDrawable) backdrop.getDrawable()).getBitmap();
                                Palette palette = PaletteTransformation.getPalette(bitmap);
                                changeSystemToolbarColor(palette);
                            }
                        });

                backdrop.setAdjustViewBounds(true);

                orgTitle = data.getString(MOV_DETAILS_COL_ORIGINAL_TITLE).trim();
                ((TextView) rootView.findViewById(R.id.orgTitle))
                        .setText(orgTitle.isEmpty() ? "-" : orgTitle);


                String overview = data.getString(MOV_DETAILS_COL_OVERVIEW).trim();
                ((TextView) rootView.findViewById(R.id.overview))
                        .setText(overview.isEmpty() ? "-" : overview);

                String status = data.getString(MOV_DETAILS_COL_STATUS).trim();
                ((TextView) rootView.findViewById(R.id.status))
                        .setText(status.isEmpty() ? "-" : status);

                String original_lang = data.getString(MOV_DETAILS_COL_ORIGINAL_LANGUAGE).toUpperCase().trim();
                ((TextView) rootView.findViewById(R.id.original_lang))
                        .setText(original_lang.isEmpty() ? "-" : original_lang);

                TextView runtimeText = (TextView) rootView.findViewById(R.id.runtime);

                int runtime = data.getInt(MOV_DETAILS_COL_RUNTIME);
                if (runtime >= 60) {
                    int min = (int) Math.floor(runtime / 60);
                    int sec = (int) (runtime - min * 60);
                    runtimeText.setText(String.format(Locale.US, "%d" + "hr" + " %d" + "s", min, sec));
                } else {
                    runtimeText.setText(String.format(Locale.US, "%s" + "s", runtime == 0 ? "-" : String.valueOf(runtime)));
                }

                long budget = data.getLong(MOV_DETAILS_COL_BUDGET);

                ((TextView) rootView.findViewById(R.id.budget))
                        .setText(budget == 0 ? "-" : String.format(Locale.getDefault(), "$%,d", budget));


                long revenue = data.getLong(MOV_DETAILS_COL_REVENUE);
                ((TextView) rootView.findViewById(R.id.revenue))
                        .setText(String.valueOf(revenue == 0 ? "-" : String.format(Locale.getDefault(), "$%,d", revenue)));

                String homepage = data.getString(MOV_DETAILS_COL_HOMEPAGE).trim();
                ((TextView) rootView.findViewById(R.id.homepage))
                        .setText(homepage.isEmpty() ? "-" : homepage);

                String releaseDate = data.getString(MOV_DETAILS_COL_RELEASE_DATE).trim();

                if (!releaseDate.isEmpty()) {
                    DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = null;
                    try {
                        date = inputFormatter.parse(releaseDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault());

                    ((TextView) rootView.findViewById(R.id.release_date))
                            .setText(formatter.format(date));
                } else {
                    ((TextView) rootView.findViewById(R.id.release_date))
                            .setText("-");
                }

                final String postURL = data.getString(MOV_DETAILS_COL_POSTER_PATH);
                if (reLoadPoster) {
                    final ImageView poster = (ImageView) rootView.findViewById(R.id.poster);

                    Picasso
                            .with(getActivity())
                            .load(postURL)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .transform(new RoundedTransformation(10, 10))
                            .fit()
                            .centerCrop()
                            .into(poster, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError() {
                                    Picasso
                                            .with(getActivity())
                                            .load(postURL)
                                            .error(R.mipmap.ic_launcher)
                                            .fit()
                                            .centerCrop()
                                            .into(poster, new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                }

                                                @Override
                                                public void onError() {
                                                }
                                            });
                                }
                            });
                    poster.setAdjustViewBounds(true);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown Loader");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onFinish(boolean isData) {
        swipeRefreshLayout.setRefreshing(false);
        populateAdapters();
    }

    public void changeSystemToolbarColor(Palette palette) {
        List<Palette.Swatch> vibrant = palette.getSwatches();
        for (Palette.Swatch s : vibrant) {
            if (s != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Activity mActivity = getActivity();
                    if (mActivity != null) {
                        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        mActivity.getWindow().setStatusBarColor(s.getRgb());
                    }
                }
            }
        }

    }
}
