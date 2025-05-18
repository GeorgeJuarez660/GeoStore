package org.utility.crud;

import org.models.Ordine;

import java.util.HashMap;

public interface ordiniCRUD {

    //Interfaccia dove dichiaro metodi astratti che mi serviranno per andare a implementarli nella classe OrdineRepository

    //metodi override per operazioni CRUD con database
    public int insertOrdineWithDB(Integer id, Ordine o);
    public HashMap<Integer, Ordine> getOrdiniWithDB();
    public Ordine getOrdineWithDB(Integer id);
    public int updateOrdineWithDB(Integer id, Ordine newO);
    public int deleteOrdineWithDB(Integer id);

}
