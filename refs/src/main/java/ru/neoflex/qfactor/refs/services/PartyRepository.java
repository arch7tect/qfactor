package ru.neoflex.qfactor.refs.services;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import ru.neoflex.qfactor.refs.entities.Currency;
import ru.neoflex.qfactor.refs.entities.Party;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PartyRepository implements PanacheRepository<Party> {

}
