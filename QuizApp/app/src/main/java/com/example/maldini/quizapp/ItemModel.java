package com.example.maldini.quizapp;

public class ItemModel {

    private String title;
    private MainPhoto mainPhoto;

    public ItemModel(String title){
        this.title=title;
    }

    public ItemModel(String title, String mainPhoto) {
        this.title = title;
        this.mainPhoto = new MainPhoto(mainPhoto);
    }

    public MainPhoto getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(MainPhoto mainPhoto) {
        this.mainPhoto = mainPhoto;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public class MainPhoto{
        private String url;

        public MainPhoto(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
