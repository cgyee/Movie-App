package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.movieapp.dbaccess.DBHelper;
import com.example.movieapp.dataOrg.Movie;
import com.example.movieapp.dataOrg.MySingleton;
import com.example.movieapp.R;
import com.example.movieapp.dataOrg.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Java code logic related to the activity_detail.xml
 */
public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final TextView mTitle = findViewById(R.id.Title);
        final TextView mDescription = findViewById(R.id.movieDescription);
        final TextView mDirector = findViewById(R.id.director);
        final TextView mYear = findViewById(R.id.year);
        final TextView mRating = findViewById(R.id.rating);
        final TextView mGenre = findViewById(R.id.genre);
        final ImageView mPoster = findViewById(R.id.moviePoster);
        final String url = "https://www.omdbapi.com/?apikey=ac1faa3e&t=";
        final String title = getIntent().getStringExtra("MOVIE_TITLE");
        final String poster = getIntent().getStringExtra("POSTER_URL");
        final String email = getIntent().getStringExtra("EMAIL");
        final User user = new User(email);
        final DBHelper dbHelper = new DBHelper(getApplicationContext());

        user.loadFavorites(dbHelper.getFavorites(email));
        mTitle.setText(title);

        //Send a JSON request for information related to the movie found in String email and set the information to the related components
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+title.replaceAll(" ", "_").toLowerCase(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mDescription.setText(response.get("Plot").toString());
                    mDirector.setText("Director: " + response.get("Director").toString());
                    mYear.setText("Released: " + response.get("Released").toString());
                    mRating.setText("Rated: " + response.get("Rated").toString());
                    mGenre.setText("Genre: " + response.get("Genre").toString());
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
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        //If clicked either add or remove Movie object from ArrayList favorites and display the relevant message
        ToggleButton favoriteButton = findViewById(R.id.favoriteMovie);
        if(user.contains(title, poster)) {
            favoriteButton.setChecked(true);
            //favoriteButton.setTextOff("UnFavorite");
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //User user = new User(email);
                user.loadFavorites(dbHelper.getFavorites(email));

                if(user.removeMovie(new Movie(title, poster))) {
                    Toast.makeText(getApplicationContext(), "Movie Removed from Favorites", Toast.LENGTH_SHORT).show();
                    dbHelper.updateFavorites(user.saveFavorites(), email);
                }
                else {
                    user.addMovie(title, poster);
                    Toast.makeText(getApplicationContext(), "Movie added to Favorites", Toast.LENGTH_SHORT).show();
                    dbHelper.updateFavorites(user.saveFavorites(), email);
                }

            }
        });

    }
}
