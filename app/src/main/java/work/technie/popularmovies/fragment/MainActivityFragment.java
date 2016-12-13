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
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.FetchMovieTask;
import work.technie.popularmovies.R;
import work.technie.popularmovies.activity.BaseActivity;
import work.technie.popularmovies.adapter.TVMovieArrayAdapter;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.model.MovieInfo;
import work.technie.popularmovies.utils.Utility;


/**
 * Created by anupam on 4/12/15.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

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
        FetchMovieTask weatherTask = new FetchMovieTask(getActivity());
        String sortingOrder = Utility.getPreferredSorting(getActivity());
        weatherTask.execute(sortingOrder, MODE);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity mActivity = getActivity();

        Bundle arguments = getArguments();
        if (arguments != null) {
            MODE = arguments.getString(Intent.EXTRA_TEXT);
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

        int count;

        Uri Uri = isMovie ? MovieContract.Movies.buildMovieUri() : MovieContract.TV.buildTVUri();
        count = mActivity.getContentResolver().query(Uri,
                isMovie ? Constants.MOVIE_COLUMNS_MIN : Constants.TV_COLUMNS_MIN,
                MovieContract.Movies.MODE + " = ?",
                new String[]{MODE},
                null).getCount();

        if (count == 0) {
            if (!Utility.hasNetworkConnection(getActivity())) {
                Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_LONG).show();
            } else {
                updateMovieList();
            }
        }

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        listAdapter =
                new TVMovieArrayAdapter(
                        getActivity(), null, 0);

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    ((Callback) getActivity())
                            .onItemSelected(cursor.getString(isMovie ? Constants.MOV_COL_MOVIE_ID : Constants.TV_COL_TV_ID));
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


        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.main_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utility.hasNetworkConnection(getActivity())) {
                    getActivity().getContentResolver().delete(isMovie ? MovieContract.Movies.CONTENT_URI : MovieContract.TV.CONTENT_URI, MovieContract.Movies.MODE + " = ?", new String[]{MODE});
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
        Uri Uri = isMovie ? MovieContract.Movies.buildMovieUri() : MovieContract.TV.buildTVUri();
        return new CursorLoader(getActivity(),
                Uri,
                isMovie ? Constants.MOVIE_COLUMNS : Constants.TV_COLUMNS,
                MovieContract.Movies.MODE + " = ?",
                new String[]{MODE},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        listAdapter.swapCursor(cursor);
        swipeRefreshLayout.setRefreshing(false);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            gridView.smoothScrollToPosition(mPosition);
        }
        try {
            TextView info = (TextView) rootView.findViewById(R.id.empty);
            if (listAdapter.getCount() == 0) {
                String sorting = Utility.getPreferredSorting(getActivity());
                if (sorting.equalsIgnoreCase(getResources().getString(R.string.pref_sort_favourite))) {
                    info.setText("Favourite List is Empty!");
                }
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

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         *
         * @param movieUri
         */
        void onItemSelected(String movieUri);
    }
}
