package com.thoughtworks.api.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface OrderItemMapper {
  HashMap[] findByOrderId(@Param("orderId") String orderId);

  void save(@Param("amount") float amount,
            @Param("quantity") int quantity,
            @Param("productId") String productId,
            @Param("orderId") String orderId);
}
