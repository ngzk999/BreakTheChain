package com.example.breakthechain.Models;

import java.util.ArrayList;

public class MainNews {
    private String status, totalResults;
    private ArrayList<InnerModel> articles;

    public MainNews(String status, String totalResults, ArrayList<InnerModel> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<InnerModel> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<InnerModel> articles) {
        this.articles = articles;
    }
}
