package com.techbulls.moviewtickets.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.techbulls.moviewtickets.Model.SearchByMovieResponse;
import com.techbulls.moviewtickets.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    List<SearchByMovieResponse> searchByMovieResponseList;
    Context context;

    public MovieAdapter(Context context, List<SearchByMovieResponse> searchByMovieResponseList) {
        this.context = context;
        this.searchByMovieResponseList = searchByMovieResponseList;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder holder, int position) {

        SearchByMovieResponse searchByMovieResponse = searchByMovieResponseList.get(position);

        String movieName = "<b>" + searchByMovieResponse.getTitle() + "</b> (" + searchByMovieResponse.getYear() + ")";
        holder.txtMovieName.setText(Html.fromHtml(movieName));

        try {
            Picasso.get()
                    .load("" + searchByMovieResponse.getPoster())
                    .placeholder(R.drawable.default_movie)
                    .into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return searchByMovieResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtMovieName;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            txtMovieName = itemView.findViewById(R.id.movieName);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
