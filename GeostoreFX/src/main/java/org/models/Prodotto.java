package org.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Prodotto {
    private Integer id = 0;
    private static Integer count=0;
    private String nome;
    private BigDecimal prezzo;
    private Disponibilita disponibilita;
    private Materiale materiale;
    private Categoria categoria;
    private Integer quantita_disp;

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

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public Disponibilita getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(Disponibilita disponibilita) {
        this.disponibilita = disponibilita;
    }

    public Materiale getMateriale() {
        return materiale;
    }

    public void setMateriale(Materiale materiale) {
        this.materiale = materiale;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getQuantita_disp() {
        return quantita_disp;
    }

    public void setQuantita_disp(Integer quantita_disp) {
        this.quantita_disp = quantita_disp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodotto prodotto = (Prodotto) o;
        return Objects.equals(id, prodotto.id) && Objects.equals(nome, prodotto.nome) && Objects.equals(prezzo, prodotto.prezzo) && Objects.equals(disponibilita, prodotto.disponibilita) && Objects.equals(materiale, prodotto.materiale) && Objects.equals(categoria, prodotto.categoria) && Objects.equals(quantita_disp, prodotto.quantita_disp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, prezzo, disponibilita, materiale, categoria, quantita_disp);
    }

    public Prodotto() {
    }

    public Prodotto(Integer id, String nome, BigDecimal prezzo, Disponibilita disponibilita, Materiale materiale, Categoria categoria, Integer quantita_disp) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.disponibilita = disponibilita;
        this.materiale = materiale;
        this.categoria = categoria;
        this.quantita_disp = quantita_disp;
    }

    @Override
    public String toString() {
        return "Oggetto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", disponibilita='" + disponibilita + '\'' +
                ", materiale='" + materiale + '\'' +
                ", categoria='" + categoria + '\'' +
                ", quantita_disp=" + quantita_disp +
                '}';
    }

    public boolean checkNotNullProdotto(Prodotto p){
        boolean canCU = true;

        if(p.getNome() == null || p.getNome().isEmpty() || p.getNome().isBlank()){
            canCU = false;
        }
        if(p.getDisponibilita() == null || p.getDisponibilita().getId() == null){
            canCU = false;
        }
        if(p.getCategoria() == null || p.getCategoria().getId() == null){
            canCU = false;
        }
        if(p.getMateriale() == null || p.getMateriale().getId() == null){
            canCU = false;
        }

        return canCU;
    }
}
