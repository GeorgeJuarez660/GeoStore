package org.utility.crud;

import org.models.Materiale;

import java.util.HashMap;

public interface materialiCRUD {

    //Interfaccia dove dichiaro metodi astratti che mi serviranno per andare a implementarli nella classe MateriaRepository

    //metodi override per operazioni CRUD con database
    public int insertMaterialeWithDB(Integer id, Materiale m);
    public HashMap<Integer, Materiale> getMaterialiWithDB();
    public Materiale getMaterialeWithDB(Integer id);
    public int updateMaterialeWithDB(Integer id, Materiale newM);
    public int deleteMaterialeWithDB(Integer id);

}
