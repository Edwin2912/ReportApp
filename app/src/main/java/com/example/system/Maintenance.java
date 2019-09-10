package com.example.system;

import android.net.Uri;

public class Maintenance
{
    public String description, message,status,reporter,imageUrl;


    public Maintenance()
    {

    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "description='" + description + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", reporter='" + reporter + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Maintenance(String description, String message,String status,String reporter,String imageUrl)
    {
        this.description = description;
        this.message = message;
        this.status = status;
        this.reporter=reporter;
        this.imageUrl=imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getimageUrl() {
        return imageUrl;
    }

    public void setimageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
