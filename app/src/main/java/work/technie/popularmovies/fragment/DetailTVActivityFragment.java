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


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
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
import android.support.v4.content.ContextCompat;
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
import java.util.Locale;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.FetchTVDetail;
import work.technie.popularmovies.R;
import work.technie.popularmovies.adapter.CastMovieAdapter;
import work.technie.popularmovies.adapter.CreatorMovieAdapter;
import work.technie.popularmovies.adapter.GenreMovieAdapter;
import work.technie.popularmovies.adapter.SeasonsTVArrayAdapter;
import work.technie.popularmovies.adapter.SimilarMovieArrayAdapter;
import work.technie.popularmovies.adapter.VideoMovieAdapter;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.utils.AsyncResponse;
import work.technie.popularmovies.utils.PaletteTransformation;
import work.technie.popularmovies.utils.RoundedTransformation;
import work.technie.popularmovies.utils.Utility;

import static work.technie.popularmovies.Constants.COL_VIDEOS_TV_KEY;
import static work.technie.popularmovies.Constants.CREATOR_COLUMNS;
import static work.technie.popularmovies.Constants.FAVOURITE_TV_COLUMNS;
import static work.technie.popularmovies.Constants.TVCAST_COLUMNS;
import static work.technie.popularmovies.Constants.TV_DETAILS_COLUMNS;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_BACKDROP_PATH;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_FIRST_AIR_DATE;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_HOMEPAGE;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_LAST_AIR_DATE;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_ORIGINAL_LANG;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_ORIGINAL_NAME;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_OVERVIEW;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_POSTER_PATH;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_STATUS;
import static work.technie.popularmovies.Constants.TV_DETAILS_COL_VOTE_AVERAGE;
import static work.technie.popularmovies.Constants.TV_GENRE_COLUMNS;
import static work.technie.popularmovies.Constants.TV_NETWORKS_COLUMNS;
import static work.technie.popularmovies.Constants.TV_NETWORKS_COL_NAME;
import static work.technie.popularmovies.Constants.TV_RUNTIME_EPISODES_COLUMNS;
import static work.technie.popularmovies.Constants.TV_RUNTIME_EPISODE_COL_TIME;
import static work.technie.popularmovies.Constants.TV_SEASONS_COLUMNS;
import static work.technie.popularmovies.Constants.TV_SIMILAR_MOVIE_COLUMNS;
import static work.technie.popularmovies.Constants.TV_VIDEO_COLUMNS;


public class DetailTVActivityFragment extends Fragment implements LoaderCallbacks<Cursor>, AsyncResponse {

    private static final String LOG_TAG = DetailTVActivityFragment.class.getSimpleName();
    private static final String MOVIE_SHARE_HASHTAG = " #PopularMovieApp";
    private static final int TV_DETAILS_LOADER = 0;
    private static final int FAVOURITE_DETAILS_LOADER = 1;
    private static final int NETWORK_LOADER = 2;
    private static final int RUNTIME_LOADER = 3;
    private static final String DARK_MUTED_COLOR = "dark_muted_color";
    private final String DETAIL_FRAGMENT_TAG = "DFTAG";
    private final String PROFILE_DETAIL_FRAGMENT_TAG = "PDFTAG";
    private View rootView;
    private String tv_id;
    private Fragment current;
    private String orgTitle;
    private CreatorMovieAdapter creatorListAdapter;
    private SeasonsTVArrayAdapter seasonsTVArrayAdapter;
    private CastMovieAdapter castListAdapter;
    private SimilarMovieArrayAdapter similarTVListAdapter;
    private VideoMovieAdapter videoListAdapter;
    private GenreMovieAdapter genreListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean reLoadPoster;
    private boolean isFlingerCastSet;
    private int dark_muted_color;

    private void updateDetailList() {
        FetchTVDetail fetchTask = new FetchTVDetail(getActivity());
        fetchTask.response = this;
        fetchTask.execute(tv_id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(DARK_MUTED_COLOR)) {
            dark_muted_color = savedInstanceState.getInt(DARK_MUTED_COLOR);
            changeColor(getActivity());
        } else {
            dark_muted_color = 0;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dark_muted_color != 0) {
            changeColor(getActivity());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(DARK_MUTED_COLOR, dark_muted_color);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();

        Bitmap imageBitmap = null;
        String transitionName = "";
        reLoadPoster = false;
        isFlingerCastSet = false;

        if (arguments != null) {
            tv_id = arguments.getString(Intent.EXTRA_TEXT);
            transitionName = arguments.getString("TRANS_NAME");
            imageBitmap = arguments.getParcelable("POSTER_IMAGE");
        }
        rootView = inflater.inflate(R.layout.fragment_tv_detail, container, false);

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
            creatorListAdapter = new CreatorMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.TVCreator.buildTVCreatorUriWithTVId(tv_id),
                    CREATOR_COLUMNS,
                    null,
                    null,
                    null
            ));

            RecyclerView recyclerViewCrew = (RecyclerView) rootView.findViewById(R.id.recyclerview_crew);
            recyclerViewCrew.setAdapter(creatorListAdapter);
            recyclerViewCrew.setLayoutManager(crewLayoutManager);

            if (creatorListAdapter.getItemCount() == 0) {
                recyclerViewCrew.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_crew).setVisibility(View.VISIBLE);
            } else {
                recyclerViewCrew.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_crew).setVisibility(View.GONE);
            }

            LinearLayoutManager castLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            SnapHelper castSnapHelper = new LinearSnapHelper();
            castListAdapter = new CastMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.TVCast.buildCastsUriWithTVId(tv_id),
                    TVCAST_COLUMNS,
                    null,
                    null,
                    " CAST ( " + MovieContract.TVCast.ORDER + " AS REAL ) ASC"
            ), true);
            RecyclerView recyclerViewCast = (RecyclerView) rootView.findViewById(R.id.recyclerview_cast);
            recyclerViewCast.setAdapter(castListAdapter);
            recyclerViewCast.setLayoutManager(castLayoutManager);
            if (!isFlingerCastSet) {
                castSnapHelper.attachToRecyclerView(recyclerViewCast);
                isFlingerCastSet = true;
            }

            if (castListAdapter.getItemCount() == 0) {
                recyclerViewCast.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_cast).setVisibility(View.VISIBLE);
            } else {
                recyclerViewCast.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_cast).setVisibility(View.GONE);
            }

            LinearLayoutManager seasonLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            seasonsTVArrayAdapter = new SeasonsTVArrayAdapter(mActivity.getContentResolver().query(
                    MovieContract.TVSeasons.buildTVSeasonsUriWithTVId(tv_id),
                    TV_SEASONS_COLUMNS,
                    null,
                    null,
                    " CAST ( " + MovieContract.TVSeasons.SEASON_NUMBER + " AS REAL ) ASC"
            ));

            RecyclerView recyclerViewSeason = (RecyclerView) rootView.findViewById(R.id.recyclerview_seasons);
            recyclerViewSeason.setAdapter(seasonsTVArrayAdapter);
            recyclerViewSeason.setLayoutManager(seasonLayoutManager);

            if (seasonsTVArrayAdapter.getItemCount() == 0) {
                recyclerViewSeason.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_seasons).setVisibility(View.VISIBLE);
            } else {
                recyclerViewSeason.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_seasons).setVisibility(View.GONE);
            }

            LinearLayoutManager similarLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            similarTVListAdapter = new SimilarMovieArrayAdapter(mActivity.getContentResolver().query(
                    MovieContract.TVSimilar.buildSimilarTVUriWithTVId(tv_id),
                    TV_SIMILAR_MOVIE_COLUMNS,
                    null,
                    null,
                    null
            ), true);
            RecyclerView recyclerViewSimilarTvs = (RecyclerView) rootView.findViewById(R.id.recyclerview_similar_tv);
            recyclerViewSimilarTvs.setAdapter(similarTVListAdapter);
            recyclerViewSimilarTvs.setLayoutManager(similarLayoutManager);

            if (similarTVListAdapter.getItemCount() == 0) {
                recyclerViewSimilarTvs.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_similar_movies).setVisibility(View.VISIBLE);
            } else {
                recyclerViewSimilarTvs.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_similar_tv).setVisibility(View.GONE);
            }

            LinearLayoutManager videoLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            videoListAdapter = new VideoMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.TVVideos.buildTVUriWithTVId(tv_id),
                    TV_VIDEO_COLUMNS,
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

            GridLayoutManager genreLayoutManager = new GridLayoutManager(mActivity, 2);
            genreListAdapter = new GenreMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.TVGenres.buildGenresUriWithTVId(tv_id),
                    TV_GENRE_COLUMNS,
                    null,
                    null,
                    null
            ));

            RecyclerView recyclerViewGenres = (RecyclerView) rootView.findViewById(R.id.recyclerview_genre);
            recyclerViewGenres.setAdapter(genreListAdapter);
            recyclerViewGenres.setLayoutManager(genreLayoutManager);


            if (genreListAdapter.getItemCount() == 0) {
                recyclerViewGenres.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_genre).setVisibility(View.VISIBLE);
            } else {
                recyclerViewGenres.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_genre).setVisibility(View.GONE);
            }

            similarTVListAdapter.setOnClickListener(new SimilarMovieArrayAdapter.SetOnClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    Cursor cursor = similarTVListAdapter.getCursor();
                    cursor.moveToPosition(position);
                    Activity mActivity = getActivity();

                    DetailTVActivityFragment fragment = new DetailTVActivityFragment();

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
                    arguments.putString(Intent.EXTRA_TEXT, cursor.getString(Constants.TV_SIMILAR_COL_TV_ID));
                    fragment.setArguments(arguments);
                    FragmentManager fragmentManager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frag_container, fragment, DETAIL_FRAGMENT_TAG)
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
                    final String source = cursor.getString(COL_VIDEOS_TV_KEY);
                    final String trailerUrl = "https://www.youtube.com/watch?v=" + source;

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                    mActivity.startActivity(intent);
                }
            });

            String firstVideoLink = "https://www.youtube.com/watch?v=";
            Cursor mCursor = mActivity.getContentResolver().query(
                    MovieContract.TVVideos.buildTVUriWithTVId(tv_id),
                    TV_VIDEO_COLUMNS,
                    null,
                    null,
                    null
            );
            if (!(mCursor == null || !(mCursor.moveToFirst()) || mCursor.getCount() == 0)) {
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

            castListAdapter.setOnClickListener(new CastMovieAdapter.SetOnClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    Cursor cursor = castListAdapter.getCursor();
                    cursor.moveToPosition(position);
                    Activity mActivity = getActivity();

                    PeopleDetailFragment fragment = new PeopleDetailFragment();

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
                    arguments.putString(Intent.EXTRA_TEXT, cursor.getString(Constants.TV_CAST_COL_ID));
                    fragment.setArguments(arguments);
                    FragmentManager fragmentManager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frag_container, fragment, PROFILE_DETAIL_FRAGMENT_TAG)
                            .addToBackStack(null)
                            .commit();

                }
            });

            creatorListAdapter.setOnClickListener(new CreatorMovieAdapter.SetOnClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    Cursor cursor = creatorListAdapter.getCursor();
                    cursor.moveToPosition(position);
                    Activity mActivity = getActivity();

                    PeopleDetailFragment fragment = new PeopleDetailFragment();

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
                    arguments.putString(Intent.EXTRA_TEXT, cursor.getString(Constants.CREATOR_COL_ID));
                    fragment.setArguments(arguments);
                    FragmentManager fragmentManager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frag_container, fragment, PROFILE_DETAIL_FRAGMENT_TAG)
                            .addToBackStack(null)
                            .commit();

                }
            });

            seasonsTVArrayAdapter.setOnClickListener(new SeasonsTVArrayAdapter.SetOnClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    Cursor cursor = seasonsTVArrayAdapter.getCursor();
                    cursor.moveToPosition(position);
                    Activity mActivity = getActivity();

                    SeasonDetailFragment fragment = new SeasonDetailFragment();

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
                    arguments.putString(Intent.EXTRA_TEXT, cursor.getString(Constants.TV_SEASON_COL_TV_ID));
                    arguments.putString(Intent.EXTRA_CC, cursor.getString(Constants.TV_SEASON_COL_SEASON_NUMBER));
                    arguments.putString(Intent.EXTRA_BCC, cursor.getString(Constants.TV_SEASON_COL_ID));
                    fragment.setArguments(arguments);
                    FragmentManager fragmentManager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frag_container, fragment, PROFILE_DETAIL_FRAGMENT_TAG)
                            .addToBackStack(null)
                            .commit();

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
                    getActivity().getContentResolver().delete(MovieContract.TVDetails.buildTVDetailsUriWithTvId(tv_id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.TVCast.buildCastsUriWithTVId(tv_id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.TVCreator.buildTVCreatorUriWithTVId(tv_id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.TVEpisodeRuntime.buildTVEpisodeRuntimeUriWithTVId(tv_id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.TVGenres.buildGenresUriWithTVId(tv_id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.TVNetworks.buildTVNetworksUriWithTVId(tv_id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.TVSeasons.buildTVSeasonsUriWithTVId(tv_id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.TVSimilar.buildSimilarTVUriWithTVId(tv_id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.TVEpisodeRuntime.buildTVEpisodeRuntimeUriWithTVId(tv_id), null, null);
                    getActivity().getContentResolver().delete(MovieContract.TVVideos.buildTVUriWithTVId(tv_id), null, null);
                    updateDetailList();
                } else {
                    Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        int count = 0;

        Uri Uri = MovieContract.TVDetails.buildTVDetailsUri();
        Cursor cursor = getActivity().getContentResolver().query(Uri, Constants.TV_DETAILS_COLUMNS_MIN,
                MovieContract.TVDetails.TV_ID + " = ? ",
                new String[]{tv_id},
                null);
        if (cursor != null)
            count = cursor.getCount();

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

        getLoaderManager().initLoader(TV_DETAILS_LOADER, null, this);
        getLoaderManager().initLoader(FAVOURITE_DETAILS_LOADER, null, this);
        getLoaderManager().initLoader(NETWORK_LOADER, null, this);
        getLoaderManager().initLoader(RUNTIME_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != tv_id) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            switch (id) {
                case TV_DETAILS_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.TVDetails.buildTVDetailsUriWithTvId(tv_id),
                            TV_DETAILS_COLUMNS,
                            null,
                            null,
                            null
                    );
                case FAVOURITE_DETAILS_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.FavouritesTVs.buildMoviesUriWithTvId(tv_id),
                            FAVOURITE_TV_COLUMNS,
                            null,
                            null,
                            null
                    );
                case NETWORK_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.TVNetworks.buildTVNetworksUriWithTVId(tv_id),
                            TV_NETWORKS_COLUMNS,
                            null,
                            null,
                            null
                    );
                case RUNTIME_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.TVEpisodeRuntime.buildTVEpisodeRuntimeUriWithTVId(tv_id),
                            TV_RUNTIME_EPISODES_COLUMNS,
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
        rootView.findViewById(R.id.bookmark).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.overview_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.featured_crew_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.top_billed_cast_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.status_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.genre_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.original_lang_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.runtime_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.homepage_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.videos_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.similar_tv_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.seasons_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.network_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.first_air_date_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.last_air_date_title).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        swipeRefreshLayout.setRefreshing(false);
        switch (loader.getId()) {
            case TV_DETAILS_LOADER:
                if (!data.moveToFirst()) {
                    break;
                }
                defaultShow();

                String votAvg = data.getString(TV_DETAILS_COL_VOTE_AVERAGE);
                double vote = Double.parseDouble(votAvg);
                votAvg = String.valueOf((double) Math.round(vote * 10d) / 10d);
                ((TextView) rootView.findViewById(R.id.vote))
                        .setText(votAvg.equals("10.0") ? "10" : votAvg);
                String backdropURL = data.getString(TV_DETAILS_COL_BACKDROP_PATH);
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

                orgTitle = data.getString(TV_DETAILS_COL_ORIGINAL_NAME).trim();
                ((TextView) rootView.findViewById(R.id.orgTitle))
                        .setText(orgTitle.isEmpty() ? "-" : orgTitle);


                String overview = data.getString(TV_DETAILS_COL_OVERVIEW).trim();
                ((TextView) rootView.findViewById(R.id.overview))
                        .setText(overview.isEmpty() ? "-" : overview);

                String status = data.getString(TV_DETAILS_COL_STATUS).trim();
                ((TextView) rootView.findViewById(R.id.status))
                        .setText(status.isEmpty() ? "-" : status);

                String original_lang = data.getString(TV_DETAILS_COL_ORIGINAL_LANG).toUpperCase().trim();
                ((TextView) rootView.findViewById(R.id.original_lang))
                        .setText(original_lang.isEmpty() ? "-" : original_lang);

                String homepage = data.getString(TV_DETAILS_COL_HOMEPAGE).trim();
                ((TextView) rootView.findViewById(R.id.homepage))
                        .setText(homepage.isEmpty() ? "-" : homepage);

                String firstAirDate = data.getString(TV_DETAILS_COL_FIRST_AIR_DATE).trim();

                if (!firstAirDate.isEmpty()) {
                    DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = null;
                    try {
                        date = inputFormatter.parse(firstAirDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault());

                    ((TextView) rootView.findViewById(R.id.first_air_date))
                            .setText(date != null ? formatter.format(date) : "-");
                } else {
                    ((TextView) rootView.findViewById(R.id.first_air_date))
                            .setText("-");
                }

                String lastAirDate = data.getString(TV_DETAILS_COL_LAST_AIR_DATE).trim();

                if (!lastAirDate.isEmpty()) {
                    DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = null;
                    try {
                        date = inputFormatter.parse(lastAirDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault());

                    ((TextView) rootView.findViewById(R.id.last_air_date))
                            .setText(date != null ? formatter.format(date) : "-");
                } else {
                    ((TextView) rootView.findViewById(R.id.last_air_date))
                            .setText("-");
                }

                final String postURL = data.getString(TV_DETAILS_COL_POSTER_PATH);
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

            case FAVOURITE_DETAILS_LOADER:
                final FloatingActionButton bookmark = (FloatingActionButton) rootView.findViewById(R.id.bookmark);
                if (data == null || !(data.moveToFirst()) || data.getCount() == 0) {
                    bookmark.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_border_black_24dp));
                } else {
                    bookmark.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_black_24dp));
                }
                bookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Activity mActivity = getActivity();
                        if (mActivity != null) {
                            Cursor cursor = mActivity.getContentResolver().query(MovieContract.FavouritesTVs.buildMoviesUriWithTvId(tv_id),
                                    FAVOURITE_TV_COLUMNS,
                                    null,
                                    null,
                                    null);
                            if (cursor == null || !(cursor.moveToFirst()) || cursor.getCount() == 0) {
                                ContentValues sh1 = new ContentValues();
                                sh1.put(MovieContract.TVDetails.FAVOURED, "1");
                                mActivity.getContentResolver().update(
                                        MovieContract.TVDetails.CONTENT_URI.buildUpon().appendPath(tv_id).build(),
                                        sh1, null, new String[]{tv_id});

                                ContentValues sh = new ContentValues();
                                sh.put(MovieContract.FavouritesTVs.TV_ID, tv_id);
                                mActivity.getContentResolver().insert(MovieContract.FavouritesTVs.buildTVUri(), sh);
                                Toast.makeText(getContext(), "Added to bookmarks", Toast.LENGTH_SHORT).show();
                                bookmark.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_black_24dp));
                            } else {
                                ContentValues sh1 = new ContentValues();
                                sh1.put(MovieContract.TVDetails.FAVOURED, "0");
                                mActivity.getContentResolver().update(
                                        MovieContract.TVDetails.CONTENT_URI.buildUpon().appendPath(tv_id).build(),
                                        sh1, null, new String[]{tv_id});

                                getActivity().getContentResolver().delete(MovieContract.TVDetails.buildTVDetailsUriWithTvId(tv_id), null, null);
                                Toast.makeText(getContext(), "Removed from bookmarks", Toast.LENGTH_SHORT).show();
                                bookmark.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_border_black_24dp));
                                cursor.close();
                            }
                        }
                    }
                });
                break;
            case NETWORK_LOADER:
                TextView networkNames = ((TextView) rootView.findViewById(R.id.network));
                networkNames.setText("-");
                if (data.moveToFirst()) {
                    String network = "";
                    do {
                        network += data.getString(TV_NETWORKS_COL_NAME) + ", ";
                    }
                    while (data.moveToNext());
                    network = network.substring(0, network.length() - 2);
                    networkNames.setText(network);
                }
                break;
            case RUNTIME_LOADER:
                TextView runtimeText = ((TextView) rootView.findViewById(R.id.runtime));
                runtimeText.setText("-");
                if (data.moveToFirst()) {
                    String runtimeT = "";
                    do {
                        int runtime = data.getInt(TV_RUNTIME_EPISODE_COL_TIME);
                        if (runtime >= 60) {
                            int min = (int) Math.floor(runtime / 60);
                            int sec = (int) (runtime - min * 60);
                            if (sec != 0) {
                                runtimeT += String.format(Locale.US, "%d" + "hr" + " %d" + "m", min, sec) + ", ";
                            } else {
                                runtimeT += String.format(Locale.US, "%d" + "hr", min) + ", ";
                            }
                        } else {
                            runtimeT += String.format(Locale.US, "%s" + "m", runtime == 0 ? "-" : String.valueOf(runtime)) + ", ";
                        }
                    }
                    while (data.moveToNext());
                    runtimeT = runtimeT.substring(0, runtimeT.length() - 2);
                    runtimeText.setText(runtimeT);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Activity mActivity = getActivity();
            if (mActivity != null) {
                dark_muted_color = palette.getDarkMutedColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
                changeColor(mActivity);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeColor(Activity mActivity) {
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mActivity.getWindow().setStatusBarColor(dark_muted_color);
    }
}
