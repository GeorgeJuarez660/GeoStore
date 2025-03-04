package org.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Amministratore extends Cliente{
    private Codice codiceAdmin;

    public Codice getCodiceAdmin() {
        return codiceAdmin;
    }

    public void setCodeAdmin(Codice codiceAdmin) {
        this.codiceAdmin = codiceAdmin;
    }

    public Amministratore() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amministratore cliente = (Amministratore) o;
        return Objects.equals(codiceAdmin, cliente.codiceAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceAdmin);
    }

    public Amministratore(Integer id, String nome, String cognome, String genere, Date dataNascita, String email, String password, BigDecimal portafoglio, String telefono, String indirizzo, Codice codiceAdmin) {
        super(id, nome, cognome, genere, dataNascita, email, password, indirizzo, telefono, portafoglio);
        this.codiceAdmin = codiceAdmin;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codiceAdmin='" + codiceAdmin + '\'' +
                '}';
    }
}
