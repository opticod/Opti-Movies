package work.technie.popularmovies.fragment;
/*
 * Copyright (C) 2017 Anupam Das
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
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.R;
import work.technie.popularmovies.adapter.CastMovieAdapter;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.utils.AsyncResponse;
import work.technie.popularmovies.utils.PaletteTransformation;

import static work.technie.popularmovies.Constants.TV_EPISODE_COLUMNS;
import static work.technie.popularmovies.Constants.TV_EPISODE_COL_AIR_DATE;
import static work.technie.popularmovies.Constants.TV_EPISODE_COL_EPISODE_NUMBER;
import static work.technie.popularmovies.Constants.TV_EPISODE_COL_NAME;
import static work.technie.popularmovies.Constants.TV_EPISODE_COL_OVERVIEW;
import static work.technie.popularmovies.Constants.TV_EPISODE_COL_VOTE_AVERAGE;
import static work.technie.popularmovies.Constants.TV_EPISODE_CREW_COLUMNS;
import static work.technie.popularmovies.Constants.TV_EPISODE_GUEST_COLUMNS;

/**
 * Created by anupam on 19/12/16.
 */

public class EpisodeDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AsyncResponse {
    private static final int EPISODE_DETAILS_LOADER = 0;
    private static final String DARK_MUTED_COLOR = "dark_muted_color";
    private static final String MUTED_COLOR = "muted_color";
    private final String PROFILE_DETAIL_FRAGMENT_TAG = "PDFTAG";

    private View rootView;
    private String season_Id;
    private String episode_Id;
    private CollapsingToolbarLayout collapsingToolbar;
    private int dark_muted_color;
    private int muted_color;
    private Fragment current;
    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(DARK_MUTED_COLOR) && savedInstanceState.containsKey(MUTED_COLOR)) {
            dark_muted_color = savedInstanceState.getInt(DARK_MUTED_COLOR);
            muted_color = savedInstanceState.getInt(MUTED_COLOR);
        } else {
            dark_muted_color = 0;
            muted_color = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();

        String transitionName = "";

        if (arguments != null) {
            season_Id = arguments.getString(Intent.EXTRA_CC);
            episode_Id = arguments.getString(Intent.EXTRA_BCC);
            mTwoPane = arguments.getBoolean(Intent.ACTION_SCREEN_ON);

            transitionName = arguments.getString("TRANS_NAME");
        }
        rootView = inflater.inflate(R.layout.fragment_episodes, container, false);

        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootView.findViewById(R.id.episode_img).setTransitionName(transitionName);
        }

        if (dark_muted_color != 0 && muted_color != 0 && !mTwoPane) {
            changeColor(getActivity());
        }
        current = this;

        loadData();
        return rootView;
    }

    private void populateAdapters() {

        Activity mActivity = getActivity();
        if (mActivity != null) {
            Log.e("a", episode_Id + " ");
            LinearLayoutManager castLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            final CastMovieAdapter regularListAdapter = new CastMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.TVEpisodeCrew.buildEpisodeCrewUriWithEpisodeId(episode_Id),
                    TV_EPISODE_CREW_COLUMNS,
                    null,
                    null,
                    null
            ), false, true);

            RecyclerView recyclerViewCrew = (RecyclerView) rootView.findViewById(R.id.recyclerview_season_regulars);
            recyclerViewCrew.setAdapter(regularListAdapter);
            recyclerViewCrew.setLayoutManager(castLayoutManager);

            if (regularListAdapter.getItemCount() == 0) {
                recyclerViewCrew.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_season_regulars).setVisibility(View.VISIBLE);
            } else {
                recyclerViewCrew.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_season_regulars).setVisibility(View.GONE);
            }


            LinearLayoutManager guestLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            final CastMovieAdapter guestListAdapter = new CastMovieAdapter(mActivity.getContentResolver().query(
                    MovieContract.TVEpisodeGuestStar.buildEpisodeGuestStarUriWithEpisodeId(episode_Id),
                    TV_EPISODE_GUEST_COLUMNS,
                    null,
                    null,
                    null
            ), false, false, true);

            RecyclerView recyclerViewGuest = (RecyclerView) rootView.findViewById(R.id.recyclerview_guest_star);
            recyclerViewGuest.setAdapter(guestListAdapter);
            recyclerViewGuest.setLayoutManager(guestLayoutManager);

            if (guestListAdapter.getItemCount() == 0) {
                recyclerViewGuest.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_guestStar).setVisibility(View.VISIBLE);
            } else {
                recyclerViewGuest.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_guestStar).setVisibility(View.GONE);
            }

            regularListAdapter.setOnClickListener(new CastMovieAdapter.SetOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Cursor cursor = regularListAdapter.getCursor();
                    cursor.moveToPosition(position);
                    Activity mActivity = getActivity();

                    PeopleDetailFragment fragment = new PeopleDetailFragment();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !mTwoPane) {
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
                    arguments.putString(Intent.EXTRA_TEXT, cursor.getString(Constants.TV_EPISODE_CREW_COL_ID));
                    fragment.setArguments(arguments);
                    arguments.putBoolean(Intent.ACTION_SCREEN_ON, mTwoPane);

                    FragmentManager fragmentManager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
                    if (mTwoPane) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.movie_detail_container, fragment, PROFILE_DETAIL_FRAGMENT_TAG)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        fragmentManager.beginTransaction()
                                .add(R.id.frag_container, fragment, PROFILE_DETAIL_FRAGMENT_TAG)
                                .addToBackStack(null)
                                .commit();
                    }

                }
            });

            guestListAdapter.setOnClickListener(new CastMovieAdapter.SetOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Cursor cursor = guestListAdapter.getCursor();
                    cursor.moveToPosition(position);
                    Activity mActivity = getActivity();

                    PeopleDetailFragment fragment = new PeopleDetailFragment();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !mTwoPane) {
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
                    arguments.putString(Intent.EXTRA_TEXT, cursor.getString(Constants.TV_EPISODE_GUEST_COL_ID));
                    fragment.setArguments(arguments);
                    arguments.putBoolean(Intent.ACTION_SCREEN_ON, mTwoPane);

                    FragmentManager fragmentManager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
                    if (mTwoPane) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.movie_detail_container, fragment, PROFILE_DETAIL_FRAGMENT_TAG)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        fragmentManager.beginTransaction()
                                .add(R.id.frag_container, fragment, PROFILE_DETAIL_FRAGMENT_TAG)
                                .addToBackStack(null)
                                .commit();
                    }

                }
            });
        }
    }

    private void loadData() {
        populateAdapters();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(EPISODE_DETAILS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(DARK_MUTED_COLOR, dark_muted_color);
        outState.putInt(MUTED_COLOR, muted_color);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != season_Id) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            switch (id) {
                case EPISODE_DETAILS_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.TVEpisode.buildEpisodeUriWithEpisodeId(episode_Id),
                            TV_EPISODE_COLUMNS,
                            null,
                            null,
                            null
                    );
            }
        }
        return null;
    }

    private void defaultShow() {
        rootView.findViewById(R.id.episode_img).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.overview_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.air_date_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.vote_average_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.crew_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.guest_star_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.tmdb_episode).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }
        switch (loader.getId()) {
            case EPISODE_DETAILS_LOADER:

                defaultShow();
                String name = data.getString(TV_EPISODE_COL_NAME).trim();
                String episode_number = data.getString(TV_EPISODE_COL_EPISODE_NUMBER).trim();
                collapsingToolbar.setTitle(name.isEmpty() || name.equalsIgnoreCase("null") ? "-" : "Episode " + episode_number + ": " + name);

                String overview = data.getString(TV_EPISODE_COL_OVERVIEW).trim();
                ((TextView) rootView.findViewById(R.id.overview))
                        .setText(overview.isEmpty() || overview.equalsIgnoreCase("null") ? "-" : overview);

                String air_date_title = data.getString(TV_EPISODE_COL_AIR_DATE).trim();

                if (!air_date_title.isEmpty() && !air_date_title.equalsIgnoreCase("null")) {
                    DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = null;
                    try {
                        date = inputFormatter.parse(air_date_title);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault());

                    ((TextView) rootView.findViewById(R.id.air_date))
                            .setText(date != null ? formatter.format(date) : "-");
                } else {
                    ((TextView) rootView.findViewById(R.id.air_date))
                            .setText("-");
                }

                String vote_avg = data.getString(TV_EPISODE_COL_VOTE_AVERAGE).trim();
                ((TextView) rootView.findViewById(R.id.vote_average))
                        .setText(vote_avg.isEmpty() || vote_avg.equalsIgnoreCase("null") ? "-" : vote_avg);

                final String PROFILE_BASE_URL = "http://image.tmdb.org/t/p/w185";
                final String profileURL = data.getString(Constants.TV_EPISODE_COL_STILL_PATH).trim();

                if (!profileURL.isEmpty() && !profileURL.equalsIgnoreCase("null")) {
                    final ImageView profile_img = (ImageView) rootView.findViewById(R.id.episode_img);
                    Picasso.with(getActivity())
                            .load(PROFILE_BASE_URL + profileURL)
                            .fit().centerCrop()
                            .transform(PaletteTransformation.instance())
                            .into(profile_img, new Callback.EmptyCallback() {
                                @Override
                                public void onSuccess() {
                                    if (!mTwoPane) {
                                        Bitmap bitmap = ((BitmapDrawable) profile_img.getDrawable()).getBitmap();
                                        Palette palette = PaletteTransformation.getPalette(bitmap);
                                        changeSystemToolbarColor(palette);
                                    }
                                }
                            });

                    profile_img.setAdjustViewBounds(true);
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
        populateAdapters();
    }

    private void changeSystemToolbarColor(Palette palette) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Activity mActivity = getActivity();
            if (mActivity != null) {
                dark_muted_color = palette.getDarkMutedColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
                muted_color = palette.getMutedColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
                changeColor(mActivity);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void changeColor(Activity mActivity) {
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mActivity.getWindow().setStatusBarColor(dark_muted_color);
        collapsingToolbar.setContentScrimColor(muted_color);
    }
}
