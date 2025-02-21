package org.models;

import java.sql.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class Utente {
    private Integer id = 0;
    private static Integer count=0;
    private String nome;
    private String cognome;
    private String sesso;
    private Date dataNascita;
    private String telefono;
    private String indirizzo;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Utente() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente cliente = (Utente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome) && Objects.equals(cognome, cliente.cognome) && Objects.equals(sesso, cliente.sesso) && Objects.equals(dataNascita, cliente.dataNascita) && Objects.equals(telefono, cliente.telefono) && Objects.equals(indirizzo, cliente.indirizzo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cognome, sesso, dataNascita, telefono, indirizzo);
    }

    public Utente(Integer id, String nome, String cognome, String sesso, Date dataNascita, String telefono, String indirizzo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.dataNascita = dataNascita;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", sesso='" + sesso + '\'' +
                ", dataNascita='" + dataNascita + '\'' +
                ", telefono='" + telefono + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                '}';
    }

    public boolean checkNotNullUtente(Utente u){
        boolean canCU = true;

        if(u.getNome() == null || u.getNome().isEmpty() || u.getNome().isBlank()){
            canCU = false;
        }
        if(u.getCognome() == null || u.getCognome().isEmpty() || u.getCognome().isBlank()){
            canCU = false;
        }
        if(u.getSesso() == null || u.getSesso().isEmpty() || u.getSesso().isBlank()){
            canCU = false;
        }
        else if(u.getSesso().length() != 1){
            canCU = false;
        }
        else if(!u.getSesso().equals("M") || !u.getSesso().equals("F") || !u.getSesso().equals("P") || !u.getSesso().equals("N")){
            canCU = false;
        }

        if(u.getDataNascita() == null){
            canCU = false;
        }
        if(u.getIndirizzo() == null || u.getIndirizzo().isEmpty() || u.getIndirizzo().isBlank()){
            canCU = false;
        }
        if(u.getTelefono() == null || u.getTelefono().isEmpty() || u.getTelefono().isBlank()){
            canCU = false;
        }

        Cliente c = (Cliente) u;

        String regex = "^[a-zA-Z]+@[a-zA-Z]+\\\\.(it|com|net|org|edu)$";

        if(c.getEmail() == null || c.getEmail().isEmpty() || c.getEmail().isBlank()){
            canCU = false;
        }
        else if(!Pattern.matches(regex, c.getEmail())){
            canCU = false;
        }

        regex = "^[a-zA-Z0-9]+$";

        if(c.getPassword() == null || c.getPassword().isEmpty() || c.getPassword().isBlank()){
            canCU = false;
        }
        else if(!Pattern.matches(regex, c.getPassword())){
            canCU = false;
        }

        return canCU;

    }

    public String checkNotNullLoginCliente(Cliente c){
        String canCU = "";
        boolean areThereNull = false;

        String regex = "^[a-zA-Z]+@[a-zA-Z]+\\\\.(it|com|net|org|edu)$";

        if(c.getEmail() == null || c.getEmail().isEmpty() || c.getEmail().isBlank()){
            canCU += "EMAIL (NULLO) ";
            areThereNull = true;
        }
        else if(!Pattern.matches(regex, c.getEmail())){
            canCU += "EMAIL (FORMATO NOME@CASELLAPOSTALE.COM/IT/NET/ORG/EDU) ";
            areThereNull = true;
        }

        regex = "^[a-zA-Z0-9]+$";

        if(c.getPassword() == null || c.getPassword().isEmpty() || c.getPassword().isBlank()){
            canCU += "PASSWORD (NULLO) ";
            areThereNull = true;
        }
        else if(!Pattern.matches(regex, c.getPassword())){
            canCU += "PASSWORD (QUALCHE CARATTERE E QUALCHE NUMERO) ";
            areThereNull = true;
        }

        if(areThereNull){
            canCU = "ALCUNI CAMPI DEVONO ESSERE COMPILATI O FORMATTATI BENE: " + canCU;
        }

        return canCU;

    }

    public String checkNotNullLoginAdmin(Amministratore a){
        String canCU = "";
        boolean areThereNull = false;

        String regex = "^[a-zA-Z]+@[a-zA-Z]+\\\\.(it|com|net|org|edu)$";

        if(a.getEmail() == null || a.getEmail().isEmpty() || a.getEmail().isBlank()){
            canCU += "EMAIL (NULLO) ";
            areThereNull = true;
        }
        else if(!Pattern.matches(regex, a.getEmail())){
            canCU += "EMAIL (FORMATO NOME@CASELLAPOSTALE.COM/IT/NET/ORG/EDU) ";
            areThereNull = true;
        }

        regex = "^[a-zA-Z0-9]+$";

        if(a.getPassword() == null || a.getPassword().isEmpty() || a.getPassword().isBlank()){
            canCU += "PASSWORD (NULLO) ";
            areThereNull = true;
        }
        else if(!Pattern.matches(regex, a.getPassword())){
            canCU += "PASSWORD (QUALCHE CARATTERE E QUALCHE NUMERO) ";
            areThereNull = true;
        }

        regex = "^GS[A-Z]\\d{3}$";

        if(a.getCodeAdmin() == null || a.getCodeAdmin().isEmpty() || a.getCodeAdmin().isBlank()){
            canCU += "CODICE (NULLO) ";
            areThereNull = true;
        }
        else if(!Pattern.matches(regex, a.getCodeAdmin())){
            canCU += "CODICE (FORMATO GSX123) ";
            areThereNull = true;
        }

        if(areThereNull){
            canCU = "ALCUNI CAMPI DEVONO ESSERE COMPILATI O FORMATTATI BENE: " + canCU;
        }

        return canCU;

    }

    public boolean checkCorrectBornDate(String dataNas){
        boolean isCorrect = true;

        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$";

        if(!Pattern.matches(regex, dataNas)) {
            isCorrect = false;
        }

        return isCorrect;

    }

}
