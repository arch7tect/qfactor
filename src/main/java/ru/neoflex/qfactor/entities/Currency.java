package ru.neoflex.qfactor.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "currency")
@Data
public class Currency extends PanacheEntity {
    @Column(length = 32, unique = true)
    public String code;

    public static Currency add(String code) {
        Currency currency = new Currency();
        currency.code = code;
        currency.persist();
        return currency;
    }
}
