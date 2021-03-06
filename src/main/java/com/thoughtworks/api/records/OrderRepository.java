package com.thoughtworks.api.records;

import com.thoughtworks.api.core.Order;
import com.thoughtworks.api.mappers.OrderItemMapper;
import com.thoughtworks.api.mappers.OrderMapper;
import com.thoughtworks.api.mappers.ProductMapper;

import javax.inject.Inject;
import java.util.*;

public class OrderRepository implements com.thoughtworks.api.core.OrderRepository {

  @Inject
  ProductMapper productMapper;

  @Inject
  OrderMapper orderMapper;

  @Inject
  OrderItemMapper orderItemMapper;

  @Override
  public String generateId() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  @Override
  public List<Order> find(String userId) {
    return orderMapper.find(userId);
  }

  @Override
  public Order findById(String userId, String orderId) {
    return orderMapper.findById(userId, orderId);
  }

  @Override
  public int save(Map info, String userId) {

    String orderId = generateId();

    orderMapper.save(orderId,
      info.get("name").toString(),
      info.get("address").toString(),
      info.get("phone").toString(),
      userId);


    ArrayList orderItems = (ArrayList) info.get("order_item");

    for (int i = 0; i< orderItems.size(); i++) {
      Map orderItem = (Map) orderItems.get(i);

      float price = productMapper.findById(orderItem.get("product_id").toString()).getPrice();

      orderItemMapper.save(price,
        Integer.valueOf(orderItem.get("quantity").toString()),
        orderItem.get("product_id").toString(),
        orderId);
    }

    return 201;
  }

  @Override
  public HashMap[] findOrderItemByOrderId(String orderId) {
    return orderItemMapper.findByOrderId(orderId);
  }
}
