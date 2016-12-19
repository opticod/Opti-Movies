/*
 * Copyright (C) 2016 Anupam Das
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package work.technie.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.R;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.utils.RoundedTransformation;


public class TVMovieArrayAdapter extends CursorAdapter {
    private static final String LOG_TAG = TVMovieArrayAdapter.class.getSimpleName();

    private boolean isMovie;
    private boolean isMovieBookmark;
    private boolean isTVBookmark;

    public TVMovieArrayAdapter(Context context, boolean isMovie, boolean isMovieBookmark, boolean isTVBookmark, Cursor c, int flags) {
        super(context, c, flags);
        this.isMovie = isMovie;
        this.isMovieBookmark = isMovieBookmark;
        this.isTVBookmark = isTVBookmark;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    /*
       This is where we fill-in the views with the contents of the cursor.
    */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        final String url = cursor.getString(isMovieBookmark ? Constants.MOV_DETAILS_COL_POSTER_PATH : (isMovie ? Constants.MOV_COL_POSTER_PATH : Constants.TV_COL_POSTER_PATH));
        Picasso
                .with(context)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .transform(new RoundedTransformation(10, 10))
                .fit()
                .centerCrop()
                .into(viewHolder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Picasso
                                .with(context)
                                .load(url)
                                .error(R.mipmap.ic_launcher)
                                .fit()
                                .centerCrop()
                                .into(viewHolder.imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onError() {
                                    }
                                });
                    }
                });
        viewHolder.imageView.setAdjustViewBounds(true);

        String date = cursor.getString(isMovieBookmark ? Constants.MOV_DETAILS_COL_RELEASE_DATE : (isMovie ? Constants.MOV_COL_RELEASE_DATE : Constants.TV_COL_FIRST_AIR_DATE));
        int pos = date.indexOf('-');
        viewHolder.year.setText(date.substring(0, pos >= 0 ? pos : 0));

        int fav = 0;
        if (isMovie) {
            Cursor cursor1 = context.getContentResolver().query(
                    MovieContract.FavouritesMovies.buildMoviesUriWithMovieId(cursor.getString(isMovieBookmark ? Constants.MOV_DETAILS_COL_MOVIE_ID : Constants.MOV_COL_MOVIE_ID)),
                    null, null, null, null);
            if (!(cursor1 == null || !(cursor1.moveToFirst()) || cursor1.getCount() == 0)) {
                fav = 1;
                cursor1.close();
            }
        } else {/*
            Cursor cursor1 = context.getContentResolver().query(
                    MovieContract.FavouritesMovies.buildTVUriWithTVId(cursor.getString(Constants.TV_COL_ID)),
                    null, null, null, null);
            if (!(cursor1 == null || !(cursor1.moveToFirst()) || cursor1.getCount() == 0)) {
                fav = 1;
                cursor1.close();
            }*/
        }

        if (fav == 1) {
            viewHolder.favIcon.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            viewHolder.favIcon.setImageResource(R.drawable.ic_star_border_black_24dp);
        }

        String rating = cursor.getString(isMovieBookmark ? Constants.MOV_DETAILS_COL_VOTE_AVERAGE : (isMovie ? Constants.MOV_COL_VOTE_AVERAGE : Constants.TV_COL_VOTE_AVERAGE));
        double vote = Double.parseDouble(rating);
        rating = String.valueOf((double) Math.round(vote * 10d) / 10d);

        viewHolder.userRating.setText(String.format(Locale.ENGLISH, "%s/10", rating));

        String popularity = cursor.getString(isMovieBookmark ? Constants.MOV_DETAILS_COL_POPULARITY : (isMovie ? Constants.MOV_COL_POPULARITY : Constants.TV_COL_POPULARITY));
        pos = popularity.indexOf(".");
        viewHolder.pop_text.setText(popularity.substring(0, pos >= 0 ? pos : 0));
    }

    private static class ViewHolder {

        final ImageView imageView;
        final TextView year;
        final ImageView favIcon;
        final TextView userRating;
        final TextView pop_text;

        ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.grid_item_poster);
            year = (TextView) view.findViewById(R.id.year);
            favIcon = (ImageView) view.findViewById(R.id.vote_icon);
            userRating = (TextView) view.findViewById(R.id.vote_text);
            pop_text = (TextView) view.findViewById(R.id.pop_text);
        }
    }
}