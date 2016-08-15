package com.pop.popularmovies.movie_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pop.popularmovies.R;
import com.pop.popularmovies.util.MovieReview;

import java.util.ArrayList;

/**
 * Created by Pop H2 on 8/14/2016.
 * Pop Inc
 * Lagos Nigeria
 */
public class MovieReviewAdapter extends ArrayAdapter<MovieReview> {

    private ArrayList<MovieReview> mItems;
    private final LayoutInflater mInflater;
    private final int mLayoutResourceID, mImageResourceID, mTextAuthorResourceID, mTextContentResourceID;

    public MovieReviewAdapter(Context context, int layoutResourceID, int imageResourceID, int textAuthorResourceID, int textContentResourceID, ArrayList<MovieReview> itemList) {

        super(context, layoutResourceID, textAuthorResourceID);

        mInflater = LayoutInflater.from(context);
        mLayoutResourceID = layoutResourceID;
        mImageResourceID = imageResourceID;
        mTextAuthorResourceID = textAuthorResourceID;
        mTextContentResourceID = textContentResourceID;
        mItems = itemList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;

        //ImageView picture;
        //TextView name;

        if (v == null) {
            v = mInflater.inflate(mLayoutResourceID, viewGroup, false);
            v.setTag(mImageResourceID, v.findViewById(mImageResourceID));
            v.setTag(mTextAuthorResourceID, v.findViewById(mTextAuthorResourceID));
            v.setTag(mTextContentResourceID, v.findViewById(mTextContentResourceID));
        }

        MovieReview movieReview = getItem(i);

        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.authorTextView.setText(movieReview.getAuthor());
        viewHolder.contentTextView.setText(movieReview.getContent());

        return v;
    }

    static class ViewHolder {
        final TextView authorTextView;
        final TextView contentTextView;
        final ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.review_image_view);
            authorTextView = (TextView) view.findViewById(R.id.review_author_textview);
            contentTextView = (TextView) view.findViewById(R.id.review_content_textview);
        }
    }

}
