/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.StringReader;
import javax.enterprise.context.ApplicationScoped;
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
    
    private MessageController messages = new MessageController();
    
    @GET
    @Produces("application/json")
    public Response getAll() {
        System.out.println("GetAll" + messages.returnJson().toString());
        return Response.ok(messages.returnJson().toString()).build();
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response newMessage(String str) {
        JsonObject json = Json.createReader(new StringReader(str)).readObject();
        Message msg = new Message(json);
        messages.addMessages(msg);
        return Response.ok(msg.returnJson()).build();
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        return Response.ok(messages.returnById(id)).build();
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
    
}
