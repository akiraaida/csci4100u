package ca.uoit.csci4100u.samplemidterm;

public class Story {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String title;
    private String author;
    private String content;

    public Story(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

}
