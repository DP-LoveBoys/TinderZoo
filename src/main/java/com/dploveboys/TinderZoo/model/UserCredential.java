package com.dploveboys.TinderZoo.model;

import com.dploveboys.TinderZoo.service.encryption;
import org.apache.tomcat.util.net.openssl.ciphers.Encryption;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="credentials")
public class UserCredential{

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long id;

    //@Column(nullable = false, unique = true, length = 50)     //uncomment these to have them autocreated by spring (no need)
    private String email;

    //@Column(nullable = false, unique = false, length = 50)
    private String name;

    //@Column(nullable = false, unique = false, length = 50)
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authenticationProvider;

    public UserCredential(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public UserCredential() {
    }

    @Override
    public String toString() {
        return "UserCredential{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCredential that = (UserCredential) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
}