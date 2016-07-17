package com.pop.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        /*if (savedInstanceState == null) {

            Intent i = getIntent();
            String movieItemJSON = i.getStringExtra("movie_item");

            Bundle bundle = new Bundle();
            bundle.putString("movie_item", movieItemJSON );
            MovieDetailsActivityFragment mdaFragment = new MovieDetailsActivityFragment();
            mdaFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.movie_container, mdaFragment)
                    .commit();
        }*/

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

}
