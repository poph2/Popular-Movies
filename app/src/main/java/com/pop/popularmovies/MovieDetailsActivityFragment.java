package com.pop.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pop.popularmovies.util.MovieItem;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsActivityFragment extends Fragment {

    private MovieItem mMovieItem;

    public MovieDetailsActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String movieItemJSON = this.getArguments().getString("movie_item");

        mMovieItem = new Gson().fromJson(movieItemJSON, MovieItem.class);



        View rootView = inflater.inflate(R.layout.movie_details, container, false);

        TextView movieDetailsTextView = (TextView)rootView.findViewById(R.id.movieDetailsTextView);
        movieDetailsTextView.setText(mMovieItem.getTitle());

        ImageView movieDetailsPosterImageView = (ImageView)rootView.findViewById(R.id.movieDetailsPosterImageView);
        ImageView movieDetailsBackDropImageView = (ImageView)rootView.findViewById(R.id.movieDetailsBackDropImageView);

        String imgPosterUrl = "http://image.tmdb.org/t/p/w500" + mMovieItem.getPoster_path();
        Glide.with(getContext()).load(imgPosterUrl).into(movieDetailsPosterImageView);

        String imgBackDropUrl = "http://image.tmdb.org/t/p/w500" + mMovieItem.getBackdrop_path();
        Glide.with(getContext()).load(imgBackDropUrl).into(movieDetailsBackDropImageView);

        return rootView;
    }


}
