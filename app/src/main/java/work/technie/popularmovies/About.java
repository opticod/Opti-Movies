package work.technie.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Created by anupam on 13/2/16.
 */
public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        TextView txtView = (TextView) findViewById(R.id.about_text);
        txtView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
