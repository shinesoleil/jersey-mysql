package com.thoughtworks.api.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface PaymentMapper {

  HashMap findByOrderId(@Param("orderId") int orderId);

  void save(@Param("payType") String payType,
            @Param("amount") float amount,
            @Param("orderId") int orderId);
}
