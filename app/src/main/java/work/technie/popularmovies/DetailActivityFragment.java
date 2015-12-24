package work.technie.popularmovies;


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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import work.technie.popularmovies.data.MovieContract;


public class DetailActivityFragment extends Fragment implements LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private View rootView;
    private static final String MOVIE_SHARE_HASHTAG = " #PopularMovieApp #ByAnupam ";
    static final String DETAIL_URI = "URI";
    private Uri mUri;

    private static final int DETAIL_LOADER = 0;
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
    private void updateMovieList(String MovieId) {
        FetchTrailReview weatherTask = new FetchTrailReview(getActivity());
            weatherTask.execute(MovieId);
    }
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


    String orgLang;
    String orgTitle;
    String overview;
    String relDate;
    String postURL;
    String popularity;
    String votAvg;
    String favourite;
    String movieId;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DetailActivityFragment.DETAIL_URI);
        }
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }
    void onSortingChanged() {
        Uri uri = mUri;
        if (null != uri) {
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(null!=mUri) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        Log.v(LOG_TAG, "In onLoadFinished");
        if (!data.moveToFirst()) { return; }

            orgLang = data.getString(COL_ORIGINAL_LANG);
            ((TextView) rootView.findViewById(R.id.orgLang))
                    .setText("Original Language :   " + orgLang);

            orgTitle = data.getString(COL_ORIGINAL_TITLE);
            ((TextView) rootView.findViewById(R.id.orgTitle))
                    .setText("Original Title :  " + orgTitle);

            overview = data.getString(COL_OVERVIEW);
            ((TextView) rootView.findViewById(R.id.overview))
                    .setText("Plot synopsis :   " + overview);

            relDate = data.getString(COL_RELEASE_DATE);
            ((TextView) rootView.findViewById(R.id.relDate))
                    .setText("Release Date :    " + relDate);

            postURL = data.getString(COL_POSTER_PATH);
            ImageView poster = (ImageView) rootView.findViewById(R.id.poster);
            Picasso
                    .with(getActivity())
                    .load(postURL)
                    .fit()
                    .into(poster);

            movieId=data.getString(COL_MOVIE_ID);
            updateMovieList(movieId);

            popularity = data.getString(COL_POPULARITY);
            ((TextView) rootView.findViewById(R.id.popularity))
                    .setText("Popularity :  " + popularity);

            votAvg = data.getString(COL_VOTE_AVERAGE);
            ((TextView) rootView.findViewById(R.id.voteAvg))
                    .setText("User Rating :     " + votAvg);
            FloatingActionButton share = (FloatingActionButton) rootView.findViewById(R.id.share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT,
                            "Title: " + orgTitle + ". Release Dt.:  " + relDate + ". Vote Avg.: " + votAvg + " " + MOVIE_SHARE_HASHTAG);
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, getString(R.string.share_links)));

                }
            });
            favourite=data.getString(COL_FAVOURED);
        FloatingActionButton fab ;
        if(favourite.equalsIgnoreCase("1")){
            rootView.findViewById(R.id.bookmark).setVisibility(View.VISIBLE);
            fab = (FloatingActionButton) rootView.findViewById(R.id.bookmark);
        }else {
            rootView.findViewById(R.id.border_bookmark).setVisibility(View.VISIBLE);
            fab = (FloatingActionButton) rootView.findViewById(R.id.border_bookmark);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieId=data.getString(COL_MOVIE_ID);
                ContentValues fav = new ContentValues();
                if(favourite.equalsIgnoreCase("1")) {
                    fav.put(MovieContract.Movies.FAVOURED, "0");
                    Toast.makeText(getContext(), "REMOVED FROM FAVOURITES!", Toast.LENGTH_SHORT).show();
                    rootView.findViewById(R.id.bookmark).setVisibility(View.GONE);
                    rootView.findViewById(R.id.border_bookmark).setVisibility(View.VISIBLE);

                }else{
                    fav.put(MovieContract.Movies.FAVOURED, "1");
                    Toast.makeText(getContext(), "ADDED TO FAVOURITES!", Toast.LENGTH_SHORT).show();
                    rootView.findViewById(R.id.bookmark).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.border_bookmark).setVisibility(View.GONE);
                }
                getContext().getContentResolver().update(
                        MovieContract.Movies.CONTENT_URI.buildUpon().appendPath(movieId).build(),
                        fav, null, new String[]{movieId});

            }
        });

    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
