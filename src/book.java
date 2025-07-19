// book.java
public class book {
    private int id;
    private String title;
    private String author;
    private boolean isBorrowed;

    public book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    public boolean isBorrowed() { return isBorrowed; }
    public void setBorrowed(boolean borrowed) { this.isBorrowed = borrowed; }
}
