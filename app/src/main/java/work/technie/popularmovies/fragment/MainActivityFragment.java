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

import work.technie.popularmovies.FetchMovieTask;
import work.technie.popularmovies.R;
import work.technie.popularmovies.adapter.MovieArrayAdapter;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.model.MovieInfo;
import work.technie.popularmovies.utils.Utility;

/**
 * Created by anupam on 4/12/15.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String SELECTED_KEY = "selected_position";
    private static final int MOVIE_LOADER = 0;
    private static final String[] MOVIE_COLUMNS = {

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
    public static int COL_ID = 0;
    public static int COL_PAGE = 1;
    public static int COL_POSTER_PATH = 2;
    public static int COL_ADULT = 3;
    public static int COL_OVERVIEW = 4;
    //private boolean isLoading=false;
    //private TextView loading;
    //private String lastSortingOrder="initial";
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
    private static boolean firstTime = true;
    private MovieArrayAdapter movieListAdapter;
    private int mPosition = ListView.INVALID_POSITION;
    private GridView gridView;
    private ArrayList<MovieInfo> movieList;
    private SwipeRefreshLayout swipeRefreshLayout;
    //private static final int MAX_PAGE=100;
    private int PAGE_LOADED = 0;
    private View rootView;

    private void updateMovieList() {
        FetchMovieTask weatherTask = new FetchMovieTask(getActivity());
        String sortingOrder = Utility.getPreferredSorting(getActivity());
        if (!sortingOrder.equalsIgnoreCase(getResources().getString(R.string.pref_sort_favourite))) {
            {/*
            if(!sortingOrder.equals(lastSortingOrder)){
                PAGE_LOADED=0;
                lastSortingOrder=sortingOrder;
            }*/
                weatherTask.execute(sortingOrder, String.valueOf(PAGE_LOADED + 1));
            }
        } else if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
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
        if (firstTime == true) {
            if (!Utility.hasNetworkConnection(getActivity())) {
                Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_LONG).show();
            }
            updateMovieList();
            ContentValues movieValues = new ContentValues();
            movieValues.put(MovieContract.Favourites.PAGE, "0");
            movieValues.put(MovieContract.Favourites.POSTER_PATH, "0");
            movieValues.put(MovieContract.Favourites.ADULT, "0");
            movieValues.put(MovieContract.Favourites.OVERVIEW, "0");
            movieValues.put(MovieContract.Favourites.RELEASE_DATE, "0");
            movieValues.put(MovieContract.Favourites.MOVIE_ID, "0");
            movieValues.put(MovieContract.Favourites.ORIGINAL_TITLE, "0");
            movieValues.put(MovieContract.Favourites.ORIGINAL_LANGUAGE, "0");
            movieValues.put(MovieContract.Favourites.TITLE, "0");
            movieValues.put(MovieContract.Favourites.BACKDROP_PATH, "0");
            movieValues.put(MovieContract.Favourites.POPULARITY, "0");
            movieValues.put(MovieContract.Favourites.VOTE_COUNT, "0");
            movieValues.put(MovieContract.Favourites.VOTE_AVERAGE, "0");
            movieValues.put(MovieContract.Favourites.SORT_BY, "0");
            getActivity().getContentResolver().insert(MovieContract.Favourites.buildMovieUri(), movieValues);
            firstTime = !firstTime;
        }

        if (savedInstanceState == null || !savedInstanceState.containsKey("movieList")) {
            movieList = new ArrayList<>();
        } else {
            movieList = savedInstanceState.getParcelableArrayList("movieList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        movieListAdapter =
                new MovieArrayAdapter(
                        getActivity(), null, 0);

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the GridView, and attach this adapter to it.
        gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setAdapter(movieListAdapter);

        final Activity mActivity = getActivity();

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
                            .onItemSelected(cursor.getString(COL_MOVIE_ID));
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
                    getActivity().getContentResolver().delete(MovieContract.Movies.CONTENT_URI, null, null);
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

    // since we read the new soring order when we create the loader, all we need to do is restart things
    public void onSortingChanged() {
        String sorting = Utility.getPreferredSorting(getActivity());
        updateMovieList();
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String sortOrder = MovieContract.Movies._ID + " ASC";
        Uri movie = MovieContract.Movies.buildMovieUri();
        Uri fav = MovieContract.Favourites.buildMovieUri();
        String sorting = Utility.getPreferredSorting(getActivity());
        if (sorting.equalsIgnoreCase(getResources().getString(R.string.pref_sort_favourite))) {
            return new CursorLoader(getActivity(),
                    fav,
                    FAVOURITE_MOVIE_COLUMNS,
                    MovieContract.Favourites.FAVOURED + " = ?",
                    new String[]{"1"},
                    sortOrder);
        }
        return new CursorLoader(getActivity(),
                movie,
                MOVIE_COLUMNS,
                MovieContract.Movies.SORT_BY + " = ?",
                new String[]{sorting},
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        movieListAdapter.swapCursor(cursor);
        swipeRefreshLayout.setRefreshing(false);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            gridView.smoothScrollToPosition(mPosition);
        }
        try {
            TextView info = (TextView) rootView.findViewById(R.id.empty);
            if (movieListAdapter.getCount() == 0) {
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
        movieListAdapter.swapCursor(null);
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
