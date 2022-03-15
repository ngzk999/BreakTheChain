package com.example.breakthechain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.breakthechain.Models.InnerModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<InnerModel> innerModelArrayList;

    public Adapter(Context context, ArrayList<InnerModel> innerModelArrayList) {
        this.context = context;
        this.innerModelArrayList = innerModelArrayList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, webView.class);
                intent.putExtra("url", innerModelArrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        String publishedTime = innerModelArrayList.get(position).getPublishedAt() == null ? "No publish date" : "Published at:-"+innerModelArrayList.get(position).getPublishedAt();
        String authorName = innerModelArrayList.get(position).getAuthor() == null ? "No author info" : innerModelArrayList.get(position).getAuthor();
        String heading = innerModelArrayList.get(position).getTitle() == null ? "No heading" : innerModelArrayList.get(position).getTitle();
        String content = innerModelArrayList.get(position).getDescription() == null ? "No description" : innerModelArrayList.get(position).getDescription();
        String imageUrl = innerModelArrayList.get(position).getUrlToImage() == null ? "https://icon-library.com/images/placeholder-image-icon/placeholder-image-icon-7.jpg" : innerModelArrayList.get(position).getUrlToImage();

        if (innerModelArrayList.get(position).getPublishedAt() == null)
            publishedTime = "No publish date";
        else
        {
            String[] dateTime = innerModelArrayList.get(position).getPublishedAt().split("T");
            dateTime[1] = dateTime[1].substring(0, dateTime[1].length()-1);
            publishedTime = dateTime[0] + " " + dateTime[1] + " UTC (+000)";
        }

        holder.mtime.setText(publishedTime);
        holder.mauthor.setText(authorName);
        holder.mheading.setText(heading);
        holder.mcontent.setText(content);
        Glide.with(context).load(imageUrl).into(holder.mimageview);
    }

    @Override
    public int getItemCount() {
        return innerModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mheading, mcontent, mauthor, mtime;
        ImageView mimageview;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mheading = itemView.findViewById(R.id.mainheading);
            mcontent = itemView.findViewById(R.id.content);
            mauthor = itemView.findViewById(R.id.author);
            mtime = itemView.findViewById(R.id.time);
            mimageview = itemView.findViewById(R.id.imageview);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
