package com.pop.popularmovies.movie_details;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pop.popularmovies.R;
import com.pop.popularmovies.util.MovieItem;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

//    @BindView(R.id.movieDetailsTextView)        TextView movieDetailsTextView;
//    @BindView(R.id.detailsOverviewTextView)     TextView detailsOverviewTextView;
//    @BindView(R.id.userRatingTextView)          TextView userRatingTextView;
//    @BindView(R.id.releaseDateTextView)         TextView releaseDateTextView;
//    @BindView(R.id.detailsBackDropImageView)    ImageView detailsBackDropImageView;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent i = getActivity().getIntent();
        String movieItemJSON = i.getStringExtra("movie_item");

        MovieItem mMovieItem = new Gson().fromJson(movieItemJSON, MovieItem.class);

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

//        ButterKnife.bind(this, rootView);

        getActivity().setTitle(mMovieItem.getTitle());

        TextView movieDetailsTextView = (TextView)rootView.findViewById(R.id.movieDetailsTextView);
        movieDetailsTextView.setText(mMovieItem.getTitle());

        TextView detailsOverviewTextView = (TextView)rootView.findViewById(R.id.detailsOverviewTextView);
        detailsOverviewTextView.setText(mMovieItem.getOverview());

        TextView userRatingTextView = (TextView)rootView.findViewById(R.id.userRatingTextView);
        userRatingTextView.setText(Double.toString(mMovieItem.getVote_average()));

        TextView releaseDateTextView = (TextView)rootView.findViewById(R.id.releaseDateTextView);
        releaseDateTextView.setText(mMovieItem.getRelease_date());

        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            String imgBackDropUrl = "http://image.tmdb.org/t/p/w500" + mMovieItem.getBackdrop_path();

            ImageView detailsBackDropImageView = (ImageView)rootView.findViewById(R.id.detailsBackDropImageView);
            Glide.with(getContext()).load(imgBackDropUrl).into(detailsBackDropImageView);
        }
        else {
            Toast.makeText(getContext(), "No internet connection. Could not load Image.", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }
}
