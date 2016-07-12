package com.thoughtworks.api.core;

public interface OrderItem {
  String getId();
  void setId(String id);

  float getAmount();
  void setAmount(float amount);

  int getQuantity();
  void setQuantity(int quantity);
}
