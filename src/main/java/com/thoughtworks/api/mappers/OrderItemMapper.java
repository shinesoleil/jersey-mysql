package com.thoughtworks.api.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface OrderItemMapper {
  HashMap[] findByOrderId(@Param("orderId") int orderId);

  void save(@Param("amount") float amount,
            @Param("quantity") int quantity,
            @Param("productId") int productId,
            @Param("orderId") int orderId);
}
