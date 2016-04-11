/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

/**
 *
 * @author kepoly
 */
@ApplicationScoped
public class MessageController {
    
    private final List<Message> messages = new ArrayList<>();

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
    
    public JsonArray returnJson() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Message msg : this.getMessages()) {
            json.add(msg.toString());
        }
        return json.build();
    }
    public String returnById(int index) {
        for(Message msg : this.getMessages()) {
            if(msg.getId() == index) {
                return msg.toString();
            }
        }
        return "{" + "\"" + "error" + "\":" + "1" + "}";
    }
    
}
