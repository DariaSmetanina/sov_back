package com.sovback.sovback.model;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "news")
@Data
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_news")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "importance")
    private String importance;

    @Column(name = "date")
    private String date;

    @Column(name = "main_part")
    private String main_part;

    @Column(name = "text")
    private String text;

    @Column(name = "files")
    private String files;

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMain_part(String main_part) {
        this.main_part = main_part;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getTitle() {
        return title;
    }

    public String getImportance() {
        return importance;
    }

    public String getDate() {
        return date;
    }

    public String getMain_part() {
        return main_part;
    }

    public String getText() {
        return text;
    }

    public String getFiles() {
        return files;
    }
}
