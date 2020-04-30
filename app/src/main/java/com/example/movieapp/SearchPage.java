package com.example.movieapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_page);
        super.onCreate(savedInstanceState);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final  String url = "http://www.omdbapi.com/?apikey=ac1faa3e&s=";
        final Intent intent = new Intent(this, Details.class);
        final DBHelper dbHelper = new DBHelper(getApplicationContext());

        final Intent getIntent = getIntent();
        final User user = new User(getIntent.getStringExtra("USER"));
        intent.putExtra("EMAIL", user.getEmail());

        //Setting up recyclerView
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        MovieAdapter movieAdapter= new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);

        //If searchButton is clicked send a JSONrequest for the text in searchText TextView and
        // update the recyclerView with the results
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText searchText =findViewById(R.id.searchText);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+searchText.getText().toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("Search");
                            System.out.println("1");
                            MovieAdapter movieAdapter= new MovieAdapter(results, intent);
                            recyclerView.setAdapter(movieAdapter);
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
            }
        });

        //If the favButton is clicked get the current User favorites from the database and
        //update the recyclerView with the results
        Button favoritesButton = findViewById(R.id.favButton);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("temp");
                user.loadFavorites(dbHelper.getFavorites(getIntent.getStringExtra("USER")));
                JSONArray jsonArray = new JSONArray();
                 ArrayList<Movie> favorites = user.getFavorites();
                 for(int i= 0; i < favorites.size(); i++) {
                     JSONObject jsonObject = new JSONObject();
                     try {
                         jsonObject.put("Title", favorites.get(i).getTitle());
                         jsonObject.put(("Poster"), favorites.get(i).getPoster_url());
                         jsonArray.put(jsonObject);
                     }
                     catch (JSONException e){
                         System.out.println();
                     }
                }
                 MovieAdapter favAdapter = new MovieAdapter(jsonArray, intent);
                 recyclerView.setAdapter(favAdapter);
            }
        });

        //If the accountButton is selected start the account_page activity
        Button accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountIntent = new Intent(getApplicationContext(), AccountPage.class);
                accountIntent.putExtra("EMAIL", user.getEmail());
                startActivity(accountIntent);
            }
        });

    }
}
