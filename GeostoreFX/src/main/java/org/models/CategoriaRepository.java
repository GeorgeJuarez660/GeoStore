package org.models;

import org.services.DBConnection;
import org.utility.Utility;
import org.utility.crud.categorieCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
        String sql = "INSERT INTO `categorie`(`nome`, `lingua`) VALUES (?,?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, c.getNome());
            preparedStatement.setString(2, c.getLingua());
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
                cat.setNome(rs.getString("nome"));

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
    public Categoria getCategoriaWithDB(Integer id) {
        String sql = "SELECT * FROM Categorie c WHERE c.ID = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Categoria cat = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
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
    public int updateCategoriaWithDB(Integer id, Categoria newC) {
        String sql = "UPDATE `categorie` SET `nome` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, newC.getNome());
            preparedStatement.setInt(2, id);
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
    public int deleteCategoriaWithDB(Integer id) {
        String sql = "DELETE FROM `categorie` WHERE id = ? ";
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
            Utility.msgInf("GEOSTORE", "Errore nel deleteCategoriaWithDB: " + e.getMessage());
        }

        return num;
    }

    public int checkDuplicatesCategoria(Categoria c) {
        String sql = "select count(*) as duplicates from categorie c where nome = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, c.getNome());

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

}
