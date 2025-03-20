package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;

public class Note implements Serializable {
    private String title;
    private String text;
    private String url;
    private String imgUrl;
    private List<String> categories;
    private final String createdAt;
    private String editedAt;

    @JsonCreator
    public Note(
            @JsonProperty("title") String title,
            @JsonProperty("text") String text,
            @JsonProperty("url") String url,
            @JsonProperty("imgUrl") String imgUrl,
            @JsonProperty("categories") List<String> categories)  {

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Note title is required");
        }
        if (categories == null || categories.isEmpty() || categories.contains("")) {
            throw new IllegalArgumentException("At least one category is required");
        }
        if ((text == null || text.isEmpty()) && (url == null || url.isEmpty()) && (imgUrl == null || imgUrl.isEmpty())) {
            throw new IllegalArgumentException("Note content (text, url, or image) is required");
        }

        this.title = title;
        this.text = text;
        this.url = url;
        this.imgUrl = imgUrl;
        this.categories = categories;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.editedAt = this.createdAt;
    }

    // getters
    @JsonProperty("title")
    public String getTitle() {
        if (title == null) {
            return "";
        }
        return title;
    }

    @JsonProperty("text")
    public String getText() {
        if (text == null) {
            return "";
        }
        return text;
    }

    @JsonProperty("url")
    public String getUrl() {
        if (url == null) {
            return "";
        }
        return url;
    }

    @JsonProperty("imgUrl")
    public String getImgUrl() {
        if (imgUrl == null) {
            return "";
        }
        return imgUrl;
    }

    @JsonProperty("categories")
    public List<String> getCategories() {
        if (categories == null) {
            return List.of();
        }
        return categories;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("editedAt")
    public String getEditedAt() {
        return editedAt;
    }

    // setters
    public void setTitle(String title) {
        this.title = title;
        updateEditedAt();
    }

    public void setText(String text) {
        this.text = text;
        updateEditedAt();
    }

    public void setUrl(String url) {
        this.url = url;
        updateEditedAt();
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        updateEditedAt();
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
        updateEditedAt();
    }

    private void updateEditedAt() {
        this.editedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}