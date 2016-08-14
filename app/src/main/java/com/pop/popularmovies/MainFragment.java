package com.pop.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pop.popularmovies.util.APIGetter;
import com.pop.popularmovies.util.CallBack;
import com.pop.popularmovies.util.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final String LOAD_MOVIES_INTENT = "com.pop.popularmovies.action.RELOAD_DATA_INTENT";

    private IntentFilter filter;
    private BroadcastReceiver broadcastReceiver;

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

        filter = new IntentFilter(LOAD_MOVIES_INTENT);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                if(mMovieAdapter.getmItems().size() == 0) {     //Only load on network change if no movies have been loaded initially
                    updateMovies();
                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver, filter);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateMovies();
    }

    private void updateMovies() {
        //Toast.makeText(getContext(), "HERE1111", Toast.LENGTH_LONG).show();
        MovieAsyncTask movieAsyncTask = new MovieAsyncTask();
        movieAsyncTask.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MovieItem movieItem = mMovieAdapter.getItem(position);
        ((CallBack)getActivity()).onItemSelected(movieItem);

//            Intent i = new Intent(getContext(), DetailsActivity.class);
//            MovieItem movieItem = mMovieAdapter.getItem(position);
//
//            String jsonText = new Gson().toJson(movieItem);
//
//            i.putExtra("movie_item", jsonText);
//
//            startActivity(i);
    }

    class MovieAsyncTask extends AsyncTask<Void, Void, ArrayList<MovieItem>> {

        private final String LOG_TAG = MovieAsyncTask.class.getSimpleName();

        @Override
        protected ArrayList<MovieItem> doInBackground(Void... params) {

            String sortOrder = getSortOrder();

            String movieJson = APIGetter.getMovies(sortOrder);

            ArrayList<MovieItem> movieItemList = new ArrayList<>();

            if(movieJson == null) {
                return null;
            }

            try {
                movieItemList = getMovieItemsFromJson(movieJson);
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

    private String getSortOrder() {

        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString("sort_by", "popular");

    }

}