package com.thoughtworks.api.resources.services;

import com.thoughtworks.api.core.OrderRepository;
import com.thoughtworks.api.core.PaymentRepository;
import com.thoughtworks.api.core.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("users")
public class UserResource {
  @Context
  UserRepository userRepository;

  @Context
  OrderRepository orderRepository;

  @Context
  PaymentRepository paymentRepository;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Map<String, Object>> listUsers() {
    return userRepository.list();
  }

  @GET
  @Path("{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Map<String, Object> findUserById(@PathParam("userId") String id) {
    return userRepository.findById(id);
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response saveUser(@FormParam("name") String name) {
    if (userRepository.save(name) == 1) {
      return Response.status(201).build();
    } else {
      return Response.status(400).build();
    }
  }

  @GET
  @Path("{userId}/orders")
  @Produces(MediaType.APPLICATION_JSON)
  public HashMap[] listOrders(@PathParam("userId") String userId) {
    HashMap[] orders =  orderRepository.find(userId);


    for (HashMap order: orders) {
      HashMap[] orderItems = orderRepository.findOrderItemByOrderId(order.get("ID").toString());
      order.put("order_item", orderItems);
    }

    return orders;
  }

  @GET
  @Path("{userId}/orders/{orderId}")
  @Produces(MediaType.APPLICATION_JSON)
  public HashMap findOrderById(@PathParam("userId") String userId,
                               @PathParam("orderId") String orderId) {
    HashMap order =  orderRepository.findById(userId, orderId);
    HashMap[] orderItems = orderRepository.findOrderItemByOrderId(orderId);

    order.put("order_item", orderItems);
    return order;
  }

  @POST
  @Path("{userId}/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  public int saveOrder(Map<String, Object> info,
                       @PathParam("userId") String userId) {
    System.out.println(userId);
    return orderRepository.save(info, userId);
  }

  @POST
  @Path("{userId}/orders/{orderId}/payment")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public int savePayment(@PathParam("userId") String userId,
                         @PathParam("orderId") String orderId,
                         @FormParam("pay_type") String payType,
                         @FormParam("amount") float amount) {

    return paymentRepository.save(orderId, payType, amount);
  }

  @GET
  @Path("{userId}/orders/{orderId}/payment")
  @Produces(MediaType.APPLICATION_JSON)
  public HashMap findPaymentByOrderId(@PathParam("orderId") String orderId) {
    return paymentRepository.findByOrderId(orderId);
  }
}
