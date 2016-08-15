package com.pop.popularmovies.movie_details;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pop.popularmovies.R;
import com.pop.popularmovies.util.APIGetter;
import com.pop.popularmovies.util.MovieItem;
import com.pop.popularmovies.util.MovieReview;
import com.pop.popularmovies.util.MovieTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
@SuppressWarnings("unused")
public class DetailsFragment extends Fragment {

    boolean fav = false;

    @BindView(R.id.movieDetailsTextView)        TextView movieDetailsTextView;
    @BindView(R.id.detailsOverviewTextView)     TextView detailsOverviewTextView;
    @BindView(R.id.userRatingTextView)          TextView userRatingTextView;
    @BindView(R.id.releaseDateTextView)         TextView releaseDateTextView;
    @BindView(R.id.detailsBackDropImageView)    ImageView detailsBackDropImageView;
    @BindView(R.id.detailsFavoriteImageButton)  ImageView detailsFavoriteImageButton;

    @BindView(R.id.trailerListView)             ListView trailerListView;
    @BindView(R.id.reviewListView)              ListView reviewListView;

    MovieItem mMovieItem;
    ArrayList<MovieTrailer> mMovieTrailerList;
    ArrayList<MovieReview> mMovieReviewList;

    MovieTrailerAdapter mMovieTrailerAdapter;
    MovieReviewAdapter mMovieReviewAdapter;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String movieItemJSON = null;

        mMovieTrailerList = new ArrayList<>();
        mMovieReviewList = new ArrayList<>();

        mMovieTrailerAdapter = new MovieTrailerAdapter(
                getActivity(),
                R.layout.movie_trailer_list_item,
                R.id.trailer_image_view,
                R.id.trailer_text_textview,
                R.id.trailer_source_textview,
                mMovieTrailerList);

        mMovieReviewAdapter = new MovieReviewAdapter(
                getActivity(),
                R.layout.movie_review_list_item,
                R.id.review_image_view,
                R.id.review_author_textview,
                R.id.review_content_textview,
                mMovieReviewList);

        Bundle arguments = getArguments();
        if (arguments != null) {
            movieItemJSON = arguments.getString("movie_item");
        }
        if(movieItemJSON != null) {

            mMovieItem = new Gson().fromJson(movieItemJSON, MovieItem.class);

            View rootView = inflater.inflate(R.layout.fragment_details, container, false);
            ButterKnife.bind(this, rootView);

            //ListView listView = (ListView) rootView.findViewById(R.id.trailerListView);
            trailerListView.setAdapter(mMovieTrailerAdapter);
            trailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    MovieTrailer movieTrailer = mMovieTrailerAdapter.getItem(i);
                    watchYoutubeVideo(movieTrailer.getKey());
                }
            });


            reviewListView.setAdapter(mMovieReviewAdapter);
            //reviewListView.setOnItemClickListener(this);



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

            MovieTrailerAsyncTask movieTrailerAsyncTask = new MovieTrailerAsyncTask();
            movieTrailerAsyncTask.execute();

            MovieReviewAsyncTask movieReviewAsyncTask = new MovieReviewAsyncTask();
            movieReviewAsyncTask.execute();

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

    public void watchYoutubeVideo(String videoKey){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoKey));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoKey));
            startActivity(intent);
        }
    }

    class MovieTrailerAsyncTask extends AsyncTask<Void, Void, ArrayList<MovieTrailer>> {

        private final String LOG_TAG = MovieTrailerAsyncTask.class.getSimpleName();

        @Override
        protected ArrayList<MovieTrailer> doInBackground(Void... params) {

            String jsonStr = APIGetter.getTrailer(Integer.toString(mMovieItem.getId()));

            ArrayList<MovieTrailer> movieTrailerList = new ArrayList<>();

            if(jsonStr == null) {
                return null;
            }

            try {
                movieTrailerList = getMovieTrailersFromJson(jsonStr);
            }
            catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return movieTrailerList;
        }

        public ArrayList<MovieTrailer> getMovieTrailersFromJson(String jsonString) throws JSONException {
            ArrayList<MovieTrailer> movieTrailerList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray resultJsonArray = jsonObject.getJSONArray("results");

            for(int i = 0; i < resultJsonArray.length(); i++) {
                JSONObject movieTrailerJsonObject = resultJsonArray.getJSONObject(i);
                movieTrailerList.add(new MovieTrailer(movieTrailerJsonObject));
            }

            return movieTrailerList;

        }

        @Override
        protected void onPostExecute(ArrayList<MovieTrailer> movieTrailers) {
            if (movieTrailers != null) {
                mMovieTrailerAdapter.clear();
                for(MovieTrailer movieTrailer : movieTrailers) {
                    mMovieTrailerAdapter.add(movieTrailer);
                    setListViewHeightBasedOnChildren(trailerListView);
                }
            }
        }

        public void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }

    class MovieReviewAsyncTask extends AsyncTask<Void, Void, ArrayList<MovieReview>> {

        private final String LOG_TAG = MovieReviewAsyncTask.class.getSimpleName();

        @Override
        protected ArrayList<MovieReview> doInBackground(Void... params) {

            String jsonStr = APIGetter.getReview(Integer.toString(mMovieItem.getId()));

            ArrayList<MovieReview> movieReviewList = new ArrayList<>();

            if(jsonStr == null) {
                return null;
            }

            try {
                movieReviewList = getMovieReviewsFromJson(jsonStr);
            }
            catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return movieReviewList;
        }

        public ArrayList<MovieReview> getMovieReviewsFromJson(String jsonString) throws JSONException {
            ArrayList<MovieReview> movieReviewList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray resultJsonArray = jsonObject.getJSONArray("results");

            for(int i = 0; i < resultJsonArray.length(); i++) {
                JSONObject movieReviewJsonObject = resultJsonArray.getJSONObject(i);
                movieReviewList.add(new MovieReview(movieReviewJsonObject));
            }

            return movieReviewList;

        }

        @Override
        protected void onPostExecute(ArrayList<MovieReview> movieReviews) {
            if (movieReviews != null) {
                mMovieReviewAdapter.clear();
                for(MovieReview movieReview : movieReviews) {
                    mMovieReviewAdapter.add(movieReview);
                    setListViewHeightBasedOnChildren(reviewListView);
                }
            }
        }

        public void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }
}
