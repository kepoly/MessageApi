/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author kepoly
 */
@ServerEndpoint("/messagesSocket")
public class MessageServer {
    
    @Inject 
    private MessageController messageController;

    
    
//    @OnOpen
//    public void onOpen(Throwable t) {
//        System.out.println("ok");
//    }

    @OnMessage
    public void onMessage(String str, Session session) throws IOException {
        JsonObject json = Json.createReader(new StringReader(str)).readObject();
        System.out.println(json);
        
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        if(json.containsKey("getAll")) {
            if(json.getBoolean("getAll")) {
                List<Message> returnAllMessages = messageController.getAll();
                basic.sendText(messageController.returnJson().toString());
            } else {
                basic.sendText(" { \"error\" : \"Some meaningful error message.\" }");
            }
        } else if(json.containsKey("post")) {

            String getMessage = json.getJsonObject("post").toString();
            Message msg = messageController.newMessage(getMessage);
            
            if(msg != null) {
                basic.sendText(msg.returnJson().toString());
            } else {
                basic.sendText(" { \"error\" : \"Some meaningful error message.\" }");
            }
        } else if(json.containsKey("getById")) {
            int getMessageId = json.getInt("getById");
            Message msg = messageController.getById(getMessageId);
            if(msg != null) {
                basic.sendText(msg.returnJson().toString());
            } else {
                basic.sendText(" { \"error\" : \"Some meaningful error message.\" }");
            }
        } else if(json.containsKey("put")) {
            int id = json.getJsonObject("put").getInt("id");
            String getMessage = json.getJsonObject("put").toString();
            Message msg = messageController.updateMessage(id, getMessage);
            if(msg != null) {
                basic.sendText("{ \"ok\" : true }");
            } else {
                basic.sendText(" { \"error\" : \"Some meaningful error message.\" }");
            }
        } else if(json.containsKey("delete")) {
            int id = json.getInt("delete");
            if(messageController.deleteMessage(id)) {
                basic.sendText("{ \"ok\" : true }");
            } else {
                basic.sendText(" { \"error\" : \"Some meaningful error message.\" }");
            }
        }  else if(json.containsKey("getFromTo")) {
            messageController.updateGetAll();
            JsonArray newJson = json.getJsonArray("getFromTo");
            String start = newJson.getJsonString(0).getString();
            String end = newJson.getJsonString(1).getString();
            JsonArray retVal = null;
            Boolean checker = false;
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            try {
                Date startDate = format.parse(start);
                Date endDate = format.parse(end);
                retVal = messageController.returnByDateRange(startDate, endDate);
                checker = true;
            } catch (ParseException ex) {
                Logger.getLogger(MessageServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(checker) {
                System.out.println("1 " + retVal.size());
                System.out.println("2" +  retVal);
                if(retVal.size() > 0) {
                    basic.sendText(retVal.toString());
                } else {
                    basic.sendText(" { \"error\" : \"Some meaningful error message.\" }");
                }
            } else {
                basic.sendText(" { \"error\" : \"Some meaningful error message.\" }");
            }
        }
        else {
       basic.sendText(" { \"error\" : \"Some meaningful error message.\" }");
    }   
    }
}
