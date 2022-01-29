package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Topic;
import services.TopicService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/topic")
public class TopicController {

    @EJB
    private TopicService topicService;

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopic(@PathParam("id") Long id) {
        try {
            return  Response
                    .status(Response.Status.OK)
                    .entity(jsonParserFromObject(topicService.getTopicById(id)))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @RolesAllowed({"CHECKER", "ADMIN"})
    @Path("/create")
    public Response saveTopic(
            @FormParam("interest_area_id") Long id,
            @FormParam("name") String name){
        try {
            Topic topic = new Topic(name);
            topicService.createNewTopic(topic, id);
            return Response.ok().build();
        }   catch (Exception e) {
            return Response.serverError().build();
        }
    }

    public String jsonParserFromObject(Topic topic) throws JsonProcessingException {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", topic.getId());
        map.put("name", topic.getName());
        map.put("interest_area", topic.getInterestArea());
        return new ObjectMapper().writeValueAsString(map);
    }
}
