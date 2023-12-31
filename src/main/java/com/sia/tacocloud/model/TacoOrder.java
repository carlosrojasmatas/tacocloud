package com.sia.tacocloud.model;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Table("orders")
public final class TacoOrder {

    @PrimaryKey
    private UUID id = Uuids.timeBased();
    private Date placedAt = new Date();

    @NotBlank(message = "Delivery name is required")
    private String name;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    private String zip;

    @CreditCardNumber(message = "Credit card number is required")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([2-9][0-9])$", message = "Must be formated MM/YY")
    private String ccExpiration;

    @Digits(integer = 3,fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private List<TacoUDT> tacos = new ArrayList<>();

    public void addTaco(TacoUDT taco) {
        tacos.add(taco);
    }

}


