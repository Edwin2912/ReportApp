package com.example.system;

public class Photo
{
    private String Name;
    private String ImageUrl;

    public Photo()
    {
    }

    public Photo(String name, String imageUrl)
    {
        if (name.trim()=="")
        {
            Name="No Name";

        }
        Name = name;
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
