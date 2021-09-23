package ru.neoflex.qfactor.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class GLTransaction extends PanacheEntity {
    @ManyToOne
    public GLRest debit;
    @ManyToOne
    public GLRest credit;
    @ManyToOne
    public Currency currency;
    public BigDecimal amount;
    public Date date;

    public static GLTransaction add(GLRest debit, GLRest credit, Currency currency, BigDecimal amount, Date date) {
        GLTransaction transaction = new GLTransaction();
        transaction.debit = debit;
        transaction.credit = credit;
        transaction.currency = currency;
        transaction.amount = amount;
        transaction.date = date;
        transaction.persist();
        return transaction;
    }
}
