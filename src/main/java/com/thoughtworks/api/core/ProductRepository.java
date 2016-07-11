package com.thoughtworks.api.core;

import java.util.List;
import java.util.Map;

public interface ProductRepository {

  String generateId();

  List<Map<String, Object>> list();

  Map<String, Object> findById(String id);

  int save(String name, String description, float price, int rating);
}
