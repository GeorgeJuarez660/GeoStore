package org.models;

import org.services.DBConnection;
import org.utility.Utility;
import org.utility.crud.newsCRUD;

import java.sql.*;
import java.util.ArrayList;

public class NewsRepository implements newsCRUD {

    private ArrayList<News> notizie = new ArrayList<>();

    @Override
    public int insertNotizieWithDB(Date dataPub, Date dataMod, String testo, Integer idAdmin) {
        String sql = "INSERT INTO `news`(`data_pubblicazione`, `data_modifica`, `testo`, `admin_id`) VALUES (?, ?, ?, ?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setDate(1, dataPub);
            preparedStatement.setDate(2, dataMod);
            preparedStatement.setString(3, testo);
            preparedStatement.setInt(4, idAdmin);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertNotizieWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public ArrayList<News> getNotizieWithDB() {
        String sql = "SELECT * FROM News n ORDER BY n.data_modifica DESC";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        notizie = new ArrayList<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            News n;

            while(rs.next()){
                n = new News();
                n.setId(rs.getInt("id"));
                n.setTesto(rs.getString("testo"));
                n.setDataPub(rs.getDate("data_pubblicazione"));
                n.setDataMod(rs.getDate("data_modifica"));

                notizie.add(n);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getNotizieWithDB: " + e.getMessage());
        }

        return notizie;
    }

    public News getNotiziaWithByIdWithDB(String keyword) {
        String sql = "SELECT * FROM News n WHERE n.id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        News news = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, keyword);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                news = new News();
                news.setId(rs.getInt("id"));
                news.setTesto(rs.getString("testo"));
                news.setDataPub(rs.getDate("data_pubblicazione"));
                news.setDataMod(rs.getDate("data_modifica"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getNotiziaWithDB: " + e.getMessage());
        }
        return news;
    }

    @Override
    public ArrayList<News> getNotizieByKeywordWithDB(String keyword) {
        String sql = "SELECT * FROM News n WHERE n.TESTO LIKE ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        notizie = new ArrayList<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            keyword = "%" + keyword + "%";
            preparedStatement.setString(1, keyword);
            rs = preparedStatement.executeQuery();
            News n;

            while(rs.next()){
                n = new News();
                n.setId(rs.getInt("id"));
                n.setTesto(rs.getString("testo"));
                n.setDataPub(rs.getDate("data_pubblicazione"));
                n.setDataMod(rs.getDate("data_modifica"));

                notizie.add(n);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getNotizieByKeywordWithDB: " + e.getMessage());
        }

        return notizie;
    }

    @Override
    public int updateNotizieWithDB(Integer id, Date newDataMod, String newTesto, Integer newIdAdmin) {
        String sql = "UPDATE `news` SET `data_modifica` = ?, `testo` = ?, `admin_id` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setDate(1, newDataMod);
            preparedStatement.setString(2, newTesto);
            preparedStatement.setInt(3, newIdAdmin);
            preparedStatement.setInt(4, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateNotizieWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteNotizieWithDB(Integer id) {
        String sql = "DELETE FROM `news` WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel deleteNotizieWithDB: " + e.getMessage());
        }

        return num;
    }

}
