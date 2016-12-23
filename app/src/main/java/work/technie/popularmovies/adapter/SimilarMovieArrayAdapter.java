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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.R;
import work.technie.popularmovies.utils.RoundedTransformation;
import work.technie.popularmovies.utils.ViewHolderUtil;


public class SimilarMovieArrayAdapter extends
        RecyclerView.Adapter<SimilarMovieArrayAdapter.ViewHolder> {

    private final Cursor cursor;
    private final boolean isTV;
    private Context context;
    private ViewHolderUtil.SetOnClickListener listener;

    public SimilarMovieArrayAdapter(Cursor cursor) {
        this.cursor = cursor;
        this.isTV = false;
    }

    public SimilarMovieArrayAdapter(Cursor cursor, boolean isTV) {
        this.cursor = cursor;
        this.isTV = isTV;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_similar_movie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        cursor.moveToPosition(position);

        final String url = cursor.getString(isTV ? Constants.TV_SIMILAR_COL_POSTER_PATH : Constants.SIMILAR_MOV_COL_POSTER_PATH);
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
        viewHolder.name.setText(cursor.getString(isTV ? Constants.TV_SIMILAR_COL_ORIGINAL_NAME : Constants.SIMILAR_MOV_COL_ORIGINAL_TITLE));
        viewHolder.setItemClickListener(listener);
    }

    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setOnClickListener(ViewHolderUtil.SetOnClickListener clickListener) {
        this.listener = clickListener;
    }

    public interface SetOnClickListener extends ViewHolderUtil.SetOnClickListener {
        void onItemClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView name;
        private ViewHolderUtil.SetOnClickListener listener;

        ViewHolder(final View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.similar_movie_poster);
            name = (TextView) view.findViewById(R.id.name);
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        void setItemClickListener(ViewHolderUtil.SetOnClickListener itemClickListener) {
            this.listener = itemClickListener;
        }

    }
}