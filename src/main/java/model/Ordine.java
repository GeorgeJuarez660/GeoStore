package src.main.java.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Ordine {
    private Integer id = 0;
    private static Integer count=0;
    private Cliente cliente;
    private Prodotto prodotto;
    private Timestamp data_ordine;
    private Integer quantita;
    private BigDecimal prezzo_unitario;
    private Stato stato;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public BigDecimal getPrezzo_unitario() {
        return prezzo_unitario;
    }

    public void setPrezzo_unitario(BigDecimal prezzo_unitario) {
        this.prezzo_unitario = prezzo_unitario;
    }

    public Timestamp getData_ordine() {
        return data_ordine;
    }

    public void setData_ordine(Timestamp data_ordine) {
        this.data_ordine = data_ordine;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public Ordine() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordine ordine1 = (Ordine) o;
        return Objects.equals(id, ordine1.id) && Objects.equals(cliente, ordine1.cliente) && Objects.equals(prodotto, ordine1.prodotto) && Objects.equals(quantita, ordine1.quantita) && Objects.equals(prezzo_unitario, ordine1.prezzo_unitario) && Objects.equals(data_ordine, ordine1.data_ordine) && Objects.equals(stato, ordine1.stato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, prodotto, quantita, prezzo_unitario, data_ordine, stato);
    }

    public Ordine(Integer id, Cliente cliente, Prodotto prodotto, Integer quantita, BigDecimal prezzo_unitario, Timestamp data_ordine, Stato stato) {
        this.id = id;
        this.cliente = cliente;
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzo_unitario = prezzo_unitario;
        this.data_ordine = data_ordine;
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "id=" + id +
                ", cliente='" + cliente + '\'' +
                ", prodotto='" + prodotto + '\'' +
                ", quantita=" + quantita +
                ", prezzo_unitario=" + prezzo_unitario +
                ", data_ordine=" + data_ordine +
                ", stato=" + stato +
                '}';
    }
}
