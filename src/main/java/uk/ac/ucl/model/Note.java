package uk.ac.ucl.model;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private String text;
    private String url;
    private String imgUrl;
    private String category;

    public Note (String title, String text, String url, String imgUrl, String category) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Note title is required");
        }
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Note category is required");
        }
        if ((text == null || text.isEmpty()) && (url == null || url.isEmpty()) && (imgUrl == null || imgUrl.isEmpty())) {
            throw new IllegalArgumentException("Note content (text/url/image) is required");
        }

        this.title = title;
        this.text = text;
        this.url = url;
        this.imgUrl = imgUrl;
        this.category = category;
    }

    // getters
    public String getTitle() { return title; }
    public String getText() { return text; }
    public String getUrl() { return url; }
    public String getImgUrl() { return imgUrl; }
    public String getCategory() { return category; }

    // setters
    public void setTitle(String title) { this.title = title; }
    public void setText(String text) { this.text = text; }
    public void setUrl(String url) { this.url = url; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    public void setCategory(String category) { this.category = category; }

//    public static void main(String[] args) {
//        try {
//            Note note1 = new Note("Meeting Notes", "Work", "Discuss project milestones", null, "Work");
//            System.out.println("Note 1 created: " + note1.getTitle());
//
//            Note note2 = new Note("Reference", "Study", null, "https://example.com", "Hi");
//            System.out.println("Note 2 created: " + note2.getTitle());
//
//            // This will throw an exception because no text, url, or imgUrl is provided
//            Note invalidNote = new Note("Invalid", null, null, null, "General");
//            System.out.println("Invalid Note created: " + invalidNote.getTitle());
//
//        } catch (IllegalArgumentException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
}
