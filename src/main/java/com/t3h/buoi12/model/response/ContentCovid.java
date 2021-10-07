package com.t3h.buoi12.model.response;

public class ContentCovid {
    private String title;
    private String content;
    private String image;

    public ContentCovid(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public ContentCovid() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
