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
public class GLResource {
    @Inject
    EntityManager entityManager;

    @GET
    @Path("/q/rest")
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
    @Path("/q/entity")
    public List<Object> getQueryResults(String query) {
        return entityManager.createQuery(query).getResultList();
    }

}
