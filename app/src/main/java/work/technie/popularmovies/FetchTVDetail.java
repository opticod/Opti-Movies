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

import work.technie.popularmovies.data.MovieContract.TVCast;
import work.technie.popularmovies.data.MovieContract.TVCreator;
import work.technie.popularmovies.data.MovieContract.TVDetails;
import work.technie.popularmovies.data.MovieContract.TVEpisodeRuntime;
import work.technie.popularmovies.data.MovieContract.TVGenres;
import work.technie.popularmovies.data.MovieContract.TVNetworks;
import work.technie.popularmovies.data.MovieContract.TVSeasons;
import work.technie.popularmovies.data.MovieContract.TVSimilar;
import work.technie.popularmovies.data.MovieContract.TVVideos;
import work.technie.popularmovies.utils.AsyncResponse;

/**
 * Created by anupam on 24/12/15.
 */
public class FetchTVDetail extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchTVDetail.class.getSimpleName();

    private final Context mContext;
    public AsyncResponse response = null;
    private boolean DEBUG = true;

    public FetchTVDetail(Context context) {
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
        final String NAME = "name";
        final String GENRE_ID = "id";
        final String TOTAL_PAGES_REVIEWS = "total_pages";
        final String TOTAL_RESULTS_REVIEWS = "total_results";
        final String PAGE = "page";
        final String tv_ID = "id";

        final String RESULT = "results";
        final String RESULT1 = "videos";
        final String RESULT3 = "genres";
        final String RESULT4 = "similar";
        final String RESULT5 = "seasons";
        final String RESULT6 = "episode_run_time";
        final String RESULT7 = "networks";
        final String CREDITS = "credits";
        final String CREATED_BY = "created_by";
        final String CAST = "cast";
        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
        final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";


        try {
            JSONObject tvJson = new JSONObject(tvJsonStr);
            JSONArray tvArrayVideos = tvJson.getJSONObject(RESULT1).getJSONArray(RESULT);
            JSONArray tvArrayCast = tvJson.getJSONObject(CREDITS).getJSONArray(CAST);
            JSONArray tvArrayCreator = tvJson.getJSONArray(CREATED_BY);

            JSONObject tvArraySimilar = tvJson.getJSONObject(RESULT4);
            JSONArray tvArrayGenres = tvJson.getJSONArray(RESULT3);
            JSONArray tvArraySeasons = tvJson.getJSONArray(RESULT5);
            JSONArray tvArrayNetworks = tvJson.getJSONArray(RESULT7);
            JSONArray tvArrayEpisodeRuntime = tvJson.getJSONArray(RESULT6);

            String tv_id = tvJson.getString(tv_ID);
            Vector<ContentValues> cVVectorEpisodeRuntime = new Vector<ContentValues>(tvArrayEpisodeRuntime.length());

            for (int i = 0; i < tvArrayEpisodeRuntime.length(); i++) {

                String runtime = tvArrayEpisodeRuntime.getString(i);

                ContentValues tvValues = new ContentValues();
                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(TVEpisodeRuntime.TV_ID, tv_id);
                tvValues.put(TVEpisodeRuntime.TIME, runtime);
                cVVectorEpisodeRuntime.add(tvValues);

            }
            // add to database
            if (cVVectorEpisodeRuntime.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorEpisodeRuntime.size()];
                cVVectorEpisodeRuntime.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TVEpisodeRuntime.CONTENT_URI, cvArray);
            }

            Vector<ContentValues> cVVectorTrailer = new Vector<ContentValues>(tvArrayVideos.length());

            for (int i = 0; i < tvArrayVideos.length(); i++) {

                JSONObject trailerInfo = tvArrayVideos.getJSONObject(i);

                String video_id;
                String key;
                String name;
                String site;
                String size;
                String type;


                video_id = trailerInfo.getString(TVVideos.VIDEO_ID);
                key = trailerInfo.getString(TVVideos.KEY);
                name = trailerInfo.getString(TVVideos.NAME);
                site = trailerInfo.getString(TVVideos.SITE);
                size = trailerInfo.getString(TVVideos.SIZE);
                type = trailerInfo.getString(TVVideos.TYPE);

                if (!site.trim().equalsIgnoreCase("youtube")) {
                    continue;
                }

                ContentValues tvValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(TVVideos.VIDEO_ID, video_id);
                tvValues.put(TVVideos.KEY, key);
                tvValues.put(TVVideos.NAME, name);
                tvValues.put(TVVideos.SITE, site);
                tvValues.put(TVVideos.SIZE, size);
                tvValues.put(TVVideos.TYPE, type);
                tvValues.put(TVVideos.TV_ID, tv_id);
                cVVectorTrailer.add(tvValues);
            }
            // add to database
            if (cVVectorTrailer.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorTrailer.size()];
                cVVectorTrailer.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TVVideos.CONTENT_URI, cvArray);
            }

            Vector<ContentValues> cVVectorSeasons = new Vector<ContentValues>(tvArraySeasons.length());

            for (int i = 0; i < tvArraySeasons.length(); i++) {

                JSONObject gen = tvArraySeasons.getJSONObject(i);
                String id = gen.getString(TVSeasons.ID);
                String episode_count = gen.getString(TVSeasons.EPISODE_COUNT);
                String season_number = gen.getString(TVSeasons.SEASON_NUMBER);
                String poster_path = gen.getString(TVSeasons.POSTER_PATH);

                ContentValues tvValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(TVSeasons.ID, id);
                tvValues.put(TVSeasons.EPISODE_COUNT, episode_count);
                tvValues.put(TVSeasons.TV_ID, tv_id);
                tvValues.put(TVSeasons.SEASON_NUMBER, season_number);
                tvValues.put(TVSeasons.POSTER_PATH, poster_path);
                cVVectorSeasons.add(tvValues);

            }
            // add to database
            if (cVVectorSeasons.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorSeasons.size()];
                cVVectorSeasons.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TVSeasons.CONTENT_URI, cvArray);
            }


            Vector<ContentValues> cVVectorNetworks = new Vector<ContentValues>(tvArraySeasons.length());

            for (int i = 0; i < tvArrayNetworks.length(); i++) {

                JSONObject gen = tvArrayNetworks.getJSONObject(i);
                String id = gen.getString(TVNetworks.ID);
                String name = gen.getString(TVNetworks.NAME);
                ContentValues tvValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(TVNetworks.ID, id);
                tvValues.put(TVNetworks.TV_ID, tv_id);
                tvValues.put(TVNetworks.NAME, name);
                cVVectorNetworks.add(tvValues);

            }
            // add to database
            if (cVVectorNetworks.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorNetworks.size()];
                cVVectorNetworks.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TVNetworks.CONTENT_URI, cvArray);
            }

            String page = tvArraySimilar.getString(PAGE);

            JSONArray similar = tvArraySimilar.getJSONArray("results");

            Vector<ContentValues> cVVectorSimilar = new Vector<ContentValues>(similar.length());

            for (int j = 0; j < similar.length(); j++) {

                String tv_id_new;
                String first_air_date;
                String orgLang;
                String orgName;
                String overview;
                String postURL;
                String popularity;
                String votAvg;
                String vote_count;
                String backdropURL;
                String name;

                JSONObject tvInfo = similar.getJSONObject(j);

                tv_id_new = tvInfo.getString(TVSimilar.TV_ID);
                first_air_date = tvInfo.getString(TVSimilar.FIRST_AIR_DATE);
                orgLang = tvInfo.getString(TVSimilar.ORIGINAL_LANGUAGE);
                orgName = tvInfo.getString(TVSimilar.ORIGINAL_NAME);
                overview = tvInfo.getString(TVSimilar.OVERVIEW);
                popularity = tvInfo.getString(TVSimilar.POPULARITY);
                votAvg = tvInfo.getString(TVSimilar.VOTE_AVERAGE);
                vote_count = tvInfo.getString(TVSimilar.VOTE_COUNT);
                name = tvInfo.getString(TVSimilar.NAME);

                postURL = Uri.parse(POSTER_BASE_URL).buildUpon().
                        appendEncodedPath(tvInfo.getString(TVSimilar.POSTER_PATH)).build().toString();

                backdropURL = Uri.parse(BACKDROP_BASE_URL).buildUpon().
                        appendEncodedPath(tvInfo.getString(TVSimilar.BACKDROP_PATH)).build().toString();


                ContentValues tvValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(TVSimilar.TV_ID_OLD, tv_id);
                tvValues.put(TVSimilar.PAGE, page);
                tvValues.put(TVSimilar.POSTER_PATH, postURL);
                tvValues.put(TVSimilar.OVERVIEW, overview);
                tvValues.put(TVSimilar.FIRST_AIR_DATE, first_air_date);
                tvValues.put(TVSimilar.TV_ID, tv_id_new);
                tvValues.put(TVSimilar.ORIGINAL_NAME, orgName);
                tvValues.put(TVSimilar.ORIGINAL_LANGUAGE, orgLang);
                tvValues.put(TVSimilar.NAME, name);
                tvValues.put(TVSimilar.BACKDROP_PATH, backdropURL);
                tvValues.put(TVSimilar.POPULARITY, popularity);
                tvValues.put(TVSimilar.VOTE_COUNT, vote_count);
                tvValues.put(TVSimilar.VOTE_AVERAGE, votAvg);
                cVVectorSimilar.add(tvValues);

            }
            // add to database
            if (cVVectorSimilar.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorSimilar.size()];
                cVVectorSimilar.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TVSimilar.CONTENT_URI, cvArray);
            }

            Vector<ContentValues> cVVectorGenres = new Vector<ContentValues>(tvArrayGenres.length());

            for (int i = 0; i < tvArrayGenres.length(); i++) {

                JSONObject gen = tvArrayGenres.getJSONObject(i);
                String genre_id = gen.getString(GENRE_ID);
                String name = gen.getString(NAME);

                ContentValues tvValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(TVGenres.ID_GENRES, genre_id);
                tvValues.put(TVGenres.NAME, name);
                tvValues.put(TVGenres.TV_ID, tv_id);
                cVVectorGenres.add(tvValues);

            }
            // add to database
            if (cVVectorGenres.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorGenres.size()];
                cVVectorGenres.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TVGenres.CONTENT_URI, cvArray);
            }

            Vector<ContentValues> cVVectorCreator = new Vector<ContentValues>(tvArrayCreator.length());

            for (int i = 0; i < tvArrayCreator.length(); i++) {

                JSONObject obj = tvArrayCreator.getJSONObject(i);
                String id = obj.getString(TVCreator.ID);
                String name = obj.getString(TVCreator.NAME);
                String profile_path = obj.getString(TVCreator.PROFILE_PATH);

                ContentValues tvValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(TVCreator.TV_ID, tv_id);
                tvValues.put(TVCreator.ID, id);
                tvValues.put(TVCreator.NAME, name);
                tvValues.put(TVCreator.PROFILE_PATH, profile_path);
                cVVectorCreator.add(tvValues);

            }
            // add to database
            if (cVVectorCreator.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorCreator.size()];
                cVVectorCreator.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TVCreator.CONTENT_URI, cvArray);
            }

            Vector<ContentValues> cVVectorCast = new Vector<ContentValues>(tvArrayCast.length());

            for (int i = 0; i < tvArrayCast.length(); i++) {

                JSONObject obj = tvArrayCast.getJSONObject(i);
                String character = obj.getString(TVCast.CHARACTER);
                String credit_id = obj.getString(TVCast.CREDIT_ID);
                String id = obj.getString(TVCast.ID);
                String name = obj.getString(TVCast.NAME);
                String order = obj.getString("order");
                String profile_path = obj.getString(TVCast.PROFILE_PATH);

                ContentValues tvValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(TVCast.CHARACTER, character);
                tvValues.put(TVCast.CREDIT_ID, credit_id);
                tvValues.put(TVCast.ID, id);
                tvValues.put(TVCast.NAME, name);
                tvValues.put(TVCast.ORDER, order);
                tvValues.put(TVCast.PROFILE_PATH, profile_path);
                tvValues.put(TVCast.TV_ID, tv_id);
                cVVectorCast.add(tvValues);

            }
            // add to database
            if (cVVectorCast.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorCast.size()];
                cVVectorCast.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(TVCast.CONTENT_URI, cvArray);
            }

            ContentValues[] cvArray = new ContentValues[1];

            String orgLang;
            String orgName;
            String overview;
            String postURL;
            String popularity;
            String votAvg;
            String vote_count;
            String backdropURL;
            String name;
            String homepage;
            String status;
            String first_air_date;
            String in_production;
            String last_air_date;
            String number_of_episodes;
            String number_of_seasons;
            String type;

            homepage = tvJson.getString(TVDetails.HOMEPAGE);
            status = tvJson.getString(TVDetails.STATUS);
            tv_id = tvJson.getString(TVDetails.TV_ID);
            orgLang = tvJson.getString(TVDetails.ORIGINAL_LANG);
            orgName = tvJson.getString(TVDetails.ORIGINAL_NAME);
            overview = tvJson.getString(TVDetails.OVERVIEW);

            postURL = Uri.parse(POSTER_BASE_URL).buildUpon().
                    appendEncodedPath(tvJson.getString(TVDetails.POSTER_PATH)).build().toString();
            popularity = tvJson.getString(TVDetails.POPULARITY);
            votAvg = tvJson.getString(TVDetails.VOTE_AVERAGE);
            vote_count = tvJson.getString(TVDetails.VOTE_COUNT);
            name = tvJson.getString(TVDetails.NAME);
            first_air_date = tvJson.getString(TVDetails.FIRST_AIR_DATE);
            last_air_date = tvJson.getString(TVDetails.LAST_AIR_DATE);
            in_production = tvJson.getString(TVDetails.IN_PRODUCTION);
            number_of_episodes = tvJson.getString(TVDetails.NUMBER_OF_EPISODES);
            number_of_seasons = tvJson.getString(TVDetails.NUMBER_OF_SEASONS);
            type = tvJson.getString(TVDetails.TYPE);

            backdropURL = Uri.parse(BACKDROP_BASE_URL).buildUpon().
                    appendEncodedPath(tvJson.getString(TVDetails.BACKDROP_PATH)).build().toString();

            ContentValues tvValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.

            tvValues.put(TVDetails.HOMEPAGE, homepage);
            tvValues.put(TVDetails.STATUS, status);
            tvValues.put(TVDetails.POSTER_PATH, postURL);
            tvValues.put(TVDetails.OVERVIEW, overview);
            tvValues.put(TVDetails.TV_ID, tv_id);
            tvValues.put(TVDetails.ORIGINAL_NAME, orgName);
            tvValues.put(TVDetails.ORIGINAL_LANG, orgLang);
            tvValues.put(TVDetails.NAME, name);
            tvValues.put(TVDetails.BACKDROP_PATH, backdropURL);
            tvValues.put(TVDetails.POPULARITY, popularity);
            tvValues.put(TVDetails.VOTE_COUNT, vote_count);
            tvValues.put(TVDetails.VOTE_AVERAGE, votAvg);
            tvValues.put(TVDetails.FIRST_AIR_DATE, first_air_date);
            tvValues.put(TVDetails.LAST_AIR_DATE, last_air_date);
            tvValues.put(TVDetails.IN_PRODUCTION, in_production);
            tvValues.put(TVDetails.NUMBER_OF_EPISODES, number_of_episodes);
            tvValues.put(TVDetails.NUMBER_OF_SEASONS, number_of_seasons);
            tvValues.put(TVDetails.TYPE, type);
            cvArray[0] = tvValues;
            mContext.getContentResolver().bulkInsert(TVDetails.CONTENT_URI, cvArray);

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
            final String DATA = "append_to_response";

            Uri builtUri = Uri.parse(tv_BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .appendQueryParameter(DATA, "videos,credits,similar")
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