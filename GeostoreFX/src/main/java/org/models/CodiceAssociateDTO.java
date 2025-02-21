package org.models;

public class CodiceAssociateDTO {
    private String key;
    private Integer idCodice;
    private String codiceAdmin;
    private Integer idUtente;
    private String emailUtente;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getIdCodice() {
        return idCodice;
    }

    public void setIdCodice(Integer idCodice) {
        this.idCodice = idCodice;
    }

    public String getCodiceAdmin() {
        return codiceAdmin;
    }

    public void setCodiceAdmin(String codiceAdmin) {
        this.codiceAdmin = codiceAdmin;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public boolean checkNotNullCodiceAssociato(CodiceAssociateDTO ca){
        boolean canCU = true;

        if(ca.getIdCodice() == null){
            canCU = false;
        }
        if(ca.getEmailUtente() == null || ca.getEmailUtente().isEmpty() || ca.getEmailUtente().isBlank()){
            canCU = false;
        }

        return canCU;

    }
}
