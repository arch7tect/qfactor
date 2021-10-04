package ru.neoflex.qfactor.gl.services;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import ru.neoflex.qfactor.gl.entities.GLRest;
import ru.neoflex.qfactor.gl.entities.GeneralLedger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GeneralLedgerRepository implements PanacheRepository<GeneralLedger> {
}
