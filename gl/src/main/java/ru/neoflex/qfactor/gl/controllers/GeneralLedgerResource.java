package ru.neoflex.qfactor.gl.controllers;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import ru.neoflex.qfactor.gl.entities.GeneralLedger;

@ResourceProperties(path = "/api/gl/generalledger")
public interface GeneralLedgerResource extends PanacheEntityResource<GeneralLedger, Long> {
}