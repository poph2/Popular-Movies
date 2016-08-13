package com.pop.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.pop.popularmovies.movie_details.DetailsActivity;
import com.pop.popularmovies.movie_details.DetailsActivityFragment;
import com.pop.popularmovies.util.CallBack;
import com.pop.popularmovies.util.MovieItem;

public class MainActivity extends AppCompatActivity implements CallBack {

    boolean mTwoPane;

    public final static String DETAIL_FRAGMENT_TAG = "DetailFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if(savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detail_container, new DetailsActivityFragment(), DETAIL_FRAGMENT_TAG)
                        .commit();
            }
        }
        else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        //MainFragment mainFragment =  ((MainFragment)getSupportFragmentManager()
        //        .findFragmentById(R.id.fragment_main));
        //mainFragment.setUseTodayLayout(!mTwoPane);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(MovieItem movieItem) {

        String jsonText = new Gson().toJson(movieItem);

        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

            Bundle args = new Bundle();
            args.putString("movie_item", jsonText);

            DetailsActivityFragment fragment = new DetailsActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        }
        else {

            Intent i = new Intent(this, DetailsActivity.class);

            i.putExtra("movie_item", jsonText);

            startActivity(i);
        }
    }
}
