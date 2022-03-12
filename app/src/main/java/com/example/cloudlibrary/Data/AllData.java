package com.example.cloudlibrary.Data;

public class AllData {
    private String url="192.168.43.241";

    public AllData(String url) {
        this.url = url;
    }
    public AllData(){
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
