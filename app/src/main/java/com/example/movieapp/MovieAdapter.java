package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieView> {
    private int mData;

    public class MovieView extends RecyclerView.ViewHolder {
        TextView text;

        public MovieView(RelativeLayout layout) {
            super(layout);
        }
    }

    public  MovieAdapter(int data) {
        mData = data;
    }

    @Override
    public MovieAdapter.MovieView onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout constraint = (ConstraintLayout) LayoutInflater.from((parent.getContext())).inflate(R.layout.activity_details, parent, false);
        RelativeLayout layout = constraint.findViewById(R.id.recycler);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80);
        ImageButton image = new ImageButton(layout.getContext());
        image.setImageResource(R.drawable.ic_launcher_background);
        image.setLayoutParams(params);
        params.leftMargin = 0;
        params.topMargin = 40;
        layout.addView(image, params);

        TextView text = new TextView(layout.getContext());
        text.setText("Testing");
        params.leftMargin = 80;
        params.topMargin = 20;

        layout.addView(text, params);

        MovieView mv = new MovieView(layout);
        return  mv;
    }

    @Override
    public void onBindViewHolder(MovieView holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mData;
    }
}
