package ru.neoflex.qfactor.gl.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class GLTransaction extends PanacheEntity {
    @ManyToOne
    public GLAccount debit;
    @ManyToOne
    public GLAccount credit;
    public Long currencyId;
    public BigDecimal amount;
    public LocalDate date;

    public static GLTransaction add(GLAccount debit, GLAccount credit, Long currencyId, BigDecimal amount, LocalDate date) {
        GLTransaction transaction = new GLTransaction();
        transaction.debit = debit;
        transaction.credit = credit;
        transaction.currencyId = currencyId;
        transaction.amount = amount;
        transaction.date = date;
        transaction.persist();
        return transaction;
    }
}
