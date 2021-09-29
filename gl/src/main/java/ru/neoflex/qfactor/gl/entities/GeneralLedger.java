package ru.neoflex.qfactor.gl.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class GeneralLedger extends PanacheEntity {
    @Column(length = 32, unique = true)
    public String code;
    public String name;

    public static GeneralLedger add(String code, String name) {
        GeneralLedger gl = new GeneralLedger();
        gl.code = code;
        gl.name = name;
        gl.persist();
        return gl;
    }
}
