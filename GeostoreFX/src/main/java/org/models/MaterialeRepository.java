package org.models;

import org.services.DBConnection;
import org.utility.Utility;
import org.utility.crud.materialiCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MaterialeRepository implements materialiCRUD {

    private HashMap<Integer, Materiale> materiali = new HashMap<>();

    //metodi override per operazioni CRUD con database

    @Override
    public int insertMaterialeWithDB(Integer id, Materiale m) {
        String sql = "INSERT INTO `materiali`(`nome`) VALUES (?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, m.getNome());
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertMaterialeWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, Materiale> getMaterialiWithDB() {
        String sql = "SELECT * FROM Materiali m";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        materiali = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Materiale mat;

            while(rs.next()){
                mat = new Materiale();
                mat.setId(rs.getInt("id"));
                mat.setNome(rs.getString("nome"));

                materiali.put(mat.getId(), mat);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getMaterialiWithDB: " + e.getMessage());
        }

        return materiali;
    }

    @Override
    public Materiale getMaterialeWithDB(Integer id) {
        String sql = "SELECT * FROM Materiali m WHERE m.ID = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Materiale mat = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                mat = new Materiale();
                mat.setId(rs.getInt("id"));
                mat.setNome(rs.getString("nome"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getMaterialeWithDB: " + e.getMessage());
        }
        return mat;
    }

    @Override
    public int updateMaterialeWithDB(Integer id, Materiale newM) {
        String sql = "UPDATE `materiali` SET `nome` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, newM.getNome());
            preparedStatement.setInt(2, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateMaterialeWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteMaterialeWithDB(Integer id) {
        String sql = "DELETE FROM `materiali` WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel deleteMaterialeWithDB: " + e.getMessage());
        }

        return num;
    }

    public int checkDuplicatesMateriale(Materiale m) {
        String sql = "select count(*) as duplicates from materiali m where nome = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, m.getNome());

            rs = preparedStatement.executeQuery();

            while(rs.next()){
                num = rs.getInt("duplicates");
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel checkDuplicatesMateriale: " + e.getMessage());
        }

        return num;
    }

}
