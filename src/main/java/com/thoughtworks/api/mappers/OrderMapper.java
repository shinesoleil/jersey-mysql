package com.thoughtworks.api.mappers;

import com.thoughtworks.api.core.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
  List<Order> find(@Param("userId") String userId);

  Order findById(@Param("userId") String userId,
                 @Param("orderId") String orderId);

  void save(@Param("id") String id,
            @Param("name") String name,
            @Param("address") String address,
            @Param("phone") String phone,
            @Param("userId") String userId);

  int countOrders();
}
