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

import work.technie.popularmovies.activity.BaseActivity;
import work.technie.popularmovies.data.MovieContract;

/**
 * Created by anupam on 17/12/15.
 */
public class FetchMovieTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private final Context mContext;

    public FetchMovieTask(Context context) {
        mContext = context;
    }

    /**
     * Take the String representing the complete movie list in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private void getMovieDataFromJson(String movieJsonStr, String mode)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MOVIE_ID = "id";
        final String ORGLANG = "original_language";
        final String ORGTITLE = "original_title";
        final String OVER = "overview";
        final String RELDATE = "release_date";
        final String POSTERPATH = "poster_path";
        final String POPULARITY = "popularity";
        final String VOTAVG = "vote_average";
        final String ADULT = "adult";
        final String TITLE = "title";
        final String BACKDROP_PATH = "backdrop_path";
        final String VOTE_COUNT = "vote_count";
        final String PAGE = "page";


        final String RESULT = "results";
        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
        final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";

        try {
            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(RESULT);

            String page = movieJson.getString(PAGE);

            Vector<ContentValues> cVVector = new Vector<ContentValues>(movieArray.length());

            for (int i = 0; i < movieArray.length(); i++) {

                String movie_id;
                String orgLang;
                String orgTitle;
                String overview;
                String relDate;
                String postURL;
                String popularity;
                String votAvg;
                String vote_count;
                String backdropURL;
                String title;
                String adult;


                JSONObject movieInfo = movieArray.getJSONObject(i);

                movie_id = movieInfo.getString(MOVIE_ID);
                orgLang = movieInfo.getString(ORGLANG);
                orgTitle = movieInfo.getString(ORGTITLE);
                overview = movieInfo.getString(OVER);
                relDate = movieInfo.getString(RELDATE);

                postURL = Uri.parse(POSTER_BASE_URL).buildUpon().
                        appendEncodedPath(movieInfo.getString(POSTERPATH)).build().toString();
                popularity = movieInfo.getString(POPULARITY);
                votAvg = movieInfo.getString(VOTAVG);
                vote_count = movieInfo.getString(VOTE_COUNT);
                title = movieInfo.getString(TITLE);
                adult = movieInfo.getString(ADULT);

                backdropURL = Uri.parse(BACKDROP_BASE_URL).buildUpon().
                        appendEncodedPath(movieInfo.getString(BACKDROP_PATH)).build().toString();


                ContentValues movieValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                movieValues.put(MovieContract.Movies.PAGE, page);
                movieValues.put(MovieContract.Movies.POSTER_PATH, postURL);
                movieValues.put(MovieContract.Movies.ADULT, adult);
                movieValues.put(MovieContract.Movies.OVERVIEW, overview);
                movieValues.put(MovieContract.Movies.RELEASE_DATE, relDate);
                movieValues.put(MovieContract.Movies.MOVIE_ID, movie_id);
                movieValues.put(MovieContract.Movies.ORIGINAL_TITLE, orgTitle);
                movieValues.put(MovieContract.Movies.ORIGINAL_LANGUAGE, orgLang);
                movieValues.put(MovieContract.Movies.TITLE, title);
                movieValues.put(MovieContract.Movies.BACKDROP_PATH, backdropURL);
                movieValues.put(MovieContract.Movies.POPULARITY, popularity);
                movieValues.put(MovieContract.Movies.VOTE_COUNT, vote_count);
                movieValues.put(MovieContract.Movies.VOTE_AVERAGE, votAvg);
                movieValues.put(MovieContract.Movies.MODE, mode);
                cVVector.add(movieValues);
            }
            int inserted = 0;
            // add to database
            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(MovieContract.Movies.CONTENT_URI, cvArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getTVDataFromJson(String tvJsonStr, String mode)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String TV_ID = "id";
        final String ORGLANG = "original_language";
        final String NAME = "name";
        final String ORGNAME = "original_name";
        final String OVER = "overview";
        final String FIRST_AIR_DATE = "first_air_date";
        final String POSTERPATH = "poster_path";
        final String POPULARITY = "popularity";
        final String VOTAVG = "vote_average";
        final String BACKDROP_PATH = "backdrop_path";
        final String VOTE_COUNT = "vote_count";
        final String MODE = "mode";
        final String PAGE = "page";


        final String RESULT = "results";
        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
        final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";

        try {
            JSONObject tvJson = new JSONObject(tvJsonStr);
            JSONArray tvArray = tvJson.getJSONArray(RESULT);

            String page = tvJson.getString(PAGE);

            Vector<ContentValues> cVVector = new Vector<ContentValues>(tvArray.length());

            for (int i = 0; i < tvArray.length(); i++) {

                String tv_id;
                String orgLang;
                String name;
                String org_name;
                String overview;
                String first_air_date;
                String postURL;
                String popularity;
                String votAvg;
                String vote_count;
                String backdropURL;


                JSONObject movieInfo = tvArray.getJSONObject(i);

                tv_id = movieInfo.getString(TV_ID);
                orgLang = movieInfo.getString(ORGLANG);
                name = movieInfo.getString(NAME);
                org_name = movieInfo.getString(ORGNAME);
                overview = movieInfo.getString(OVER);
                first_air_date = movieInfo.getString(FIRST_AIR_DATE);
                popularity = movieInfo.getString(POPULARITY);
                votAvg = movieInfo.getString(VOTAVG);
                backdropURL = movieInfo.getString(BACKDROP_PATH);
                vote_count = movieInfo.getString(VOTE_COUNT);


                postURL = Uri.parse(POSTER_BASE_URL).buildUpon().
                        appendEncodedPath(movieInfo.getString(POSTERPATH)).build().toString();

                backdropURL = Uri.parse(BACKDROP_BASE_URL).buildUpon().
                        appendEncodedPath(movieInfo.getString(BACKDROP_PATH)).build().toString();


                ContentValues tvValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                tvValues.put(MovieContract.TV.PAGE, page);
                tvValues.put(MovieContract.TV.POSTER_PATH, postURL);
                tvValues.put(MovieContract.TV.OVERVIEW, overview);
                tvValues.put(MovieContract.TV.FIRST_AIR_DATE, first_air_date);
                tvValues.put(MovieContract.TV.TV_ID, tv_id);
                tvValues.put(MovieContract.TV.ORIGINAL_NAME, org_name);
                tvValues.put(MovieContract.TV.ORIGINAL_LANGUAGE, orgLang);
                tvValues.put(MovieContract.TV.NAME, name);
                tvValues.put(MovieContract.TV.BACKDROP_PATH, backdropURL);
                tvValues.put(MovieContract.TV.POPULARITY, popularity);
                tvValues.put(MovieContract.TV.VOTE_COUNT, vote_count);
                tvValues.put(MovieContract.TV.VOTE_AVERAGE, votAvg);
                tvValues.put(MovieContract.TV.MODE, mode);
                cVVector.add(tvValues);
            }
            int inserted = 0;
            // add to database
            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(MovieContract.TV.CONTENT_URI, cvArray);
            }

        } catch (JSONException e) {
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
        String movieJsonStr = null;
        boolean isMovie = true;

        try {
            // Construct the URL for the movieAPI query
            // Possible parameters are avaiable at OWM's forecast API page, at

            String MOVIE_BASE_URL = "https://api.themoviedb.org/3/";

            switch (params[1]) {
                case BaseActivity.FRAGMENT_TAG_MOV_NOW_PLAYING:
                    MOVIE_BASE_URL += "movie/now_playing?";
                    break;
                case BaseActivity.FRAGMENT_TAG_MOV_POPULAR:
                    MOVIE_BASE_URL += "movie/popular?";
                    break;

                case BaseActivity.FRAGMENT_TAG_MOV_TOP_RATED:
                    MOVIE_BASE_URL += "movie/top_rated?";
                    break;

                case BaseActivity.FRAGMENT_TAG_MOV_UPCOMING:
                    MOVIE_BASE_URL += "movie/upcoming?";
                    break;

                case BaseActivity.FRAGMENT_TAG_TV_AIRING_TODAY:
                    isMovie = false;
                    MOVIE_BASE_URL += "/tv/airing_today?";
                    break;

                case BaseActivity.FRAGMENT_TAG_TV_ON_THE_AIR:
                    isMovie = false;
                    MOVIE_BASE_URL += "/tv/on_the_air?";
                    break;

                case BaseActivity.FRAGMENT_TAG_TV_POPULAR:
                    isMovie = false;
                    MOVIE_BASE_URL += "/tv/popular?";
                    break;

                case BaseActivity.FRAGMENT_TAG_TV_TOP_RATED:
                    isMovie = false;
                    MOVIE_BASE_URL += "/tv/top_rated?";
                    break;

                default:
                    break;
            }

            final String APPID_PARAM = "api_key";
            final String PAGE_PARAM = "page";
            final String LANG_PARAM = "language";
            final String REGION_PARAM = "region";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .appendQueryParameter(LANG_PARAM, "hi")
                    .appendQueryParameter(PAGE_PARAM, "1")
                    .appendQueryParameter(REGION_PARAM, "IN")
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
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
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();
            if (isMovie) {
                getMovieDataFromJson(movieJsonStr, params[1]);
            } else {
                getTVDataFromJson(movieJsonStr, params[1]);
            }
        } catch (IOException e) {
            //Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the movie data, there's no point in attemping
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
}