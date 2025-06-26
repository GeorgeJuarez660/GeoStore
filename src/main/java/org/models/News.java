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
        //crea prodotto
        if(preText.contains("PRD-CN1")){
            preText = preText.replace("PRD-CN1", resLang.getString("auto.news.createdProduct.1st"));
        }
        if(preText.contains("PRD-CN2")){
            preText = preText.replace("PRD-CN2", resLang.getString("auto.news.createdProduct.2nd"));
        }
        if(preText.contains("PRD-CN3")){
            preText = preText.replace("PRD-CN3", resLang.getString("auto.news.createdProduct.3rd"));
        }

        //modifica nome prodotto
        if(preText.contains("PRD-UNN1")){
            preText = preText.replace("PRD-UNN1", resLang.getString("auto.news.updatedNameProduct.1st"));
        }
        if(preText.contains("PRD-UNN2")){
            preText = preText.replace("PRD-UNN2", resLang.getString("auto.news.updatedNameProduct.2nd"));
        }
        if(preText.contains("PRD-UNN3")){
            preText = preText.replace("PRD-UNN3", resLang.getString("auto.news.updatedNameProduct.3rd"));
        }

        //modifica prezzo prodotto
        if(preText.contains("PRD-UNPD")){
            preText = preText.replace("PRD-UNPD", resLang.getString("auto.news.updatedPriceProduct.dec"));
        }
        if(preText.contains("PRD-UNPI")){
            preText = preText.replace("PRD-UNPI", resLang.getString("auto.news.updatedPriceProduct.inc"));
        }
        if(preText.contains("PRD-UNP2")){
            preText = preText.replace("PRD-UNP2", resLang.getString("auto.news.updatedPriceProduct.2nd"));
        }
        if(preText.contains("PRD-UNP3")){
            preText = preText.replace("PRD-UNP3", resLang.getString("auto.news.updatedPriceProduct.3rd"));
        }

        //modifica categoria prodotto
        if(preText.contains("PRD-UNC1")){
            preText = preText.replace("PRD-UNC1", resLang.getString("auto.news.updatedCategoryProduct.1st"));
        }
        if(preText.contains("PRD-UNC2")){
            preText = preText.replace("PRD-UNC2", resLang.getString("auto.news.updatedCategoryProduct.2nd"));
        }
        if(preText.contains("PRD-UNC3")){
            preText = preText.replace("PRD-UNC3", resLang.getString("auto.news.updatedCategoryProduct.3rd"));
        }
        if(preText.contains("PRD-UNC4")){
            preText = preText.replace("PRD-UNC4", resLang.getString("auto.news.updatedCategoryProduct.4th"));
        }

        //modifica disponibilità prodotto
        if(preText.contains("PRD-UNA1")){
            preText = preText.replace("PRD-UNA1", resLang.getString("auto.news.updatedAvailabilityProduct.1st"));
        }
        if(preText.contains("PRD-UNA2")){
            preText = preText.replace("PRD-UNA2", resLang.getString("auto.news.updatedAvailabilityProduct.2nd"));
        }
        if(preText.contains("PRD-UNO2")){
            preText = preText.replace("PRD-UNO2", resLang.getString("auto.news.updatedAvailabilityProduct.2NA"));
        }
        if(preText.contains("PRD-UND3")){
            preText = preText.replace("PRD-UND3", resLang.getString("auto.news.updatedAvailabilityProduct.3DA"));
        }
        if(preText.contains("PRD-UNM3")){
            preText = preText.replace("PRD-UNM3", resLang.getString("auto.news.updatedAvailabilityProduct.3EM"));
        }
        if(preText.contains("PRD-UNR3")){
            preText = preText.replace("PRD-UNR3", resLang.getString("auto.news.updatedAvailabilityProduct.3ER"));
        }
        if(preText.contains("PRD-UNO3")){
            preText = preText.replace("PRD-UNO3", resLang.getString("auto.news.updatedAvailabilityProduct.3NA"));
        }
        if(preText.contains("PRD-UNM4")){
            preText = preText.replace("PRD-UNM4", resLang.getString("auto.news.updatedAvailabilityProduct.4EM"));
        }

        //elimina prodotto
        if(preText.contains("PRD-DN1")){
            preText = preText.replace("PRD-DN1", resLang.getString("auto.news.deletedProduct.1st"));
        }
        if(preText.contains("PRD-DN2")){
            preText = preText.replace("PRD-DN2", resLang.getString("auto.news.deletedProduct.2nd"));
        }

        //crea categoria
        if(preText.contains("CAT-CN1")){
            preText = preText.replace("CAT-CN1", resLang.getString("auto.news.createdCategory.1st"));
        }
        if(preText.contains("CAT-CN2")){
            preText = preText.replace("CAT-CN2", resLang.getString("auto.news.createdCategory.2nd"));
        }

        //modifica nome prodotto
        if(preText.contains("CAT-UN1")){
            preText = preText.replace("CAT-UN1", resLang.getString("auto.news.updatedCategory.1st"));
        }
        if(preText.contains("CAT-UN2")){
            preText = preText.replace("CAT-UN2", resLang.getString("auto.news.updatedCategory.2nd"));
        }
        if(preText.contains("CAT-UN3")){
            preText = preText.replace("CAT-UN3", resLang.getString("auto.news.updatedCategory.3rd"));
        }

        //elimina categoria
        if(preText.contains("CAT-DN1")){
            preText = preText.replace("CAT-DN1", resLang.getString("auto.news.deletedCategory.1st"));
        }
        if(preText.contains("CAT-DN2")){
            preText = preText.replace("CAT-DN2", resLang.getString("auto.news.deletedCategory.2nd"));
        }

        return preText;
    }
}
