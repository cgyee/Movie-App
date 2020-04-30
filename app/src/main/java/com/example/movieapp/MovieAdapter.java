package com.example.movieapp;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONArray;
import org.json.JSONException;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieView> {
    private JSONArray mData;
    private Context context;
    private Intent intent;

    public class MovieView extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton imageButton;

        public MovieView(LinearLayout layout) {
            super(layout);
        }
    }

    public  MovieAdapter(JSONArray data, Intent intent) { mData = data; this.intent = intent;}
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

        this.context = parent.getContext();
        MovieView mv = new MovieView(layout);
        mv.textView = text;
        mv.imageButton = image;
        return  mv;
    }

    @Override
    public void onBindViewHolder(final MovieView holder, final int position) {
        final MovieView temp = holder;


       try {
           holder.textView.setText(mData.getJSONObject(position).get("Title").toString());
           ImageRequest imageRequest = new ImageRequest(mData.getJSONObject(position).get("Poster").toString(), new Response.Listener<Bitmap>() {
               @Override
               public void onResponse(Bitmap response) {
                    temp.imageButton.setImageBitmap(response);
               }
           }, 0, 0, null,
           new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                    temp.imageButton.setImageResource(R.drawable.ic_launcher_background);
               }
           });
           MySingleton.getInstance(this.context).addToRequestQueue(imageRequest);
       }
       catch (JSONException e){
           System.out.println("MV Image");
       }
       final Intent tempIntent = this.intent;
       holder.imageButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               tempIntent.putExtra("MOVIE_TITLE", holder.textView.getText().toString());
               tempIntent.putExtra("EMAIL", intent.getStringExtra("EMAIL"));
               try {
                   tempIntent.putExtra("POSTER_URL", mData.getJSONObject(position).get("Poster").toString());
               }
               catch (JSONException e) {
                   System.out.println(e);
               }
               if(tempIntent!=null) {
                   context.startActivity(tempIntent);
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return mData!=null ? mData.length() : 0;
    }
}
