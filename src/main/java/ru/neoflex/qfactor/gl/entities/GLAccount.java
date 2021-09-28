package ru.neoflex.qfactor.gl.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import ru.neoflex.qfactor.refs.entities.Party;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "GLAccount", indexes = {
        @Index(name = "idx_glaccount_ledger", columnList = "ledger_id"),
        @Index(name = "ak_glaccount_ledger_number", columnList = "ledger_id,number", unique = true)
})
@Entity
@Data
public class GLAccount extends PanacheEntity {
    @ManyToOne
    public GeneralLedger ledger;
    @ManyToOne
    public Party party;
    public String number;

    public static GLAccount add(GeneralLedger ledger, Party party, String number) {
        GLAccount glAccount = new GLAccount();
        glAccount.ledger = ledger;
        glAccount.party = party;
        glAccount.number = number;
        glAccount.persist();
        return glAccount;
    }
}
