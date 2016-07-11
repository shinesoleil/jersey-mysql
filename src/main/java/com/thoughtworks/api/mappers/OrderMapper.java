package com.thoughtworks.api.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface OrderMapper {
  HashMap[] find(@Param("userId") int userId);

  HashMap findById(@Param("userId") int userId,
                   @Param("orderId") int orderId);

  void save(@Param("id") String id,
            @Param("name") String name,
            @Param("address") String address,
            @Param("phone") String phone,
            @Param("userId") int userId);

  int countOrders();
}
