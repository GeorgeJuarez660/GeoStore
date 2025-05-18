package org.utility.crud;

import org.models.News;

import java.sql.Date;
import java.util.ArrayList;

public interface newsCRUD {

    //Interfaccia dove dichiaro metodi astratti che mi serviranno per andare a implementarli nella classe NewsRepository

    //metodi override per operazioni CRUD con database
    public int insertNotizieWithDB(Date dataPub, Date dataMod, String testo, Integer idAdmin);
    public ArrayList<News> getNotizieWithDB();
    public ArrayList<News> getNotizieByKeywordWithDB(String keyword);
    public int updateNotizieWithDB(Integer id, Date newDataMod, String newTesto, Integer newIdAdmin);
    public int deleteNotizieWithDB(Integer id);

}
