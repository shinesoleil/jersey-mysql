package com.thoughtworks.api.records;

import com.thoughtworks.api.core.Product;
import com.thoughtworks.api.mappers.ProductMapper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductRepository implements com.thoughtworks.api.core.ProductRepository{

  @Inject
  ProductMapper productMapper;

  @Override
  public String generateId() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  @Override
  public List<Map<String, Object>> list() {
    List<Product> products = productMapper.find();
    List<Map<String, Object>> jsonList = new ArrayList<>();

    for (Product product: products) {
      jsonList.add(product.toJson());
    }

    return jsonList;
  }

  @Override
  public Map<String, Object> findById(String id) {
    Product product =  productMapper.findById(id);

    return product.toJson();
  }

  @Override
  public int save(String name, String description, float price, int rating) {
    return productMapper.save(this.generateId(), name, description, price, rating);
  }
}
