package com.thoughtworks.api.resources.services;

import com.thoughtworks.api.core.ProductRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("products")
public class ProductResource {
  @Context
  ProductRepository productRepository;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Map<String, Object>> list() {
    return productRepository.list();
  }

  @GET
  @Path("{productId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Map<String, Object> findById(@PathParam("productId") String id) {
    return productRepository.findById(id);
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response save(@FormParam("name") String name,
                  @FormParam("description") String description,
                  @FormParam("price") float price,
                  @FormParam("rating") int rating) {

    if (productRepository.save(name, description, price, rating) == 1) {
      return Response.status(201).build();
    } else {
      return Response.status(400).build();
    }
  }
}
