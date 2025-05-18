package org.services;

import org.utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    //Directory automatica del db
    private static final String currentDir = System.getProperty("user.dir");
    //private static final String URL = "jdbc:sqlite:" + currentDir + "/" + "geostore.db";
    //Directory fissa del db
    //private static final String URL = "jdbc:sqlite:C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/DB/geostore.db";

    //Metodo per ottenere la connessione al db
    public static Connection sqlConnect(){
        Path folder = Paths.get(currentDir + "/geostoreDB");
        Path file = folder.resolve("geostore.db");

        Connection connection = null;

        try{
            if(!Files.exists(folder)){ //se non esiste la cartella, allora la crea nella stessa directory del programma
                Files.createDirectories(folder);

                Utility.msgInf("GEOSTORE", "Cartella creata nella seguente directory: " + folder);
            }

            if(!Files.exists(file)){ //se non esiste il file db, allora prende dal classpath e lo copia nella cartella creata
                InputStream inputDB = DBConnection.class.getResourceAsStream("/org/db/geostore.db");

                if (inputDB == null) {
                    throw new FileNotFoundException("Database non trovato nel classpath");
                }
                // Copia lo stream nel file temporaneo
                Files.copy(inputDB, file, StandardCopyOption.REPLACE_EXISTING);

                Utility.msgInf("GEOSTORE", "File db ccopiato nella seguente directory: " + folder);
            }

            // Costruisci la connessione SQLite con il file temporaneo
            //Connessione al db stabilita
            Class.forName("org.sqlite.JDBC"); //driver sqlite
            connection = DriverManager.getConnection("jdbc:sqlite:" + file); //creazione della connessione
        }
        catch(SQLException e){ //errore connessione
            Utility.msgInf("GEOSTORE", "Errore alla connessione al db: " + e.getMessage());
        } catch (ClassNotFoundException e) { //errore driver
            Utility.msgInf("GEOSTORE", "Driver non caricato al db: " + e.getMessage());
        } catch(Exception e){
            Utility.msgInf("GEOSTORE", "ERRORE: " + e.getMessage());
        }

        return connection;
    }
}
