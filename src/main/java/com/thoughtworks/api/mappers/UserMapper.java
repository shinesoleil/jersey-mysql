package com.thoughtworks.api.mappers;

import com.thoughtworks.api.core.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
  List<User> find();

  User findById(String id);

  int save(@Param("id") String id,
            @Param("name") String name);
}
