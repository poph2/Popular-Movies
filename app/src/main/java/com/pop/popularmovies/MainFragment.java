package com.pop.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.pop.popularmovies.util.MovieItem;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private MovieGridAdapter mMovieAdapter;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<MovieItem> movieItemList = new ArrayList<>();

        movieItemList.add(new MovieItem("Red",          R.drawable.red));
        movieItemList.add(new MovieItem("Aquamarine",   R.drawable.aquamarine));
        movieItemList.add(new MovieItem("Bittersweet",  R.drawable.bittersweet));
        movieItemList.add(new MovieItem("Deep Lilac",   R.drawable.deep_lilac));
        movieItemList.add(new MovieItem("Gold",         R.drawable.gold));
        movieItemList.add(new MovieItem("Azure",        R.drawable.azure));

        mMovieAdapter = new MovieGridAdapter(
                getActivity(),
                R.layout.movie_grid_item,
                R.id.movie_grid_item_imageview,
                R.id.movie_grid_item_textview,
                movieItemList);



        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_gridview);
        gridView.setAdapter(mMovieAdapter);


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //updateMovies();
    }

    public void updateMovies() {
        MovieAsyncTask movieAsyncTask = new MovieAsyncTask();
        movieAsyncTask.execute();
    }

    class MovieAsyncTask extends AsyncTask<Void, Void, ArrayList<MovieItem>> {

        @Override
        protected ArrayList<MovieItem> doInBackground(Void... params) {

            ArrayList<MovieItem> movieItemList = new ArrayList<>();

            movieItemList.add(new MovieItem("Red",          R.drawable.red));
            movieItemList.add(new MovieItem("Aquamarine",   R.drawable.aquamarine));
            movieItemList.add(new MovieItem("Bittersweet",  R.drawable.bittersweet));
            movieItemList.add(new MovieItem("Deep Lilac",   R.drawable.deep_lilac));
            movieItemList.add(new MovieItem("Gold",         R.drawable.gold));
            movieItemList.add(new MovieItem("Azure",        R.drawable.azure));



            return movieItemList;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> movieItemList) {
            if (movieItemList != null) {
                mMovieAdapter.clear();
                mMovieAdapter.addAll(movieItemList);
            }
            Toast.makeText(getActivity(), "RED", Toast.LENGTH_SHORT).show();
        }
    }


}
