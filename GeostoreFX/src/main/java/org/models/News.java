package org.models;

import java.sql.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class News {
    private Integer id = 0;
    private static Integer count=0;
    private String testo;
    private Utente utente;
    private Date dataPub;
    private Date dataMod;

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

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Date getDataPub() {
        return dataPub;
    }

    public void setDataPub(Date dataPub) {
        this.dataPub = dataPub;
    }

    public Date getDataMod() {
        return dataMod;
    }

    public void setDataMod(Date dataMod) {
        this.dataMod = dataMod;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) && Objects.equals(testo, news.testo) && Objects.equals(dataPub, news.dataPub) && Objects.equals(dataMod, news.dataMod) && Objects.equals(utente, news.utente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, testo, dataPub, dataMod, utente);
    }

    public News() {
    }

    public News(String testo) {
        this.testo = testo;
    }

    @Override
    public String toString() {
        return "News{" +
                "testo='" + testo + '\'' +
                "dataPub='" + dataPub + '\'' +
                "dataMod='" + dataMod + '\'' +
                "utente='" + utente + '\'' +
                '}';
    }

    public boolean checkNotNullNotizia(News n){
        boolean canCU = true;

        if(n.getTesto() == null || n.getTesto().isEmpty() || n.getTesto().isBlank()){
            canCU = false;
        }

        return canCU;

    }

    public String dynamicText(String preText, ResourceBundle resLang){
        if(preText.contains("PRD-CN1")){
            preText = preText.replace("PRD-CN1", resLang.getString("auto.news.createdProduct.1st"));
        }
        if(preText.contains("PRD-CN2")){
            preText = preText.replace("PRD-CN2", resLang.getString("auto.news.createdProduct.2nd"));
        }
        if(preText.contains("PRD-CN3")){
            preText = preText.replace("PRD-CN3", resLang.getString("auto.news.createdProduct.3rd"));
        }

        return preText;
    }
}
