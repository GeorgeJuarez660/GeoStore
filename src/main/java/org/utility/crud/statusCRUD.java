package org.utility.crud;

import org.models.Stato;

import java.util.HashMap;

public interface statusCRUD {

    //Interfaccia dove dichiaro metodi astratti che mi serviranno per andare a implementarli nella classe UtenteRepository

    //metodi override per operazioni CRUD con database
    public int insertStatoWithDB(Integer id, Stato s);
    public HashMap<Integer, Stato> getStatusWithDB();
    public Stato getStatoWithDB(String code);
    public int updateStatoWithDB(Integer id, Stato newS);
    public int deleteStatoWithDB(Integer id);

}
