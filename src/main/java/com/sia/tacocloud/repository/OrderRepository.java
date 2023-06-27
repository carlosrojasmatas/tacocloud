package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Order;

public interface OrderRepository {

    public Order save(Order order);
}
