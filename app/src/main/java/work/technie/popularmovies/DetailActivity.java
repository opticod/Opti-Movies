package work.technie.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        String movieId=null;
        if(extras!=null){
            movieId = extras.getString(Intent.EXTRA_TEXT);
        }
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(Intent.EXTRA_TEXT, movieId);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }
    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}