package ru.neoflex.qfactor.gl.controllers;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.neoflex.qfactor.gl.entities.GLAccount;
import ru.neoflex.qfactor.gl.entities.GLRest;
import ru.neoflex.qfactor.gl.entities.GLTransaction;
import ru.neoflex.qfactor.gl.entities.GeneralLedger;
import ru.neoflex.qfactor.gl.services.RefsService;
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
    @Inject
    @RestClient
    RefsService refsService;

    @GET
    @Path("/rests/{id}")
    @Query("getRest")
    @Description("Get Rest")
    public GLRest getRest(@PathParam("id") Long id) {
        return GLRest.findById(id);
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

    public GLTransaction transact(LocalDate date, Long currencyId, GLAccount debit, GLAccount credit, BigDecimal amount) {
        GLRest dRest = (GLRest) GLRest.find("glAccount=?1 and currencyId=?2", debit, currencyId).singleResultOptional().orElseGet(()->GLRest.add(debit, currencyId));
        GLRest cRest = (GLRest) GLRest.find("glAccount=?1 and currencyId=?2", credit, currencyId).singleResultOptional().orElseGet(()->GLRest.add(credit, currencyId));
        dRest.amount = dRest.getAmount().subtract(amount);
        if (dRest.amount.equals(BigDecimal.ZERO)) {
            dRest.delete();
        }
        cRest.amount = cRest.getAmount().add(amount);
        if (cRest.amount.equals(BigDecimal.ZERO)) {
            cRest.delete();
        }
        return GLTransaction.add(debit, credit, currencyId, amount, date);
    }

    public ru.neoflex.qfactor.apis.Currency getCurrency(@Source GLRest glRest) {
        return refsService.getCurrency(glRest.getCurrencyId());
    }

    public ru.neoflex.qfactor.apis.Currency getCurrency(@Source GLTransaction glTransaction) {
        return refsService.getCurrency(glTransaction.currencyId);
    }

    public ru.neoflex.qfactor.apis.Party getParty(@Source GLAccount glAccount) {
        return refsService.getParty(glAccount.partyId);
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
        GLAccount na = GLAccount.add(gl, n.id, "N");
        GLAccount oa = GLAccount.add(gl, o.id, "O");
        GLAccount ra = GLAccount.add(gl, r.id, "R");
        LocalDate today = LocalDate.now();
        transact(today, rub.id, na, oa, new BigDecimal(100));
        transact(today, rub.id, na, ra, new BigDecimal(200));
        transact(today, rub.id, ra, oa, new BigDecimal(25));
        transact(today, usd.id, na, oa, new BigDecimal(10));
        transact(today, usd.id, oa, ra, new BigDecimal(10));
    }
}
