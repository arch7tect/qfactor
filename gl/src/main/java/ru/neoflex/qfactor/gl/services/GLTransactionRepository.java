package ru.neoflex.qfactor.gl.services;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import ru.neoflex.qfactor.gl.entities.GLAccount;
import ru.neoflex.qfactor.gl.entities.GLRest;
import ru.neoflex.qfactor.gl.entities.GLTransaction;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApplicationScoped
public class GLTransactionRepository implements PanacheRepository<GLTransaction> {

    public GLTransaction transact(GLTransaction transaction) {
        GLRest dRest = (GLRest) GLRest.find("glAccount=?1 and currencyId=?2", transaction.debit, transaction.currencyId).singleResultOptional().
                orElseGet(()->GLRest.add(transaction.debit, transaction.currencyId));
        GLRest cRest = (GLRest) GLRest.find("glAccount=?1 and currencyId=?2", transaction.credit, transaction.currencyId).singleResultOptional().
                orElseGet(()->GLRest.add(transaction.credit, transaction.currencyId));
        dRest.amount = dRest.getAmount().subtract(transaction.amount);
        if (dRest.amount.compareTo(BigDecimal.ZERO) == 0) {
            dRest.delete();
        }
        cRest.amount = cRest.getAmount().add(transaction.amount);
        if (cRest.amount.compareTo(BigDecimal.ZERO) == 0) {
            cRest.delete();
        }
        getEntityManager().persist(transaction);
        return transaction;
    }
}
