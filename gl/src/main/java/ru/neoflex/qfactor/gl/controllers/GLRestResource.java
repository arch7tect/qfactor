package ru.neoflex.qfactor.gl.controllers;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import ru.neoflex.qfactor.gl.entities.GLRest;

@ResourceProperties(path = "/api/gl/glrest")
public interface GLRestResource extends PanacheEntityResource<GLRest, Long> {
}