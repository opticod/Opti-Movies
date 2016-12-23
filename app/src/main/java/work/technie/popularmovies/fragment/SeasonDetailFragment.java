package work.technie.popularmovies.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.R;
import work.technie.popularmovies.adapter.EpisodesTVArrayAdapter;
import work.technie.popularmovies.asyntask.FetchSeasonDetail;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.utils.AsyncResponse;
import work.technie.popularmovies.utils.PaletteTransformation;
import work.technie.popularmovies.utils.Utility;

import static work.technie.popularmovies.Constants.TV_SEASON_DETAILS_COLUMNS;
import static work.technie.popularmovies.Constants.TV_SEASON_DETAILS_COL_AIR_DATE;
import static work.technie.popularmovies.Constants.TV_SEASON_DETAILS_COL_NAME;
import static work.technie.popularmovies.Constants.TV_SEASON_DETAILS_COL_OVERVIEW;

/**
 * Created by anupam on 19/12/16.
 */

public class SeasonDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AsyncResponse {
    private static final int SEASON_DETAILS_LOADER = 0;
    private static final String DARK_MUTED_COLOR = "dark_muted_color";
    private static final String MUTED_COLOR = "muted_color";
    private final String SEASON_DETAIL_FRAGMENT_TAG = "PDFTAG";

    private View rootView;
    private String season_Id;
    private String tv_Id;
    private String season_number;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private int dark_muted_color;
    private int muted_color;
    private Fragment current;


    private void updateDetailList() {
        FetchSeasonDetail fetchTask = new FetchSeasonDetail(getActivity());
        fetchTask.response = this;
        fetchTask.execute(tv_Id, season_number);
    }

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
            tv_Id = arguments.getString(Intent.EXTRA_TEXT);
            season_number = arguments.getString(Intent.EXTRA_CC);
            season_Id = arguments.getString(Intent.EXTRA_BCC);
            transitionName = arguments.getString("TRANS_NAME");
        }
        rootView = inflater.inflate(R.layout.fragment_seasons, container, false);

        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootView.findViewById(R.id.season_img).setTransitionName(transitionName);
        }

        if (dark_muted_color != 0 && muted_color != 0) {
            changeColor(getActivity());
        }
        current = this;

        loadData(rootView);
        return rootView;
    }

    private void populateAdapters() {

        Activity mActivity = getActivity();
        if (mActivity != null) {
            Uri Uri = MovieContract.TVEpisode.buildEpisodeUri();
            LinearLayoutManager crewLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            final EpisodesTVArrayAdapter episodeListAdapter = new EpisodesTVArrayAdapter(mActivity.getContentResolver().query(
                    Uri,
                    Constants.TV_EPISODE_COLUMNS,
                    MovieContract.TVEpisode.SEASON_ID + " = ?",
                    new String[]{season_Id},
                    " CAST ( " + MovieContract.TVEpisode.EPISODE_NUMBER + " AS REAL ) ASC"
            ));

            RecyclerView recyclerViewCrew = (RecyclerView) rootView.findViewById(R.id.recyclerview_episodes);
            recyclerViewCrew.setAdapter(episodeListAdapter);
            recyclerViewCrew.setLayoutManager(crewLayoutManager);

            if (episodeListAdapter.getItemCount() == 0) {
                recyclerViewCrew.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_episode).setVisibility(View.VISIBLE);
            } else {
                recyclerViewCrew.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_episode).setVisibility(View.GONE);
            }

            episodeListAdapter.setOnClickListener(new EpisodesTVArrayAdapter.SetOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Cursor cursor = episodeListAdapter.getCursor();
                    cursor.moveToPosition(position);
                    Activity mActivity = getActivity();

                    EpisodeDetailFragment fragment = new EpisodeDetailFragment();

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
                    arguments.putString(Intent.EXTRA_CC, season_Id);
                    arguments.putString(Intent.EXTRA_BCC, cursor.getString(Constants.TV_EPISODE_COL_ID));
                    fragment.setArguments(arguments);
                    FragmentManager fragmentManager = ((AppCompatActivity) mActivity).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.frag_container, fragment, SEASON_DETAIL_FRAGMENT_TAG)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    private void loadData(final View rootView) {

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.detail_season_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utility.hasNetworkConnection(getActivity())) {
                    getActivity().getContentResolver().delete(MovieContract.TVSeasonDetails.buildSeasonDetailsUriWithSeasonId(season_Id), null, null);
                    updateDetailList();
                } else {
                    Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        int count = 0;

        Uri Uri = MovieContract.TVSeasonDetails.buildSeasonDetailsUri();
        Cursor cursor = getActivity().getContentResolver().query(Uri, Constants.TV_SEASON_DETAILS_COLUMNS,
                MovieContract.TVSeasonDetails.SEASON_ID + " = ? ",
                new String[]{season_Id},
                null);
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

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
        getLoaderManager().initLoader(SEASON_DETAILS_LOADER, null, this);
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
                case SEASON_DETAILS_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.TVSeasonDetails.buildSeasonDetailsUriWithSeasonId(season_Id),
                            TV_SEASON_DETAILS_COLUMNS,
                            null,
                            null,
                            null
                    );
            }
        }
        return null;
    }

    private void defaultShow() {
        rootView.findViewById(R.id.season_img).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.overview_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.air_date_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.episodes_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.tmdb_season).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        swipeRefreshLayout.setRefreshing(false);
        if (!data.moveToFirst()) {
            return;
        }
        switch (loader.getId()) {
            case SEASON_DETAILS_LOADER:

                defaultShow();
                String name = data.getString(TV_SEASON_DETAILS_COL_NAME).trim();
                collapsingToolbar.setTitle(name.isEmpty() || name.equalsIgnoreCase("null") ? "-" : name);

                String overview = data.getString(TV_SEASON_DETAILS_COL_OVERVIEW).trim();
                ((TextView) rootView.findViewById(R.id.overview))
                        .setText(overview.isEmpty() || overview.equalsIgnoreCase("null") ? "-" : overview);

                String air_date_title = data.getString(TV_SEASON_DETAILS_COL_AIR_DATE).trim();

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

                final String PROFILE_BASE_URL = "http://image.tmdb.org/t/p/w185";
                final String profileURL = data.getString(Constants.TV_SEASON_DETAILS_COL_POSTER_PATH).trim();

                if (!profileURL.isEmpty() && !profileURL.equalsIgnoreCase("null")) {
                    final ImageView profile_img = (ImageView) rootView.findViewById(R.id.season_img);
                    Picasso.with(getActivity())
                            .load(PROFILE_BASE_URL + profileURL)
                            .fit().centerCrop()
                            .transform(PaletteTransformation.instance())
                            .into(profile_img, new Callback.EmptyCallback() {
                                @Override
                                public void onSuccess() {
                                    Bitmap bitmap = ((BitmapDrawable) profile_img.getDrawable()).getBitmap();
                                    Palette palette = PaletteTransformation.getPalette(bitmap);
                                    changeSystemToolbarColor(palette);
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
        swipeRefreshLayout.setRefreshing(false);
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
