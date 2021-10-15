package ru.neoflex.qfactor.gate.controllers;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import ru.neoflex.qfactor.apis.gl.GLAccount;
import ru.neoflex.qfactor.apis.gl.GLRest;
import ru.neoflex.qfactor.apis.gl.GLTransaction;
import ru.neoflex.qfactor.apis.gl.GeneralLedger;
import ru.neoflex.qfactor.apis.refs.Currency;
import ru.neoflex.qfactor.apis.refs.Party;
import ru.neoflex.qfactor.gate.services.GlService;
import ru.neoflex.qfactor.gate.services.RefsService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Authenticated
@Path("/gate")
@Tags(value = @Tag(name = "gate", description = "GraphQL query methods"))
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GraphQLApi
@ApplicationScoped
public class GateResource {
    private static final Logger LOGGER = Logger.getLogger(GateResource.class.getName());

    @Inject
    @RestClient
    GlService glService;
    @Inject
    @RestClient
    RefsService refsService;

    @GET
    @Path("/party")
    @RolesAllowed("user")
    @Query("getPartyList")
    @Description("Get Party List")
    public List<Party> getPartyList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("0") Integer size,
            @QueryParam("sort") List<String> sort
    ) {
        return refsService.getPartyList(filter, page, size, sort);
    }

    @GET
    @Path("/party/{id}")
    @RolesAllowed("user")
    @Query("getParty")
    @Description("Get Party")
    public Party getParty(
            @PathParam("id") Long id
    ) {
        return refsService.getParty(id);
    }

    @GET
    @Path("/currency")
    @RolesAllowed("user")
    @Query("getCurrencyList")
    @Description("Get Currency List")
    public List<Currency> getCurrencyList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("0") Integer size,
            @QueryParam("sort") List<String> sort
    ) {
        return refsService.getCurrencyList(filter, page, size, sort);
    }

    @GET
    @Path("/currency/{id}")
    @RolesAllowed("user")
    @Query("getCurrency")
    @Description("Get Currency")
    public Currency getCurrency(
            @PathParam("id") Long id
    ) {
        return refsService.getCurrency(id);
    }

    @GET
    @Path("/rest")
    @RolesAllowed("user")
    @Query("getRestList")
    @Description("Get Rest List")
    public List<GLRest> getRestList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("0") Integer size,
            @QueryParam("sort") List<String> sort
    ) {
        return glService.getRestList(filter, page, size, sort);
    }

    @GET
    @Path("/rest/{id}")
    @RolesAllowed("user")
    @Query("getRest")
    @Description("Get Rest")
    public GLRest getRest(
            @PathParam("id") Long id
    ) {
        return glService.getRest(id);
    }

    @GET
    @Path("/account")
    @RolesAllowed("user")
    @Query("getAccountList")
    @Description("Get Account List")
    public List<GLAccount> getAccountList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("0") Integer size,
            @QueryParam("sort") List<String> sort
    ) {
        return glService.getAccountList(filter, page, size, sort);
    }

    @GET
    @Path("/account/{id}")
    @RolesAllowed("user")
    @Query("getAccount")
    @Description("Get Account")
    public GLAccount getAccount(
            @PathParam("id") Long id
    ) {
        return glService.getAccount(id);
    }

    @GET
    @Path("/transaction")
    @RolesAllowed("user")
    @Query("getTransactionList")
    @Description("Get Transaction List")
    public List<GLTransaction> getTransactionList(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("0") Integer size,
            @QueryParam("sort") List<String> sort
    ) {
        return glService.getTransactionList(filter, page, size, sort);
    }

    @GET
    @Path("/transaction/{id}")
    @RolesAllowed("user")
    @Query("getTransaction")
    @Description("Get Transaction")
    public GLTransaction getTransaction(
            @PathParam("id") Long id
    ) {
        return glService.getTransaction(id);
    }

    //
    // Data Loaders
    //
    public List<List<GLTransaction>> getDebits(@Source List<GLAccount> glAccounts) {
        String ids = glAccounts.stream().
                map(GLAccount::getId).
                distinct().
                map(Object::toString).
                collect(Collectors.joining(","));
        String query = String.format("from GLTransaction where debit.id in (%s)", ids);
        var cMap = glService.getTransactionList(query, 0, 0, null).stream().
                collect(Collectors.groupingBy(glTransaction -> glTransaction.getDebit().getId()));
        return glAccounts.stream().map(glAccount -> cMap.get(glAccount.getId())).collect(Collectors.toList());
    }

    public List<List<GLTransaction>> getCredits(@Source List<GLAccount> glAccounts) {
        String ids = glAccounts.stream().
                map(GLAccount::getId).
                distinct().
                map(Object::toString).
                collect(Collectors.joining(","));
        String query = String.format("from GLTransaction where credit.id in (%s)", ids);
        var cMap = glService.getTransactionList(query, 0, 0, null).stream().
                collect(Collectors.groupingBy(glTransaction -> glTransaction.getCredit().getId()));
        return glAccounts.stream().map(glAccount -> cMap.get(glAccount.getId())).collect(Collectors.toList());
    }

    public List<List<GLRest>> getRests(@Source List<GLAccount> glAccounts) {
        String ids = glAccounts.stream().
                map(GLAccount::getId).
                distinct().
                map(Object::toString).
                collect(Collectors.joining(","));
        String query = String.format("from GLRest where glAccount.id in (%s)", ids);
        var cMap = glService.getRestList(query, 0, 0, null).stream().
                collect(Collectors.groupingBy(glRest -> glRest.getGlAccount().getId()));
        return glAccounts.stream().map(glAccount -> cMap.get(glAccount.getId())).collect(Collectors.toList());
    }

    public List<List<GLAccount>> getAccounts(@Source List<Party> parties) {
        String ids = parties.stream().
                map(Party::getId).
                distinct().
                map(Object::toString).
                collect(Collectors.joining(","));
        String query = String.format("from GLAccount where partyId in (%s)", ids);
        var cMap = glService.getAccountList(query, 0, 0, null).stream().
                collect(Collectors.groupingBy(GLAccount::getPartyId));
        return parties.stream().map(party -> cMap.get(party.getId())).collect(Collectors.toList());
    }

    public List<Currency> getCurrency(@Source List<GLRest> glRests) {
        String ids = glRests.stream().
                map(GLRest::getCurrencyId).
                distinct().
                map(Object::toString).
                collect(Collectors.joining(","));
        String query = String.format("from Currency where id in (%s)", ids);
        var cMap = refsService.getCurrencyList(query, 0, -1, null).stream().
                collect(Collectors.toMap(Currency::getId, Function.identity()));
        return glRests.stream().map(glRest -> cMap.get(glRest.getCurrencyId())).collect(Collectors.toList());
    }

    public List<Currency> getTransactionCurrency(@Source List<GLTransaction> glTransactions) {
        String ids = glTransactions.stream().
                map(GLTransaction::getCurrencyId).
                distinct().
                map(Object::toString).
                collect(Collectors.joining(","));
        String query = String.format("from Currency where id in (%s)", ids);
        var idMap = refsService.getCurrencyList(query, 0, -1, null).stream().
                collect(Collectors.toMap(Currency::getId, Function.identity()));
        return glTransactions.stream().map(glTransaction -> idMap.get(glTransaction.getCurrencyId())).collect(Collectors.toList());
    }

    public List<Party> getParty(@Source List<GLAccount> glAccounts) {
        String ids = glAccounts.stream().
                map(GLAccount::getPartyId).
                distinct().
                map(Object::toString).
                collect(Collectors.joining(","));
        String query = String.format("from Party where id in (%s)", ids);
        var idMap = refsService.getPartyList(query, 0, 0, null).stream().
                collect(Collectors.toMap(Party::getId, Function.identity()));
        return glAccounts.stream().map(glAccount -> idMap.get(glAccount.getPartyId())).collect(Collectors.toList());
    }

    @Transactional
    public void dataSeed(@Observes StartupEvent evt) {
        try {
            dataSeed();
        }
        catch (ProcessingException e) {
            LOGGER.warn("Failed dataSeed: " + e.getMessage());
        }
    }

    public void dataSeed() {
        glService.getRestList("", 0, -1, null).
                forEach(rest -> glService.deleteRest(rest.getId()));
        glService.getTransactionList("", 0, -1, null).
                forEach(transaction -> glService.deleteTransaction(transaction.getId()));
        glService.getAccountList("", 0, -1, null).
                forEach(account -> glService.deleteAccount(account.getId()));
        glService.getGeneralLedgerList("", 0, -1, null).
                forEach(ledger -> glService.deleteGeneralLedger(ledger.getId()));
        refsService.getPartyList("", 0, -1, null).
                forEach(party -> refsService.deleteParty(party.getId()));
        refsService.getCurrencyList("", 0, -1, null).
                forEach(currency -> refsService.deleteCurrency(currency.getId()));
        Currency rub = refsService.insertCurrency(new Currency().code("RUB"));
        Currency usd = refsService.insertCurrency(new Currency().code("USD"));
        Party n = refsService.insertParty(new Party().name("SDM Bank"));
        Party o = refsService.insertParty(new Party().name("Oleg"));
        Party r = refsService.insertParty(new Party().name("Elena"));
        GeneralLedger gl = glService.insertGeneralLedger(new GeneralLedger().code("gl").name("General"));
        GLAccount na = glService.insertAccount(new GLAccount().number("S1234").partyId(n.getId()).ledger(gl));
        GLAccount oa = glService.insertAccount(new GLAccount().number("O3939").partyId(o.getId()).ledger(gl));
        GLAccount ra = glService.insertAccount(new GLAccount().number("E7984").partyId(r.getId()).ledger(gl));
        LocalDate today = LocalDate.now();
        glService.transact(
                new GLTransaction().date(today).currencyId(rub.getId()).
                        debit(na).credit(oa).amount(new BigDecimal(100))
        );
        glService.transact(
                new GLTransaction().date(today).currencyId(rub.getId()).
                        debit(na).credit(ra).amount(new BigDecimal(200))
        );
        glService.transact(
                new GLTransaction().date(today).currencyId(rub.getId()).
                        debit(ra).credit(oa).amount(new BigDecimal(25))
        );
        glService.transact(
                new GLTransaction().date(today).currencyId(usd.getId()).
                        debit(na).credit(oa).amount(new BigDecimal(10))
        );
        glService.transact(
                new GLTransaction().date(today).currencyId(usd.getId()).
                        debit(oa).credit(ra).amount(new BigDecimal(10))
        );
    }
}
