package org.utility;


import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.models.Cliente;
import org.models.Ordine;
import org.models.Utente;
import org.services.LoadPage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.Period;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Utility {

    static Scanner input = new Scanner(System.in);

    //------------------UTILITY-----------------------

    public static void msgInf(String owner, String text) //metodo di messaggio info
    {
        System.out.println(owner + ": " + text);
    }

    public static int insertInt(String value){
        System.out.println(value);
        int num = 0;
        boolean flag;

        do{ //controlla ripetutamente se è un numero
            flag = false;
            try{
                num = Integer.parseInt(value);
            }catch(NumberFormatException err){
                msgInf("GEOSTORE", "devi inserire un valore numerico");
                flag = true;
            }
            catch (Exception err){
                msgInf("GEOSTORE", "errore");
                flag = true;
            }
        }while(flag);

        return num;

    }

    public static BigDecimal insertBigDecimal(String value){
        System.out.println("Portafoglio iniziale: " + value + " C");
        BigDecimal num = new BigDecimal(0);
        boolean flag;

        do{ //controlla ripetutamente se è un numero
            flag = false;
            try{
                num = new BigDecimal(value);
            }catch(NumberFormatException err){
                msgInf("GEOSTORE", "devi inserire un valore decimale");
                flag = true;
            }
            catch (Exception err){
                msgInf("GEOSTORE", "errore");
                flag = true;
            }
        }while(flag);

        return num;

    }

    public static String insertString(String value){
        System.out.println(value);
        String word = value;

        return word;

    }

    public static String formatValueInStringWithZeros(BigDecimal value){
        String formattedValue = "";

        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.ITALIAN);
        DecimalFormat df = new DecimalFormat("###,##0.00", dfs);
        formattedValue = df.format(value);

        return formattedValue;
    }

    public static BigDecimal formatValueInBigDecimalWithoutZeros(String value){
        BigDecimal formattedValue = new BigDecimal(0);

        value = value.replace(",", ".");
        formattedValue = new BigDecimal(value);

        return formattedValue.stripTrailingZeros();
    }

    public static boolean getAge(Date userDate){
        boolean canRegister;

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(userDate);
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        LocalDate bornDate = LocalDate.of(anno, mese, giorno);
        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(bornDate, currentDate);
        int age = period.getYears();

        if(age >= 13){
            canRegister = true;
        }
        else{
            canRegister = false;
        }

        return canRegister;
    }

    /*public static boolean setImage(String origineImg, String destinazioneImg){
        Path origine = Paths.get(origineImg);
        Path destinazione = Paths.get(destinazioneImg);
        boolean setted;

        if(Files.exists(origine)){ //controlla se esiste il file origine
            try{
                Files.move(origine, destinazione, StandardCopyOption.REPLACE_EXISTING);
                setted = true;
            }catch(IOException e){
                System.err.println("Errore find file png: " + e.getMessage());
                setted = false;
            }
        }
        else{
            setted = false;
        }

        return setted;
    }*/

    public static String getStringFirstLetterMaiusc(String text){
        if (text == null || text.isEmpty()) {
            return text;
        }

        //Dividi la stringa in parole
        String[] words = text.split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                //Mette la prima lettera in maiuscolo, mentre il resto mette in minuscolo
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        //Rimuovi l'ultimo spazio in eccesso
        return capitalized.toString().trim();
    }

    public static String getFirstThreeLettersAndLastThreeNumbers(){
        //funzione rand per ricavare l'id dello scontrino
        Random random = new Random();

        //genera tre lettere maiuscole casuali
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            letters.append(letter);
        }

        //genera un numero tra 000 e 999, formattato sempre con 3 cifre
        int number = random.nextInt(1000);
        String formattedNumber = String.format("%03d", number);

        String randID = letters + formattedNumber;

        //Rimuovi l'ultimo spazio in eccesso
        return randID;
    }

    //------------------RESPONSE-----------------------

    public static void sendResponseLogin(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "LOG-Y", null);
        }
        else{
            LoadPage.answerScene("negative", "LOG-N", null);
        }

        if(num>0){
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, false, true);
            });
            delay.play();
        }
        else{
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullScene("prepage", null);
            });
            delay.play();
        }

    }

    public static void sendResponseRegister(Integer num){
        if(num > 0){
            LoadPage.answerScene("positive", "REG-Y", null);
        }
        else{
            LoadPage.answerScene("negative", "REG-N", null);
        }

        //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            // Dopo 2 secondi, carica la terza scena
            LoadPage.getFullScene("prepage", null);
        });
        delay.play();
    }

    public static void sendResponse(Integer num, String dynamicEvent, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", dynamicEvent + "Y", null);
        }
        else{
            LoadPage.answerScene("negative", dynamicEvent + "N", null);
        }

        //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            // Dopo 2 secondi, carica la terza scena
            LoadPage.goesToMenu(user, null, true, false);
        });
        delay.play();
    }

    public static void sendResponseDeletedProducts(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "PRD-DY", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "PRD-IR", null);
            });
            delay.play();

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(9));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user ,null, true, false);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "PRD-DN", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay.play();
        }
    }

    public static void sendResponseDeletedOrders(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "ODR-DY", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "ODR-IRR", null);
            });
            delay.play();

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(9));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "ODR-DN", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay.play();
        }
    }

    public static void sendResponseOrderedProducts(Integer num, String response, Cliente user, boolean saveReceipt){
        if(num > 0){
            LoadPage.answerScene("positive", response + "Y", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "ODR-IPR", null);
            });
            delay.play();

            int pauseMenu = 0;
            if(saveReceipt){
                //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
                PauseTransition delayS = new PauseTransition(Duration.seconds(9));
                delayS.setOnFinished(event -> {
                    // Dopo 2 secondi, carica la scena del salvataggio scontrino
                    LoadPage.answerScene("info", "ODR-SAR", null);
                });
                delayS.play();

                pauseMenu = 15; //pausa prima che venga mostrata la scena menu e nel frattempo viene mostrano l'info scontrino
            }
            else{
                pauseMenu = 9;
            }

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(pauseMenu));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", response + "N", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(6));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay.play();
        }
    }

    public static void sendResponseUpdatedOrders(Integer num, String response, Cliente user, boolean saveReceipt){
        if(num > 0){
            LoadPage.answerScene("positive", response + "Y", null);

            int pauseMenu = 0;
            if(saveReceipt){
                //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
                PauseTransition delayS = new PauseTransition(Duration.seconds(3));
                delayS.setOnFinished(event -> {
                    // Dopo 2 secondi, carica la scena del salvataggio scontrino
                    LoadPage.answerScene("info", "ODR-SAR", null);
                });
                delayS.play();

                pauseMenu = 9; //pausa prima che venga mostrata la scena menu e nel frattempo viene mostrano l'info scontrino
            }
            else{
                pauseMenu = 3;
            }

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(pauseMenu));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", response + "N", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(6));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay.play();
        }
    }

    public static void sendResponseDeletedCategories(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "CAT-DY", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "CAT-IR", null);
            });
            delay.play();

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(9));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "CAT-DN", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay.play();
        }
    }

    public static void sendResponseDeletedMaterials(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "MAT-DY", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "MAT-IR", null);
            });
            delay.play();

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(9));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "MAT-DN", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true, false);
            });
            delay.play();
        }
    }

    //------------------CHECK INTERNET-----------------------

    public static boolean checkInternet(){
        boolean isConnected;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("8.8.8.8", 53), 2000); //cerca di connettere a google come prova
            isConnected = true;
        } catch (IOException e) {
            isConnected = false;
        }

        return isConnected;
    }
}
