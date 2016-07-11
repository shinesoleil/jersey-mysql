package com.thoughtworks.api.records;

import com.thoughtworks.api.core.User;
import com.thoughtworks.api.mappers.UserMapper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserRepository implements com.thoughtworks.api.core.UserRepository {
  @Inject
  UserMapper userMapper;

  @Override
  public String generateId() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  @Override
  public List<Map<String, Object>> list() {
    List<User> users = userMapper.find();
    List<Map<String, Object>> jsonList = new ArrayList<>();

    for (User user: users) {
      jsonList.add(user.toJson());
    }

    return jsonList;
  }

  @Override
  public Map<String, Object> findById(String id) {
    return userMapper.findById(id).toJson();
  }

  @Override
  public int save(String name) {
    return userMapper.save(this.generateId(), name);
  }
}
