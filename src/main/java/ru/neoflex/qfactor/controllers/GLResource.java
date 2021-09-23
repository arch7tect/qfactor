package ru.neoflex.qfactor.controllers;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import ru.neoflex.qfactor.entities.*;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Path("/gl")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GraphQLApi
public class GLResource {
    @Inject
    EntityManager entityManager;

    @GET
    @Path("/rests")
    @Query("allRests")
    @Description("Get all rests")
    public List<GLRest> getRests() {
        return GLRest.listAll();
    }

    @GET
    @Path("/rests")
    @Query("filteredRests")
    @Description("Get filtered rests")
    public List<GLRest> getFilteredRests(String filter) {
        return GLRest.find(filter).list();
    }

    @GET
    @Path("/query")
    public List getQueryResults(String query) {
        return entityManager.createQuery(query).getResultList();
    }

    public GLTransaction transact(Date date, Currency currency, GLAccount debit, GLAccount credit, BigDecimal amount) {
        GLRest dRest = (GLRest) GLRest.find("glAccount=?1 and currency=?2", debit, currency).singleResultOptional().orElseGet(()->GLRest.add(debit, currency));
        GLRest cRest = (GLRest) GLRest.find("glAccount=?1 and currency=?2", credit, currency).singleResultOptional().orElseGet(()->GLRest.add(credit, currency));
        dRest.amount = dRest.getAmount().subtract(amount);
        cRest.amount = cRest.getAmount().add(amount);
        return GLTransaction.add(dRest, cRest, currency, amount, date);
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
        Date today = new Date();
        transact(today, rub, na, oa, new BigDecimal(100));
        transact(today, rub, na, ra, new BigDecimal(200));
        transact(today, rub, ra, oa, new BigDecimal(25));
        transact(today, usd, na, oa, new BigDecimal(10));
    }
}
