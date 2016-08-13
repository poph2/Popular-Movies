package com.pop.popularmovies.movie_details;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
@SuppressWarnings("unused")
public class DetailsActivityFragment extends Fragment {

    boolean fav = false;

    @BindView(R.id.movieDetailsTextView)        TextView movieDetailsTextView;
    @BindView(R.id.detailsOverviewTextView)     TextView detailsOverviewTextView;
    @BindView(R.id.userRatingTextView)          TextView userRatingTextView;
    @BindView(R.id.releaseDateTextView)         TextView releaseDateTextView;
    @BindView(R.id.detailsBackDropImageView)    ImageView detailsBackDropImageView;
    @BindView(R.id.detailsFavoriteImageButton)    ImageView detailsFavoriteImageButton;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //if()

//        Intent i = getActivity().getIntent();
//        String movieItemJSON = i.getStringExtra("movie_item");

        String movieItemJSON = null;

        Bundle arguments = getArguments();
        if (arguments != null) {
            movieItemJSON = arguments.getString("movie_item");
        }
        if(movieItemJSON != null) {

            MovieItem mMovieItem = new Gson().fromJson(movieItemJSON, MovieItem.class);

            View rootView = inflater.inflate(R.layout.fragment_details, container, false);

            ButterKnife.bind(this, rootView);

            getActivity().setTitle(mMovieItem.getTitle());

            //TextView movieDetailsTextView = (TextView)rootView.findViewById(R.id.movieDetailsTextView);
            movieDetailsTextView.setText(mMovieItem.getTitle());

            //TextView detailsOverviewTextView = (TextView)rootView.findViewById(R.id.detailsOverviewTextView);
            detailsOverviewTextView.setText(mMovieItem.getOverview());

            //TextView userRatingTextView = (TextView)rootView.findViewById(R.id.userRatingTextView);
            userRatingTextView.setText(Double.toString(mMovieItem.getVote_average()));

            //TextView releaseDateTextView = (TextView)rootView.findViewById(R.id.releaseDateTextView);
            releaseDateTextView.setText(mMovieItem.getRelease_date());

            bindButtonAction();

            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isConnected) {
                String imgBackDropUrl = "http://image.tmdb.org/t/p/w500" + mMovieItem.getBackdrop_path();

                //ImageView detailsBackDropImageView = (ImageView)rootView.findViewById(R.id.detailsBackDropImageView);
                Glide.with(getContext()).load(imgBackDropUrl).into(detailsBackDropImageView);
            } else {
                Toast.makeText(getContext(), "No internet connection. Could not load Image.", Toast.LENGTH_SHORT).show();
            }

            return rootView;
        }

        return null;
    }

    public void bindButtonAction() {
        detailsFavoriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteButtonClicked();
            }
        });
    }

    public void favoriteButtonClicked() {
        if(fav) {
            detailsFavoriteImageButton.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        }
        else {
            detailsFavoriteImageButton.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
        }
        fav = !fav;
    }
}
