package work.technie.popularmovies;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.utils.Utility;

/**
 * Created by anupam on 4/12/15.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private MovieArrayAdapter movieListAdapter;
    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";
    private GridView gridView;
    private ArrayList<MovieInfo> movieList;
    //private static final int MAX_PAGE=100;
    private int PAGE_LOADED=0;
    //private boolean isLoading=false;
    //private TextView loading;
    //private String lastSortingOrder="initial";

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

    public static int COL_ID=0;
    public static int COL_PAGE=1;
    public static int COL_POSTER_PATH=2;
    public static int COL_ADULT=3;
    public static int COL_OVERVIEW=4;
    public static int COL_RELEASE_DATE=5;
    public static int COL_MOVIE_ID=6;
    public static int COL_ORIGINAL_TITLE=7;
    public static int COL_ORIGINAL_LANG=8;
    public static int COL_TITLE=9;
    public static int COL_BACKDROP_PATH=10;
    public static int COL_POPULARITY=11;
    public static int COL_VOTE_COUNT=12;
    public static int COL_VOTE_AVERAGE=13;
    public static int COL_FAVOURED=14;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri movieUri);
    }

    public MainActivityFragment(){

    }
    private void updateMovieList() {
        FetchMovieTask weatherTask = new FetchMovieTask(getActivity());
        String sortingOrder = Utility.getPreferredSorting(getActivity());
        if(!sortingOrder.equals(getResources().getString(R.string.pref_sort_favourite))){/*
            if(!sortingOrder.equals(lastSortingOrder)){
                PAGE_LOADED=0;
                lastSortingOrder=sortingOrder;
            }*/
            weatherTask.execute(sortingOrder, String.valueOf(PAGE_LOADED + 1));
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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null||!savedInstanceState.containsKey("movieList")){
            movieList=new ArrayList<>();
        }else {
            movieList=savedInstanceState.getParcelableArrayList("movieList");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        movieListAdapter =
                new MovieArrayAdapter(
                        getActivity(), null,0);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the GridView, and attach this adapter to it.
        gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setAdapter(movieListAdapter);
        //loading=(TextView)rootView.findViewById(R.id.loading);

        //startLoad();

        // Log.d("MainActivityFrag", "Value: " + gridView.getWidth());

        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                final int size = gridView.getWidth();
                int numCol = (int) Math.round((double) size / (double) getResources().getDimensionPixelSize(R.dimen.poster_width));
                gridView.setNumColumns(numCol);
                //Log.d("MainActivityFrag", "Value: " +size+" "+numCol+" "+getResources().getDimensionPixelSize(R.dimen.poster_width)+" "+test);
            }
        });

        //gridView.setNumColumns(5);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    ((Callback) getActivity())
                            .onItemSelected(MovieContract.Movies.buildMoviesUriWithMovieId(cursor.getString(COL_MOVIE_ID)));
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


       /*gridView.setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        int lastInScreen = firstVisibleItem + visibleItemCount;
                        if (lastInScreen == totalItemCount) {
                            //Log.e("time to scroll","scroll");
                            startLoad();
                        }
                    }
                }

        );
*/

        return rootView;
    }
/*
    private void startLoad(){
        if(isLoading||PAGE_LOADED>=MAX_PAGE){
            return;
        }
        isLoading=true;
        if (loading!=null){
            loading.setVisibility(View.VISIBLE);
        }

        updateMovieList();
    }
    private void stopLoad(){
        if(!isLoading){
            return;
        }
        isLoading=false;
        if (loading!=null){
            loading.setVisibility(View.GONE);
        }
    }*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    // since we read the new soring order when we create the loader, all we need to do is restart things
    void onSortingChanged( ) {
        updateMovieList();
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String sortOrder = MovieContract.Movies._ID + " ASC";
        Uri movie =MovieContract.Movies.buildMovieUri();
        String sorting=Utility.getPreferredSorting(getActivity());

        return new CursorLoader(getActivity(),
                movie,
                MOVIE_COLUMNS,
                MovieContract.Movies.SORT_BY+" = ?",
                new String[] {sorting},
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
       movieListAdapter.swapCursor(cursor);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            gridView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        movieListAdapter.swapCursor(null);
    }
}
