package ru.neoflex.qfactor.refs.controllers;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import ru.neoflex.qfactor.refs.entities.Currency;
import ru.neoflex.qfactor.refs.entities.Party;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Path("/api/refs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefsResource {
    @GET
    @Path("/parties/{id}")
    public Party getParty(@PathParam("id") Long id) {
        return Party.findById(id);
    }

    @GET
    @Path("/parties")
    public List<Party> getPartyList(@QueryParam("filter") String filter) {
        if (Objects.nonNull(filter)) {
            return Party.find(filter).list();
        }
        else {
            return Party.listAll();
        }
    }

    @GET
    @Path("/currencies/{id}")
    public Currency getCurrency(@PathParam("id") Long id) {
        return Currency.findById(id);
    }

    @GET
    @Path("/currencies")
    public List<Currency> getCurrencyList(@QueryParam("filter") String filter) {
        if (Objects.nonNull(filter)) {
            return Currency.find(filter).list();
        }
        else {
            return Currency.listAll();
        }
    }
}
