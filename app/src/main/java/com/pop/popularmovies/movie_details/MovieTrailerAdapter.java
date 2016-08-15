package com.pop.popularmovies.movie_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pop.popularmovies.R;
import com.pop.popularmovies.util.MovieTrailer;

import java.util.ArrayList;

/**
 * Created by Pop H2 on 8/14/2016.
 * Pop Inc
 * Lagos Nigeria
 */
public class MovieTrailerAdapter extends ArrayAdapter<MovieTrailer> {

    private ArrayList<MovieTrailer> mItems;
    private final LayoutInflater mInflater;
    private final int mLayoutResourceID, mImageResourceID, mTextTitleResourceID, mTextSourceResourceID;

    public MovieTrailerAdapter(Context context, int layoutResourceID, int imageResourceID, int textTitleResourceID, int textSourceResourceID, ArrayList<MovieTrailer> itemList) {

        super(context, layoutResourceID, textTitleResourceID);

        mInflater = LayoutInflater.from(context);
        mLayoutResourceID = layoutResourceID;
        mImageResourceID = imageResourceID;
        mTextTitleResourceID = textTitleResourceID;
        mTextSourceResourceID = textSourceResourceID;
        mItems = itemList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;

        //ImageView picture;
        //TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.movie_trailer_list_item, viewGroup, false);
            v.setTag(R.id.trailer_image_view, v.findViewById(R.id.trailer_image_view));
            v.setTag(R.id.trailer_text_textview, v.findViewById(R.id.trailer_text_textview));
            v.setTag(R.id.trailer_source_textview, v.findViewById(R.id.trailer_source_textview));
        }

        MovieTrailer movieTrailer = getItem(i);

        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.titleTextView.setText(movieTrailer.getName());
        viewHolder.sourceTextView.setText(movieTrailer.getSite());

        return v;
    }

    static class ViewHolder {
        final TextView titleTextView;
        final TextView sourceTextView;
        final ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.trailer_image_view);
            titleTextView = (TextView) view.findViewById(R.id.trailer_text_textview);
            sourceTextView = (TextView) view.findViewById(R.id.trailer_source_textview);
        }
    }

}
