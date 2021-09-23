package ru.neoflex.qfactor.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class Party extends PanacheEntity {
    @Column(length = 128, unique = true)
    public String name;

    public static Party add(String name) {
        Party party = new Party();
        party.name = name;
        party.persist();
        return party;
    }
}
