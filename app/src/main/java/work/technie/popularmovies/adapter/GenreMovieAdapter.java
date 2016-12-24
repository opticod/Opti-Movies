/*
 * Copyright (C) 2017 Anupam Das
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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import work.technie.popularmovies.R;

import static work.technie.popularmovies.Constants.COL_GENRE_NAME;

/**
 * Created by anupam on 28/12/15.
 */
public class GenreMovieAdapter extends
        RecyclerView.Adapter<GenreMovieAdapter.ViewHolder> {
    private static final String LOG_TAG = GenreMovieAdapter.class.getSimpleName();
    private final Cursor cursor;

    public GenreMovieAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_genre_movie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        final String genre = cursor.getString(COL_GENRE_NAME);
        holder.genre.setText(genre);
    }

    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView genre;

        ViewHolder(View view) {
            super(view);
            genre = (TextView) view.findViewById(R.id.genre);
        }
    }
}
