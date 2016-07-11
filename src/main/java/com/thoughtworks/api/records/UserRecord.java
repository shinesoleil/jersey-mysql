package com.thoughtworks.api.records;

import com.thoughtworks.api.core.User;

import java.util.HashMap;
import java.util.Map;

public class UserRecord implements User {
  private String id;
  private String name;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Map<String, Object> toJson() {
    Map<String, Object> map = new HashMap<>();

    map.put("uri", "/users/" + id);
    map.put("name", name);

    return map;
  }


}
