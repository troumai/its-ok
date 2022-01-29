package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.InterestArea;
import services.InterestAreaService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/interest_area")
public class InterestAreaController {

    @EJB
    private InterestAreaService interestAreaService;

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArea(@PathParam("id") Long id) {
        try {
            return  Response
                    .status(Response.Status.OK)
                    .entity(jsonParserFromObject(interestAreaService.getInterestAreaById(id)))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArea(){
        try {
            return  Response
                    .status(Response.Status.OK)
                    .entity(new ObjectMapper().writeValueAsString(interestAreaService.getAllInterestArea()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @RolesAllowed({"CHECKER", "ADMIN"})
    @Path("/create")
    public Response saveArea(@FormParam("name") String name){
        try {
            InterestArea interestArea = new InterestArea(name);
            interestAreaService.createNewInterestArea(interestArea);
            return Response.ok().build();
        }   catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/updateName/{id}")
    @DenyAll
    public Response updateAreaByName(@FormParam("firstName") String name, @PathParam("id") Long id) {
        try {
            interestAreaService.updateInterestAreaByName(id, name);
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
            interestAreaService.deleteInterestAreaById(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }


    public String jsonParserFromObject(InterestArea interestArea) throws JsonProcessingException {
            Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", interestArea.getId());
        map.put("name", interestArea.getName());
        map.put("topics", interestArea.getTopics());
        return new ObjectMapper().writeValueAsString(map);
    }
}
