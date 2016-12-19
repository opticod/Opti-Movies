package work.technie.popularmovies.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.facebook.stetho.Stetho;

import work.technie.popularmovies.R;
import work.technie.popularmovies.fragment.DetailActivityFragment;
import work.technie.popularmovies.fragment.MainActivityFragment;

/**
 * Created by anupam on 9/12/16.
 */

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityFragment.Callback {

    public static final String FRAGMENT_TAG_MOVIE_BOOKMARKS = "Bookmarked Movies";
    public static final String FRAGMENT_TAG_TV_BOOKMARKS = "Bookmarked TV Shows";
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
    NavigationView navigationView;
    private boolean mTwoPane;
    private int currentMenuItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Stetho.initializeWithDefaults(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            currentMenuItemId = R.id.mov_now_playing;
            navigationView.setCheckedItem(R.id.mov_now_playing);
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

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_MOV_NOW_PLAYING) == null && getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG) == null) {
            doMenuAction(currentMenuItemId);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                    return true;
                } else {
                    onBackPressed();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doMenuAction(int menuItemId) {
        String currentFragment;

        switch (menuItemId) {

            case R.id.mov_bookmarks:
                currentFragment = FRAGMENT_TAG_MOVIE_BOOKMARKS;
                break;
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
            case R.id.tv_bookmarks:
                currentFragment = FRAGMENT_TAG_TV_BOOKMARKS;
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
        }
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (currentFragment) {
            case FRAGMENT_ABOUT:
                Intent intent = new Intent(BaseActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case FRAGMENT_SETTINGS:

                intent = new Intent(BaseActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;

            default:

                Bundle arguments = new Bundle();
                MainActivityFragment fragment = new MainActivityFragment();
                fragment.setArguments(arguments);
                arguments.putString(Intent.EXTRA_TEXT, currentFragment);
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.frag_container, fragment, currentFragment).commit();
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        doMenuAction(id);

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
    public void onItemSelected(String id, ImageView sharedView, Fragment current) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putString(Intent.EXTRA_TEXT, id);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAIL_FRAGMENT_TAG)
                    .commit();
        } else {

            String imageTransitionName = "";
            DetailActivityFragment fragment = new DetailActivityFragment();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                current.setSharedElementReturnTransition(TransitionInflater.from(
                        this).inflateTransition(R.transition.change_image_trans));
                current.setExitTransition(TransitionInflater.from(
                        this).inflateTransition(android.R.transition.fade));

                fragment.setSharedElementEnterTransition(TransitionInflater.from(
                        this).inflateTransition(R.transition.change_image_trans));
                fragment.setEnterTransition(TransitionInflater.from(
                        this).inflateTransition(android.R.transition.fade));

                imageTransitionName = sharedView.getTransitionName();
            }

            Bundle arguments = new Bundle();
            arguments.putString(Intent.EXTRA_TEXT, id);
            arguments.putString("TRANS_NAME", imageTransitionName);
            BitmapDrawable sharedDrawable = (BitmapDrawable) sharedView.getDrawable();
            if (sharedDrawable != null) {
                arguments.putParcelable("POSTER_IMAGE", sharedDrawable.getBitmap());
            } else {
                arguments.putParcelable("POSTER_IMAGE", null);
            }
            fragment.setArguments(arguments);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frag_container, fragment, DETAIL_FRAGMENT_TAG)
                    .addToBackStack(LAST_FRAGMENT)
                    .addSharedElement(sharedView, imageTransitionName)
                    .commit();
        }
    }

}
