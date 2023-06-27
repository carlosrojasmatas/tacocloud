package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Ingredient;
import com.sia.tacocloud.model.Order;
import com.sia.tacocloud.model.Taco;
import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations operations;

    @Autowired
    public JdbcOrderRepository(JdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        var psf = new PreparedStatementCreatorFactory(
                "insert into Taco_Order " +
                        ("delivery_name,delivery_street,delivery_city," +
                                "delivery_state,delivery_zip,cc_number," +
                                "cc_expiration,cc_cvv,placed_at" +
                                "values(?,?,?,?,?,?,?,?,?)"),
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR);
        psf.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());
        var psc = psf.newPreparedStatementCreator(Arrays.asList(
                order.getName(),
                order.getStreet(),
                order.getCity(),
                order.getState(),
                order.getZip(),
                order.getCcNumber(),
                order.getCcExpiration(),
                order.getCcCVV(),
                order.getPlacedAt()
        ));

        var gkh = new GeneratedKeyHolder();
        operations.update(psc, gkh);
        long orderId = gkh.getKey().longValue();
        order.setId(orderId);
        int i = 0;
        for (Taco taco : order.getTacos()) {
            saveTaco(orderId, i++, taco);
        }
        return order;
    }

    private long saveTaco(long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        var pscf = new PreparedStatementCreatorFactory("insert into Taco" +
                "(name,created_at,taco_order,taco_order_key) " +
                "values(?,?,?,?)",
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG);

        pscf.setReturnGeneratedKeys(true);
        var psc = pscf.newPreparedStatementCreator(Arrays.asList(
                taco.getTacoName(),
                taco.getCreatedAt(),
                orderId,
                orderKey
        ));
        var keyHolder = new GeneratedKeyHolder();
        operations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        saveIngredients(tacoId, taco.getIngredients());

        return tacoId;
    }

    private void saveIngredients(long tacoId, List<Ingredient> ingredients) {
        int key = 0;
        ingredients.forEach(ingredient -> {
            operations.update(
                    "insert into Ingredients_Ref (ingredient,taco,taco_key) values(?,?,?)",
                    ingredient.getName(), tacoId, key);
        });
    }
}
