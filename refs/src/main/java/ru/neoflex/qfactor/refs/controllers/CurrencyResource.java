package ru.neoflex.qfactor.refs.controllers;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import ru.neoflex.qfactor.refs.entities.Currency;

@ResourceProperties(path = "/api/refs/currency")
public interface CurrencyResource extends PanacheEntityResource<Currency, Long> {
}