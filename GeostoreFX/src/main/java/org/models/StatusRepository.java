package org.models;

import org.services.DBConnection;
import org.utility.Utility;
import org.utility.crud.statusCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StatusRepository implements statusCRUD {

    private HashMap<Integer, Stato> status = new HashMap<>();


    @Override
    public int insertStatoWithDB(Integer id, Stato s) {
        return 0;
    }

    @Override
    public HashMap<Integer, Stato> getStatusWithDB() {
        String sql = "SELECT * FROM Stato s";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        status = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Stato s;

            while(rs.next()){
                s = new Stato();
                s.setId(rs.getInt("id"));
                s.setCode(rs.getString("code"));

                status.put(s.getId(), s);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getStatusWithDB: " + e.getMessage());
        }

        return status;
    }

    @Override
    public Stato getStatoWithDB(String code) {
        String sql = "SELECT * FROM Stato s WHERE s.code = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Stato s = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                s = new Stato();
                s.setId(rs.getInt("id"));
                s.setCode(rs.getString("code"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getStatoWithDB: " + e.getMessage());
        }
        return s;
    }

    @Override
    public int updateStatoWithDB(Integer id, Stato newS) {
        return 0;
    }

    @Override
    public int deleteStatoWithDB(Integer id) {
        return 0;
    }

    public String changeIntToStringStato(Integer id) {
        String sql = "SELECT s.code FROM Stato s WHERE s.ID = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String nome = "";

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                nome = rs.getString("code");
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel changeIntToStringStato: " + e.getMessage());
        }
        return nome;
    }
}
