/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
            json.add(msg.returnJson());
        }
        return json.build();
    }

    public String returnById(int index) {
        for (Message msg : this.getMessages()) {
            if (msg.getId() == index) {
                return msg.toString();
            }
        }
        return "{" + "\"" + "error" + "\":" + "1" + "}";
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
    
    public Boolean checkIfExists(Message msg) {
        boolean retVal = false;
        for(Message msgg : messages) {
            if(msgg.getId() == msg.getId()) {
                retVal = true;
            }
        }
        return retVal;
    }

}
