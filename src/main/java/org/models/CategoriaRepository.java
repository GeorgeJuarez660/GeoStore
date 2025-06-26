package org.models;

import org.services.DBConnection;
import org.utility.Translater;
import org.utility.Utility;
import org.utility.crud.categorieCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CategoriaRepository implements categorieCRUD {

    private HashMap<Integer, Categoria> categorie = new HashMap<>();

    //metodi override per operazioni CRUD locali

    @Override
    public void insertCategoria(Integer id, Categoria c) {
        categorie.put(id, c);
        Utility.msgInf("GEOSTORE", "Categoria aggiunta");
    }

    @Override
    public HashMap<Integer, Categoria> getCategorie() {
        return categorie;
    }

    @Override
    public Categoria getCategoria(String nome) {
        Categoria foundCategoria = null;

        for(Categoria categoria : categorie.values()){
            if(categoria.getNome().equals(nome))
                foundCategoria = categoria;
        }
        return foundCategoria;
    }

    @Override
    public void updateCategoria(Integer id, Categoria newC) {

        if(categorie.containsKey(id)){
            categorie.put(id, newC);

            Utility.msgInf("GEOSTORE", "Categoria modificata");
        }
        else{
            Utility.msgInf("GEOSTORE", "Errore durante la modifica della categoria");
        }
    }

    @Override
    public boolean deleteCategoria(Integer id) {
        return categorie.remove(id) != null;
    }

    //metodi override per operazioni CRUD con database

    @Override
    public int insertCategoriaWithDB(Integer id, Categoria c) {
        String sql = "INSERT INTO `categorie`(`nome_it`, `nome_en`, `nome_ja`, `codice`) VALUES (?,?,?,?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            // Divide la stringa usando il simbolo "#"
            String[] traduzioni = c.getNome().split("#");

            preparedStatement.setString(1, traduzioni[0]);
            preparedStatement.setString(2, traduzioni[1]);
            preparedStatement.setString(3, traduzioni[2]);
            preparedStatement.setString(4, c.getCodice());
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertCategoriaWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, Categoria> getCategorieWithDB() {
        String sql = "SELECT * FROM Categorie c";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        categorie = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Categoria cat;

            while(rs.next()){
                cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setCodice(rs.getString("codice"));
                if(Translater.getLanguage().equals("it")){
                    cat.setNome(rs.getString("nome_it"));
                }
                else if(Translater.getLanguage().equals("en")){
                    cat.setNome(rs.getString("nome_en"));
                }
                else{
                    cat.setNome(rs.getString("nome_ja"));
                }

                categorie.put(cat.getId(), cat);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getCategorieWithDB: " + e.getMessage());
        }

        return categorie;
    }

    @Override
    public Categoria getCategoriaWithDB(String codice, boolean multiLang) {
        String sql = "SELECT * FROM Categorie c WHERE c.codice = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Categoria cat = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, codice);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setCodice(rs.getString("codice"));
                if(multiLang){
                    cat.setNome(rs.getString("nome_it") + "#" + rs.getString("nome_en") + "#" + rs.getString("nome_ja"));
                }
                else{
                    if(Translater.getLanguage().equals("it")){
                        cat.setNome(rs.getString("nome_it"));
                    }
                    else if(Translater.getLanguage().equals("en")){
                        cat.setNome(rs.getString("nome_en"));
                    }
                    else{
                        cat.setNome(rs.getString("nome_ja"));
                    }
                }
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getCategoriaWithDB: " + e.getMessage());
        }
        return cat;
    }

    @Override
    public int updateCategoriaWithDB(String codice, Categoria newC) {
        String sql = "UPDATE `categorie` SET `nome_it` = ?, `nome_en` = ?, `nome_ja` = ? WHERE codice = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            // Divide la stringa usando il simbolo "#"
            String[] traduzioni = newC.getNome().split("#");

            preparedStatement.setString(1, traduzioni[0]);
            preparedStatement.setString(2, traduzioni[1]);
            preparedStatement.setString(3, traduzioni[2]);
            preparedStatement.setString(4, codice);

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateCategoriaWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteCategoriaWithDB(String codice) {
        String sql = "DELETE FROM `categorie` WHERE codice = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, codice);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel deleteCategoriaWithDB: " + e.getMessage());
        }

        return num;
    }

    public int checkDuplicatesCategoria(String nomeIt, String nomeEn, String nomeJa) {
        String sql = "select count(*) as duplicates from categorie c where nome_it = ? and nome_en = ? and nome_ja = ?";
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
            Utility.msgInf("GEOSTORE", "Errore nel checkDuplicatesCategoria: " + e.getMessage());
        }

        return num;
    }

    public int getIdByCode(String codice) {
        String sql = "SELECT id FROM Categorie c WHERE c.codice = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, codice);

            rs = preparedStatement.executeQuery();

            while(rs.next()){
                num = rs.getInt("id");
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getIdByCode: " + e.getMessage());
        }

        return num;
    }

}
