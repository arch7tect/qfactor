package ru.neoflex.qfactor.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Data
public class GLRest extends PanacheEntity {
    @ManyToOne
    public GLAccount glAccount;
    @ManyToOne
    public Currency currency;
    public BigDecimal amount;

    public static GLRest add(GLAccount glAccount, Currency currency) {
        GLRest rest = new GLRest();
        rest.glAccount = glAccount;
        rest.currency = currency;
        rest.amount = BigDecimal.ZERO;
        rest.persist();
        return rest;
    }
}
