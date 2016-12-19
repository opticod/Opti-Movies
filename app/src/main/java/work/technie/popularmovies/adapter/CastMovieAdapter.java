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

/**
 * Created by anupam on 27/12/15.
 */
public class CastMovieAdapter extends RecyclerView.Adapter<CastMovieAdapter.ViewHolder> {
    private static final String LOG_TAG = CastMovieAdapter.class.getSimpleName();

    private Cursor cursor;
    private Context context;
    private ViewHolderUtil.SetOnClickListener listener;

    public CastMovieAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_cast_movie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        final String cast_name = cursor.getString(Constants.CAST_COL_NAME);
        final String cast_character = cursor.getString(Constants.CAST_COL_CHARACTER);
        holder.name.setText(cast_name);
        holder.character.setText(cast_character);

        final String PROFILE_BASE_URL = "http://image.tmdb.org/t/p/w92";
        final String profileURL = PROFILE_BASE_URL + cursor.getString(Constants.CAST_COL_PROFILE_PATH);

        Picasso
                .with(context)
                .load(profileURL)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .transform(new RoundedTransformation(10, 10))
                .fit()
                .centerCrop()
                .into(holder.profile, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Picasso
                                .with(context)
                                .load(profileURL)
                                .error(R.drawable.ic_account_circle_black_48dp)
                                .fit()
                                .centerCrop()
                                .into(holder.profile, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onError() {
                                    }
                                });
                    }
                });

        holder.profile.setAdjustViewBounds(true);
        holder.setItemClickListener(listener);
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
        void onItemClick(int position, View itemView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView profile;
        final TextView name;
        final TextView character;
        private ViewHolderUtil.SetOnClickListener listener;

        ViewHolder(final View view) {
            super(view);
            profile = (ImageView) view.findViewById(R.id.cast_image);
            name = (TextView) view.findViewById(R.id.cast_name);
            character = (TextView) view.findViewById(R.id.cast_character);
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition(), view);
                    }
                }
            });
        }

        void setItemClickListener(ViewHolderUtil.SetOnClickListener itemClickListener) {
            this.listener = itemClickListener;
        }
    }
}