package com.thoughtworks.api.core;

import java.util.HashMap;

public interface PaymentRepository {

  HashMap findByOrderId(String orderId);

  int save(String orderId, String payType, float amount);
}
