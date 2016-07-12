package com.thoughtworks.api.records;

import javax.inject.Inject;
import java.util.HashMap;

public class PaymentRepository implements com.thoughtworks.api.core.PaymentRepository{
  @Inject
  PaymentMapper paymentMapper;

  @Override
  public HashMap findByOrderId(String orderId) {
    return paymentMapper.findByOrderId(Integer.valueOf(orderId));
  }

  @Override
  public int save(String orderId, String payType, float amount) {

    System.out.println(orderId);
    paymentMapper.save(payType, amount, Integer.valueOf(orderId));

    return 201;
  }
}
