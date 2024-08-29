package src.main.java.model;

import src.main.java.utility.DBConnection;
import src.main.java.utility.Utility;
import src.main.java.utility.clientiCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ClienteRepository implements clientiCRUD {

    private HashMap<Integer, Cliente> clienti = new HashMap<>();

    @Override
    public int insertClienteWithDB(Integer id, Cliente c) {
        String sql = "INSERT INTO `clienti`(`nome`, `cognome`,`email`,`telefono`,`indirizzo`, `indirizzo`, `codice_admin`) VALUES (?, ?, ?, ?, ?, ?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, c.getNome());
            preparedStatement.setString(2, c.getCognome());
            preparedStatement.setString(3, c.getEmail());
            preparedStatement.setString(4, c.getTelefono());
            preparedStatement.setString(5, c.getIndirizzo());

            if(c instanceof Amministratore){
                Amministratore a = (Amministratore) c;
                preparedStatement.setString(6, a.getCodeAdmin());
            }
            else{
                preparedStatement.setString(6, null);
            }

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertClienteWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, Cliente> getClientiWithDB() {
        String sql = "select * from clienti c ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        clienti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Cliente cl;

            while(rs.next()){
                cl = new Cliente();
                cl.setId(rs.getInt("id"));
                cl.setNome(rs.getString("nome"));
                cl.setCognome(rs.getString("cognome"));
                cl.setEmail(rs.getString("email"));
                cl.setIndirizzo(rs.getString("indirizzo"));
                cl.setTelefono(rs.getString("telefono"));

                clienti.put(cl.getId(), cl);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getClientiWithDB: " + e.getMessage());
        }

        return clienti;
    }

    @Override
    public Cliente getClienteWithDB(String nome) {
        String sql = "select * from clienti c where c.nome = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Cliente foundCliente = new Cliente();
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                foundCliente.setId(rs.getInt("id"));
                foundCliente.setNome(rs.getString("nome"));
                foundCliente.setCognome(rs.getString("cognome"));
                foundCliente.setEmail(rs.getString("email"));
                foundCliente.setIndirizzo(rs.getString("indirizzo"));
                foundCliente.setTelefono(rs.getString("telefono"));

            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getClienteWithDB: " + e.getMessage());
        }

        return foundCliente;
    }

    public Cliente checkUser(String email) {
        String sql = "select * from clienti c where c.email = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Cliente foundCliente = new Cliente();
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                foundCliente.setId(rs.getInt("id"));
                foundCliente.setNome(rs.getString("nome"));
                foundCliente.setCognome(rs.getString("cognome"));
                foundCliente.setEmail(rs.getString("email"));
                foundCliente.setIndirizzo(rs.getString("indirizzo"));
                foundCliente.setTelefono(rs.getString("telefono"));

            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel checkUser: " + e.getMessage());
        }

        return foundCliente;
    }

    @Override
    public int updateClienteWithDB(Integer id, Cliente newC) {
        String sql = "UPDATE `clienti` SET `nome` = ?, `cognome` = ?, `email` = ?, `email` = ?, `indirizzo` = ?, `telefono` = ?, `codice_admin` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, newC.getNome());
            preparedStatement.setString(2, newC.getCognome());
            preparedStatement.setString(3, newC.getEmail());
            preparedStatement.setString(4, newC.getTelefono());
            preparedStatement.setString(5, newC.getIndirizzo());

            if(newC instanceof Amministratore){
                Amministratore newA = (Amministratore) newC;
                preparedStatement.setString(6, newA.getCodeAdmin());
            }
            else{
                preparedStatement.setString(6, null);
            }

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertClienteWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteClienteWithDB(Integer id) {

    }

    //metodi override per operazioni CRUD




}
