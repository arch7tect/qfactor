package ru.neoflex.qfactor.refs.controllers;

import io.quarkus.panache.common.Sort;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.util.StringUtil;
import ru.neoflex.qfactor.refs.entities.Currency;
import ru.neoflex.qfactor.refs.entities.Party;

import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/refs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefsResource {
    @GET
    @Path("/party")
    public List<Party> getPartyList(
            @DefaultValue("") @QueryParam("filter") String filter,
            @DefaultValue("") @QueryParam("name") String name

    ) {
        if (Objects.nonNull(filter) && !filter.isBlank()) {
            return Party.find(filter).list();
        }

        if (name != null && !name.isBlank()) {
            return Party.find("name=?1", name).list();
        }

        return Party.listAll();
    }

    @GET
    @Path("/currency")
    public List<Currency> getCurrencyList(
            @DefaultValue("") @QueryParam("filter") String filter,
            @DefaultValue("") @QueryParam("code") String code
    ) {
        if (Objects.nonNull(filter) && !filter.isBlank()) {
            return Currency.find(filter).list();
        }

        if (Objects.nonNull(code) && !code.isBlank()) {
            return Currency.find("code=?1", code).list();
        }

        return Currency.listAll();
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
