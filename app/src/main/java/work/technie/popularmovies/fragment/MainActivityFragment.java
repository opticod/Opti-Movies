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
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.FetchTVMovieTask;
import work.technie.popularmovies.R;
import work.technie.popularmovies.activity.BaseActivity;
import work.technie.popularmovies.adapter.TVMovieArrayAdapter;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.model.MovieInfo;
import work.technie.popularmovies.utils.AsyncResponse;
import work.technie.popularmovies.utils.Utility;


/**
 * Created by anupam on 4/12/15.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AsyncResponse {

    private static final String SELECTED_KEY = "selected_position";
    private static final int MOVIE_LOADER = 0;
    private TVMovieArrayAdapter listAdapter;
    private int mPosition = ListView.INVALID_POSITION;
    private GridView gridView;
    private ArrayList<MovieInfo> movieList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View rootView;
    private String MODE;
    private boolean isMovie;


    private void updateMovieList() {
        FetchTVMovieTask weatherTask = new FetchTVMovieTask(getActivity());
        weatherTask.response = this;
        weatherTask.execute(MODE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movieList", movieList);
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to GridView.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movieList")) {
            movieList = new ArrayList<>();
        } else {
            movieList = savedInstanceState.getParcelableArrayList("movieList");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity mActivity = getActivity();
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.main_swipe_refresh);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        listAdapter =
                new TVMovieArrayAdapter(
                        getActivity(), null, 0);

        Bundle arguments = getArguments();
        if (arguments != null) {
            MODE = arguments.getString(Intent.EXTRA_TEXT);
            toolbar.setTitle(MODE);
        }

        if (MODE != null) {
            switch (MODE) {
                case BaseActivity.FRAGMENT_TAG_MOV_NOW_PLAYING:
                case BaseActivity.FRAGMENT_TAG_MOV_POPULAR:
                case BaseActivity.FRAGMENT_TAG_MOV_TOP_RATED:
                case BaseActivity.FRAGMENT_TAG_MOV_UPCOMING:
                    isMovie = true;
                    break;
                default:
                    isMovie = false;
                    break;
            }
        } else {
            isMovie = true;
        }

        final boolean PREF_CHILD;
        final String PREF_LANGUAGE;
        final String PREF_REGION;

        String KEY_ADULT = "child_mode";
        String KEY_LANGUAGE = "language";
        String KEY_REGION = "region";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        PREF_CHILD = sharedPreferences.getBoolean(KEY_ADULT, true);
        PREF_LANGUAGE = sharedPreferences.getString(KEY_LANGUAGE, "");
        PREF_REGION = sharedPreferences.getString(KEY_REGION, "");
        int count;

        Uri Uri = isMovie ? MovieContract.Movies.buildMovieUri() : MovieContract.TV.buildTVUri();
        count = mActivity.getContentResolver().query(Uri,
                isMovie ? Constants.MOVIE_COLUMNS_MIN : Constants.TV_COLUMNS_MIN,
                MovieContract.Movies.MODE + " = ? AND " + MovieContract.Movies.PREF_ADULT + " = ? AND " + MovieContract.Movies.PREF_LANGUAGE + " = ? AND " + MovieContract.Movies.PREF_REGION + " = ? ",
                new String[]{MODE, String.valueOf(!PREF_CHILD), PREF_LANGUAGE, PREF_REGION},
                null).getCount();

        if (count == 0) {
            if (!Utility.hasNetworkConnection(getActivity())) {
                Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_LONG).show();
            } else {
                updateMovieList();
            }
        }


        // Get a reference to the GridView, and attach this adapter to it.
        gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setAdapter(listAdapter);

        if (mActivity != null) {
            gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    final int size = gridView.getWidth();
                    int numCol = (int) Math.round((double) size / (double) mActivity.getResources().getDimensionPixelSize(R.dimen.poster_width));
                    gridView.setNumColumns(numCol);
                }
            });
        }

        final Fragment current = this;

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.grid_item_poster);
                    ImageView staticImage = (ImageView) getView().findViewById(R.id.grid_item_poster);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imageView.setTransitionName("TRANS_NAME" + position);
                    }

                    ((Callback) getActivity())
                            .onItemSelected(cursor.getString(isMovie ? Constants.MOV_COL_MOVIE_ID : Constants.TV_COL_TV_ID), imageView, staticImage, current);
                }
                mPosition = position;
            }
        });
        // If there's instance state, mine it for useful information.
        // The end-goal here is that the user never knows that turning their device sideways
        // does crazy lifecycle related things.  It should feel like some stuff stretched out,
        // or magically appeared to take advantage of room, but data or place in the app was never
        // actually *lost*.
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utility.hasNetworkConnection(getActivity())) {
                    getActivity().getContentResolver().delete(isMovie ? MovieContract.Movies.CONTENT_URI : MovieContract.TV.CONTENT_URI,
                            MovieContract.Movies.MODE + " = ? AND " + MovieContract.Movies.PREF_ADULT + " = ? AND " + MovieContract.Movies.PREF_LANGUAGE + " = ? AND " + MovieContract.Movies.PREF_REGION + " = ? ",
                            new String[]{MODE, String.valueOf(!PREF_CHILD), PREF_LANGUAGE, PREF_REGION});
                    updateMovieList();
                } else {
                    Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        final boolean PREF_CHILD;
        final String PREF_LANGUAGE;
        final String PREF_REGION;

        String KEY_ADULT = "child_mode";
        String KEY_LANGUAGE = "language";
        String KEY_REGION = "region";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        PREF_CHILD = sharedPreferences.getBoolean(KEY_ADULT, true);
        PREF_LANGUAGE = sharedPreferences.getString(KEY_LANGUAGE, "");
        PREF_REGION = sharedPreferences.getString(KEY_REGION, "");

        Uri Uri = isMovie ? MovieContract.Movies.buildMovieUri() : MovieContract.TV.buildTVUri();
        return new CursorLoader(getActivity(),
                Uri,
                isMovie ? Constants.MOVIE_COLUMNS : Constants.TV_COLUMNS,
                MovieContract.Movies.MODE + " = ? AND " + MovieContract.Movies.PREF_ADULT + " = ? AND " + MovieContract.Movies.PREF_LANGUAGE + " = ? AND " + MovieContract.Movies.PREF_REGION + " = ? ",
                new String[]{MODE, String.valueOf(!PREF_CHILD), PREF_LANGUAGE, PREF_REGION},
                " CAST ( " + MovieContract.Movies.POPULARITY + " AS REAL ) DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        listAdapter.swapCursor(cursor);
        swipeRefreshLayout.setRefreshing(false);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            gridView.post(new Runnable() {
                @Override
                public void run() {
                    gridView.setSelection(mPosition);
                    View v = gridView.getChildAt(mPosition);
                    if (v != null) {
                        v.requestFocus();
                    }
                }
            });
        }
        try {
            TextView info = (TextView) rootView.findViewById(R.id.empty);
            if (listAdapter.getCount() == 0) {
                info.setText(R.string.loading);
                info.setVisibility(View.VISIBLE);
            } else {
                info.setVisibility(View.GONE);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        listAdapter.swapCursor(null);
    }

    @Override
    public void onFinish(boolean isData) {
        if (!isData) {
            swipeRefreshLayout.setRefreshing(false);
            TextView info = (TextView) rootView.findViewById(R.id.empty);
            if (listAdapter.getCount() == 0) {
                info.setText(R.string.no_data);
                info.setVisibility(View.VISIBLE);
            } else {
                info.setVisibility(View.GONE);
            }
        }
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         *  @param movieUri
         * @param view
         * @param staticImage
         * @param current
         */
        void onItemSelected(String movieUri, ImageView view, ImageView staticImage, Fragment current);
    }
}
