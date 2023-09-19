package io.beanthemoonman.moonservice.api;

import io.beanthemoonman.moonservice.api.model.CreatePersonRequest;
import io.beanthemoonman.moonservice.persistence.EntityHelper;
import io.beanthemoonman.moonservice.persistence.entities.People;
import io.beanthemoonman.moonservice.api.model.DataModificationResponse;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/v2")
public class MoonV2Resource extends MoonV1Resource{

  @Inject
  Logger logger;

  @Inject
  EntityHelper entityHelper;

  @Override
  public Response time(String zone) {
    return Response.ok(STR."Check your own watch set for timezone: \{zone}!").build();
  }

  @POST
  @Path("/createperson")
  @Produces(MediaType.APPLICATION_JSON)
  public Response createPerson(CreatePersonRequest createPersonRequest) {
    try {
      var person = new People(createPersonRequest.firstName(), createPersonRequest.lastName());
      entityHelper.commitEntity(person);
      return Response.ok(
          DataModificationResponse.createSuccess()
      ).build();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
