package org.models;

import org.services.DBConnection;
import org.utility.Translater;
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
        String sql = "INSERT INTO `materiali`(`nome_it`, `nome_en`, `nome_ja`, `codice`) VALUES (?,?,?,?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            // Divide la stringa usando il simbolo "#"
            String[] traduzioni = m.getNome().split("#");

            preparedStatement.setString(1, traduzioni[0]);
            preparedStatement.setString(2, traduzioni[1]);
            preparedStatement.setString(3, traduzioni[2]);
            preparedStatement.setString(4, m.getCodice());
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
                mat.setCodice(rs.getString("codice"));
                if(Translater.getLanguage().equals("it")){
                    mat.setNome(rs.getString("nome_it"));
                }
                else if(Translater.getLanguage().equals("en")){
                    mat.setNome(rs.getString("nome_en"));
                }
                else{
                    mat.setNome(rs.getString("nome_ja"));
                }

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
    public Materiale getMaterialeWithDB(String codice, boolean multiLang) {
        String sql = "SELECT * FROM Materiali m WHERE m.codice = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Materiale mat = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, codice);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                mat = new Materiale();
                mat.setId(rs.getInt("id"));
                mat.setCodice(rs.getString("codice"));
                if(multiLang){
                    mat.setNome(rs.getString("nome_it") + "#" + rs.getString("nome_en") + "#" + rs.getString("nome_ja"));
                }
                else{
                    if(Translater.getLanguage().equals("it")){
                        mat.setNome(rs.getString("nome_it"));
                    }
                    else if(Translater.getLanguage().equals("en")){
                        mat.setNome(rs.getString("nome_en"));
                    }
                    else{
                        mat.setNome(rs.getString("nome_ja"));
                    }
                }
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
    public int updateMaterialeWithDB(String codice, Materiale newM) {
        String sql = "UPDATE `materiali` SET `nome_it` = ?, `nome_en` = ?, `nome_ja` = ? WHERE codice = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            // Divide la stringa usando il simbolo "#"
            String[] traduzioni = newM.getNome().split("#");

            preparedStatement.setString(1, traduzioni[0]);
            preparedStatement.setString(2, traduzioni[1]);
            preparedStatement.setString(3, traduzioni[2]);
            preparedStatement.setString(4, codice);

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
    public int deleteMaterialeWithDB(String codice) {
        String sql = "DELETE FROM `materiali` WHERE codice = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, codice);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel deleteMaterialeWithDB: " + e.getMessage());
        }

        return num;
    }

    public int checkDuplicatesMateriale(String nomeIt, String nomeEn, String nomeJa) {
        String sql = "select count(*) as duplicates from materiali m where nome_it = ? and nome_en = ? and nome_ja = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nomeIt);
            preparedStatement.setString(2, nomeEn);
            preparedStatement.setString(3, nomeJa);

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
