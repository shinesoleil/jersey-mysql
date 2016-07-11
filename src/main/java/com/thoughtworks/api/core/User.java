package com.thoughtworks.api.core;

import java.util.Map;

public interface User {
  String getId();
  void setId(String id);

  String getName();
  void setName(String name);

  Map<String, Object> toJson();

}
