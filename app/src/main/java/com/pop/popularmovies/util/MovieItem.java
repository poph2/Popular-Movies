package com.pop.popularmovies.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pop H2 on 7/6/2016.
 * Pop Inc
 * Lagos Nigeria
 */

public class MovieItem {
    public int id;
    public String title;
    public String poster_path;
    public String backdrop_path;
    public JSONObject jsonMovieObject;

    public static final String LOG_TAG = MovieItem.class.getSimpleName();


    public MovieItem(JSONObject jsonMovieObject) {
        this();

        try {
            this.jsonMovieObject    = jsonMovieObject;

            this.id                 = jsonMovieObject.getInt("id");
            this.title              = jsonMovieObject.getString("title");
            this.poster_path        = jsonMovieObject.getString("poster_path");
            this.backdrop_path      = jsonMovieObject.getString("backdrop_path");
        }
        catch(JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public MovieItem() {
        this.jsonMovieObject = null;

        this.id             = 0;
        this.title          = "";
        this.poster_path    = "";
        this.backdrop_path  = "";
    }
}