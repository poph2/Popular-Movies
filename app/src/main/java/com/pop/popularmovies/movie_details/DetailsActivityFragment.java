package com.pop.popularmovies.movie_details;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
public class DetailsActivityFragment extends Fragment {

    private MovieItem mMovieItem;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent i = getActivity().getIntent();
        String movieItemJSON = i.getStringExtra("movie_item");

        mMovieItem = new Gson().fromJson(movieItemJSON, MovieItem.class);

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        TextView movieDetailsTextView = (TextView)rootView.findViewById(R.id.detailsTextView);
        movieDetailsTextView.setText(mMovieItem.getTitle());

        TextView overviewTextView = (TextView)rootView.findViewById(R.id.detailsOverviewTextView);
        overviewTextView.setText(mMovieItem.getOverview());

        TextView userRatingTextView = (TextView)rootView.findViewById(R.id.textView3);
        userRatingTextView.setText(Double.toString(mMovieItem.getVote_average()));

        ImageView movieDetailsPosterImageView = (ImageView)rootView.findViewById(R.id.detailsPosterImageView);
        ImageView movieDetailsBackDropImageView = (ImageView)rootView.findViewById(R.id.detailsBackDropImageView);

        String imgPosterUrl = "http://image.tmdb.org/t/p/w500" + mMovieItem.getPoster_path();
        Glide.with(getContext()).load(imgPosterUrl).into(movieDetailsPosterImageView);

        String imgBackDropUrl = "http://image.tmdb.org/t/p/w500" + mMovieItem.getBackdrop_path();
        Glide.with(getContext()).load(imgBackDropUrl).into(movieDetailsBackDropImageView);

        return rootView;
    }
}
