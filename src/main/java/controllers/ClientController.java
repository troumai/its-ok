package controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.Client;
import services.ClientService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/client")
public class ClientController {

    @EJB
    private ClientService clientService;

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClient(@PathParam("id") Long id) {
        try {
            return  Response
                    .status(Response.Status.OK)
                    .entity(jsonParserFromObject(clientService.getClientById(id)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            String message = "User not found";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .build();
        }
    }

    @GET
    @Path("/getAll")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClient() {
        try {
            return  Response
                    .status(Response.Status.OK)
                    .entity(new ObjectMapper().writeValueAsString(clientService.getAllClient()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @RolesAllowed({"CHECKER", "ADMIN"})
    @Path("/create")
    public Response saveClient(@FormParam("first_name") String firstName,
                               @FormParam("last_name") String lastName,
                               @FormParam("phone") String phone,
                               @FormParam("email") String email,
                               @FormParam("city") String city,
                               @FormParam("age") int age,
                               @FormParam("sex") String sex,
                               @FormParam("balance") double balance) {

        try {
            Client client = new Client(firstName, lastName, phone, email, city, age, sex, balance);
            clientService.createNewClient(client);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/updateName/{id}")
    @DenyAll
    public Response updateClient(@FormParam("firstName") String name, @PathParam("id") Long id) {
        try {
            clientService.updateClientByName(id, name);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @RolesAllowed({"ADMIN"})
    public Response deleteById(@PathParam("id") Long id) {
        try {
            clientService.deleteClientById(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    public String jsonParserFromObject(Client client) throws JsonProcessingException {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", client.getId());
        map.put("firstName", client.getFirstName());
        map.put("lastName", client.getLastName());
        map.put("phone", client.getPhone());
        map.put("email", client.getEmail());
        map.put("age", client.getAge());
        map.put("sex", client.getSex());
        map.put("city", client.getCity());
        map.put("date_created", client.getDateCreated());
        map.put("balance", client.getBalance());
        map.put("interests", client.getInterests());
        ObjectMapper objectMapper =
                new ObjectMapper();
        objectMapper.findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);;
        return objectMapper.writeValueAsString(map);
    }
}
