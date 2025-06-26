package org.models;

import java.util.Objects;
import java.util.regex.Pattern;

public class Codice {
    private Integer id = 0;
    private static Integer count=0;
    private String codice;
    private String descrizione;

    public Integer getId() {
        return id;
    }

    public void setCount() {
        count++;
        id = count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Codice codice1 = (Codice) o;
        return Objects.equals(id, codice1.id) && Objects.equals(codice, codice1.codice) && Objects.equals(descrizione, codice1.descrizione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codice, descrizione);
    }

    public Codice() {
    }

    public Codice(Integer id, String codice, String descrizione) {
        this.id = id;
        this.codice = codice;
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "Oggetto{" +
                "id=" + id +
                ", codice='" + codice + '\'' +
                ", descrizione=" + descrizione +
                '}';
    }

    public boolean checkNotNullCodice(Codice c){
        boolean canCU = true;

        String regex = "^GS[A-Z]\\d{3}$";

        if(c.getCodice() == null || c.getCodice().isEmpty() || c.getCodice().isBlank()){
            canCU = false;
        }
        else if(!Pattern.matches(regex, c.getCodice())){
            canCU = false;
        }

        if(c.getDescrizione() == null || c.getDescrizione().isEmpty() || c.getDescrizione().isBlank()){
            canCU = false;
        }

        return canCU;
    }
}
