/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import javax.json.JsonObject;

/**
 *
 * @author kepoly
 */
public class Message {
    
    private int id;
    private String title;
    private String contents;
    private String author;
    private String senttime;

    public Message(int id, String title, String contents, String author, String senttime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.senttime = senttime;
    }

    Message(JsonObject json) {
        this.id = json.getInt("id");
        this.title = json.getString("title");
        this.contents = json.getString("contents");
        this.author = json.getString("author");
        this.senttime = json.getString("senttime");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSenttime() {
        return senttime;
    }

    public void setSenttime(String senttime) {
        this.senttime = senttime;
    }

    @Override
    public String toString() {
        return "{" + "\"" + "id" + "\":" + id + "," +
                "\"" + "title" + "\":" + "\"" + title + "\"" + "," +
                "\"" + "contents" + "\":" + "\"" + contents + "\"" + "," +
                "\"" + "author" + "\":" + "\"" + author + "\"" + "," +
                "\"" + "senttime" + "\":" + "\"" + senttime + "\"" + "}";
    }
    
}
