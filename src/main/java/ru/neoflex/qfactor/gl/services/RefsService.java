package ru.neoflex.qfactor.gl.services;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import ru.neoflex.qfactor.apis.ApiApi;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterRestClient
public interface RefsService extends ApiApi {
}
