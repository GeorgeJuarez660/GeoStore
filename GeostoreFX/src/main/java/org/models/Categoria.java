package org.models;

import java.util.Objects;

public class Categoria {
    private Integer id = 0;
    private static Integer count=0;
    private String nome;
    private String lingua;

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

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id) && Objects.equals(nome, categoria.nome) && Objects.equals(lingua, categoria.lingua);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, lingua);
    }

    public Categoria() {
    }

    public Categoria(String nome, String lingua) {
        this.nome = nome;
        this.lingua = lingua;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nome='" + nome + '\'' +
                "lingua='" + lingua + '\'' +
                '}';
    }

    public boolean checkNotNullCategoria(Categoria c){
        boolean canCU = true;

        if(c.getNome() == null || c.getNome().isEmpty() || c.getNome().isBlank()){
            canCU = false;
        }

        return canCU;

    }
}
