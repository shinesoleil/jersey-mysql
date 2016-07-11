package com.thoughtworks.api.records;

import com.thoughtworks.api.core.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductRecord implements Product {
  private String id;
  private String name;
  private String description;
  private float price;
  private int rating;

  @Override
  public String getId() {
    return this.id;
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
  public String getDescription() {
    return this.description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public float getPrice() {
    return this.price;
  }

  @Override
  public void setPrice(float price) {
    this.price = price;
  }

  @Override
  public int getRating() {
    return this.rating;
  }

  @Override
  public void setRating(int rating) {
    this.rating = rating;
  }

  @Override
  public Map<String, Object> toJson() {
    Map<String, Object> map = new HashMap<>();

    map.put("uri", "/products/" + id);
    map.put("name", name);
    map.put("description", description);
    map.put("price", price);
    map.put("rating", rating);

    return map;
  }
}
