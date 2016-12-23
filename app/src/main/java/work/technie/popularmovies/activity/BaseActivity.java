package work.technie.popularmovies.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import work.technie.popularmovies.R;
import work.technie.popularmovies.fragment.DetailMovieActivityFragment;
import work.technie.popularmovies.fragment.DetailTVActivityFragment;
import work.technie.popularmovies.fragment.MainActivityFragment;
import work.technie.popularmovies.utils.PaletteTransformation;

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
    private static final String FRAGMENT_ABOUT = "About";
    private static final String FRAGMENT_SETTINGS = "Settings";

    private final static String STATE_FRAGMENT = "stateFragment";
    private final String DETAIL_FRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;
    private int currentMenuItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            currentMenuItemId = R.id.mov_now_playing;
            navigationView.setCheckedItem(R.id.mov_now_playing);
        } else {
            currentMenuItemId = savedInstanceState.getInt(STATE_FRAGMENT);
        }

        mTwoPane = findViewById(R.id.movie_detail_container) != null;

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
            if (!mTwoPane) {
                ImageView backdrop = (ImageView) findViewById(R.id.backdropImg);
                ImageView season_backdrop = (ImageView) findViewById(R.id.season_img);
                if (season_backdrop != null) {
                    BitmapDrawable bitmapSeasonDrawable = (BitmapDrawable) season_backdrop.getDrawable();
                    if (bitmapSeasonDrawable != null) {
                        Bitmap bitmap = bitmapSeasonDrawable.getBitmap();
                        if (bitmap != null) {
                            Palette palette = PaletteTransformation.getPalette(bitmap);
                            changeSystemToolbarColor(palette);
                        }
                    }
                } else if (backdrop != null) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) backdrop.getDrawable();
                    if (bitmapDrawable != null) {
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        if (bitmap != null) {
                            Palette palette = PaletteTransformation.getPalette(bitmap);
                            changeSystemToolbarColor(palette);
                        }
                    }
                }
            }

        }
    }

    private void changeSystemToolbarColor(Palette palette) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Activity mActivity = this;
            int dark_muted_color = palette.getDarkMutedColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
            changeColor(mActivity, dark_muted_color);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void changeColor(Activity mActivity, int dark_muted_color) {
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mActivity.getWindow().setStatusBarColor(dark_muted_color);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
    public void onItemSelected(String id, ImageView sharedView, Fragment current, boolean isMovie) {
            String imageTransitionName = "";
            String LAST_FRAGMENT = "last_fragment";
            if (isMovie) {
                DetailMovieActivityFragment fragment = new DetailMovieActivityFragment();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !mTwoPane) {
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
                arguments.putBoolean(Intent.ACTION_SCREEN_ON, mTwoPane);
                BitmapDrawable sharedDrawable = (BitmapDrawable) sharedView.getDrawable();
                if (sharedDrawable != null && !mTwoPane) {
                    arguments.putParcelable("POSTER_IMAGE", sharedDrawable.getBitmap());
                } else {
                    arguments.putParcelable("POSTER_IMAGE", null);
                }
                fragment.setArguments(arguments);
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (mTwoPane) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.movie_detail_container, fragment, DETAIL_FRAGMENT_TAG)
                            .addSharedElement(sharedView, imageTransitionName)
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frag_container, fragment, DETAIL_FRAGMENT_TAG)
                            .addToBackStack(LAST_FRAGMENT)
                            .addSharedElement(sharedView, imageTransitionName)
                            .commit();
                }
            } else {
                DetailTVActivityFragment fragment = new DetailTVActivityFragment();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !mTwoPane) {
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
                arguments.putBoolean(Intent.ACTION_SCREEN_ON, mTwoPane);
                BitmapDrawable sharedDrawable = (BitmapDrawable) sharedView.getDrawable();
                if (sharedDrawable != null && !mTwoPane) {
                    arguments.putParcelable("POSTER_IMAGE", sharedDrawable.getBitmap());
                } else {
                    arguments.putParcelable("POSTER_IMAGE", null);
                }
                fragment.setArguments(arguments);
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (mTwoPane) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.movie_detail_container, fragment, DETAIL_FRAGMENT_TAG)
                            .addSharedElement(sharedView, imageTransitionName)
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frag_container, fragment, DETAIL_FRAGMENT_TAG)
                            .addToBackStack(LAST_FRAGMENT)
                            .addSharedElement(sharedView, imageTransitionName)
                            .commit();
                }
            }
        }
}
