package com.pop.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pop.popularmovies.util.MovieItem;

import java.util.ArrayList;

/**
 * Created by Pop H2 on 7/6/2016.
 * Pop Inc
 * Lagos Nigeria
 */
class MovieGridAdapter extends ArrayAdapter<MovieItem> {
    private ArrayList<MovieItem> mItems;
    private final LayoutInflater mInflater;
    private final int mLayoutResourceID, mImageResourceID, mTextResourceID;

    private final Object mLock = new Object();

    public MovieGridAdapter(Context context, int layoutResourceID, int imageResourceID, int textResourceID, ArrayList<MovieItem> itemList) {
        super(context, layoutResourceID, textResourceID, itemList);

        mInflater = LayoutInflater.from(context);
        mLayoutResourceID = layoutResourceID;
        mImageResourceID = imageResourceID;
        mTextResourceID = textResourceID;
        mItems = itemList;
    }

    @Override
    public void clear() {
        super.clear();
        mItems.clear();
    }

    public void addAll(ArrayList<MovieItem> itemList) {
        super.addAll();
        mItems =  itemList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;

        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.movie_grid_item, viewGroup, false);
            v.setTag(R.id.movie_grid_item_imageview, v.findViewById(R.id.movie_grid_item_imageview));
            v.setTag(R.id.movie_grid_item_textview, v.findViewById(R.id.movie_grid_item_textview));
        }

        MovieItem movieItem = getItem(i);

        String imgUrl = "http://image.tmdb.org/t/p/w500" + movieItem.getPoster_path();

        ViewHolder viewHolder = new ViewHolder(v);
        Glide.with(getContext()).load(imgUrl).into(viewHolder.imageView);
        viewHolder.titleTextView.setText(movieItem.getTitle());

        return v;
    }

    static class ViewHolder {
        final TextView titleTextView;
        final ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.movie_grid_item_imageview);
            titleTextView = (TextView) view.findViewById(R.id.movie_grid_item_textview);
        }
    }
}
