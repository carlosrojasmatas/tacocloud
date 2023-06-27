package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientsRepository {

    private JdbcTemplate template;


    @Autowired
    public JdbcIngredientRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return template.query("select id,name,type from Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        var rs = template.query("select id,name,type from Ingredients where id = ?", this::mapRowToIngredient, id);

        if (rs.size() == 0) return Optional.empty();
        else return Optional.of(rs.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        template.update("insert into Ingredient(id,name,type) values (?,?,?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().name());

        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int number) throws SQLException {
        var id = row.getString("id");
        var name = row.getString("name");
        var type = Ingredient.Type.valueOf(row.getString("type"));
        return new Ingredient(id, name, type);
    }

    ;
}
