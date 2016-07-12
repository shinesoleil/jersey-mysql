package com.thoughtworks.api.core;

import java.util.HashMap;
import java.util.Map;

public interface OrderRepository {
  String generateId();

  HashMap[] find(String userId);

  Order findById(String userId, String orderId);

  int save(Map info, String userId);

  HashMap[] findOrderItemByOrderId(String orderId);
}
