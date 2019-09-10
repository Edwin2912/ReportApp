package com.example.system;

import android.net.Uri;

public class Emergency
{

    private String description,reporter,date_reported,status,Video;


    public Emergency()
    {
    }

    public Emergency(String description,String reporter,String date_reported,String status,String Video)
    {
        this.description = description;
        this.reporter=reporter;
        this.date_reported=date_reported;
        this.status=status;
        this.Video=Video;
    }

    public String getVideo()
    {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getDate_reported() {
        return date_reported;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate_reported(String date_reported) {
        this.date_reported = date_reported;
    }
}
