/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
    private MessageController messageController;

    @GET
    @Produces("application/json")
    public Response getAll() {

        List<Message> returnAllMessages = messageController.getAll();
        if (returnAllMessages == null) {
            return Response.status(404).build();
        } else {
            return Response.ok(messageController.returnJson()).build();
        }

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response newMessage(String str) {
        Message msg = messageController.newMessage(str);
        if (msg == null) {
            return Response.status(404).build();
        } else {
            return Response.ok(msg.returnJson()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {

        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        messageController.updateGetAll();
        Message msg = messageController.returnById(id);
        if (msg == null) {
            return Response.status(404).build();
        } else {
            return Response.ok(msg.returnJson()).build();
        }

    }

    @PUT
    @Path("{id}")
    @Produces("application/json")
    public Response updateMessage(@PathParam("id") int id, String str) {
        messageController.updateGetAll();
        Message msg = messageController.updateMessage(id, str);
        if (msg == null) {
            return Response.status(404).build();
        } else {
            return Response.ok(msg.returnJson()).build();
        }

    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteMessage(@PathParam("id") int id) {

        messageController.updateGetAll();
        Boolean check = messageController.checkIfExists(id);
        if (check) {
            if (messageController.deleteMessage(id)) {
                return Response.status(200).build();
            } else {
                return Response.status(404).build();
            }
        }
        return Response.status(404).build();
    }

    @GET
    @Path("{startDate}/{endDate}")
    @Produces("application/json")
    public Response getMessagesByDateRange(@PathParam("startDate") String start, @PathParam("endDate") String end) {
        JsonArray retVal;
        messageController.updateGetAll();
        try {
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date startDate = format.parse(start);
            Date endDate = format.parse(end);
            retVal = messageController.returnByDateRange(startDate, endDate);
        } catch (ParseException ex) {

            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(404).build();
        }
        return Response.ok(retVal).build();
    }
}
