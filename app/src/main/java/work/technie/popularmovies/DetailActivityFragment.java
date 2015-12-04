package work.technie.popularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivityFragment extends Fragment {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    private static final String MOVIE_SHARE_HASHTAG = " #PopularMovieApp #ByAnupam ";


    String orgLang;
    String orgTitle;
    String overview;
    String relDate;
    String postURL;
    String popularity;
    String votAvg;
    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movieId")) {


            orgLang=intent.getStringExtra("orgLang");
            ((TextView) rootView.findViewById(R.id.orgLang))
                    .setText("Original Language :   "+orgLang);

            orgTitle=intent.getStringExtra("orgTitle");
            ((TextView) rootView.findViewById(R.id.orgTitle))
                    .setText("Original Title :  "+orgTitle);

            overview=intent.getStringExtra("overview");
            ((TextView) rootView.findViewById(R.id.overview))
                    .setText("Plot synopsis :   "+overview);

            relDate=intent.getStringExtra("relDate");
            ((TextView) rootView.findViewById(R.id.relDate))
                    .setText("Release Date :    "+relDate);

            postURL=intent.getStringExtra("postUrl");
            ImageView poster = (ImageView) rootView.findViewById(R.id.poster);
            Picasso
                    .with(getActivity())
                    .load(postURL)
                    .fit()
                    .into(poster);

            popularity=intent.getStringExtra("popularity");
            ((TextView) rootView.findViewById(R.id.popularity))
                    .setText("Popularity :  "+popularity);

            votAvg=intent.getStringExtra("voteAvg");
            ((TextView) rootView.findViewById(R.id.voteAvg))
                    .setText("User Rating :     "+votAvg);

        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_detail, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Attach an intent to this ShareActionProvider.  You can update this at any time,
        // like when the user selects a new piece of data they might like to share.
        if (mShareActionProvider != null ) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "Title: "+orgTitle+". Release Dt.:  "+relDate+". Vote Avg.: "+votAvg+" " + MOVIE_SHARE_HASHTAG);
        return shareIntent;
    }
}
