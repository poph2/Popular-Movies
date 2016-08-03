package com.pop.popularmovies.movie_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pop.popularmovies.R;
import com.pop.popularmovies.util.MovieItem;

import butterknife.BindView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    @BindView(R.id.movieDetailsTextView)        TextView movieDetailsTextView;
    @BindView(R.id.detailsOverviewTextView)     TextView detailsOverviewTextView;
    @BindView(R.id.userRatingTextView)          TextView userRatingTextView;
    @BindView(R.id.releaseDateTextView)         TextView releaseDateTextView;
    @BindView(R.id.detailsBackDropImageView)    ImageView detailsBackDropImageView;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent i = getActivity().getIntent();
        String movieItemJSON = i.getStringExtra("movie_item");

        MovieItem mMovieItem = new Gson().fromJson(movieItemJSON, MovieItem.class);

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        getActivity().setTitle(mMovieItem.getTitle());

        movieDetailsTextView.setText(mMovieItem.getTitle());

        detailsOverviewTextView.setText(mMovieItem.getOverview());

        userRatingTextView.setText(Double.toString(mMovieItem.getVote_average()));

        releaseDateTextView.setText(mMovieItem.getRelease_date());

        String imgBackDropUrl = "http://image.tmdb.org/t/p/w500" + mMovieItem.getBackdrop_path();
        Glide.with(getContext()).load(imgBackDropUrl).into(detailsBackDropImageView);

        return rootView;
    }
}
