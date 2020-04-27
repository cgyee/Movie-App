package com.example.movieapp;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieView> {
    private JSONArray mData;

    public class MovieView extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton imageButton;

        public MovieView(LinearLayout layout) {
            super(layout);
        }
    }

    public  MovieAdapter(JSONArray data) { mData = data;}
    public MovieAdapter() {mData=null;}

    @Override
    public MovieAdapter.MovieView onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout constraint = (ConstraintLayout) LayoutInflater.from((parent.getContext())).inflate(R.layout.activity_search_page, parent, false);
        RecyclerView recyclerView = constraint.findViewById(R.id.recycler);
        LinearLayout layout = new LinearLayout(recyclerView.getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        ImageButton image = new ImageButton(layout.getContext());
        image.setImageResource(R.drawable.ic_launcher_background);
        image.setLayoutParams(params);
        params.leftMargin = 0;
        params.topMargin = 40;
        layout.addView(image, params);

        params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = new TextView(layout.getContext());
        text.setText("Testing");
        params.leftMargin =60;
        params.topMargin = 20;

        layout.addView(text, params);

        MovieView mv = new MovieView(layout);
        mv.textView = text;
        mv.imageButton = image;
        return  mv;
    }

    @Override
    public void onBindViewHolder(MovieView holder, int position) {

       try {
           holder.textView.setText(mData.getJSONObject(position).get("Title").toString());
           holder.imageButton.setImageDrawable(LoadImageFromWebOperations(mData.getJSONObject(position).get("Poster").toString()));
       }
       catch (JSONException e){

       }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.length() :0;
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
