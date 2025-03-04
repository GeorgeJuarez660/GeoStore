package org.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Cliente extends Utente {
    private String email;
    private String password;
    private BigDecimal portafoglio;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getPortafoglio() {
        return portafoglio;
    }

    public void setPortafoglio(BigDecimal portafoglio) {
        this.portafoglio = portafoglio;
    }

    public Cliente() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(email, cliente.email) && Objects.equals(password, cliente.password) && Objects.equals(portafoglio, cliente.portafoglio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, portafoglio);
    }

    public Cliente(Integer id, String nome, String cognome, String genere, Date dataNascita, String telefono, String indirizzo, String email, String password, BigDecimal portafoglio) {
        super(id, nome, cognome, genere, dataNascita, telefono, indirizzo);
        this.email = email;
        this.password = password;
        this.portafoglio = portafoglio;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", portafoglio=" + portafoglio +
                '}';
    }
}
