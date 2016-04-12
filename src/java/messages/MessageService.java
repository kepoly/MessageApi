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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author kepoly
 */
@Path("messages")
@ApplicationScoped
public class MessageService {
    
    @Inject
    private MessageController messages;
    
    @GET
    @Produces("application/json")
    public Response getAll() {

        try {
            Connection conn;
            conn = (Connection) utils.Connection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM MESSAGES");
            while(res.next()) {
                Message msg = new Message();
                msg.setId(res.getInt("id"));
                msg.setTitle(res.getString("title"));
                msg.setContents(res.getString("contents"));
                msg.setAuthor(res.getString("author"));
                msg.setSenttime(res.getDate("senttime"));
                //for some reason it keeps adding every time page refreshed
                if(!messages.checkIfExists(msg)) {
                    messages.addMessages(msg);
                }

            }
            
            return Response.ok(messages.returnJson().toString()).build();
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(404).build();
        }
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response newMessage(String str) {
            JsonObject json = Json.createReader(new StringReader(str)).readObject();
            Message msg = new Message(json);
//            messages.addMessages(msg);
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
            if(newId.next()) {
              msg.setId(newId.getInt(1));
            }
            return Response.ok(msg.returnJson()).build();
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(500).build();
        }
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
//for some reason it is returning the wrong date format kill me now.
//        if(messages.checkIfExists(messages.returnById(id))) {
//            System.out.println("ok");
//            
//            String formattedDate = format.format(messages.returnById(id).getSenttime());
//            System.out.println(formattedDate);
//            
//            //the date is formatted as i want above but it doesnt want to work when outputting the data
//            try {
//                System.out.println("oldDate: " + formattedDate);
//                Date newDate = format.parse(formattedDate);
//                messages.returnById(id).setSenttime(newDate);
//                System.out.println("newDate: " + newDate);
//                
//                
//            } catch (ParseException ex) {
//                Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
//                System.out.println("didnt work");
//            }
//            
//            return Response.ok("aa").build();
//        } else {
            try {
                System.out.println("okok");
                Connection conn;
                conn = (Connection) utils.Connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM messages WHERE id = ?");
                pstmt.setInt(1, id);
                ResultSet newId = pstmt.executeQuery();
                Message msg = new Message();
                while(newId.next()) {
                msg.setId(newId.getInt("id"));
                msg.setTitle(newId.getString("title"));
                msg.setContents(newId.getString("contents"));
                msg.setAuthor(newId.getString("author"));
                msg.setSenttime(newId.getDate("senttime"));
                }

                return Response.ok(msg.returnJson()).build();
            } catch (SQLException ex) {
                Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
                return Response.status(404).build();
            }
            
//        }
        
    }
    
    @PUT
    @Path("{id}")
    @Produces("application/json")
    public Response updateMessage(@PathParam("id") int id, String str) {
        JsonObject json = Json.createReader(new StringReader(str)).readObject();
        Message msg = new Message(json);
        if(id > messages.getMessages().size()) {
            return Response.status(404).build();
        } else {
        messages.updateMessage(msg, id);
        return Response.ok(msg.returnJson()).build();
        }

    }
    
    @DELETE
    @Path("{id}") 
    @Produces("application/json")
    public Response deleteMessage(@PathParam("id") int id) {
        messages.removeMessage(id);
        return Response.ok().build();
    }
    
    @GET
    @Path("{startDate}/{endDate}")
    @Produces("application/json")
    public Response getMessagesByDateRange(@PathParam("startDate") String start, @PathParam("endDate") String end) {
        JsonArray retVal;
        try {
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date startDate = format.parse(start);
            Date endDate = format.parse(end);
            retVal = messages.returnByDateRange(startDate, endDate);
        } catch (ParseException ex) {
            
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(404).build();
        }
        return Response.ok(retVal).build();
    }
    
}
