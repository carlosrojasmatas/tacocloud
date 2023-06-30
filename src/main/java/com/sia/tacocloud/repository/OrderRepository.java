package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.TacoOrder;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder,Long> {

    List<TacoOrder> findByZip(String zip);

    List<TacoOrder> findByNameAndPlacedAtBetween(String zip, Date startDate, Date endDate);

    List<TacoOrder> findByNameAndCityIsIgnoreCase(String name, String city);

    List<TacoOrder> findByCityOrderByName(String city);

    @Query("from TacoOrder o where o.city = 'Seattle'")
    List<TacoOrder> findOrdersDeliveredInSeattle();

}
