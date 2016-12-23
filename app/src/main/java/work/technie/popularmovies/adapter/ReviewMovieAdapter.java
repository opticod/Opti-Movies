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
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import work.technie.popularmovies.R;

import static work.technie.popularmovies.Constants.COL_REVIEW_AUTHOR;
import static work.technie.popularmovies.Constants.COL_REVIEW_CONTENT;
import static work.technie.popularmovies.Constants.COL_REVIEW_URL;

/**
 * Created by anupam on 27/12/15.
 */
public class ReviewMovieAdapter extends
        RecyclerView.Adapter<ReviewMovieAdapter.ViewHolder> {

    private final Cursor cursor;

    public ReviewMovieAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_review_movie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        cursor.moveToPosition(position);
        final String author_name = cursor.getString(COL_REVIEW_AUTHOR);
        final String content = cursor.getString(COL_REVIEW_CONTENT);
        final String url = cursor.getString(COL_REVIEW_URL);

        viewHolder.author.setText(Html.fromHtml("<font color=\"#212121\">By " + author_name + ":</font>"));
        viewHolder.contentView.setText(content);
        viewHolder.urlView.setText(String.format(Locale.ENGLISH, "Look more at:  %s", url));
    }

    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView urlView;
        final TextView author;
        final TextView contentView;

        ViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.review_author);
            contentView = (TextView) view.findViewById(R.id.review_content);
            urlView = (TextView) view.findViewById(R.id.review_url);
        }
    }
}