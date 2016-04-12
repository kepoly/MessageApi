/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
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
    private Date senttime;

    DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

    public Message() {
        this.id = 0;
        this.title = "";
        this.contents = "";
        this.author = "";
        this.senttime = new Date();
    }
    
    public Message(int id, String title, String contents, String author, Date senttime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.senttime = senttime;
    }

    Message(JsonObject json) {
        try {
            this.id = json.getInt("id");
            this.title = json.getString("title");
            this.contents = json.getString("contents");
            this.author = json.getString("author");
            this.senttime = format.parse(json.getString("senttime"));
        } catch (ParseException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public Date getSenttime() {
        return senttime;
    }

    public void setSenttime(Date senttime) {
        this.senttime = senttime;
    }

    @Override
    public String toString() {
        return "{" + "\"" + "id" + "\":" + id + ","
                + "\"" + "title" + "\":" + "\"" + title + "\"" + ","
                + "\"" + "contents" + "\":" + "\"" + contents + "\"" + ","
                + "\"" + "author" + "\":" + "\"" + author + "\"" + ","
                + "\"" + "senttime" + "\":" + "\"" + senttime + "\"" + "}";
    }

    public JsonObject returnJson() {
        JsonObject json = Json.createObjectBuilder()
                .add("id", id)
                .add("title", title)
                .add("contents", contents)
                .add("author", author)
                .add("senttime", format.format(senttime)).build();
        return json;
    }

}
