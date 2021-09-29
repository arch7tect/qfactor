package ru.neoflex.qfactor.gl.controllers;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import ru.neoflex.qfactor.gl.entities.GLAccount;

@ResourceProperties(path = "/api/gl/glaccount")
public interface GLAccountResource extends PanacheEntityResource<GLAccount, Long> {
}