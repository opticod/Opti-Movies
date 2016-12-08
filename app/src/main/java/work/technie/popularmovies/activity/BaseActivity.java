package work.technie.popularmovies.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import work.technie.popularmovies.R;

/**
 * Created by anupam on 9/12/16.
 */

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_TAG_MOV_LATEST = "MOV_LATEST_FRAGMENT";
    private final static String STATE_FRAGMENT = "stateFragment";
    private static final String TAG = "BaseActivity";

    private final String FRAGMENT_TAG_REST = "FTAGR";
    private int currentMenuItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            currentMenuItemId = R.id.mov_latest;
            navigationView.getMenu().getItem(0).setChecked(true);
        } else {
            currentMenuItemId = savedInstanceState.getInt(STATE_FRAGMENT);
        }

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_MOV_LATEST) == null && getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_REST) == null) {
            doMenuAction(currentMenuItemId);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
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
                    finish();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doMenuAction(int menuItemId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItemId) {
            case R.id.mov_latest:
                /*
                fragmentManager.beginTransaction()
                        .replace(R.id.frag_container, new MapFragment(), FRAGMENT_TAG_MOV_LATEST).commit();
*/
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

}
