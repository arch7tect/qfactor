package ru.neoflex.qfactor.refs.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.panache.common.Sort;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;
import ru.neoflex.qfactor.common.controllers.BaseResource;
import ru.neoflex.qfactor.refs.entities.Currency;
import ru.neoflex.qfactor.refs.entities.Party;
import ru.neoflex.qfactor.refs.services.CurrencyRepository;
import ru.neoflex.qfactor.refs.services.PartyRepository;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Authenticated
@Path("/refs")
@Tags(value = @Tag(name = "ref", description = "All references methods"))
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefsResource extends BaseResource {
    @Inject
    EntityManager entityManager;
    @Inject
    CurrencyRepository currencyRepository;
    @Inject
    PartyRepository partyRepository;

    @RolesAllowed("user")
    @GET
    @Path("/currency")
    public List<Currency> getCurrencyList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") Optional<List<String>> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("0") int pageSize
    ) {
        return super.getTList(currencyRepository, filter, sortQuery, pageIndex, pageSize);
    }

    @RolesAllowed("user")
    @GET
    @Path("/currency/{id}")
    public Currency getCurrency(
            @PathParam("id") Long id
    ) {
        return super.getT(currencyRepository, id);
    }

    @Transactional
    @RolesAllowed("admin")
    @POST
    @Path("/currency")
    public Currency insertCurrency(
            Currency entity
    ) {
        return super.insertT(currencyRepository, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @PUT
    @Path("/currency/{id}")
    public Currency updateCurrency(
            @PathParam("id") Long id,
            Currency entity
    ) {
        return super.updateT(currencyRepository, id, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @DELETE
    @Path("/currency/{id}")
    public void deleteCurrency(
            @PathParam("id") Long id
    ) {
        super.deleteT(currencyRepository, id);
    }

    @GET
    @RolesAllowed("user")
    @Path("/party")
    public List<Party> getPartyList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") Optional<List<String>> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("0") int pageSize
    ) {
        return super.getTList(partyRepository, filter, sortQuery, pageIndex, pageSize);
    }

    @GET
    @RolesAllowed("user")
    @Path("/party/{id}")
    public Party getParty(
            @PathParam("id") Long id
    ) {
        return super.getT(partyRepository, id);
    }

    @Transactional
    @RolesAllowed("admin")
    @POST
    @Path("/party")
    public Party insertParty(
            Party entity
    ) {
        return super.insertT(partyRepository, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @PUT
    @Path("/party/{id}")
    public Party updateParty(
            @PathParam("id") Long id,
            Party entity
    ) {
        return super.updateT(partyRepository, id, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @DELETE
    @Path("/party/{id}")
    public void deleteParty(
            @PathParam("id") Long id
    ) {
        super.deleteT(partyRepository, id);
    }

    @RolesAllowed("user")
    @POST
    @Path("/query")
    public List<Object> getQueryResults(@QueryParam("query") String query, List<Object> params) throws JsonProcessingException {
        return super.getQueryResults(entityManager, query, params);
    }
}
