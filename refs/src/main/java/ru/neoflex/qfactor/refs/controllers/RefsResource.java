package ru.neoflex.qfactor.refs.controllers;

import io.quarkus.panache.common.Sort;
import io.quarkus.runtime.StartupEvent;
import ru.neoflex.qfactor.common.controllers.BaseResource;
import ru.neoflex.qfactor.refs.entities.Currency;
import ru.neoflex.qfactor.refs.entities.Party;

import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;

@Path("/refs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefsResource extends BaseResource {

    @GET
    @Path("/currency")
    public List<Currency> getCurrencyList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") @DefaultValue("") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize
    ) {
        Sort sort = getSortFromQuery(sortQuery);
        var query = Objects.nonNull(filter) && !filter.isBlank() ?
                (sort != null ? Currency.find(filter, sort) : Currency.find(filter)) :
                (sort != null ? Currency.findAll(sort) : Currency.findAll());
        if (pageIndex > 0 && pageSize > 0) {
            query = query.page(pageIndex, pageSize);
        }
        return query.list();
    }

    @GET
    @Path("/currency/{id}")
    public Currency getCurrency(
            @PathParam("id") Long id
    ) {
        Currency result = Currency.findById(id);
        if (result == null) {
            throw new WebApplicationException(404);
        }
        return result;
    }

    @Transactional
    @POST
    @Path("/currency")
    public Currency insertCurrency(
            Currency entity
    ) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @PUT
    @Path("/currency/{id}")
    public Currency updateCurrency(
            @PathParam("id") Long id,
            Currency entity
    ) {
        entity.id = id;
        return entityManager.merge(entity);
    }

    @Transactional
    @DELETE
    @Path("/currency/{id}")
    public void deleteCurrency(
            @PathParam("id") Long id
    ) {
        entityManager.remove(getT(id));
    }

    @GET
    @Path("/party")
    public List<Party> getPartyList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") @DefaultValue("") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize
    ) {
        Sort sort = getSortFromQuery(sortQuery);
        var query = Objects.nonNull(filter) && !filter.isBlank() ?
                (sort != null ? Party.find(filter, sort) : Party.find(filter)) :
                (sort != null ? Party.findAll(sort) : Party.findAll());
        if (pageIndex > 0 && pageSize > 0) {
            query = query.page(pageIndex, pageSize);
        }
        return query.list();
    }

    @GET
    @Path("/party/{id}")
    public Party getParty(
            @PathParam("id") Long id
    ) {
        Party result = Party.findById(id);
        if (result == null) {
            throw new WebApplicationException(404);
        }
        return result;
    }

    @Transactional
    @POST
    @Path("/party")
    public Party insertParty(
            Party entity
    ) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @PUT
    @Path("/party/{id}")
    public Party updateParty(
            @PathParam("id") Long id,
            Party entity
    ) {
        entity.id = id;
        return entityManager.merge(entity);
    }

    @Transactional
    @DELETE
    @Path("/party/{id}")
    public void deleteParty(
            @PathParam("id") Long id
    ) {
        entityManager.remove(getT(id));
    }

    @Transactional
    public void dataSeed(@Observes StartupEvent evt) {
        Party.deleteAll();
        Currency.deleteAll();
        Currency rub = Currency.add("RUB");
        Currency usd = Currency.add("USD");
        Party n = Party.add("N");
        Party o = Party.add("O");
        Party r = Party.add("R");
    }
}
