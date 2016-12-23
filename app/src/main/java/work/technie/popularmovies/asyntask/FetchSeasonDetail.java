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

package work.technie.popularmovies.asyntask;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import work.technie.popularmovies.BuildConfig;
import work.technie.popularmovies.data.MovieContract.TVEpisode;
import work.technie.popularmovies.data.MovieContract.TVEpisodeCrew;
import work.technie.popularmovies.data.MovieContract.TVEpisodeGuestStar;
import work.technie.popularmovies.data.MovieContract.TVSeasonDetails;
import work.technie.popularmovies.utils.AsyncResponse;

/**
 * Created by anupam on 24/12/15.
 */
public class FetchSeasonDetail extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchSeasonDetail.class.getSimpleName();

    private final Context mContext;
    public AsyncResponse response = null;

    public FetchSeasonDetail(Context context) {
        mContext = context;
    }

    /**
     * Take the String representing the complete tv list in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private void gettvDataFromJson(String tvJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String RESULT1 = "episodes";

        try {
            JSONObject tvJson = new JSONObject(tvJsonStr);
            JSONArray tvArrayEpisodes = tvJson.getJSONArray(RESULT1);

            String season_id = tvJson.getString("id");

            Vector<ContentValues> cVVectorEpisode = new Vector<ContentValues>(tvArrayEpisodes.length());

            for (int i = 0; i < tvArrayEpisodes.length(); i++) {

                JSONObject episode = tvArrayEpisodes.getJSONObject(i);
                JSONArray tvArrayEpisodeCrew = episode.getJSONArray("crew");
                JSONArray tvArrayEpisodeGuestStars = episode.getJSONArray("guest_stars");


                String air_date = episode.getString(TVEpisode.AIR_DATE);
                String episode_number = episode.getString(TVEpisode.EPISODE_NUMBER);
                String episode_name = episode.getString(TVEpisode.NAME);
                String overview = episode.getString(TVEpisode.AIR_DATE);
                String id = episode.getString(TVEpisode.ID);
                String production_code = episode.getString(TVEpisode.PRODUCTION_CODE);
                String season_number = episode.getString(TVEpisode.SEASON_NUMBER);
                String still_path = episode.getString(TVEpisode.STILL_PATH);
                String vote_average = episode.getString(TVEpisode.VOTE_AVERAGE);
                String vote_count = episode.getString(TVEpisode.VOTE_COUNT);

                Vector<ContentValues> cVVectorEpisodeCrew = new Vector<ContentValues>(tvArrayEpisodeCrew.length());

                for (int j = 0; j < tvArrayEpisodeCrew.length(); j++) {
                    JSONObject crew = tvArrayEpisodeCrew.getJSONObject(j);

                    String crew_id = crew.getString("id");
                    String credit_id = crew.getString(TVEpisodeCrew.CREDIT_ID);
                    String name = crew.getString(TVEpisodeCrew.NAME);
                    String department = crew.getString(TVEpisodeCrew.DEPARTMENT);
                    String job = crew.getString(TVEpisodeCrew.JOB);
                    String profile_path = crew.getString(TVEpisodeCrew.PROFILE_PATH);

                    ContentValues tvValues = new ContentValues();
                    tvValues.put(TVEpisodeCrew.EPISODE_ID, id);
                    tvValues.put(TVEpisodeCrew.ID, crew_id);
                    tvValues.put(TVEpisodeCrew.CREDIT_ID, credit_id);
                    tvValues.put(TVEpisodeCrew.NAME, name);
                    tvValues.put(TVEpisodeCrew.DEPARTMENT, department);
                    tvValues.put(TVEpisodeCrew.JOB, job);
                    tvValues.put(TVEpisodeCrew.PROFILE_PATH, profile_path);
                    cVVectorEpisodeCrew.add(tvValues);

                }
                // add to database
                if (cVVectorEpisodeCrew.size() > 0) {
                    ContentValues[] cvArray = new ContentValues[cVVectorEpisodeCrew.size()];
                    cVVectorEpisodeCrew.toArray(cvArray);
                    mContext.getContentResolver().bulkInsert(TVEpisodeCrew.CONTENT_URI, cvArray);
                }

                Vector<ContentValues> cVVectorEpisodeGuestStars = new Vector<ContentValues>(tvArrayEpisodeGuestStars.length());

                for (int j = 0; j < tvArrayEpisodeGuestStars.length(); j++) {
                    JSONObject guest = tvArrayEpisodeGuestStars.getJSONObject(j);

                    String guest_id = guest.getString("id");
                    String name = guest.getString(TVEpisodeGuestStar.NAME);
                    String credit_id = guest.getString(TVEpisodeGuestStar.CREDIT_ID);
                    String character = guest.getString(TVEpisodeGuestStar.CHARACTER);
                    String order = guest.getString("order");
                    String profile_path = guest.getString(TVEpisodeGuestStar.PROFILE_PATH);

                    ContentValues tvValues = new ContentValues();
                    tvValues.put(TVEpisodeGuestStar.EPISODE_ID, id);
                    tvValues.put(TVEpisodeGuestStar.ID, guest_id);
                    tvValues.put(TVEpisodeGuestStar.CREDIT_ID, credit_id);
                    tvValues.put(TVEpisodeGuestStar.NAME, name);
                    tvValues.put(TVEpisodeGuestStar.CHARACTER, character);
                    tvValues.put(TVEpisodeGuestStar.ORDER, order);
                    tvValues.put(TVEpisodeGuestStar.PROFILE_PATH, profile_path);
                    cVVectorEpisodeGuestStars.add(tvValues);

                }
                // add to database
                if (cVVectorEpisodeGuestStars.size() > 0) {
                    ContentValues[] cvArray = new ContentValues[cVVectorEpisodeGuestStars.size()];
                    cVVectorEpisodeGuestStars.toArray(cvArray);
                    mContext.getContentResolver().bulkInsert(TVEpisodeGuestStar.CONTENT_URI, cvArray);
                }

                ContentValues tvValues = new ContentValues();
                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(TVEpisode.SEASON_ID, season_id);
                tvValues.put(TVEpisode.AIR_DATE, air_date);
                tvValues.put(TVEpisode.EPISODE_NUMBER, episode_number);
                tvValues.put(TVEpisode.NAME, episode_name);
                tvValues.put(TVEpisode.OVERVIEW, overview);
                tvValues.put(TVEpisode.ID, id);
                tvValues.put(TVEpisode.PRODUCTION_CODE, production_code);
                tvValues.put(TVEpisode.SEASON_NUMBER, season_number);
                tvValues.put(TVEpisode.STILL_PATH, still_path);
                tvValues.put(TVEpisode.VOTE_AVERAGE, vote_average);
                tvValues.put(TVEpisode.VOTE_COUNT, vote_count);
                cVVectorEpisode.add(tvValues);

            }

            // add to database
            if (cVVectorEpisode.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorEpisode.size()];
                cVVectorEpisode.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TVEpisode.CONTENT_URI, cvArray);
            }

            String _id = tvJson.getString("_id");
            String air_date = tvJson.getString(TVSeasonDetails.AIR_DATE);
            String name = tvJson.getString(TVSeasonDetails.NAME);
            String overview = tvJson.getString(TVSeasonDetails.OVERVIEW);
            String poster_path = tvJson.getString(TVSeasonDetails.POSTER_PATH);
            String season_number = tvJson.getString(TVSeasonDetails.SEASON_NUMBER);

            ContentValues[] cvArray = new ContentValues[1];

            ContentValues tvValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.

            tvValues.put(TVSeasonDetails.SEASON_ID, season_id);
            tvValues.put(TVSeasonDetails.AIR_DATE, air_date);
            tvValues.put(TVSeasonDetails.NAME, name);
            tvValues.put(TVSeasonDetails.OVERVIEW, overview);
            tvValues.put(TVSeasonDetails.SEASON__ID, _id);
            tvValues.put(TVSeasonDetails.POSTER_PATH, poster_path);
            tvValues.put(TVSeasonDetails.SEASON_NUMBER, season_number);
            cvArray[0] = tvValues;
            mContext.getContentResolver().bulkInsert(TVSeasonDetails.CONTENT_URI, cvArray);


        } catch (JSONException e) {
            //Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String tvJsonStr;

        try {
            // Construct the URL for the tvAPI query
            // Possible parameters are avaiable at OWM's forecast API page, at
            final String tv_BASE_URL =
                    "http://api.themoviedb.org/3/tv/";
            final String APPID_PARAM = "api_key";
            final String SEASON_PARAM = "season";

            Uri builtUri = Uri.parse(tv_BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .appendPath(SEASON_PARAM)
                    .appendPath(params[1])
                    .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            tvJsonStr = buffer.toString();
            gettvDataFromJson(tvJsonStr);
        } catch (IOException e) {
            //Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the tv data, there's no point in attemping
            // to parse it.
            return null;
        } catch (JSONException e) {
            //Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    //Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        response.onFinish(true);
    }
}