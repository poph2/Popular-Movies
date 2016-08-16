package com.pop.popularmovies.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pop H2 on 7/6/2016.
 * Pop Inc
 * Lagos Nigeria
 */

public class MovieItem {
    private int id;

    private String title;
    private String poster_path;
    private String backdrop_path;

    private boolean adult;
    private String overview;
    private String release_date;
    //        "genre_ids":[18],
    private String original_title;
    private String original_language;
    private double popularity;
    private String vote_count;
    private boolean video;
    private double vote_average;

    private JSONObject jsonMovieObject;

    private static final String LOG_TAG = MovieItem.class.getSimpleName();

    private ArrayList<MovieTrailer> movieTrailers;
    private ArrayList<MovieReview> movieReviews;

    public MovieItem(JSONObject jsonMovieObject) {
        this();

        try {
            this.jsonMovieObject    = jsonMovieObject;

            this.id                 = jsonMovieObject.getInt("id");
            this.title              = jsonMovieObject.getString("title");
            this.poster_path        = jsonMovieObject.getString("poster_path");
            this.backdrop_path      = jsonMovieObject.getString("backdrop_path");

            this.adult              = jsonMovieObject.getBoolean("adult");
            this.overview           = jsonMovieObject.getString("overview");
            this.release_date       = jsonMovieObject.getString("release_date");
            this.original_title     = jsonMovieObject.getString("original_title");
            this.original_language  = jsonMovieObject.getString("original_language");
            this.popularity         = jsonMovieObject.getDouble("popularity");
            this.vote_count         = jsonMovieObject.getString("vote_count");
            this.video              = jsonMovieObject.getBoolean("video");
            this.vote_average       = jsonMovieObject.getDouble("vote_average");

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

        this.adult              = false;
        this.overview           = "";
        this.release_date       = "";
        this.original_title     = "";
        this.original_language  = "";
        this.popularity         = 0.0;
        this.vote_count         = "";
        this.video              = false;
        this.vote_average       = 0.0;

        this.movieTrailers      = new ArrayList<>();
        this.movieReviews       = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public JSONObject getJsonMovieObject() {
        return jsonMovieObject;
    }

    public void setJsonMovieObject(JSONObject jsonMovieObject) {
        this.jsonMovieObject = jsonMovieObject;
    }

    public static String getLogTag() {
        return LOG_TAG;
    }

    public ArrayList<MovieTrailer> getMovieTrailers() {
        return movieTrailers;
    }

    public void setMovieTrailers(ArrayList<MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    public ArrayList<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(ArrayList<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }
}