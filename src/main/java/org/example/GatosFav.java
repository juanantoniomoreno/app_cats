package org.example;

public class GatosFav {
    String id;
    String image_id;
    String apiKey = "live_LqVpFWDwq33BtkrlUquHh703e4WVHQTlAfRkAoKuY97xrbyuLSFifj0EiL8DmRi7";
    ImageX image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ImageX getImage() {
        return image;
    }

    public void setImage(ImageX image) {
        this.image = image;
    }
}
