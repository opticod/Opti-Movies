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

package work.technie.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Created by anupam on 27/12/15.
 */
public class ReviewMovieAdapter extends CursorAdapter {
        private static final String LOG_TAG = ReviewMovieAdapter.class.getSimpleName();

        public ReviewMovieAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }
    public static class ViewHolder {

        public final TextView author;
        public final TextView contentView;
        public final TextView urlView;

        public ViewHolder(View view) {
            author = (TextView) view.findViewById(R.id.review_author);
            contentView = (TextView) view.findViewById(R.id.review_content);
            urlView = (TextView) view.findViewById(R.id.review_url);

        }
    }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_review_movie, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
            return view;
        }
        /*
           This is where we fill-in the views with the contents of the cursor.
        */
        @Override
        public void bindView(View view, final Context context, Cursor cursor) {
            final String author_name=cursor.getString(DetailActivityFragment.COL_REVIEW_AUTHOR);
            final String content=cursor.getString(DetailActivityFragment.COL_REVIEW_CONTENT);
            final String url=cursor.getString(DetailActivityFragment.COL_REVIEW_URL);

            ViewHolder viewHolder = (ViewHolder) view.getTag();

            viewHolder.author.setText("Author:  "+author_name);
            viewHolder.contentView.setText("Content:  "+content);
            viewHolder.urlView.setText("Look more at:  "+url);
        }
    }