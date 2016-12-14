package work.technie.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import work.technie.popularmovies.R;
import work.technie.popularmovies.fragment.AboutFragment;
import work.technie.popularmovies.fragment.DetailActivityFragment;
import work.technie.popularmovies.fragment.MainActivityFragment;
import work.technie.popularmovies.fragment.SettingsFragment;

/**
 * Created by anupam on 9/12/16.
 */

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityFragment.Callback {

    public static final String FRAGMENT_TAG_MOV_NOW_PLAYING = "Now Playing Movies";
    public static final String FRAGMENT_TAG_MOV_POPULAR = "Popular Movies";
    public static final String FRAGMENT_TAG_MOV_TOP_RATED = "Top Rated Movies";
    public static final String FRAGMENT_TAG_MOV_UPCOMING = "Upcoming Movies";
    public static final String FRAGMENT_TAG_TV_AIRING_TODAY = "TV Airing Today";
    public static final String FRAGMENT_TAG_TV_ON_THE_AIR = "TV On The Air";
    public static final String FRAGMENT_TAG_TV_POPULAR = "Popular TV Shows";
    public static final String FRAGMENT_TAG_TV_TOP_RATED = "Top Rated TV Shows";
    public static final String FRAGMENT_ABOUT = "About";
    public static final String FRAGMENT_SETTINGS = "Settings";

    private final static String STATE_FRAGMENT = "stateFragment";
    private final String DETAIL_FRAGMENT_TAG = "DFTAG";
    private final String LAST_FRAGMENT = "last_fragment";

    private String CURRENT_FRAGMENT_TAG;
    private boolean mTwoPane;
    private int currentMenuItemId;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final View.OnClickListener originalToolbarListener = toggle.getToolbarNavigationClickListener();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    toggle.setDrawerIndicatorEnabled(false);
                    toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.popBackStack();
                            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                            String fragment = sharedPref.getString(CURRENT_FRAGMENT_TAG, FRAGMENT_TAG_MOV_NOW_PLAYING);
                            toolbar.setTitle(fragment);
                        }
                    });
                } else {
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setToolbarNavigationClickListener(originalToolbarListener);
                }
            }
        });

        if (findViewById(R.id.detail_swipe_refresh) != null) {
            toggle.setDrawerIndicatorEnabled(false);
        } else {
            toggle.setDrawerIndicatorEnabled(true);
            toggle.setToolbarNavigationClickListener(originalToolbarListener);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            currentMenuItemId = R.id.mov_now_playing;
            navigationView.setCheckedItem(R.id.mov_now_playing);
        } else {
            currentMenuItemId = savedInstanceState.getInt(STATE_FRAGMENT);
        }

        if (getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG) == null) {
            sharedPref = getPreferences(Context.MODE_PRIVATE);
            String fragment = sharedPref.getString(LAST_FRAGMENT, FRAGMENT_TAG_MOV_NOW_PLAYING);
            toolbar.setTitle(fragment);
        }

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailActivityFragment(), DETAIL_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_MOV_NOW_PLAYING) == null && getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG) == null) {
            doMenuAction(currentMenuItemId);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG) == null) {
            sharedPref = getPreferences(Context.MODE_PRIVATE);
            String fragment = sharedPref.getString(LAST_FRAGMENT, FRAGMENT_TAG_MOV_NOW_PLAYING);
            toolbar.setTitle(fragment);
        }
    }

    private void doMenuAction(int menuItemId) {
        String currentFragment;

        switch (menuItemId) {

            case R.id.mov_now_playing:
                currentFragment = FRAGMENT_TAG_MOV_NOW_PLAYING;
                break;
            case R.id.mov_popular:
                currentFragment = FRAGMENT_TAG_MOV_POPULAR;
                break;
            case R.id.mov_top_rated:
                currentFragment = FRAGMENT_TAG_MOV_TOP_RATED;
                break;
            case R.id.mov_upcoming:
                currentFragment = FRAGMENT_TAG_MOV_UPCOMING;
                break;
            case R.id.tv_airing_today:
                currentFragment = FRAGMENT_TAG_TV_AIRING_TODAY;
                break;
            case R.id.tv_on_the_air:
                currentFragment = FRAGMENT_TAG_TV_ON_THE_AIR;
                break;
            case R.id.tv_popular:
                currentFragment = FRAGMENT_TAG_TV_POPULAR;
                break;
            case R.id.tv_top_rated:
                currentFragment = FRAGMENT_TAG_TV_TOP_RATED;
                break;
            case R.id.about:
                currentFragment = FRAGMENT_ABOUT;
                break;
            case R.id.settings:
                currentFragment = FRAGMENT_SETTINGS;
                break;

            default:
                currentFragment = FRAGMENT_TAG_MOV_NOW_PLAYING;
                break;
                //nothing;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LAST_FRAGMENT, currentFragment);
        editor.apply();

        if (currentFragment.equals(FRAGMENT_ABOUT)) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.frag_container, new AboutFragment(), currentFragment).commit();

        } else if (currentFragment.equals(FRAGMENT_SETTINGS)) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.frag_container, new SettingsFragment(), currentFragment).commit();

        } else {
            Bundle arguments = new Bundle();
            MainActivityFragment fragment = new MainActivityFragment();
            fragment.setArguments(arguments);
            arguments.putString(Intent.EXTRA_TEXT, currentFragment);
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.frag_container, fragment, currentFragment).commit();
        }
        getSupportActionBar().setTitle(currentFragment);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        doMenuAction(id);
        currentMenuItemId = id;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_FRAGMENT, currentMenuItemId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemSelected(String movieId) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putString(Intent.EXTRA_TEXT, movieId);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAIL_FRAGMENT_TAG)
                    .commit();
        } else {

            Bundle arguments = new Bundle();
            arguments.putString(Intent.EXTRA_TEXT, movieId);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(CURRENT_FRAGMENT_TAG)
                    .add(R.id.frag_container, fragment, DETAIL_FRAGMENT_TAG)
                    .commit();
        }
    }

}
