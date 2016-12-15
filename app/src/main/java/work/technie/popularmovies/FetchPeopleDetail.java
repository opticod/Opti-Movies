package work.technie.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import work.technie.popularmovies.data.MovieContract.People;

/**
 * Created by anupam on 16/12/16.
 */

public class FetchPeopleDetail extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchTVMovieTask.class.getSimpleName();

    private final Context mContext;
    private boolean DEBUG = true;

    public FetchPeopleDetail(Context context) {
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

        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

        try {
            JSONObject profileJson = new JSONObject(movieJsonStr);
            ContentValues[] cvArray = new ContentValues[1];

            String adult;
            String biography;
            String birthday;
            String deathday;
            String gender;
            String homepage;
            String id;
            String name;
            String place_of_birth;
            String popularity;
            String profile_path;

            adult = profileJson.getString(People.ADULT);
            biography = profileJson.getString(People.BIOGRAPHY);
            birthday = profileJson.getString(People.BIRTHDAY);
            deathday = profileJson.getString(People.DEATHDAY);
            gender = profileJson.getString(People.GENDER);
            homepage = profileJson.getString(People.HOMEPAGE);
            id = profileJson.getString(People.ID);
            name = profileJson.getString(People.NAME);
            place_of_birth = profileJson.getString(People.PLACE_OF_BIRTH);
            popularity = profileJson.getString(People.POPULARITY);

            profile_path = Uri.parse(POSTER_BASE_URL).buildUpon().
                    appendEncodedPath(profileJson.getString(People.PROFILE_PATH)).build().toString();

            ContentValues movieValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.

            movieValues.put(People.ADULT, adult);
            movieValues.put(People.BIOGRAPHY, biography);
            movieValues.put(People.BIRTHDAY, birthday);
            movieValues.put(People.DEATHDAY, deathday);
            movieValues.put(People.GENDER, gender);
            movieValues.put(People.HOMEPAGE, homepage);
            movieValues.put(People.ID, id);
            movieValues.put(People.NAME, name);
            movieValues.put(People.PLACE_OF_BIRTH, place_of_birth);
            movieValues.put(People.POPULARITY, popularity);
            movieValues.put(People.PROFILE_PATH, profile_path);
            cvArray[0] = movieValues;
            mContext.getContentResolver().bulkInsert(People.CONTENT_URI, cvArray);

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

        try {
            // Construct the URL for the movieAPI query
            // Possible parameters are avaiable at OWM's forecast API page, at
            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/person/";
            final String APPID_PARAM = "api_key";
            final String DATA = "append_to_response";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIE_DB_API_KEY)
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
}