package org.models;

import java.util.Objects;

public class Materiale {
    private Integer id = 0;
    private static Integer count=0;
    private String nome;
    private String codice;

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

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public Materiale() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materiale ordine1 = (Materiale) o;
        return Objects.equals(id, ordine1.id) && Objects.equals(nome, ordine1.nome) && Objects.equals(codice, ordine1.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, codice);
    }

    public Materiale(Integer id, String nome, String codice) {
        this.id = id;
        this.nome = nome;
        this.codice = codice;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codice='" + codice + '\'' +
                '}';
    }

    public boolean checkNotNullMateria(String nomeIt, String nomeEn, String nomeJa){
        boolean canCU = true;

        if(nomeIt == null || nomeIt.isEmpty() || nomeIt.isBlank()){
            canCU = false;
        }

        if(nomeEn == null || nomeEn.isEmpty() || nomeEn.isBlank()){
            canCU = false;
        }

        if(nomeJa == null || nomeJa.isEmpty() || nomeJa.isBlank()){
            canCU = false;
        }

        return canCU;

    }
}
