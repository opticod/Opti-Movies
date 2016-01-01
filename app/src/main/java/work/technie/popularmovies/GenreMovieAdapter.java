package work.technie.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by anupam on 28/12/15.
 */
public class GenreMovieAdapter extends CursorAdapter {
    private static final String LOG_TAG = GenreMovieAdapter.class.getSimpleName();


    public GenreMovieAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_genre_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    public static class ViewHolder {

        public final TextView genre;

        public ViewHolder(View view) {
            genre = (TextView) view.findViewById(R.id.genre);
        }
    }


    /*
       This is where we fill-in the views with the contents of the cursor.
    */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final String genre = cursor.getString(DetailActivityFragment.COL_GENRE_NAME);

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.genre.setText("Genre: " + genre);

    }
}
