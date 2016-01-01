package work.technie.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by anupam on 27/12/15.
 */
public class TrailerMovieAdapter extends CursorAdapter {
    private static final String LOG_TAG = TrailerMovieAdapter.class.getSimpleName();


    public TrailerMovieAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_trailer_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }
    public static class ViewHolder {

        public final TextView trailer;
        public final ImageView trailerImg;

        public ViewHolder(View view) {
            trailer = (TextView) view.findViewById(R.id.trailer_name);
            trailerImg = (ImageView) view.findViewById(R.id.youtubeImg);
        }
    }


    /*
       This is where we fill-in the views with the contents of the cursor.
    */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final String trailer_name=cursor.getString(DetailActivityFragment.COL_TRAILER_NAME);
        final String source=cursor.getString(DetailActivityFragment.COL_TRAILER_SOURCE);
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.trailer.setText(trailer_name);
        final String BASE_URL="http://img.youtube.com/vi/";
        final String url=BASE_URL+source+"/0.jpg";
        Picasso
                .with(context)
                .load(url)
                .transform(new RoundedCornersTransformation(10, 10))
                .fit()
                .centerCrop()
                        .into(viewHolder.trailerImg);
        viewHolder.trailerImg.setAdjustViewBounds(true);

        final String trailerUrl="https://www.youtube.com/watch?v="+source;
        ImageView trailerImg = (ImageView) view.findViewById(R.id.youtubeImg);
        trailerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                context.startActivity(intent);
            }
        });
    }
}