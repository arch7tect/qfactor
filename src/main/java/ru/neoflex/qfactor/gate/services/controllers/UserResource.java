package ru.neoflex.qfactor.gate.services.controllers;

import io.quarkus.runtime.StartupEvent;
import ru.neoflex.qfactor.entities.User;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @GET
    @Path("/me")
    @RolesAllowed("user")
    public Principal me(@Context SecurityContext securityContext) {
        return securityContext.getUserPrincipal();
    }

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
        User.deleteAll();
        User.add("admin", "admin", "admin");
        User.add("user", "user", "user");
    }
}
