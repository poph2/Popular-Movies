package com.pop.popularmovies.util;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pop H2 on 7/6/2016.
 * Pop Inc
 * Lagos Nigeria
 */
public class APIGetter {

    private static final String api_key = "";

    private static final String LOG_TAG = APIGetter.class.getSimpleName();

    final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    final String level_1_account    = "account";

    final String PARAM_API_KEY      = "api_key";
    final String PARAM_SESSION_ID   = "session_id";
    final String PARAM_PAGE         = "page";
    final String PARAM_LANGUAGE     = "language";
    final String PARAM_SORT_BY      = "sort_by";
    final String PARAM_MEDIA_TYPE   = "media_type";
    final String PARAM_MEDIA_ID     = "media_id";
    final String PARAM_FAVORITE     = "favorite";

    private static String getJson(String page) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String moviesJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/" + page + "?";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, api_key)
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
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
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return moviesJsonStr;
    }

    public static String doAction(String sortOrder) {

        if(sortOrder.equalsIgnoreCase("popular")) {
            return getJson("popular");
        }
        else {
            return getJson("top_rated");
        }

    }


}
