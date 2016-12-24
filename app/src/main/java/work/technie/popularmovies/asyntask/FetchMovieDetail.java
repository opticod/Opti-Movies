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
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.data.MovieContract.Cast;
import work.technie.popularmovies.data.MovieContract.Crew;
import work.technie.popularmovies.data.MovieContract.Genres;
import work.technie.popularmovies.data.MovieContract.MovieDetails;
import work.technie.popularmovies.data.MovieContract.Reviews;
import work.technie.popularmovies.data.MovieContract.SimilarMovies;
import work.technie.popularmovies.data.MovieContract.Videos;
import work.technie.popularmovies.utils.AsyncResponse;


/**
 * Created by anupam on 24/12/15.
 */
public class FetchMovieDetail extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchTVMovieTask.class.getSimpleName();

    private final Context mContext;
    public AsyncResponse response = null;
    private boolean DEBUG = true;

    public FetchMovieDetail(Context context) {
        mContext = context;
    }

    /**
     * Take the String representing the complete movie list in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private void getMovieDataFromJson(String movieJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String NAME = "name";
        final String REVIEW_ID = "id";
        final String GENRE_ID = "id";
        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String URL = "url";
        final String TOTAL_PAGES_REVIEWS = "total_pages";
        final String TOTAL_RESULTS_REVIEWS = "total_results";
        final String PAGE = "page";
        final String MOVIE_ID = "id";

        final String RESULT = "results";
        final String RESULT1 = "videos";
        final String RESULT2 = "reviews";
        final String RESULT3 = "genres";
        final String RESULT4 = "similar";
        final String CREDITS = "credits";
        final String CAST = "cast";
        final String CREW = "crew";
        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
        final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";


        try {
            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArrayVideos = movieJson.getJSONObject(RESULT1).getJSONArray(RESULT);
            JSONArray movieArrayCast = movieJson.getJSONObject(CREDITS).getJSONArray(CAST);
            JSONArray movieArrayCrew = movieJson.getJSONObject(CREDITS).getJSONArray(CREW);

            JSONObject movieArrayReviews = movieJson.getJSONObject(RESULT2);
            JSONObject movieArraySimilar = movieJson.getJSONObject(RESULT4);
            JSONArray movieArrayGenres = movieJson.getJSONArray(RESULT3);

            String movie_id = movieJson.getString(MOVIE_ID);

            Vector<ContentValues> cVVectorTrailer = new Vector<ContentValues>(movieArrayVideos.length());

            for (int i = 0; i < movieArrayVideos.length(); i++) {

                JSONObject trailerInfo = movieArrayVideos.getJSONObject(i);

                String video_id;
                String key;
                String name;
                String site;
                String size;
                String type;


                video_id = trailerInfo.getString(Videos.VIDEO_ID);
                key = trailerInfo.getString(Videos.KEY);
                name = trailerInfo.getString(Videos.NAME);
                site = trailerInfo.getString(Videos.SITE);
                size = trailerInfo.getString(Videos.SIZE);
                type = trailerInfo.getString(Videos.TYPE);

                if (!site.trim().equalsIgnoreCase("youtube")) {
                    continue;
                }

                ContentValues movieValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                movieValues.put(Videos.VIDEO_ID, video_id);
                movieValues.put(Videos.KEY, key);
                movieValues.put(Videos.NAME, name);
                movieValues.put(Videos.SITE, site);
                movieValues.put(Videos.SIZE, size);
                movieValues.put(Videos.TYPE, type);
                movieValues.put(Videos.MOVIE_ID, movie_id);
                cVVectorTrailer.add(movieValues);
            }
            // add to database
            if (cVVectorTrailer.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorTrailer.size()];
                cVVectorTrailer.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(Videos.CONTENT_URI, cvArray);
            }

            String page = movieArrayReviews.getString(PAGE);
            String total_page = movieArrayReviews.getString(TOTAL_PAGES_REVIEWS);
            String total_results = movieArrayReviews.getString(TOTAL_RESULTS_REVIEWS);

            JSONArray reviews = movieArrayReviews.getJSONArray("results");

            Vector<ContentValues> cVVectorReviews = new Vector<ContentValues>(reviews.length());

            for (int j = 0; j < reviews.length(); j++) {

                JSONObject reviewsInfo = reviews.getJSONObject(j);

                String id_reviews;
                String author;
                String content;
                String url;


                id_reviews = reviewsInfo.getString(REVIEW_ID);
                author = reviewsInfo.getString(AUTHOR);
                content = reviewsInfo.getString(CONTENT);
                url = reviewsInfo.getString(URL);

                ContentValues movieValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                movieValues.put(Reviews.PAGE, page);
                movieValues.put(Reviews.TOTAL_PAGE, total_page);
                movieValues.put(Reviews.TOTAL_RESULTS, total_results);
                movieValues.put(Reviews.ID_REVIEWS, id_reviews);
                movieValues.put(Reviews.AUTHOR, author);
                movieValues.put(Reviews.CONTENT, content);
                movieValues.put(Reviews.URL, url);
                movieValues.put(Reviews.MOVIE_ID, movie_id);

                cVVectorReviews.add(movieValues);

            }
            // add to database
            if (cVVectorReviews.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorReviews.size()];
                cVVectorReviews.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(Reviews.CONTENT_URI, cvArray);
            }

            page = movieArraySimilar.getString(PAGE);

            JSONArray similar = movieArraySimilar.getJSONArray("results");

            Vector<ContentValues> cVVectorSimilar = new Vector<ContentValues>(similar.length());

            for (int j = 0; j < similar.length(); j++) {

                String movie_id_new;
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


                JSONObject movieInfo = similar.getJSONObject(j);

                movie_id_new = movieInfo.getString(SimilarMovies.MOVIE_ID);
                orgLang = movieInfo.getString(SimilarMovies.ORIGINAL_LANGUAGE);
                orgTitle = movieInfo.getString(SimilarMovies.ORIGINAL_TITLE);
                overview = movieInfo.getString(SimilarMovies.OVERVIEW);
                relDate = movieInfo.getString(SimilarMovies.RELEASE_DATE);

                postURL = Uri.parse(POSTER_BASE_URL).buildUpon().
                        appendEncodedPath(movieInfo.getString(SimilarMovies.POSTER_PATH)).build().toString();
                popularity = movieInfo.getString(SimilarMovies.POPULARITY);
                votAvg = movieInfo.getString(SimilarMovies.VOTE_AVERAGE);
                vote_count = movieInfo.getString(SimilarMovies.VOTE_COUNT);
                title = movieInfo.getString(SimilarMovies.TITLE);
                adult = movieInfo.getString(SimilarMovies.ADULT);

                backdropURL = Uri.parse(BACKDROP_BASE_URL).buildUpon().
                        appendEncodedPath(movieInfo.getString(SimilarMovies.BACKDROP_PATH)).build().toString();


                ContentValues movieValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                movieValues.put(SimilarMovies.MOVIE_ID_OLD, movie_id);
                movieValues.put(MovieContract.SimilarMovies.PAGE, page);
                movieValues.put(MovieContract.SimilarMovies.POSTER_PATH, postURL);
                movieValues.put(MovieContract.SimilarMovies.ADULT, adult);
                movieValues.put(MovieContract.SimilarMovies.OVERVIEW, overview);
                movieValues.put(MovieContract.SimilarMovies.RELEASE_DATE, relDate);
                movieValues.put(MovieContract.SimilarMovies.MOVIE_ID, movie_id_new);
                movieValues.put(MovieContract.SimilarMovies.ORIGINAL_TITLE, orgTitle);
                movieValues.put(MovieContract.SimilarMovies.ORIGINAL_LANGUAGE, orgLang);
                movieValues.put(MovieContract.SimilarMovies.TITLE, title);
                movieValues.put(MovieContract.SimilarMovies.BACKDROP_PATH, backdropURL);
                movieValues.put(MovieContract.SimilarMovies.POPULARITY, popularity);
                movieValues.put(MovieContract.SimilarMovies.VOTE_COUNT, vote_count);
                movieValues.put(MovieContract.SimilarMovies.VOTE_AVERAGE, votAvg);
                cVVectorSimilar.add(movieValues);

            }
            // add to database
            if (cVVectorSimilar.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorSimilar.size()];
                cVVectorSimilar.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(SimilarMovies.CONTENT_URI, cvArray);
            }

            Vector<ContentValues> cVVectorGenres = new Vector<ContentValues>(movieArrayGenres.length());

            for (int i = 0; i < movieArrayGenres.length(); i++) {

                JSONObject gen = movieArrayGenres.getJSONObject(i);
                String genre_id = gen.getString(GENRE_ID);
                String name = gen.getString(NAME);

                ContentValues movieValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                movieValues.put(Genres.ID_GENRES, genre_id);
                movieValues.put(Genres.NAME, name);
                movieValues.put(Genres.MOVIE_ID, movie_id);
                cVVectorGenres.add(movieValues);

            }
            // add to database
            if (cVVectorGenres.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorGenres.size()];
                cVVectorGenres.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(Genres.CONTENT_URI, cvArray);
            }

            Vector<ContentValues> cVVectorCrew = new Vector<ContentValues>(movieArrayCrew.length());

            for (int i = 0; i < movieArrayCrew.length(); i++) {

                JSONObject obj = movieArrayCrew.getJSONObject(i);
                String credit_id = obj.getString(Crew.CREDIT_ID);
                String department = obj.getString(Crew.DEPARTMENT);
                String id = obj.getString(Crew.ID);
                String job = obj.getString(Crew.JOB);
                String name = obj.getString(Crew.NAME);
                String profile_path = obj.getString(Crew.PROFILE_PATH);

                ContentValues movieValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                movieValues.put(Crew.CREDIT_ID, credit_id);
                movieValues.put(Crew.DEPARTMENT, department);
                movieValues.put(Crew.ID, id);
                movieValues.put(Crew.JOB, job);
                movieValues.put(Crew.NAME, name);
                movieValues.put(Crew.PROFILE_PATH, profile_path);
                movieValues.put(Crew.MOVIE_ID, movie_id);
                cVVectorCrew.add(movieValues);

            }
            // add to database
            if (cVVectorCrew.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorCrew.size()];
                cVVectorCrew.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(Crew.CONTENT_URI, cvArray);
            }

            Vector<ContentValues> cVVectorCast = new Vector<ContentValues>(movieArrayCast.length());

            for (int i = 0; i < movieArrayCast.length(); i++) {

                JSONObject obj = movieArrayCast.getJSONObject(i);
                String cast_id = obj.getString(Cast.CAST_ID);
                String character = obj.getString(Cast.CHARACTER);
                String credit_id = obj.getString(Cast.CREDIT_ID);
                String id = obj.getString(Cast.ID);
                String name = obj.getString(Cast.NAME);
                String order = obj.getString("order");
                String profile_path = obj.getString(Cast.PROFILE_PATH);

                ContentValues movieValues = new ContentValues();

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.

                movieValues.put(Cast.CAST_ID, cast_id);
                movieValues.put(Cast.CHARACTER, character);
                movieValues.put(Cast.CREDIT_ID, credit_id);
                movieValues.put(Cast.ID, id);
                movieValues.put(Cast.NAME, name);
                movieValues.put(Cast.ORDER, order);
                movieValues.put(Cast.PROFILE_PATH, profile_path);
                movieValues.put(Cast.MOVIE_ID, movie_id);
                cVVectorCast.add(movieValues);

            }
            // add to database
            if (cVVectorCast.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVectorCast.size()];
                cVVectorCast.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(Cast.CONTENT_URI, cvArray);
            }

            ContentValues[] cvArray = new ContentValues[1];

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
            String budget;
            String homepage;
            String revenue;
            String runtime;
            String status;
            String tagline;

            budget = movieJson.getString(MovieDetails.BUDGET);
            homepage = movieJson.getString(MovieDetails.HOMEPAGE);
            revenue = movieJson.getString(MovieDetails.REVENUE);
            runtime = movieJson.getString(MovieDetails.RUNTIME);
            status = movieJson.getString(MovieDetails.STATUS);
            tagline = movieJson.getString(MovieDetails.TAGLINE);
            movie_id = movieJson.getString(MovieDetails.MOVIE_ID);
            orgLang = movieJson.getString(MovieDetails.ORIGINAL_LANGUAGE);
            orgTitle = movieJson.getString(MovieDetails.ORIGINAL_TITLE);
            overview = movieJson.getString(MovieDetails.OVERVIEW);
            relDate = movieJson.getString(MovieDetails.RELEASE_DATE);

            postURL = Uri.parse(POSTER_BASE_URL).buildUpon().
                    appendEncodedPath(movieJson.getString(MovieDetails.POSTER_PATH)).build().toString();
            popularity = movieJson.getString(MovieDetails.POPULARITY);
            votAvg = movieJson.getString(MovieDetails.VOTE_AVERAGE);
            vote_count = movieJson.getString(MovieDetails.VOTE_COUNT);
            title = movieJson.getString(MovieDetails.TITLE);
            adult = movieJson.getString(MovieDetails.ADULT);

            backdropURL = Uri.parse(BACKDROP_BASE_URL).buildUpon().
                    appendEncodedPath(movieJson.getString(MovieDetails.BACKDROP_PATH)).build().toString();


            ContentValues movieValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.

            movieValues.put(MovieDetails.BUDGET, budget);
            movieValues.put(MovieDetails.HOMEPAGE, homepage);
            movieValues.put(MovieDetails.REVENUE, revenue);
            movieValues.put(MovieDetails.RUNTIME, runtime);
            movieValues.put(MovieDetails.STATUS, status);
            movieValues.put(MovieDetails.TAGLINE, tagline);
            movieValues.put(MovieDetails.POSTER_PATH, postURL);
            movieValues.put(MovieDetails.ADULT, adult);
            movieValues.put(MovieDetails.OVERVIEW, overview);
            movieValues.put(MovieDetails.RELEASE_DATE, relDate);
            movieValues.put(MovieDetails.MOVIE_ID, movie_id);
            movieValues.put(MovieDetails.ORIGINAL_TITLE, orgTitle);
            movieValues.put(MovieDetails.ORIGINAL_LANGUAGE, orgLang);
            movieValues.put(MovieDetails.TITLE, title);
            movieValues.put(MovieDetails.BACKDROP_PATH, backdropURL);
            movieValues.put(MovieDetails.POPULARITY, popularity);
            movieValues.put(MovieDetails.VOTE_COUNT, vote_count);
            movieValues.put(MovieDetails.VOTE_AVERAGE, votAvg);
            cvArray[0] = movieValues;
            mContext.getContentResolver().bulkInsert(MovieContract.MovieDetails.CONTENT_URI, cvArray);

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
        String movieJsonStr;

        try {
            // Construct the URL for the movieAPI query
            // Possible parameters are avaiable at OWM's forecast API page, at
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/movie/";
            final String APPID_PARAM = "api_key";
            final String DATA = "append_to_response";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .appendQueryParameter(DATA, "videos,credits,similar,reviews")
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
            movieJsonStr = buffer.toString();
            getMovieDataFromJson(movieJsonStr);
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

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        response.onFinish(true);
    }
}