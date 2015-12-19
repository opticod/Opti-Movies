package work.technie.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieArrayAdapter  extends CursorAdapter {
    private static final String LOG_TAG = MovieArrayAdapter.class.getSimpleName();
    private View view;

    public MovieArrayAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false);
        return view;
    }

    /*
       This is where we fill-in the views with the contents of the cursor.
    */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView) view.findViewById(R.id.grid_item_poster);
        String url=cursor.getString(MainActivityFragment.COL_POSTER_PATH);
        //Log.e("url" , url);
        Picasso
                .with(context)
                .load(url)
                .error(R.mipmap.ic_launcher)
                .fit()
                .into(imageView);

        imageView.setAdjustViewBounds(true);

    }
}