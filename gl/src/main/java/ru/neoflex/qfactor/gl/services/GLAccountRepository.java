package ru.neoflex.qfactor.gl.services;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import ru.neoflex.qfactor.gl.entities.GLAccount;
import ru.neoflex.qfactor.gl.entities.GLRest;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GLAccountRepository implements PanacheRepository<GLAccount> {
}
