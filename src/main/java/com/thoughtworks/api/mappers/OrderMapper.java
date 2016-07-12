package com.thoughtworks.api.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface OrderMapper {
  HashMap[] find(@Param("userId") String userId);

  HashMap findById(@Param("userId") String userId,
                   @Param("orderId") String orderId);

  void save(@Param("id") String id,
            @Param("name") String name,
            @Param("address") String address,
            @Param("phone") String phone,
            @Param("userId") String userId);

  int countOrders();
}
