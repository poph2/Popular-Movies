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

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsActivityFragment extends Fragment {

    private MovieItem mMovieItem;

    public MovieDetailsActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent i = getActivity().getIntent();
        String movieItemJSON = i.getStringExtra("movie_item");

        mMovieItem = new Gson().fromJson(movieItemJSON, MovieItem.class);

        View rootView = inflater.inflate(R.layout.movie_details, container, false);

        TextView detailsTextView = (TextView)rootView.findViewById(R.id.detailsTextView);
        detailsTextView.setText(mMovieItem.getTitle());

        TextView overviewTextView = (TextView)rootView.findViewById(R.id.detailsOverviewTextView);
        overviewTextView.setText(" -- " + mMovieItem.getOverview());

        TextView userRatingTextView = (TextView)rootView.findViewById(R.id.textView3);
        userRatingTextView.setText(Double.toString(mMovieItem.getVote_average()));

        ImageView detailsPosterImageView = (ImageView)rootView.findViewById(R.id.detailsPosterImageView);
        ImageView detailsBackDropImageView = (ImageView)rootView.findViewById(R.id.detailsBackDropImageView);

        String imgPosterUrl = "http://image.tmdb.org/t/p/w500" + mMovieItem.getPoster_path();
        Glide.with(getContext()).load(imgPosterUrl).into(detailsPosterImageView);

        String imgBackDropUrl = "http://image.tmdb.org/t/p/w500" + mMovieItem.getBackdrop_path();
        Glide.with(getContext()).load(imgBackDropUrl).into(detailsBackDropImageView);

        return rootView;
    }


}
