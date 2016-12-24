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

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.R;
import work.technie.popularmovies.utils.ViewHolderUtil;

/**
 * Created by anupam on 27/12/15.
 */
public class CrewMovieAdapter extends
        RecyclerView.Adapter<CrewMovieAdapter.ViewHolder> {
    private static final String LOG_TAG = CrewMovieAdapter.class.getSimpleName();

    private final Cursor cursor;
    private ViewHolderUtil.SetOnClickListener listener;

    public CrewMovieAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_crew_movie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        final String crew_name = cursor.getString(Constants.CREW_COL_NAME);
        final String crew_department = cursor.getString(Constants.CREW_COL_JOB);
        holder.name.setText(crew_name);
        holder.department.setText(crew_department);
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
        void onItemClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView name;
        final TextView department;
        private ViewHolderUtil.SetOnClickListener listener;

        ViewHolder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.crew_name);
            department = (TextView) view.findViewById(R.id.crew_department);
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