package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final TextView mTitle = findViewById(R.id.Title);
        final TextView mDescription = findViewById(R.id.movieDescription);
        final ImageView mPoster = findViewById(R.id.moviePoster);
        final String url = "http://www.omdbapi.com/?apikey=ac1faa3e&t=";
        final String title = getIntent().getStringExtra("MOVIE_TITLE");
        final String poster = getIntent().getStringExtra("POSTER_URL");
        final  String email = getIntent().getStringExtra("EMAILs");
        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        mTitle.setText(title);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+title.replaceAll(" ", "_").toLowerCase(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mDescription.setText(response.get("Plot").toString());
                    ImageRequest imageRequest = new ImageRequest(response.get("Poster").toString(), new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            mPoster.setImageBitmap(response);
                        }
                    }, 0, 0, null,
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                   mPoster.setImageResource(R.drawable.ic_launcher_background);
                                }
                            });
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(imageRequest);
                }
                catch (JSONException e) {
                    System.out.println("2");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("3");
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        Button favoriteButton = findViewById(R.id.favoriteMovie);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("temp");
                user.addMovie(title, poster);
                user.saveFavorites();
                dbHelper.updateFavorites(user.saveFavorites(), email);

            }
        });

    }
}
