package ru.neoflex.qfactor.gl.controllers;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import ru.neoflex.qfactor.gl.entities.GLTransaction;

@ResourceProperties(path = "/api/gl/gltransaction")
public interface GLTransactionResource extends PanacheEntityResource<GLTransaction, Long> {
}