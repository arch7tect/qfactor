package ru.neoflex.qfactor.gl.controllers;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import ru.neoflex.qfactor.gl.entities.GLAccount;
import ru.neoflex.qfactor.gl.entities.GLRest;
import ru.neoflex.qfactor.gl.entities.GLTransaction;
import ru.neoflex.qfactor.gl.entities.GeneralLedger;
import ru.neoflex.qfactor.refs.entities.Currency;
import ru.neoflex.qfactor.refs.entities.Party;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Path("/api/gl")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GraphQLApi
public class GLResource {
    @Inject
    EntityManager entityManager;

    @GET
    @Path("/rests/{id}")
    @Query("getRest")
    @Description("Get Rest")
    public Party getRest(@PathParam("id") Long id) {
        return Party.findById(id);
    }

    @GET
    @Path("/rests")
    @Query("getRestList")
    @Description("Get Rest List")
    public List<GLRest> getRestList(String filter) {
        if (Objects.nonNull(filter)) {
            return GLRest.find(filter).list();
        }
        else {
            return GLRest.listAll();
        }
    }

    @GET
    @Path("/query")
    public List<Object> getQueryResults(String query) {
        return entityManager.createQuery(query).getResultList();
    }

    public GLTransaction transact(LocalDate date, Currency currency, GLAccount debit, GLAccount credit, BigDecimal amount) {
        GLRest dRest = (GLRest) GLRest.find("glAccount=?1 and currency=?2", debit, currency).singleResultOptional().orElseGet(()->GLRest.add(debit, currency));
        GLRest cRest = (GLRest) GLRest.find("glAccount=?1 and currency=?2", credit, currency).singleResultOptional().orElseGet(()->GLRest.add(credit, currency));
        dRest.amount = dRest.getAmount().subtract(amount);
        if (dRest.amount.equals(BigDecimal.ZERO)) {
            dRest.delete();
        }
        cRest.amount = cRest.getAmount().add(amount);
        if (cRest.amount.equals(BigDecimal.ZERO)) {
            cRest.delete();
        }
        return GLTransaction.add(debit, credit, currency, amount, date);
    }

    @Transactional
    public void dataSeed(@Observes StartupEvent evt) {
        GLTransaction.deleteAll();
        GLRest.deleteAll();
        GLAccount.deleteAll();
        GeneralLedger.deleteAll();
        Party.deleteAll();
        Currency.deleteAll();
        Currency rub = Currency.add("RUB");
        Currency usd = Currency.add("USD");
        Party n = Party.add("N");
        Party o = Party.add("O");
        Party r = Party.add("R");
        GeneralLedger gl = GeneralLedger.add("def", "default");
        GLAccount na = GLAccount.add(gl, n, "N");
        GLAccount oa = GLAccount.add(gl, o, "O");
        GLAccount ra = GLAccount.add(gl, r, "R");
        LocalDate today = LocalDate.now();
        transact(today, rub, na, oa, new BigDecimal(100));
        transact(today, rub, na, ra, new BigDecimal(200));
        transact(today, rub, ra, oa, new BigDecimal(25));
        transact(today, usd, na, oa, new BigDecimal(10));
        transact(today, usd, oa, ra, new BigDecimal(10));
    }
}
