package org.models;

import java.sql.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class Utente {
    private Integer id = 0;
    private static Integer count=0;
    private String nome;
    private String cognome;
    private String genere;
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

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
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
        return Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome) && Objects.equals(cognome, cliente.cognome) && Objects.equals(genere, cliente.genere) && Objects.equals(dataNascita, cliente.dataNascita) && Objects.equals(telefono, cliente.telefono) && Objects.equals(indirizzo, cliente.indirizzo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cognome, genere, dataNascita, telefono, indirizzo);
    }

    public Utente(Integer id, String nome, String cognome, String genere, Date dataNascita, String telefono, String indirizzo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.genere = genere;
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
                ", genere='" + genere + '\'' +
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
        if(u.getGenere() == null || u.getGenere().isEmpty() || u.getGenere().isBlank()){
            canCU = false;
        }
        else if(u.getGenere().length() != 1){
            canCU = false;
        }
        else if(!u.getGenere().equals("M") && !u.getGenere().equals("F") && !u.getGenere().equals("P") && !u.getGenere().equals("N")){
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

        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(it|com|net|org|edu)$";

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

    public boolean checkNotNullLoginCliente(Cliente c){
        boolean canCU = true;

        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(it|com|net|org|edu)$";

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

    public boolean checkNotNullLoginAdmin(Amministratore a){
        boolean canCU = true;

        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(it|com|net|org|edu)$";

        if(a.getEmail() == null || a.getEmail().isEmpty() || a.getEmail().isBlank()){
            canCU = false;
        }
        else if(!Pattern.matches(regex, a.getEmail())){
            canCU = false;
        }

        regex = "^[a-zA-Z0-9]+$";

        if(a.getPassword() == null || a.getPassword().isEmpty() || a.getPassword().isBlank()){
            canCU = false;
        }
        else if(!Pattern.matches(regex, a.getPassword())){
            canCU = false;
        }

        regex = "^GS[A-Z]\\d{3}$";

        if(a.getCodiceAdmin().getCodice() == null || a.getCodiceAdmin().getCodice().isEmpty() || a.getCodiceAdmin().getCodice().isBlank()){
            canCU = false;
        }
        else if(!Pattern.matches(regex, a.getCodiceAdmin().getCodice())){
            canCU = false;
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
