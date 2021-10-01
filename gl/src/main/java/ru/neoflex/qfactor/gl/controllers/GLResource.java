package ru.neoflex.qfactor.gl.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;
import ru.neoflex.qfactor.common.controllers.BaseResource;
import ru.neoflex.qfactor.gl.entities.GLAccount;
import ru.neoflex.qfactor.gl.entities.GLRest;
import ru.neoflex.qfactor.gl.entities.GLTransaction;
import ru.neoflex.qfactor.gl.entities.GeneralLedger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Path("/gl")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GLResource extends BaseResource {

    @GET
    @Path("/rest")
    public List<GLRest> getRestList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") @DefaultValue("") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize
    ) {
        Sort sort = getSortFromQuery(sortQuery);
        var query = Objects.nonNull(filter) && !filter.isBlank() ?
                (sort != null ? GLRest.find(filter, sort): GLRest.find(filter)) :
                (sort != null ? GLRest.findAll(sort) : GLRest.findAll());
        if (pageIndex > 0 && pageSize > 0) {
            query = query.page(pageIndex, pageSize);
        }
        return query.list();
    }

    @GET
    @Path("/rest/{id}")
    public GLRest getRest(
            @PathParam("id") Long id
    ) {
        GLRest result = GLRest.findById(id);
        if (result == null) {
            throw new WebApplicationException(404);
        }
        return result;
    }

    @Transactional
    @POST
    @Path("/rest")
    public GLRest insertRest(
            GLRest entity
    ) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @PUT
    @Path("/rest/{id}")
    public GLRest updateRest(
            @PathParam("id") Long id,
            GLRest entity
    ) {
        entity.id = id;
        return entityManager.merge(entity);
    }

    @Transactional
    @DELETE
    @Path("/rest/{id}")
    public void deleteRest(
            @PathParam("id") Long id
    ) {
        entityManager.remove(getRest(id));
    }

    @GET
    @Path("/account")
    public List<GLAccount> getAccountList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") @DefaultValue("") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize
    ) {
        Sort sort = getSortFromQuery(sortQuery);
        var query = Objects.nonNull(filter) && !filter.isBlank() ?
                (sort != null ? GLAccount.find(filter, sort): GLAccount.find(filter)) :
                (sort != null ? GLAccount.findAll(sort) : GLAccount.findAll());
        if (pageIndex > 0 && pageSize > 0) {
            query = query.page(pageIndex, pageSize);
        }
        return query.list();
    }

    @GET
    @Path("/account/{id}")
    public GLAccount getAccount(
            @PathParam("id") Long id
    ) {
        GLAccount result = GLAccount.findById(id);
        if (result == null) {
            throw new WebApplicationException(404);
        }
        return result;
    }

    @Transactional
    @POST
    @Path("/account")
    public GLAccount insertAccount(
            GLAccount entity
    ) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @PUT
    @Path("/account/{id}")
    public GLAccount updateAccount(
            @PathParam("id") Long id,
            GLAccount entity
    ) {
        entity.id = id;
        return entityManager.merge(entity);
    }

    @Transactional
    @DELETE
    @Path("/account/{id}")
    public void deleteAccount(
            @PathParam("id") Long id
    ) {
        entityManager.remove(getAccount(id));
    }

    @GET
    @Path("/transaction")
    public List<GLTransaction> getTransactionList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") @DefaultValue("") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize
    ) {
        Sort sort = getSortFromQuery(sortQuery);
        var query = Objects.nonNull(filter) && !filter.isBlank() ?
                (sort != null ? GLTransaction.find(filter, sort): GLTransaction.find(filter)) :
                (sort != null ? GLTransaction.findAll(sort) : GLTransaction.findAll());
        if (pageIndex > 0 && pageSize > 0) {
            query = query.page(pageIndex, pageSize);
        }
        return query.list();
    }

    @GET
    @Path("/transaction/{id}")
    public GLTransaction getTransaction(
            @PathParam("id") Long id
    ) {
        GLTransaction result = GLTransaction.findById(id);
        if (result == null) {
            throw new WebApplicationException(404);
        }
        return result;
    }

    @Transactional
    @POST
    @Path("/transaction")
    public GLTransaction insertTransaction(
            GLTransaction entity
    ) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @PUT
    @Path("/transaction/{id}")
    public GLTransaction updateTransaction(
            @PathParam("id") Long id,
            GLTransaction entity
    ) {
        entity.id = id;
        return entityManager.merge(entity);
    }

    @Transactional
    @DELETE
    @Path("/transaction/{id}")
    public void deleteTransaction(
            @PathParam("id") Long id
    ) {
        entityManager.remove(getTransaction(id));
    }


    @GET
    @Path("/general-ledger")
    public List<GeneralLedger> getGeneralLedgerList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") @DefaultValue("") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize
    ) {
        Sort sort = getSortFromQuery(sortQuery);
        var query = Objects.nonNull(filter) && !filter.isBlank() ?
                (sort != null ? GeneralLedger.find(filter, sort): GeneralLedger.find(filter)) :
                (sort != null ? GeneralLedger.findAll(sort) : GeneralLedger.findAll());
        if (pageIndex > 0 && pageSize > 0) {
            query = query.page(pageIndex, pageSize);
        }
        return query.list();
    }

    @GET
    @Path("/general-ledger/{id}")
    public GeneralLedger getGeneralLedger(
            @PathParam("id") Long id
    ) {
        GeneralLedger result = GeneralLedger.findById(id);
        if (result == null) {
            throw new WebApplicationException(404);
        }
        return result;
    }

    @Transactional
    @POST
    @Path("/general-ledger")
    public GeneralLedger insertGeneralLedger(
            GeneralLedger entity
    ) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @PUT
    @Path("/general-ledger/{id}")
    public GeneralLedger updateTransaction(
            @PathParam("id") Long id,
            GeneralLedger entity
    ) {
        entity.id = id;
        return entityManager.merge(entity);
    }

    @Transactional
    @DELETE
    @Path("/general-ledger/{id}")
    public void deleteGeneralLedger(
            @PathParam("id") Long id
    ) {
        entityManager.remove(getGeneralLedger(id));
    }

    @POST
    @Path("/query")
    public List<Object> getQueryResults(@QueryParam("query") String query, List<Object> params) throws JsonProcessingException {
        return super.getQueryResults(query, params);
    }
}
