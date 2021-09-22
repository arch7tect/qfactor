package ru.neoflex.qfactor;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@UserDefinition
@Table(name = "base_user")
public class User extends PanacheEntity {
    @Username
    public String userName;
    @Password
    public String password;
    @Roles
    public String role;

    public static void add(String username, String password, String role) {
        User user = new User();
        user.userName = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.role = role;
        user.persist();
    }
}
