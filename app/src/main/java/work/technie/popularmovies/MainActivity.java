package work.technie.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import work.technie.popularmovies.utils.Utility;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPlane;
    private String mSorting;
    private final String MOVIE_FRAGMENT_TAG = "MFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSorting=Utility.getPreferredSorting(this);
        super.onCreate(savedInstanceState);

         setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainActivityFragment(), MOVIE_FRAGMENT_TAG)
                    .commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id=item.getItemId();
        if(id==R.id.action_settings){
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        String sortOrder = Utility.getPreferredSorting(this);
        // update the sorting Order in our second pane using the fragment manager
        if (sortOrder != null && !sortOrder.equals(mSorting)) {
            MainActivityFragment ff = (MainActivityFragment)getSupportFragmentManager().findFragmentByTag(MOVIE_FRAGMENT_TAG);
            if ( null != ff ) {
                //Log.e("mainAct","dect");
                ff.onSortingChanged();
            }
            mSorting=sortOrder;
        }
    }
}
