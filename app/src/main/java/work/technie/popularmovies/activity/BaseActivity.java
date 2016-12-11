package work.technie.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import work.technie.popularmovies.R;
import work.technie.popularmovies.fragment.DetailActivityFragment;
import work.technie.popularmovies.fragment.MainActivityFragment;

/**
 * Created by anupam on 9/12/16.
 */

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityFragment.Callback {

    private final static String STATE_FRAGMENT = "stateFragment";
    private static final String TAG = "BaseActivity";
    private final String FRAGMENT_TAG_MOV_LATEST = "Latest Movies";
    private final String DETAIL_FRAGMENT_TAG = "DFTAG";
    private final String FRAGMENT_TAG_REST = "FTAGR";
    private String CURRENT_FRAGMENT_TAG;
    private boolean mTwoPane;
    private String mSorting;
    private int currentMenuItemId;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            currentMenuItemId = R.id.mov_latest;
            navigationView.getMenu().getItem(0).setChecked(true);
        } else {
            currentMenuItemId = savedInstanceState.getInt(STATE_FRAGMENT);
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

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_MOV_LATEST) == null && getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_REST) == null) {
            doMenuAction(currentMenuItemId);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (findViewById(R.id.detail_swipe_refresh) != null) {
                    onBackPressed();
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle.setDrawerIndicatorEnabled(true);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
                getSupportFragmentManager().popBackStack();
                getSupportActionBar().setTitle(fragmentTag);
            } else {
                finish();
            }
        }
    }

    private void doMenuAction(int menuItemId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItemId) {
            case R.id.mov_latest:
                toggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setTitle(FRAGMENT_TAG_MOV_LATEST);
                CURRENT_FRAGMENT_TAG = FRAGMENT_TAG_MOV_LATEST;

                fragmentManager.beginTransaction()
                        .replace(R.id.frag_container, new MainActivityFragment(), FRAGMENT_TAG_MOV_LATEST).commit();

                break;
            case R.id.mov_now_playing:
                /*
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_container, new DriveCollectionFragment(), FRAGMENT_TAG_REST)
                        .commit();
                */
                break;
            default:
                //nothing;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (findViewById(R.id.detail_swipe_refresh) != null) {
            toggle.setDrawerIndicatorEnabled(false);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
        }
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

            toggle.setDrawerIndicatorEnabled(false);

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(CURRENT_FRAGMENT_TAG)
                    .add(R.id.frag_container, fragment, FRAGMENT_TAG_REST)
                    .commit();
        }
    }

}
