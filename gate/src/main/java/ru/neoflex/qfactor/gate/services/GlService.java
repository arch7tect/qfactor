package ru.neoflex.qfactor.gate.services;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import ru.neoflex.qfactor.apis.gl.GlApi;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface GlService extends GlApi {
}
