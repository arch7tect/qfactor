package ru.neoflex.qfactor.gl.entities;

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
    public Long currencyId;
    public BigDecimal amount;

    public static GLRest add(GLAccount glAccount, Long currencyId) {
        GLRest rest = new GLRest();
        rest.glAccount = glAccount;
        rest.currencyId = currencyId;
        rest.amount = BigDecimal.ZERO;
        rest.persist();
        return rest;
    }
}
