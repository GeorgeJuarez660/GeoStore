package org.models;

import java.util.Objects;

public class Categoria {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id) && Objects.equals(nome, categoria.nome) && Objects.equals(codice, categoria.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, codice);
    }

    public Categoria() {
    }

    public Categoria(String nome, String codice) {
        this.nome = nome;
        this.codice = codice;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nome='" + nome + '\'' +
                "codice='" + codice + '\'' +
                '}';
    }

    public boolean checkNotNullCategoria(String nomeIt, String nomeEn, String nomeJa){
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
