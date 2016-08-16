package com.pop.popularmovies.util;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Pop H2 on 7/18/2016.
 * Pop Inc
 * Lagos Nigeria
 */
public class MoviePreference {

    public static String PREF_NAME = "MOVIE_PREF_FILE";

    private SharedPreferences moviePref;

    public MoviePreference(SharedPreferences postPref) {
        this.moviePref = postPref;
    }

    public ArrayList<MovieItem> getMovieItems() {
        String jsonStr = moviePref.getString("movie_arr", "");

        ArrayList<MovieItem> movieArrayList;

        if(jsonStr.equalsIgnoreCase("")) {
            movieArrayList = new ArrayList<MovieItem>();
        }
        else {
            Type listType = new TypeToken<ArrayList<MovieItem>>() {}.getType();

            movieArrayList = new Gson().fromJson(jsonStr, listType);

        }
        return movieArrayList;
    }

    public void saveMovieItems(ArrayList<MovieItem> movieArrayList) {
        Gson gson = new Gson();

        String jsonStr = gson.toJson(movieArrayList);

        SharedPreferences.Editor editor = moviePref.edit();
        editor.putString("movie_arr", jsonStr);
        editor.apply();
    }

    public void addMovieItem(MovieItem movieItem) {
        ArrayList<MovieItem> postArrayList = getMovieItems();
        postArrayList.add(0, movieItem);
        saveMovieItems(postArrayList);
    }

    public void deleteMovieItem(MovieItem movieItem) {
        ArrayList<MovieItem> movieArrayList = getMovieItems();

        for(int i = 0; i < movieArrayList.size(); i++) {
            if(movieItem.getId() == movieArrayList.get(i).getId()) {
                movieArrayList.remove(i);
                saveMovieItems(movieArrayList);
                return;
            }
        }
    }

    public boolean searchForMovieItem(MovieItem movieItem) {
        ArrayList<MovieItem> movieArrayList = getMovieItems();

        for(int i = 0; i < movieArrayList.size(); i++) {
            if(movieItem.getId() == movieArrayList.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

}
