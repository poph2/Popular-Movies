package com.pop.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.pop.popularmovies.movie_details.DetailsActivity;
import com.pop.popularmovies.util.APIGetter;
import com.pop.popularmovies.util.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainFragment extends Fragment implements AdapterView.OnItemClickListener{

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

        //movieItemList.add(new MovieItem("Red",          R.drawable.red));
        //movieItemList.add(new MovieItem("Aquamarine",   R.drawable.aquamarine));
        //movieItemList.add(new MovieItem("Bittersweet",  R.drawable.bittersweet));
        //movieItemList.add(new MovieItem("Deep Lilac",   R.drawable.deep_lilac));
        //movieItemList.add(new MovieItem("Gold",         R.drawable.gold));
        //movieItemList.add(new MovieItem("Azure",        R.drawable.azure));

        mMovieAdapter = new MovieGridAdapter(
                getActivity(),
                R.layout.movie_grid_item,
                R.id.movie_grid_item_imageview,
                R.id.movie_grid_item_textview,
                movieItemList);



        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_gridview);
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(this);

        updateMovies();

        // Inflate the layout for this fragment
        return rootView;
    }

    /*@Override
    public void onStart() {
        super.onStart();
        //updateMovies();
    }*/

    private void updateMovies() {
        //Toast.makeText(getContext(), "HERE1111", Toast.LENGTH_LONG).show();
        MovieAsyncTask movieAsyncTask = new MovieAsyncTask();
        movieAsyncTask.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        //Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();

        Intent i = new Intent(getContext(), DetailsActivity.class);
        MovieItem movieItem = mMovieAdapter.getItem(position);

        String jsonText = new Gson().toJson(movieItem);

        i.putExtra("movie_item", jsonText);

        //Toast.makeText(getContext(), jsonText, Toast.LENGTH_LONG).show();

        startActivity(i);
    }

    class MovieAsyncTask extends AsyncTask<Void, Void, ArrayList<MovieItem>> {

        private final String LOG_TAG = MovieAsyncTask.class.getSimpleName();

        @Override
        protected ArrayList<MovieItem> doInBackground(Void... params) {

            String movieJson = APIGetter.doAction2();

            ArrayList<MovieItem> movieItemList = new ArrayList<>();

            //movieItemList.add(new MovieItem("Red",          R.drawable.red));
            //movieItemList.add(new MovieItem("Aquamarine",   R.drawable.aquamarine));
            //movieItemList.add(new MovieItem("Bittersweet",  R.drawable.bittersweet));
            //movieItemList.add(new MovieItem("Deep Lilac",   R.drawable.deep_lilac));
            //movieItemList.add(new MovieItem("Gold",         R.drawable.gold));
            //movieItemList.add(new MovieItem("Azure",        R.drawable.azure));

            try {
                movieItemList = getMovieItemsFromJson(movieJson);
                //Toast.makeText(getActivity(), "HERE", Toast.LENGTH_LONG).show();
            }
            catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return movieItemList;
        }

        public ArrayList<MovieItem> getMovieItemsFromJson(String jsonString) throws JSONException {
            ArrayList<MovieItem> movieItemList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray resultJsonArray = jsonObject.getJSONArray("results");

            for(int i = 0; i < resultJsonArray.length(); i++) {
                JSONObject movieJsonObject = resultJsonArray.getJSONObject(i);
                movieItemList.add(new MovieItem(movieJsonObject));
            }

            return movieItemList;

        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> movieItemList) {
            if (movieItemList != null) {
                mMovieAdapter.clear();
                for(MovieItem movieItem : movieItemList) {
                    mMovieAdapter.add(movieItem);
                }
            }
        }
    }


}