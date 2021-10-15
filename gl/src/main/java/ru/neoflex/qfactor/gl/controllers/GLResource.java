package ru.neoflex.qfactor.gl.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;
import ru.neoflex.qfactor.common.controllers.BaseResource;
import ru.neoflex.qfactor.gl.entities.GLAccount;
import ru.neoflex.qfactor.gl.entities.GLRest;
import ru.neoflex.qfactor.gl.entities.GLTransaction;
import ru.neoflex.qfactor.gl.entities.GeneralLedger;
import ru.neoflex.qfactor.gl.services.GLAccountRepository;
import ru.neoflex.qfactor.gl.services.GLRestRepository;
import ru.neoflex.qfactor.gl.services.GLTransactionRepository;
import ru.neoflex.qfactor.gl.services.GeneralLedgerRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Authenticated
@Path("/gl")
@Tags(value = @Tag(name = "gl", description = "All general ledger methods"))
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GLResource extends BaseResource {
    @Inject
    EntityManager entityManager;
    @Inject
    GeneralLedgerRepository generalLedgerRepository;
    @Inject
    GLAccountRepository glAccountRepository;
    @Inject
    GLRestRepository glRestRepository;
    @Inject
    GLTransactionRepository glTransactionRepository;

    @RolesAllowed("user")
    @GET
    @Path("/rest")
    public List<GLRest> getRestList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") Optional<List<String>> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("0") int pageSize
    ) {
        return super.getTList(glRestRepository, filter, sortQuery, pageIndex, pageSize);
    }

    @RolesAllowed("user")
    @GET
    @Path("/rest/{id}")
    public GLRest getRest(
            @PathParam("id") Long id
    ) {
        return super.getT(glRestRepository, id);
    }

    @Transactional
    @RolesAllowed("admin")
    @POST
    @Path("/rest")
    public GLRest insertRest(
            GLRest entity
    ) {
        return super.insertT(glRestRepository, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @PUT
    @Path("/rest/{id}")
    public GLRest updateRest(
            @PathParam("id") Long id,
            GLRest entity
    ) {
        return super.updateT(glRestRepository, id, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @DELETE
    @Path("/rest/{id}")
    public void deleteRest(
            @PathParam("id") Long id
    ) {
        super.deleteT(glRestRepository, id);
    }

    @RolesAllowed("user")
    @GET
    @Path("/account")
    public List<GLAccount> getAccountList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") Optional<List<String>> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("0") int pageSize
    ) {
        return super.getTList(glAccountRepository, filter, sortQuery, pageIndex, pageSize);
    }

    @RolesAllowed("user")
    @GET
    @Path("/account/{id}")
    public GLAccount getAccount(
            @PathParam("id") Long id
    ) {
        return super.getT(glAccountRepository, id);
    }

    @Transactional
    @RolesAllowed("admin")
    @POST
    @Path("/account")
    public GLAccount insertAccount(
            GLAccount entity
    ) {
        return super.insertT(glAccountRepository, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @PUT
    @Path("/account/{id}")
    public GLAccount updateAccount(
            @PathParam("id") Long id,
            GLAccount entity
    ) {
        return super.updateT(glAccountRepository, id, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @DELETE
    @Path("/account/{id}")
    public void deleteAccount(
            @PathParam("id") Long id
    ) {
        super.deleteT(glAccountRepository, id);
    }

    @GET
    @RolesAllowed("user")
    @Path("/transaction")
    public List<GLTransaction> getTransactionList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") Optional<List<String>> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("0") int pageSize
    ) {
        return super.getTList(glTransactionRepository, filter, sortQuery, pageIndex, pageSize);
    }

    @GET
    @RolesAllowed("user")
    @Path("/transaction/{id}")
    public GLTransaction getTransaction(
            @PathParam("id") Long id
    ) {
        return super.getT(glTransactionRepository, id);
    }

    @Transactional
    @RolesAllowed("admin")
    @POST
    @Path("/transaction")
    public GLTransaction insertTransaction(
            GLTransaction entity
    ) {
        return super.insertT(glTransactionRepository, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @PUT
    @Path("/transaction/{id}")
    public GLTransaction updateTransaction(
            @PathParam("id") Long id,
            GLTransaction entity
    ) {
        return super.updateT(glTransactionRepository, id, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @DELETE
    @Path("/transaction/{id}")
    public void deleteTransaction(
            @PathParam("id") Long id
    ) {
        super.deleteT(glTransactionRepository, id);
    }


    @RolesAllowed("user")
    @GET
    @Path("/general-ledger")
    public List<GeneralLedger> getGeneralLedgerList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") Optional<List<String>> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("0") int pageSize
    ) {
        return super.getTList(generalLedgerRepository, filter, sortQuery, pageIndex, pageSize);
    }

    @RolesAllowed("user")
    @GET
    @Path("/general-ledger/{id}")
    public GeneralLedger getGeneralLedger(
            @PathParam("id") Long id
    ) {
        return super.getT(generalLedgerRepository, id);
    }

    @Transactional
    @RolesAllowed("admin")
    @POST
    @Path("/general-ledger")
    public GeneralLedger insertGeneralLedger(
            GeneralLedger entity
    ) {
        return super.insertT(generalLedgerRepository, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @PUT
    @Path("/general-ledger/{id}")
    public GeneralLedger updateTransaction(
            @PathParam("id") Long id,
            GeneralLedger entity
    ) {
        return super.updateT(generalLedgerRepository, id, entity);
    }

    @Transactional
    @RolesAllowed("admin")
    @DELETE
    @Path("/general-ledger/{id}")
    public void deleteGeneralLedger(
            @PathParam("id") Long id
    ) {
        super.deleteT(generalLedgerRepository, id);
    }

    @POST
    @RolesAllowed("admin")
    @Path("/query")
    public List<Object> getQueryResults(@QueryParam("query") String query, List<Object> params) throws JsonProcessingException {
        return super.getQueryResults(entityManager, query, params);
    }

    @POST
    @RolesAllowed("admin")
    @Path("/transact")
    @Transactional
    public GLTransaction transact(GLTransaction transaction) {
        return glTransactionRepository.transact(transaction);
    }
}
