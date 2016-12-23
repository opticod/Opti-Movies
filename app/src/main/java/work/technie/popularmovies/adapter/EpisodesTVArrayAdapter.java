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

import java.util.Locale;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.R;
import work.technie.popularmovies.utils.RoundedTransformation;
import work.technie.popularmovies.utils.ViewHolderUtil;


public class EpisodesTVArrayAdapter extends
        RecyclerView.Adapter<EpisodesTVArrayAdapter.ViewHolder> {
    private static final String LOG_TAG = EpisodesTVArrayAdapter.class.getSimpleName();

    private final Cursor cursor;
    private Context context;
    private ViewHolderUtil.SetOnClickListener listener;

    public EpisodesTVArrayAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_tv_seasons, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        cursor.moveToPosition(position);

        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
        final String url = POSTER_BASE_URL + cursor.getString(Constants.TV_EPISODE_COL_STILL_PATH);
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
        viewHolder.name.setText(String.format(Locale.ENGLISH, "%s", cursor.getString(Constants.TV_EPISODE_COL_NAME)));
        viewHolder.count.setText(String.format(Locale.ENGLISH, "%d.", cursor.getInt(Constants.TV_EPISODE_COL_EPISODE_NUMBER)));
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
        final TextView count;
        private ViewHolderUtil.SetOnClickListener listener;

        ViewHolder(final View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.similar_movie_poster);
            name = (TextView) view.findViewById(R.id.name);
            count = (TextView) view.findViewById(R.id.season_count);
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