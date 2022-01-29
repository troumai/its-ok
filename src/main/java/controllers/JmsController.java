package controllers;

import entities.Client;
import services.JmsService;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/jms")
public class JmsController {
    @EJB
    private JmsService jmsService;

    @POST
    @PermitAll
    @Path("/addClient")
    public String addClient(@FormParam("first_name") String firstName,
                            @FormParam("last_name") String lastName,
                            @FormParam("phone") String phone,
                            @FormParam("email") String email,
                            @FormParam("city") String city,
                            @FormParam("age") int age,
                            @FormParam("sex") String sex,
                            @FormParam("balance") double balance) {
        Client client = new Client(firstName, lastName, phone, email, city, age, sex, balance);
        return jmsService.sendJmsMessage(client);
    }

    @GET
    @Path("/getClient")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient() {
        return jmsService.getMessage();
    }

    @POST
    @PermitAll
    @Path("/client/secondJMS")
    public String addClientSecondJms(@FormParam("first_name") String firstName,
                                     @FormParam("last_name") String lastName,
                                     @FormParam("phone") String phone,
                                     @FormParam("email") String email,
                                     @FormParam("city") String city,
                                     @FormParam("age") int age,
                                     @FormParam("sex") String sex,
                                     @FormParam("balance") double balance) {
        Client client = new Client(firstName, lastName, phone, email, city, age, sex, balance);
        return jmsService.sendJmsMessageSecond(client);
    }

    @GET
    @PermitAll
    @Path("/client/secondJMS")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Client> getStudentsFromSecondJMS() {
        Client client;
        ArrayList<Client> result = new ArrayList<>();
        do {
            client = jmsService.getMessageSecond();
            if (client != null) {
                result.add(client);
            }
        } while (client != null);
        return result;
    }
}
