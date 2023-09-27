package io.beanthemoonman.moonservice.api;

import com.google.gson.Gson;
import io.beanthemoonman.moonservice.api.model.*;
import io.beanthemoonman.moonservice.persistence.EntityHelper;
import io.beanthemoonman.moonservice.persistence.entities.People;
import io.beanthemoonman.moonservice.persistence.model.FilterType;
import io.beanthemoonman.moonservice.persistence.model.FilterWrapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
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
  @Path("/getpeople")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPeople(getPeopleRequest request) {
    try {
      return Response.ok(
          entityHelper.queryEntity(
              People.class, List.of(new FilterWrapper(request.query(), request.column(), FilterType.LIKE)
              ), null, request.start(), request.count())
      ).build();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      throw new RuntimeException(e);
    }
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

  @POST
  @Path("/genericcreate")
  @Produces(MediaType.APPLICATION_JSON)
  public Response genericCreate(GenericCreateRequest genericCreateRequest) {
    try {
      var entity = Class.forName(genericCreateRequest.classname());
      entityHelper.commitEntity(new Gson().fromJson(genericCreateRequest.data(), entity));
      return Response.ok(
          DataModificationResponse.createSuccess()
      ).build();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @POST
  @Path("/genericlookup")
  @Produces(MediaType.APPLICATION_JSON)
  public Response genericLookup(GenericLookupRequest genericLookupRequest) {
    try {
      var entity = Class.forName(genericLookupRequest.classname());
      return Response.ok(
          entityHelper.queryEntity(
              entity,
              genericLookupRequest.filters(),
              genericLookupRequest.orders(),
              genericLookupRequest.start(),
              genericLookupRequest.count()
          )
      ).build();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
