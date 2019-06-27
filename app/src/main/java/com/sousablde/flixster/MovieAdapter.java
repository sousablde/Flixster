package com.sousablde.flixster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sousablde.flixster.models.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    //list of movies
    ArrayList<Movie> movies;

    //initialize with list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;

    }

    //creates and inflates a new view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //get the context from the parent
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create view object using item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        //return a new viewholder
        return new ViewHolder(movieView);
    }

    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the movie data at the specified position
        Movie movie = movies.get(position);
        //populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());
    }

    //returns n of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create view holder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //declaring the fields
        //track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //lookup view objects by id
            ivPosterImage = itemView.findViewById(R.id.ivPosterImage);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
