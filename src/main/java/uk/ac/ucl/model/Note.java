package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private String text;
    private String url;
    private String imgUrl;
    private String category;

    @JsonCreator
    public Note(
            @JsonProperty("title") String title,
            @JsonProperty("text") String text,
            @JsonProperty("url") String url,
            @JsonProperty("imgUrl") String imgUrl,
            @JsonProperty("category") String category) {

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Note title is required");
        }
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Note category is required");
        }
        if ((text == null || text.isEmpty()) && (url == null || url.isEmpty()) && (imgUrl == null || imgUrl.isEmpty())) {
            throw new IllegalArgumentException("Note content (text, url, or image) is required");
        }

        this.title = title;
        this.text = text;
        this.url = url;
        this.imgUrl = imgUrl;
        this.category = category;
    }

    // getters
    public String getTitle() {
        if (title == null) {
            return "";
        }
        return title;
    }

    public String getText() {
        if (text == null) {
            return "";
        }
        return text;
    }

    public String getUrl() {
        if (url == null) {
            return "";
        }
        return url;
    }

    public String getImgUrl() {
        if (imgUrl == null) {
            return "";
        }
        return imgUrl;
    }

    public String getCategory() {
        if (category == null) {
            return "";
        }
        return category;
    }

    // setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}