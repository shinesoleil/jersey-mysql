package com.thoughtworks.api.records;

import com.thoughtworks.api.core.Order;
import com.thoughtworks.api.core.OrderItem;

import java.util.List;

public class OrderRecord implements Order{
  private String id;
  private String name;
  private String address;
  private String phone;
  private List<OrderItem> orderItems;

  @Override
  public String getId() {
    return this.id ;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getAddress() {
    return this.address;
  }

  @Override
  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String getPhone() {
    return this.phone;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  @Override
  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }
}
