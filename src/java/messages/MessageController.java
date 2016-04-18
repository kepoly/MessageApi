/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author kepoly
 */
@ApplicationScoped
public class MessageController {

    private List<Message> messages = new ArrayList<>();

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessages(Message msg) {
        messages.add(msg);
    }

    public void removeMessage(int index) {
        messages.remove(index);
    }

    public void updateMessage(Message msg, int index) {
        messages.set(index, msg);
    }

    public Message returnById(int index) {
        for (Message msg : this.getMessages()) {
            if (msg.getId() == index) {
                return msg;
            }
        }
        return null;
    }

    public void deleteById(int index) {
        for (Message msg : this.getMessages()) {
            if (msg.getId() == index) {
                messages.remove(index);
            }
        }
    }

    public Boolean checkIfExists(Message inMsg) {
        boolean retVal = false;
        for (Message msg : messages) {
            if (msg.getId() == inMsg.getId()) {
                retVal = true;
            }
        }
        return retVal;
    }

    public Boolean checkIfExists(int id) {
        boolean retVal = false;
        for (Message msg : messages) {
            if (msg.getId() == id) {
                retVal = true;
            }
        }
        return retVal;
    }

    public JsonArray returnJson() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Message msg : this.getMessages()) {
            json.add(msg.returnJson());
        }
        return json.build();
    }

    //when we do getall checking for multiple will stop it from
    public void updateGetAll() {
        messages = new ArrayList<>();
        try {
            Connection conn;
            conn = (Connection) utils.Connection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM MESSAGES");
            while (res.next()) {
                Message msg = new Message();
                msg.setId(res.getInt("id"));
                msg.setTitle(res.getString("title"));
                msg.setContents(res.getString("contents"));
                msg.setAuthor(res.getString("author"));
                msg.setSenttime(res.getDate("senttime"));
                //for some reason it keeps adding every time page refreshed

                messages.add(msg);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Message> getAll() {
        this.updateGetAll();
        try {
            Connection conn;
            conn = (Connection) utils.Connection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM MESSAGES");
            while (res.next()) {
                Message msg = new Message();
                msg.setId(res.getInt("id"));
                msg.setTitle(res.getString("title"));
                msg.setContents(res.getString("contents"));
                msg.setAuthor(res.getString("author"));
                msg.setSenttime(res.getDate("senttime"));
                //for some reason it keeps adding every time page refreshed
                if (!this.checkIfExists(msg)) {
                    messages.add(msg);
                }
            }
            return messages;
        } catch (SQLException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Message newMessage(String str) {
        JsonObject json = Json.createReader(new StringReader(str)).readObject();
        Message msg = new Message(json);

        try {
            Connection conn;
            conn = (Connection) utils.Connection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO messages (title, contents, author, senttime) VALUES (?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, msg.getTitle());
            pstmt.setString(2, msg.getContents());
            pstmt.setString(3, msg.getAuthor());
            pstmt.executeUpdate();

            //idk if this is the way to do this: http://stackoverflow.com/questions/4224228/preparedstatement-with-statement-return-generated-keys
            ResultSet newId = pstmt.getGeneratedKeys();
            if (newId.next()) {
                msg.setId(newId.getInt(1));
            }
            return msg;
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Message getById(int id) {

        if (this.returnById(id) != null) {
            return returnById(id);
        } else {
            try {
                System.out.println("okok");
                Connection conn;
                conn = (Connection) utils.Connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM messages WHERE id = ?");
                pstmt.setInt(1, id);
                ResultSet newId = pstmt.executeQuery();
                Message msg = new Message();
                while (newId.next()) {
                    msg.setId(newId.getInt("id"));
                    msg.setTitle(newId.getString("title"));
                    msg.setContents(newId.getString("contents"));
                    msg.setAuthor(newId.getString("author"));
                    msg.setSenttime(newId.getDate("senttime"));
                }
                return msg;
            } catch (SQLException ex) {
                Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
    }

    public Message updateMessage(int id, String str) {

        JsonObject json = Json.createReader(new StringReader(str)).readObject();
        Message msg = new Message(json);
        try {
            Connection conn;
            conn = (Connection) utils.Connection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE messages SET title = ?, contents = ?, author = ?, senttime = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, msg.getTitle());
            pstmt.setString(2, msg.getContents());
            pstmt.setString(3, msg.getAuthor());
            pstmt.setDate(4, new java.sql.Date(msg.getSenttime().getTime()));
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            msg.setId(id);
            return msg;
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Boolean deleteMessage(int id) {

        try {
            Connection conn;
            conn = (Connection) utils.Connection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM messages WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public JsonArray returnByDateRange(Date startIn, Date endIn) {
        JsonArrayBuilder json = Json.createArrayBuilder();
        Date startMinus = startIn;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(startMinus);
        gc.add(Calendar.DAY_OF_YEAR, -1);
        Date start = gc.getTime();

        Date endMinus = endIn;
        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.setTime(endMinus);
        gc1.add(Calendar.DAY_OF_YEAR, 1);
        Date end = gc1.getTime();

        for (Message msg : this.getMessages()) {
            if (msg.getSenttime().after(start) && msg.getSenttime().before(end)) {
                json.add(msg.returnJson());
            }
        }
        return json.build();
    }
}
