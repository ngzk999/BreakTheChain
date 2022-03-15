package com.example.breakthechain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.breakthechain.Models.InnerModel;
import com.example.breakthechain.Models.MainNews;
import com.example.breakthechain.Retrofits.ApiUtilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    String apiKey = "f99d3c62659e4088a1a286fde6664c9a";
    ArrayList<InnerModel> innerModelArrayList;
    Adapter adapter;
    String q = "covid";
    String country = "my";
    private RecyclerView newsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        innerModelArrayList = new ArrayList<>();
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext(), innerModelArrayList);
        newsRecyclerView.setAdapter(adapter);

        findNews();

        return view;
    }

    private void findNews() {
        ApiUtilities.getApiInterface().getNews(country, q, apiKey).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {
                if (response.isSuccessful()){
                    innerModelArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {

            }
        });
    }
}