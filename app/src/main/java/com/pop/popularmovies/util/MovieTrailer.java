package com.pop.popularmovies.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pop H2 on 8/13/2016.
 * Pop Inc
 * Lagos Nigeria
 */
public class MovieTrailer {

    private String id;

    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    private static final String LOG_TAG = MovieTrailer.class.getSimpleName();

    public MovieTrailer(JSONObject jsonObject) {

        this();

        try {

            this.id         = jsonObject.getString("id");
            this.iso_639_1  = jsonObject.getString("iso_639_1");
            this.iso_3166_1 = jsonObject.getString("iso_3166_1");
            this.key        = jsonObject.getString("key");
            this.name       = jsonObject.getString("name");
            this.site       = jsonObject.getString("site");
            this.size       = jsonObject.getInt("size");
            this.type       = jsonObject.getString("type");
        }
        catch(JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public MovieTrailer() {

        this.id         = "";
        this.iso_639_1  = "";
        this.iso_3166_1 = "";
        this.key        = "";
        this.name       = "";
        this.site       = "";
        this.size       = 0;
        this.type       = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getLogTag() {
        return LOG_TAG;
    }


    /*

    {
        "id":297761,
        "results":
            [
                {
                    "id":"57878d7592514137c9005476",
                    "iso_639_1":"en",
                    "iso_3166_1":"US",
                    "key":"JsbG97hO6lA",
                    "name":"Harley Quinn Therapy",
                    "site":"YouTube",
                    "size":1080,
                    "type":"Featurette"
                },
                {
                    "id":"5791d255c3a36874640010bb",
                    "iso_639_1":"en",
                    "iso_3166_1":"US",
                    "key":"NW0lOo9_8Bw",
                    "name":"Team Suicide Squad",
                    "site":"YouTube",
                    "size":1080,
                    "type":"Featurette"
                },
                {"id":"57950ed1c3a3681f31003941","iso_639_1":"en","iso_3166_1":"US","key":"PLLQK9la6Go","name":"Comic Con First Look","site":"YouTube","size":1080,"type":"Trailer"},
                {"id":"57975b61c3a36865ae001ece","iso_639_1":"en","iso_3166_1":"US","key":"CmRih_VtVAs","name":"Official Trailer 1","site":"YouTube","size":1080,"type":"Trailer"},
                {"id":"57950e679251412ba3000cdd","iso_639_1":"en","iso_3166_1":"US","key":"5AwUdTIbA8I","name":"Blitz Trailer","site":"YouTube","size":1080,"type":"Trailer"}
            ]
        }
*/
}
