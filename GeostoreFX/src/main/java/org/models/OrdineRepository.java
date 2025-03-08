package org.models;

import org.services.DBConnection;
import org.utility.Utility;
import org.utility.crud.ordiniCRUD;

import java.sql.*;
import java.util.HashMap;

public class OrdineRepository implements ordiniCRUD {

    private HashMap<Integer, Ordine> ordini = new HashMap<>();

    @Override
    public int insertOrdineWithDB(Integer id, Ordine o) {
        String sql = "INSERT INTO `ordini`(`utente_id`, `prodotto_id`,`data_ordine`,`quantita`,`prezzo_unitario`,`stato_id`) VALUES (?, ?, ?, ?, ?, ?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, o.getUtente().getId());
            preparedStatement.setInt(2, o.getProdotto().getId());
            preparedStatement.setString(3, String.valueOf(o.getData_ordine()));
            preparedStatement.setInt(4, o.getQuantita());
            preparedStatement.setBigDecimal(5, o.getPrezzo_unitario());
            preparedStatement.setInt(6, o.getStato().getId());
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertOrdineWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, Ordine> getOrdiniWithDB() {
        String sql = "SELECT o.id, u.id AS id_utente, u.nome AS nome_utente, u.cognome AS cognome_utente, u.genere AS genere_utente, u.data_nascita AS dataNascita_utente, u.email AS email_utente, u.telefono AS telefono_utente, ac.codice AS code_admin, u.portafoglio AS portafoglio_utente, og.id AS id_prodotto, og.nome AS nome_prodotto, og.quantita_disp AS quant_disp_prod, o.data_ordine, o.quantita, o.prezzo_unitario, s.id as st_id, s.code as st_code, s.descrizione as st_desc " +
                " FROM ordini o JOIN utenti u ON(o.utente_id =u.id ) \n" +
                " JOIN stato s ON(o.stato_id=s.id)\n" +
                " JOIN prodotti og ON(o.prodotto_id =og.id ) \n" +
                " LEFT JOIN admin_codes ac ON(u.codice_id = ac.id)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ordini = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Ordine ord;
            Utente foundUtente;

            while(rs.next()){

                String codeAdmin = rs.getString("code_admin");

                if(codeAdmin != null) {
                    foundUtente = new Amministratore();
                }
                else {
                    foundUtente = new Cliente();
                }

                if(foundUtente instanceof Amministratore){
                    Amministratore foundAdmin = (Amministratore) foundUtente;
                    foundAdmin.setId(rs.getInt("id_utente"));
                    foundAdmin.setNome(rs.getString("nome_utente"));
                    foundAdmin.setCognome(rs.getString("cognome_utente"));
                    foundAdmin.setGenere(rs.getString("genere_utente"));
                    foundAdmin.setDataNascita(rs.getDate("dataNascita_utente"));
                    Codice codice = new Codice();
                    codice.setCodice(codeAdmin);
                    foundAdmin.setCodeAdmin(codice);
                    foundAdmin.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundAdmin.setEmail(rs.getString("email_utente"));
                    foundAdmin.setEmail(rs.getString("email_utente"));
                    foundAdmin.setTelefono(rs.getString("telefono_utente"));
                    foundUtente = foundAdmin;
                }
                else{
                    Cliente foundCliente = (Cliente) foundUtente;
                    foundCliente.setId(rs.getInt("id_utente"));
                    foundCliente.setNome(rs.getString("nome_utente"));
                    foundCliente.setCognome(rs.getString("cognome_utente"));
                    foundCliente.setGenere(rs.getString("genere_utente"));
                    foundCliente.setDataNascita(rs.getDate("dataNascita_utente"));
                    foundCliente.setEmail(rs.getString("email_utente"));
                    foundCliente.setTelefono(rs.getString("telefono_utente"));
                    foundCliente.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundCliente;
                }

                ord = new Ordine();
                ord.setId(rs.getInt("id"));
                ord.setUtente(foundUtente);
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome_prodotto"));
                prodotto.setQuantita_disp(rs.getInt("quant_disp_prod"));
                ord.setProdotto(prodotto);
                ord.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ord.setQuantita(rs.getInt("quantita"));
                ord.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("st_id"));
                stato.setCode(rs.getString("st_code"));
                stato.setDescrizione(rs.getString("st_desc"));
                ord.setStato(stato);

                ordini.put(ord.getId(), ord);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdiniWithDB: " + e.getMessage());
        }

        return ordini;
    }

    public HashMap<Integer, Ordine> getOrdiniByEmailWithDB(String email) {
        String sql = "SELECT o.id, u.id AS id_utente, u.nome AS nome_utente, u.cognome AS cognome_utente, u.genere AS genere_utente, u.data_nascita AS dataNascita_utente, u.email AS email_utente, u.telefono AS telefono_utente, ac.codice AS code_admin, u.portafoglio AS portafoglio_utente, og.id AS id_prodotto, og.nome AS nome_prodotto, og.quantita_disp AS quant_disp_prod, o.data_ordine, o.quantita, o.prezzo_unitario, s.id as st_id, s.code as st_code, s.descrizione as st_desc " +
                " FROM ordini o JOIN utenti u ON(o.utente_id =u.id ) \n" +
                " JOIN stato s ON(o.stato_id=s.id)\n" +
                " JOIN prodotti og ON(o.prodotto_id =og.id )\n" +
                " LEFT JOIN admin_codes ac ON(u.codice_id = ac.id )\n" +
                " WHERE u.email LIKE ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ordini = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            email = "%" + email + "%";
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            Ordine ord;
            Utente foundUtente;

            while(rs.next()){

                String codeAdmin = rs.getString("code_admin");

                if(codeAdmin != null) {
                    foundUtente = new Amministratore();
                }
                else {
                    foundUtente = new Cliente();
                }

                if(foundUtente instanceof Amministratore){
                    Amministratore foundAdmin = (Amministratore) foundUtente;
                    foundAdmin.setId(rs.getInt("id_utente"));
                    foundAdmin.setNome(rs.getString("nome_utente"));
                    foundAdmin.setCognome(rs.getString("cognome_utente"));
                    foundAdmin.setGenere(rs.getString("genere_utente"));
                    foundAdmin.setDataNascita(rs.getDate("dataNascita_utente"));
                    Codice codice = new Codice();
                    codice.setCodice(codeAdmin);
                    foundAdmin.setCodeAdmin(codice);
                    foundAdmin.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundAdmin.setEmail(rs.getString("email_utente"));
                    foundAdmin.setTelefono(rs.getString("telefono_utente"));
                    foundUtente = foundAdmin;
                }
                else{
                    Cliente foundCliente = (Cliente) foundUtente;
                    foundCliente.setId(rs.getInt("id_utente"));
                    foundCliente.setNome(rs.getString("nome_utente"));
                    foundCliente.setCognome(rs.getString("cognome_utente"));
                    foundCliente.setGenere(rs.getString("genere_utente"));
                    foundCliente.setDataNascita(rs.getDate("dataNascita_utente"));
                    foundCliente.setEmail(rs.getString("email_utente"));
                    foundCliente.setTelefono(rs.getString("telefono_utente"));
                    foundCliente.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundCliente;
                }

                ord = new Ordine();
                ord.setId(rs.getInt("id"));
                ord.setUtente(foundUtente);
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome_prodotto"));
                prodotto.setQuantita_disp(rs.getInt("quant_disp_prod"));
                ord.setProdotto(prodotto);
                ord.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ord.setQuantita(rs.getInt("quantita"));
                ord.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("st_id"));
                stato.setCode(rs.getString("st_code"));
                stato.setDescrizione(rs.getString("st_desc"));
                ord.setStato(stato);

                ordini.put(ord.getId(), ord);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdiniByEmailWithDB: " + e.getMessage());
        }

        return ordini;
    }

    public HashMap<Integer, Ordine> getOrdiniByUserWithDB(Integer idUtente) {
        String sql = "SELECT o.id, u.id AS id_utente, u.nome AS nome_utente, u.cognome AS cognome_utente, u.genere AS genere_utente, u.data_nascita AS dataNascita_utente, u.email AS email_utente, u.telefono AS telefono_utente, u.portafoglio AS portafoglio_utente, ac.codice AS code_admin, og.nome AS nome_prodotto, og.id AS id_prodotto, og.quantita_disp AS quant_disp_prod, o.data_ordine, o.quantita, o.prezzo_unitario, s.id as st_id, s.code as st_code, s.descrizione as st_desc " +
                " FROM ordini o JOIN utenti u ON(o.utente_id =u.id ) \n" +
                " JOIN stato s ON(o.stato_id=s.id)\n" +
                " JOIN prodotti og ON(o.prodotto_id = og.id )\n" +
                " JOIN admin_codes ac ON(u.codice_id = ac.id )\n" +
                "WHERE o.utente_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ordini = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idUtente);
            rs = preparedStatement.executeQuery();
            Ordine ord;
            Utente foundUtente;

            while(rs.next()){
                String codeAdmin = rs.getString("code_admin");

                if(codeAdmin != null) {
                    foundUtente = new Amministratore();
                }
                else {
                    foundUtente = new Cliente();
                }

                if(foundUtente instanceof Amministratore){
                    Amministratore foundAdmin = (Amministratore) foundUtente;
                    foundAdmin.setId(rs.getInt("id_utente"));
                    foundAdmin.setNome(rs.getString("nome_utente"));
                    foundAdmin.setCognome(rs.getString("cognome_utente"));
                    foundAdmin.setGenere(rs.getString("genere_utente"));
                    foundAdmin.setDataNascita(rs.getDate("dataNascita_utente"));
                    Codice codice = new Codice();
                    codice.setCodice(codeAdmin);
                    foundAdmin.setCodeAdmin(codice);
                    foundAdmin.setEmail(rs.getString("email_utente"));
                    foundAdmin.setTelefono(rs.getString("telefono_utente"));
                    foundAdmin.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundAdmin;
                }
                else{
                    Cliente foundCliente = (Cliente) foundUtente;
                    foundCliente.setId(rs.getInt("id_utente"));
                    foundCliente.setNome(rs.getString("nome_utente"));
                    foundCliente.setCognome(rs.getString("cognome_utente"));
                    foundCliente.setGenere(rs.getString("genere_utente"));
                    foundCliente.setDataNascita(rs.getDate("dataNascita_utente"));
                    foundCliente.setEmail(rs.getString("email_utente"));
                    foundCliente.setTelefono(rs.getString("telefono_utente"));
                    foundCliente.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundCliente;
                }

                ord = new Ordine();
                ord.setId(rs.getInt("id"));
                ord.setUtente(foundUtente);
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome_prodotto"));
                prodotto.setQuantita_disp(rs.getInt("quant_disp_prod"));
                ord.setProdotto(prodotto);
                ord.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ord.setQuantita(rs.getInt("quantita"));
                ord.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("st_id"));
                stato.setCode(rs.getString("st_code"));
                stato.setDescrizione(rs.getString("st_desc"));
                ord.setStato(stato);

                ordini.put(ord.getId(), ord);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdiniByUserWithDB: " + e.getMessage());
        }

        return ordini;
    }

    public HashMap<Integer, Ordine> getOrdiniByUserAndKeywordWithDB(Integer idUtente, String keyword) {
        String sql = "SELECT o.id, u.id AS id_utente, u.nome AS nome_utente, u.cognome AS cognome_utente, u.genere AS genere_utente, u.data_nascita AS dataNascita_utente, u.email AS email_utente, u.telefono AS telefono_utente, u.portafoglio AS portafoglio_utente, ac.codice AS code_admin, og.nome AS nome_prodotto, og.id AS id_prodotto, og.quantita_disp AS quant_disp_prod, o.data_ordine, o.quantita, o.prezzo_unitario, s.id as st_id, s.code as st_code, s.descrizione as st_desc " +
                " FROM ordini o JOIN utenti u ON(o.utente_id =u.id ) \n" +
                " JOIN stato s ON(o.stato_id=s.id)\n" +
                " JOIN prodotti og ON(o.prodotto_id =og.id )\n" +
                " JOIN admin_codes ac ON(u.codice_id = ac.id )\n" +
                "WHERE o.utente_id = ? AND og.nome LIKE ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ordini = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            keyword = "%" + keyword + "%";
            preparedStatement.setInt(1, idUtente);
            preparedStatement.setString(2, keyword);
            rs = preparedStatement.executeQuery();
            Ordine ord;
            Utente foundUtente;

            while(rs.next()){
                String codeAdmin = rs.getString("code_admin");

                if(codeAdmin != null) {
                    foundUtente = new Amministratore();
                }
                else {
                    foundUtente = new Cliente();
                }

                if(foundUtente instanceof Amministratore){
                    Amministratore foundAdmin = (Amministratore) foundUtente;
                    foundAdmin.setId(rs.getInt("id_utente"));
                    foundAdmin.setNome(rs.getString("nome_utente"));
                    foundAdmin.setCognome(rs.getString("cognome_utente"));
                    foundAdmin.setGenere(rs.getString("genere_utente"));
                    foundAdmin.setDataNascita(rs.getDate("dataNascita_utente"));
                    Codice codice = new Codice();
                    codice.setCodice(codeAdmin);
                    foundAdmin.setCodeAdmin(codice);
                    foundAdmin.setEmail(rs.getString("email_utente"));
                    foundAdmin.setTelefono(rs.getString("telefono_utente"));
                    foundAdmin.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundAdmin;
                }
                else{
                    Cliente foundCliente = (Cliente) foundUtente;
                    foundCliente.setId(rs.getInt("id_utente"));
                    foundCliente.setNome(rs.getString("nome_utente"));
                    foundCliente.setCognome(rs.getString("cognome_utente"));
                    foundCliente.setGenere(rs.getString("genere_utente"));
                    foundCliente.setDataNascita(rs.getDate("dataNascita_utente"));
                    foundCliente.setEmail(rs.getString("email_utente"));
                    foundCliente.setTelefono(rs.getString("telefono_utente"));
                    foundCliente.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundCliente;
                }

                ord = new Ordine();
                ord.setId(rs.getInt("id"));
                ord.setUtente(foundUtente);
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome_prodotto"));
                prodotto.setQuantita_disp(rs.getInt("quant_disp_prod"));
                ord.setProdotto(prodotto);
                ord.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ord.setQuantita(rs.getInt("quantita"));
                ord.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("st_id"));
                stato.setCode(rs.getString("st_code"));
                stato.setDescrizione(rs.getString("st_desc"));
                ord.setStato(stato);

                ordini.put(ord.getId(), ord);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdiniByUserAndKeywordWithDB: " + e.getMessage());
        }

        return ordini;
    }

    public HashMap<Integer, Ordine> getOrdiniByProductWithDB(Integer idProdotto) {
        String sql = "SELECT o.id, u.id AS id_utente, u.nome AS nome_utente, u.cognome AS cognome_utente, u.genere AS genere_utente, u.data_nascita AS dataNascita_utente, u.email AS email_utente, u.telefono AS telefono_utente, u.portafoglio AS portafoglio_utente, ac.codice AS code_admin, og.nome AS nome_prodotto, og.id AS id_prodotto, og.quantita_disp AS quant_disp_prod, o.data_ordine, o.quantita, o.prezzo_unitario, s.id as st_id, s.code as st_code, s.descrizione as st_desc " +
                " FROM ordini o JOIN utenti u ON(o.utente_id =u.id ) \n" +
                " JOIN stato s ON(o.stato_id=s.id)\n" +
                " JOIN prodotti og ON(o.prodotto_id = og.id )\n" +
                " LEFT JOIN admin_codes ac ON(u.codice_id = ac.id )\n" +
                "WHERE o.prodotto_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ordini = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idProdotto);
            rs = preparedStatement.executeQuery();
            Ordine ord;
            Utente foundUtente;

            while(rs.next()){
                String codeAdmin = rs.getString("code_admin");

                if(codeAdmin != null) {
                    foundUtente = new Amministratore();
                }
                else {
                    foundUtente = new Cliente();
                }

                if(foundUtente instanceof Amministratore){
                    Amministratore foundAdmin = (Amministratore) foundUtente;
                    foundAdmin.setId(rs.getInt("id_utente"));
                    foundAdmin.setNome(rs.getString("nome_utente"));
                    foundAdmin.setCognome(rs.getString("cognome_utente"));
                    foundAdmin.setGenere(rs.getString("genere_utente"));
                    foundAdmin.setDataNascita(rs.getDate("dataNascita_utente"));
                    Codice codice = new Codice();
                    codice.setCodice(codeAdmin);
                    foundAdmin.setCodeAdmin(codice);
                    foundAdmin.setEmail(rs.getString("email_utente"));
                    foundAdmin.setTelefono(rs.getString("telefono_utente"));
                    foundAdmin.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundAdmin;
                }
                else{
                    Cliente foundCliente = (Cliente) foundUtente;
                    foundCliente.setId(rs.getInt("id_utente"));
                    foundCliente.setNome(rs.getString("nome_utente"));
                    foundCliente.setCognome(rs.getString("cognome_utente"));
                    foundCliente.setGenere(rs.getString("genere_utente"));
                    foundCliente.setDataNascita(rs.getDate("dataNascita_utente"));
                    foundCliente.setEmail(rs.getString("email_utente"));
                    foundCliente.setTelefono(rs.getString("telefono_utente"));
                    foundCliente.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundCliente;
                }

                ord = new Ordine();
                ord.setId(rs.getInt("id"));
                ord.setUtente(foundUtente);
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome_prodotto"));
                prodotto.setQuantita_disp(rs.getInt("quant_disp_prod"));
                ord.setProdotto(prodotto);
                ord.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ord.setQuantita(rs.getInt("quantita"));
                ord.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("st_id"));
                stato.setCode(rs.getString("st_code"));
                stato.setDescrizione(rs.getString("st_desc"));
                ord.setStato(stato);

                ordini.put(ord.getId(), ord);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdiniByProductWithDB: " + e.getMessage());
        }

        return ordini;
    }

    public Ordine getOrdineByUserWithDB(Integer idUtente, Integer idOrdine) {
        String sql = "SELECT o.id, u.id AS id_utente, u.nome AS nome_utente, u.cognome AS cognome_utente, u.genere AS genere_utente, u.data_nascita AS dataNascita_utente, u.email AS email_utente, u.telefono AS telefono_utente, u.portafoglio AS portafoglio_utente, ac.codice AS code_admin, og.nome AS nome_prodotto, og.id AS id_prodotto, og.quantita_disp AS quant_disp_prod, o.data_ordine, o.quantita, o.prezzo_unitario, s.id as st_id, s.code as st_code, s.descrizione as st_desc " +
                " FROM ordini o JOIN utenti u ON(o.utente_id =u.id ) \n" +
                " JOIN stato s ON(o.stato_id=s.id)\n" +
                " JOIN prodotti og ON(o.prodotto_id=og.id)\n" +
                " LEFT JOIN admin_codes ac ON(u.codice_id=ac.id)\n" +
                "WHERE o.utente_id = ? AND o.id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Ordine ord = new Ordine();
        Utente foundUtente;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idUtente);
            preparedStatement.setInt(2, idOrdine);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                String codeAdmin = rs.getString("code_admin");

                if(codeAdmin != null) {
                    foundUtente = new Amministratore();
                }
                else {
                    foundUtente = new Cliente();
                }

                if(foundUtente instanceof Amministratore){
                    Amministratore foundAdmin = (Amministratore) foundUtente;
                    foundAdmin.setId(rs.getInt("id_utente"));
                    foundAdmin.setNome(rs.getString("nome_utente"));
                    foundAdmin.setCognome(rs.getString("cognome_utente"));
                    foundAdmin.setGenere(rs.getString("genere_utente"));
                    foundAdmin.setDataNascita(rs.getDate("dataNascita_utente"));
                    Codice codice = new Codice();
                    codice.setCodice(codeAdmin);
                    foundAdmin.setCodeAdmin(codice);
                    foundAdmin.setEmail(rs.getString("email_utente"));
                    foundAdmin.setTelefono(rs.getString("telefono_utente"));
                    foundAdmin.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundAdmin;
                }
                else{
                    Cliente foundCliente = (Cliente) foundUtente;
                    foundCliente.setId(rs.getInt("id_utente"));
                    foundCliente.setNome(rs.getString("nome_utente"));
                    foundCliente.setCognome(rs.getString("cognome_utente"));
                    foundCliente.setGenere(rs.getString("genere_utente"));
                    foundCliente.setDataNascita(rs.getDate("dataNascita_utente"));
                    foundCliente.setEmail(rs.getString("email_utente"));
                    foundCliente.setTelefono(rs.getString("telefono_utente"));
                    foundCliente.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundCliente;
                }

                ord = new Ordine();
                ord.setId(rs.getInt("id"));
                ord.setUtente(foundUtente);
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome_prodotto"));
                prodotto.setQuantita_disp(rs.getInt("quant_disp_prod"));
                ord.setProdotto(prodotto);
                ord.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ord.setQuantita(rs.getInt("quantita"));
                ord.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("st_id"));
                stato.setCode(rs.getString("st_code"));
                stato.setDescrizione(rs.getString("st_desc"));
                ord.setStato(stato);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdineByUserWithDB: " + e.getMessage());
        }

        return ord;
    }

    @Override
    public Ordine getOrdineWithDB(Integer id) {
        String sql = "SELECT o.id, u.id AS id_utente, u.nome AS nome_utente, u.cognome AS cognome_utente, u.genere AS genere_utente, u.data_nascita AS dataNascita_utente, u.email AS email_utente, u.telefono AS telefono_utente, u.portafoglio AS portafoglio_utente, ac.codice AS code_admin, og.nome AS nome_prodotto, og.id AS id_prodotto, og.quantita_disp AS quant_disp_prod, o.data_ordine, o.quantita, o.prezzo_unitario, s.id as st_id, s.code as st_code, s.descrizione as st_desc " +
                " FROM ordini o JOIN utenti u ON(o.utente_id =u.id ) \n" +
                " JOIN stato s ON(o.stato_id=s.id)\n" +
                " JOIN prodotti og ON(o.prodotto_id=og.id)\n" +
                " LEFT JOIN admin_codes ac ON(u.codice_id=ac.id)\n" +
                "WHERE o.id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Ordine ord = new Ordine();
        Utente foundUtente;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                String codeAdmin = rs.getString("code_admin");

                if(codeAdmin != null) {
                    foundUtente = new Amministratore();
                }
                else {
                    foundUtente = new Cliente();
                }

                if(foundUtente instanceof Amministratore){
                    Amministratore foundAdmin = (Amministratore) foundUtente;
                    foundAdmin.setId(rs.getInt("id_utente"));
                    foundAdmin.setNome(rs.getString("nome_utente"));
                    foundAdmin.setCognome(rs.getString("cognome_utente"));
                    foundAdmin.setGenere(rs.getString("genere_utente"));
                    foundAdmin.setDataNascita(rs.getDate("dataNascita_utente"));
                    Codice codice = new Codice();
                    codice.setCodice(codeAdmin);
                    foundAdmin.setCodeAdmin(codice);
                    foundAdmin.setEmail(rs.getString("email_utente"));
                    foundAdmin.setTelefono(rs.getString("telefono_utente"));
                    foundAdmin.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundAdmin;
                }
                else{
                    Cliente foundCliente = (Cliente) foundUtente;
                    foundCliente.setId(rs.getInt("id_utente"));
                    foundCliente.setNome(rs.getString("nome_utente"));
                    foundCliente.setCognome(rs.getString("cognome_utente"));
                    foundCliente.setGenere(rs.getString("genere_utente"));
                    foundCliente.setDataNascita(rs.getDate("dataNascita_utente"));
                    foundCliente.setEmail(rs.getString("email_utente"));
                    foundCliente.setTelefono(rs.getString("telefono_utente"));
                    foundCliente.setPortafoglio(rs.getBigDecimal("portafoglio_utente"));
                    foundUtente = foundCliente;
                }

                ord = new Ordine();
                ord.setId(rs.getInt("id"));
                ord.setUtente(foundUtente);
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome_prodotto"));
                prodotto.setQuantita_disp(rs.getInt("quant_disp_prod"));
                ord.setProdotto(prodotto);
                ord.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ord.setQuantita(rs.getInt("quantita"));
                ord.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("st_id"));
                stato.setCode(rs.getString("st_code"));
                stato.setDescrizione(rs.getString("st_desc"));
                ord.setStato(stato);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdineWithDB: " + e.getMessage());
        }

        return ord;
    }

    @Override
    public int updateOrdineWithDB(Integer id, Ordine newO) {
        String sql = "UPDATE `ordini` SET `stato_id` = ?, `quantita` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, newO.getStato().getId());
            preparedStatement.setInt(2, newO.getQuantita());
            preparedStatement.setInt(3, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateOrdineWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteOrdineWithDB(Integer id) {
        String sql = "DELETE FROM `ordini` WHERE id = ? ";
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
            Utility.msgInf("GEOSTORE", "Errore nel deleteOrdineWithDB: " + e.getMessage());
        }

        return num;
    }

    public int deleteOrdineBeforeDeleteProduct(Integer idProd) {
        String sql = "DELETE FROM `ordini` WHERE prodotto_id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idProd);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel deleteOrdineAfterDeleteProduct: " + e.getMessage());
        }

        return num;
    }

    public Ordine getOrdineTotGiorWithDB(Utente u, String data) {
        String sql = "SELECT u.nome as nome_utente, u.cognome as cognome_utente, o.data_ordine, sum(o.quantita*o.prezzo_unitario) AS tot_ord_gior FROM ordini o JOIN utenti u ON(o.utente_id =u.id )\n" +
                "JOIN prodotti p ON(o.prodotto_id =p.id )\n" +
                "WHERE u.id = ? AND o.data_ordine LIKE ? AND o.stato_id = 2\n" +
                "GROUP BY o.data_ordine ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Ordine ordine = new Ordine();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, u.getId());
            preparedStatement.setString(2, data + "%");
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Utente utente = new Utente();
                utente.setNome(rs.getString("nome_utente"));
                utente.setCognome(rs.getString("cognome_utente"));
                ordine.setUtente(utente);
                ordine.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ordine.setPrezzo_unitario(rs.getBigDecimal("tot_ord_gior"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdineTotGiorWithDB: " + e.getMessage());
        }

        return ordine;
    }

}
