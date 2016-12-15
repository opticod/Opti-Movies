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
import android.widget.TextView;

import work.technie.popularmovies.R;

import static work.technie.popularmovies.Constants.COL_GENRE_NAME;

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

    /*
       This is where we fill-in the views with the contents of the cursor.
    */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final String genre = cursor.getString(COL_GENRE_NAME);

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.genre.setText("Genre: " + genre);

    }

    public static class ViewHolder {

        public final TextView genre;

        public ViewHolder(View view) {
            genre = (TextView) view.findViewById(R.id.genre);
        }
    }
}
