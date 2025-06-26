package org.utility.crud;

import org.models.Codice;

import java.util.HashMap;

public interface codiciCRUD {

    //Interfaccia dove dichiaro metodi astratti che mi serviranno per andare a implementarli nella classe DisponibilitaRepository

    //metodi override per operazioni CRUD con database
    public int insertCodiceWithDB(Integer id, Codice c);
    public HashMap<Integer, Codice> getCodiciWithDB();
    public Codice getCodiceWithDB(Integer id);
    public int updateCodiceWithDB(Integer id, Codice newC);
    public int deleteCodiceWithDB(Integer id);

}
