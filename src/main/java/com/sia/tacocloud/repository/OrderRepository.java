package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {

}
