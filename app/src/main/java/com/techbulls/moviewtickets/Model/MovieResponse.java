package com.techbulls.moviewtickets.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("Search")
    @Expose
    private List<SearchByMovieResponse> searchByMovieResponseList;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("Error")
    @Expose
    private String Error;


    public List<SearchByMovieResponse> getSearchByMovieResponseList() {
        return searchByMovieResponseList;
    }

    public void setSearchByMovieResponseList(List<SearchByMovieResponse> searchByMovieResponseList) {
        this.searchByMovieResponseList = searchByMovieResponseList;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}
