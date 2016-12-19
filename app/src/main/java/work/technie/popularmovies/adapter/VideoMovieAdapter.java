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

import work.technie.popularmovies.R;
import work.technie.popularmovies.utils.RoundedTransformation;

import static work.technie.popularmovies.Constants.COL_VIDEOS_KEY;
import static work.technie.popularmovies.Constants.COL_VIDEOS_NAME;

/**
 * Created by anupam on 27/12/15.
 */
public class VideoMovieAdapter extends RecyclerView.Adapter<VideoMovieAdapter.ViewHolder> {
    private static final String LOG_TAG = VideoMovieAdapter.class.getSimpleName();

    private Cursor cursor;
    private Context context;

    public VideoMovieAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_video_movie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        cursor.moveToPosition(position);
        final String trailer_name = cursor.getString(COL_VIDEOS_NAME);
        final String source = cursor.getString(COL_VIDEOS_KEY);
        viewHolder.trailer.setText(trailer_name);
        final String BASE_URL = "http://img.youtube.com/vi/";
        final String url = BASE_URL + source + "/0.jpg";

        Picasso
                .with(context)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .transform(new RoundedTransformation(10, 10))
                .fit()
                .centerCrop()
                .into(viewHolder.trailerImg, new Callback() {
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
                                .into(viewHolder.trailerImg, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onError() {
                                    }
                                });
                    }
                });
        viewHolder.trailerImg.setAdjustViewBounds(true);
/*
                final String trailerUrl = "https://www.youtube.com/watch?v=" + source;
                ImageView trailerImg = (ImageView) view.findViewById(R.id.youtubeImg);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                        context.startActivity(intent);
                    }
                });*/
    }

    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView trailer;
        final ImageView trailerImg;

        ViewHolder(View view) {
            super(view);
            trailer = (TextView) view.findViewById(R.id.trailer_name);
            trailerImg = (ImageView) view.findViewById(R.id.youtubeImg);
        }
    }
}