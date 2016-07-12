package com.thoughtworks.api.records;

import com.thoughtworks.api.core.OrderItem;

public class OrderItemRecord implements OrderItem {
  private String id;
  private float amount;
  private int quantity;

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public float getAmount() {
    return this.amount;
  }

  @Override
  public void setAmount(float amount) {
    this.amount = amount;
  }

  @Override
  public int getQuantity() {
    return this.quantity;
  }

  @Override
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
