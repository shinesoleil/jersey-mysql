package com.thoughtworks.api.core;

import java.util.List;

public interface Order {
  String getId();
  void setId(String id);

  String getName();
  void setName(String name);

  String getAddress();
  void setAddress(String address);

  String getPhone();
  void setPhone(String phone);

  List<OrderItem> getOrderItems();
  void setOrderItems(List<OrderItem> orderItems);
}
