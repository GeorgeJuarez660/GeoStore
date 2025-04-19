package org.utility;


import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.models.Cliente;
import org.models.News;
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

    public static String formatValueBigDecimal(BigDecimal value){
        String formattedValue = "";

        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.ITALIAN);
        DecimalFormat df = new DecimalFormat("###,##0.00", dfs);
        formattedValue = df.format(value);

        return formattedValue;
    }

    public static BigDecimal formatValueString(String value){
        BigDecimal formattedValue = new BigDecimal(0);

        value = value.replace(",", ".");
        formattedValue = new BigDecimal(value);

        return formattedValue;
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
                LoadPage.goesToMenu(user, null, false);
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
            LoadPage.goesToMenu(user, null, true);
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
                LoadPage.goesToMenu(user ,null, true);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "PRD-DN", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true);
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
                LoadPage.goesToMenu(user, null, true);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "ODR-DN", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true);
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
                LoadPage.goesToMenu(user, null, true);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", response + "N", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(6));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true);
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
                LoadPage.goesToMenu(user, null, true);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", response + "N", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(6));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true);
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
                LoadPage.goesToMenu(user, null, true);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "CAT-DN", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true);
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
                LoadPage.goesToMenu(user, null, true);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "MAT-DN", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null, true);
            });
            delay.play();
        }
    }

    //------------------RECEIPT-----------------------

    public static void checkLangBeforeSavingReceiptOP(String productName, BigDecimal uniPrice, Integer quantity, Utente user) throws Exception {
        //per prima cosa controllo se la lingua del programma è impostata in giapponese
        if(Translater.getLanguage().equals("ja")){
            savingJAReceiptAfterOrderedProduct(productName, uniPrice, quantity, user);
        }
        else{
            savingReceiptAfterOrderedProduct(productName, uniPrice, quantity, user);
        }
    }

    public static void checkLangBeforeSavingReceiptUO(String productName, BigDecimal uniPrice, Integer quantityOld, Integer quantityNew, boolean itsRefund, Utente user) throws Exception {
        //per prima cosa controllo se la lingua del programma è impostata in giapponese
        if(Translater.getLanguage().equals("ja")){
            savingJAReceiptAfterUpdatedOrder(productName, uniPrice, quantityOld, quantityNew, itsRefund, user);
        }
        else{
            savingReceiptAfterUpdatedOrder(productName, uniPrice, quantityOld, quantityNew, itsRefund, user);
        }
    }

    public static void checkLangBeforeSavingReceiptTD(BigDecimal dailyTotalPrice, Timestamp totalDate, Map<Integer, Ordine> listaOrdini, Utente user) throws Exception {
        //per prima cosa controllo se la lingua del programma è impostata in giapponese
        if(Translater.getLanguage().equals("ja")){
            savingJAReceiptAfterOrderedTotalPrice(dailyTotalPrice, totalDate, listaOrdini, user);
        }
        else{
            savingReceiptAfterOrderedTotalPrice(dailyTotalPrice, totalDate, listaOrdini, user);
        }
    }


    private static void savingReceiptAfterOrderedProduct(String productName, BigDecimal uniPrice, Integer quantity, Utente user) throws Exception { //salvo lo scontrino dopo l'ordinazione del prodotto
        //mi setto il path dei pdf, xml e xsl
        String pdfPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        String xmlPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";
        String xslPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/stylesheets/";

        String IDReceipt = Utility.getFirstThreeLettersAndLastThreeNumbers(); //recupero l'id random
        String pdfNameOrderedProduct = "receiptOrderedProduct" + LocalDate.now().toString().replace("-", "") + IDReceipt + ".pdf";
        Locale locale = new Locale(Translater.getLanguage()); // Setti il linguaggio di default da prendere il resource
        ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

        Path xmlAbsPath = Paths.get(xmlPath + "receiptOP.xml"); //mi prendo in considerazione l'xml dell'ordinazione prodotto
        File inputFile = xmlAbsPath.toFile();

        // Parsing XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputFile);

        // Modifica il valore di un nodo specifico
        NodeList nodeList = doc.getElementsByTagName("nomeNegozio"); //in questo caso modifico il titolo nome negozio
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.name"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("via"); //in questo caso modifico il titolo via
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.location"));
        }

        //------TITOLI------

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaT1"); //in questo caso modifico il titolo prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.product"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaT2"); //in questo caso modifico il titolo quantità
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.quantity"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaT3"); //in questo caso modifico il titolo prezzo unitario
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.price"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaPT"); //in questo caso modifico il titolo totale
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.total"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaDT"); //in questo caso modifico il titolo id documento
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.id"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaNT"); //in questo caso modifico il titolo cliente
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.user"));
        }

        //------VALORI------

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaC1"); //in questo caso modifico il valore default con il nome del prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(productName);
        }

        nodeList = doc.getElementsByTagName("colonnaC2"); //successivamente modifico il valore default con la quantità ordinata
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent("x" + quantity.toString());
        }

        nodeList = doc.getElementsByTagName("colonnaC3"); //poi modifico il valore default con il prezzo unitario del prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(Utility.formatValueBigDecimal(uniPrice));
        }

        nodeList = doc.getElementsByTagName("colonnaPC"); //modifico il valore default con il totale acquistato
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent("C          " + Utility.formatValueBigDecimal(uniPrice.multiply(new BigDecimal(quantity))));
        }

        //ricavo la data e l'ora
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        nodeList = doc.getElementsByTagName("colonnaDC"); //modifico il valore default con la data e l'ora
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(giornoEsatto+"/"+meseEsatto+"/"+anno + "  " + LocalTime.now().format(timeFormatter));
        }

        nodeList = doc.getElementsByTagName("colonnaNC"); //infine modifico il valore default con il nome e il cognome del cliente
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(user.getCognome() + " " + user.getNome());
        }

        // Scrivi il nuovo XML su file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");//evita di creare gli spazi
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(inputFile);
        transformer.transform(source, result);

        System.out.println("XML modificato con successo.");

        //File xconfFile = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/_test/Java/JavaFXTest/src/main/resources/org/example/javafxtest/xml/propReceipt.xconf");

        // Configurazione di FOP
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        //System.out.println("FOP configuration file exists: " + xconfFile.exists() xconfFile.getAbsolutePath());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        foUserAgent.setProducer("FOP with custom font debug");


        // Creazione del PDF
        try (OutputStream out = new FileOutputStream(new File(pdfPath + pdfNameOrderedProduct))) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Trasformazione XSLT: XML -> XSL-FO
            TransformerFactory factory2 = TransformerFactory.newInstance();
            Transformer transformer2 = factory2.newTransformer(new StreamSource(new File(xslPath + "styleReceiptOP.xsl")));// XSLT file che trasforma XML in XSL-FO

            Source src = new StreamSource(inputFile); // XML di input
            Result res = new SAXResult(fop.getDefaultHandler()); // PDF di output

            transformer2.transform(src, res);
            System.out.println("PDF generato con successo.");
        }
    }

    private static void savingJAReceiptAfterOrderedProduct(String productName, BigDecimal uniPrice, Integer quantity, Utente user) throws Exception { //salvo lo scontrino dopo l'ordinazione del prodotto
        //mi setto il path dei pdf, xml e xsl
        String pdfPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        String xmlPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";
        String xslPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/stylesheets/";

        String IDReceipt = Utility.getFirstThreeLettersAndLastThreeNumbers(); //recupero l'id random
        String pdfNameOrderedProduct = "receiptOrderedProduct" + LocalDate.now().toString().replace("-", "") + IDReceipt + ".pdf";

        Path xmlAbsPath = Paths.get(xmlPath + "receiptOPJapanese.xml"); //mi prendo in considerazione l'xml giapponese dell'ordinazione prodotto
        File inputFile = xmlAbsPath.toFile();

        // Parsing XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputFile);

        //------VALORI------

        // Modifica il valore di un nodo specifico
        NodeList nodeList = doc.getElementsByTagName("colonnaC1"); //in questo caso modifico il valore default con il nome del prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(productName);
        }

        nodeList = doc.getElementsByTagName("colonnaC2"); //successivamente modifico il valore default con la quantità ordinata
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent("x" + quantity.toString());
        }

        nodeList = doc.getElementsByTagName("colonnaC3"); //poi modifico il valore default con il prezzo unitario del prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(Utility.formatValueBigDecimal(uniPrice));
        }

        nodeList = doc.getElementsByTagName("colonnaPC"); //modifico il valore default con il totale acquistato
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent("C          " + Utility.formatValueBigDecimal(uniPrice.multiply(new BigDecimal(quantity))));
        }

        //ricavo la data e l'ora
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        nodeList = doc.getElementsByTagName("colonnaDC"); //modifico il valore default con la data e l'ora
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(giornoEsatto+"/"+meseEsatto+"/"+anno + "  " + LocalTime.now().format(timeFormatter));
        }

        nodeList = doc.getElementsByTagName("colonnaNC"); //infine modifico il valore default con il nome e il cognome del cliente
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(user.getCognome() + " " + user.getNome());
        }

        // Scrivi il nuovo XML su file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");//evita di creare gli spazi
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(inputFile);
        transformer.transform(source, result);

        System.out.println("XML modificato con successo.");

        //File xconfFile = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/_test/Java/JavaFXTest/src/main/resources/org/example/javafxtest/xml/propReceipt.xconf");

        // Configurazione di FOP
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        //System.out.println("FOP configuration file exists: " + xconfFile.exists() xconfFile.getAbsolutePath());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        foUserAgent.setProducer("FOP with custom font debug");


        // Creazione del PDF
        try (OutputStream out = new FileOutputStream(new File(pdfPath + pdfNameOrderedProduct))) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Trasformazione XSLT: XML -> XSL-FO
            TransformerFactory factory2 = TransformerFactory.newInstance();
            Transformer transformer2 = factory2.newTransformer(new StreamSource(new File(xslPath + "styleReceiptOPJapanese.xsl")));// XSLT file che trasforma XML in XSL-FO

            Source src = new StreamSource(inputFile); // XML di input
            Result res = new SAXResult(fop.getDefaultHandler()); // PDF di output

            transformer2.transform(src, res);
            System.out.println("PDF generato con successo.");
        }
    }

    private static void savingReceiptAfterUpdatedOrder(String productName, BigDecimal uniPrice, Integer quantityOld, Integer quantityNew, boolean itsRefund, Utente user) throws Exception{ //salvo lo scontrino dopo la modifica dell'ordine
        //mi setto il path dei pdf, xml e xsl
        String pdfPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        String xmlPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";
        String xslPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/stylesheets/";

        String IDReceipt = Utility.getFirstThreeLettersAndLastThreeNumbers(); //recupero l'id random
        String pdfNameOrderedProduct = "receiptUpdatedOrder" + LocalDate.now().toString().replace("-", "") + IDReceipt + ".pdf";
        Locale locale = new Locale(Translater.getLanguage()); // Setti il linguaggio di default da prendere il resource
        ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

        Path xmlAbsPath = Paths.get(xmlPath + "receiptUO.xml"); //mi prendo in considerazione l'xml della modifica ordine
        File inputFile = xmlAbsPath.toFile();

        // Parsing XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputFile);

        //------TITOLI------

        // Modifica il valore di un nodo specifico
        NodeList nodeList = doc.getElementsByTagName("nomeNegozio"); //in questo caso modifico il titolo nome negozio
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.name"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("via"); //in questo caso modifico il titolo via
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.location"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaT1"); //in questo caso modifico il titolo prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.product"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaT2"); //in questo caso modifico il titolo quantità
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.quantity"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaT3"); //in questo caso modifico il titolo prezzo unitario
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.price"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaPT"); //in questo caso modifico il titolo totale
        if (nodeList.getLength() > 0) {
            if(itsRefund){
                nodeList.item(0).setTextContent(resLang.getString("receipt.totalR"));
            }
            else{
                nodeList.item(0).setTextContent(resLang.getString("receipt.totalS"));
            }
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaDT"); //in questo caso modifico il titolo id documento
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.id"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaNT"); //in questo caso modifico il titolo cliente
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.user"));
        }

        //------VALORI------

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaC1"); //in questo caso modifico il valore default con il nome del prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(productName);
        }

        nodeList = doc.getElementsByTagName("colonnaC2"); //successivamente modifico il valore default con la quantità ordinata
        if (nodeList.getLength() > 0) {
            int quantity;
            if(itsRefund){
                quantity = quantityOld - quantityNew;
                nodeList.item(0).setTextContent("-" + quantity);
            }
            else{
                quantity = quantityNew - quantityOld;
                nodeList.item(0).setTextContent("+" + quantity);
            }
        }

        nodeList = doc.getElementsByTagName("colonnaC3"); //poi modifico il valore default con il prezzo unitario del prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(Utility.formatValueBigDecimal(uniPrice));
        }

        nodeList = doc.getElementsByTagName("colonnaPC"); //modifico il valore default con il totale acquistato
        if (nodeList.getLength() > 0) {
            BigDecimal totalOld = uniPrice.multiply(new BigDecimal(quantityOld));
            BigDecimal totalNew = uniPrice.multiply(new BigDecimal(quantityNew));

            if(itsRefund){
                nodeList.item(0).setTextContent("- C          " + Utility.formatValueBigDecimal(totalOld.subtract(totalNew)));
            }
            else{
                nodeList.item(0).setTextContent("+ C          " + Utility.formatValueBigDecimal(totalNew.subtract(totalOld)));
            }

        }

        //ricavo la data e l'ora
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        nodeList = doc.getElementsByTagName("colonnaDC"); //modifico il valore default con la data e l'ora
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(giornoEsatto+"/"+meseEsatto+"/"+anno + "  " + LocalTime.now().format(timeFormatter));
        }

        nodeList = doc.getElementsByTagName("colonnaNC"); //infine modifico il valore default con il nome e il cognome del cliente
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(user.getCognome() + " " + user.getNome());
        }

        // Scrivi il nuovo XML su file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");//evita di creare gli spazi
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(inputFile);
        transformer.transform(source, result);

        System.out.println("XML modificato con successo.");

        //File xconfFile = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/_test/Java/JavaFXTest/src/main/resources/org/example/javafxtest/xml/propReceipt.xconf");

        // Configurazione di FOP
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        //System.out.println("FOP configuration file exists: " + xconfFile.exists() xconfFile.getAbsolutePath());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        foUserAgent.setProducer("FOP with custom font debug");


        // Creazione del PDF
        try (OutputStream out = new FileOutputStream(new File(pdfPath + pdfNameOrderedProduct))) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Trasformazione XSLT: XML -> XSL-FO
            TransformerFactory factory2 = TransformerFactory.newInstance();
            Transformer transformer2 = factory2.newTransformer(new StreamSource(new File(xslPath + "styleReceiptUO.xsl")));// XSLT file che trasforma XML in XSL-FO

            Source src = new StreamSource(inputFile); // XML di input
            Result res = new SAXResult(fop.getDefaultHandler()); // PDF di output

            transformer2.transform(src, res);
            System.out.println("PDF generato con successo.");
        }
    }

    private static void savingJAReceiptAfterUpdatedOrder(String productName, BigDecimal uniPrice, Integer quantityOld, Integer quantityNew, boolean itsRefund, Utente user) throws Exception{ //salvo lo scontrino dopo la modifica dell'ordine
        //mi setto il path dei pdf, xml e xsl
        String pdfPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        String xmlPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";
        String xslPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/stylesheets/";

        String IDReceipt = Utility.getFirstThreeLettersAndLastThreeNumbers(); //recupero l'id random
        String pdfNameOrderedProduct = "receiptUpdatedOrder" + LocalDate.now().toString().replace("-", "") + IDReceipt + ".pdf";

        Path xmlAbsPath = Paths.get(xmlPath + "receiptUOJapanese.xml"); //mi prendo in considerazione l'xml giapponese della modifica ordine
        File inputFile = xmlAbsPath.toFile();

        // Parsing XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputFile);

        //------TITOLI------

        // Modifica il valore di un nodo specifico
        NodeList nodeList = doc.getElementsByTagName("colonnaPT"); //in questo caso modifico il titolo totale
        if (nodeList.getLength() > 0) {
            if(itsRefund){
                nodeList.item(0).setTextContent("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");
            }
            else{
                nodeList.item(0).setTextContent("iVBORw0KGgoAAAANSUhEUgAAAFkAAAAqCAYAAADYvffAAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAi5SURBVGhD7Zl7VFNXFsa/C2RIAxNYrmYWmdoidI0dBdE6traj06rg2HYsiuJYX6CIWrRq1YHiExFR6wNx6qsMijytLb6t1gqI0ioqYmshAZEQRgWEIEjgGrghe/6oZpELwo1oZqbN77/sb599cr971rn73MsQEcHKM8WGH7Dy9LGabAGsJlsAq8kWwGqyBbCabAGsJlsAq8kWwGqyBbCabAGsJluAJzK5VKVC+PKVqNFo+BIAIO9qPtau/xQsy/KlbpGVfQ7jJ07Crdu3+dJTISv7HPwnTkZlVRVf6hZmm0xEOHrsBBwkEjg7OfNlEBHOZp+D2N4eYrEYAMBxHKKi1yPzbDY/3SxsbW3h7OQMJ6kTX3oq2NraQiZ7HtLfSvlStzDb5OIbJci/dg2TJ02ESGSHkpKbUKnKjLqqrAwFhYXwG+sLG5ufy4tEIviNHYP4vQkoKFS0qWY+YrE97Oxs+eGnho2NDRiGH+0egkzmOD2+v3ARjY2NiN+bgJE+PnjZ3R16vR5JqWnYtn0HWJYFx+mRtv8A3hg8GO5ubiY1PD36IjhoBjZs3ARVmdpEAwCtVova2nvgOD1fMovm5mbUaDTQarV8CQCQkvYFVkREQqfT8aVOaW5uxpLQcGyOieVLXWLHD3SEtlGLtC8OYFPMVvxOJsNY39EAgJKbN1GmVmP92jWQSCQ4eOgIbpaWYl7IHDAMgyNHj+OH69cRtmQRJBIJRgx7G2wTi9DwpfgkdAleHzTIOEfMts+QkZnVZtbHM+pvvvxQO3y8RyBixTJ+GK2trRCJRMatTCj29vaQOkkhk8n4UteQQOrv36d5CxbRoSNHiYiopYWj5SsjKP3gYSIiysg6S2PGT6CfCgqJiIjjOApftpL2JaWY1CEiOp/zHc2cHUIVFZV8qVOyz+fQ3PkLqampiS8JZl9SCq2OiuaHiQTUXx0V3eH1dIWg7QIAnKRSLA8Pw+lvz6CgUIGr+fnQNjZi1F99wHF6/PjjdSz4aB48PfoCANTl5aisqoKP93AAgMFgwKOPMH8ZOgTxn++EXO5iMscvFcEmA4Bc7oKP5oZgy9ZtiIvfg8BpU+Ho6AiRyA6LP16AEcPeBh52GGcysuDVzxO/l8vBcRw2b41Fcmqa0ehfE2aZDAAeffugf/9+eG3QIAzo74Xy8n+jubnZJOf2nTu4kpeHMb6jwTAMRCIR5gTPxMXcy8jKPmeS+2uAITOXVv61H7A7Lh4botdAKpVi45atGODVD++9+w7wcBXvjotHQ0MDAgOmokytRn1dPQqVRVAoFNBoarF1y0a87O7OL90l53K+w5dfpWPThnWQSCTGuMFgAMMwYAT0XonJqYjfm8APCyY4aAYCp03hhzvFLJPv37+P5asiETQ9AANfHQAAuHwlD4nJKVgWHoZVq6Nwo6QEYrEYTlIp5HIX9OzZE6/0/gN6vvAC3Hr1wrETJ6BQFmFtZATs7e1N6rMsi9DwZbj+U4FJvCtsGAazgmdi6uQP+FI7EpNToS4v77DzeNxNfETk2nXo5er6bExWqcoQGb0OtbX3wLIsfiMS4cGDB3B2doaDgwM4jsOihfPh6voS5C4uxkNIUxOL554TG3/j4Y06ePgo/u4/Do6Ojm1mMYWIsDkmFra2tli0cP5jV2n2+Rxs37EL0VGr8Urv3ny5HaoyNVi2CZ4eHnypS5MLCgshkTjA3a0XX+ocfrvREU1NTZR76TLdvVtNdXV1VF1dTQFBwaRQKomI6OSpb2hVZBS1traajFkREUmzPpxLanV5m2rCuVlaSlMCppOyqJgvERFRRUUlTZ423dhGdpeuWrgnRdCDTyKRYPDrr8HJSQpnZ2fwz50+3t5YsfQTkxUrkUiwJmIlfN8fjQWL/4Hs8zlmdxbubm7w8R6BXZ/HtXvZxHF6JCQlw8OjL3zf//lw1BXKoiIsCQtHRWUlXxJE8Y0bmBIwA1fyrvKlThFk8iM2bNqCPQmJJrF9SSk4fOQoRCKRMaYsKkLAjGDcvnMHo997F+uiItHa2moyTggMw8B/nB+ICOmHDhtvEsfpERO7DVVVVVgwLwQikaCDKwCgpkYDO1vh+W3p0aMH7ER2cHR04EudIthknU4HrVZrfOA9YuiQN3HpyhU0Njbi1DensX3nbgAAY8MYj64effvAe/gwMAyDuPg9iP1su+BV7ejoiKVhoTj9bQaSU9Og0zVjx67dUCiVWBoW2um+3hGMDQPGpuP9/Vkh2GRNbS2amli89OKLJnG5ixwikQh3Kipw8dJleHq2f6C0ZeIEfxQXl5jVL8vlLoiKjMDJU6cxdvwElKpU2LLx0/+bE6NgkwsLFZDJnoeTk+m7VgcHCTZER0EikaCmRgOPPn800R9BRCgoVEAsFiNw2hSk7T+Ae/fu8dM6xGAw4EZJCbSNjQAArbYRDQ0N/LT/WQRtTnq9Ht9fzMWQN9+AnV37IUSEr9IP4dUBXpDJZNDU1vJTwLIPkLAvCUEzAvGngQNxJiMTCmURhg75Mz/ViMFgQF5+PnbuioNOp0PEymXw8uyHL9PTMXf+QvTv74WQObPQy9WVP/Sx3L1bjXETOu+nO3vL5+Bg3n4MCGzhdDodfX3yFNXX1xMRUXVNjUkL19raShcu5lJ1TQ0RERUVF9OUgOlUqioz6plns2nm7BDSaGrbVO4YlmXp2PGvaeLkaeTr509Hjh0nnU5nklNVdZfWrF1Hw3xG0czZIXQmI5NYljXJ4aNQKikgKNj4P82Ff91CEWQyn64ma2nhaFNMLA0f+Q4NHeZNbw33oUlTA+lCbi4/1UhdXR2dycik+R8vNhqXfT6HWlo4fqoJ9fX1lJicQr5+/vTWiJEUNOtDSj90mG7dum3St9MvzWRzMRgMtDvuXzRpaiAlp+4XtNr5GAwGUqvL6Z87dpLfhA8oITGJDAYDP+2/gqBjtZXuIbi7sPLkWE22AFaTLYDVZAtgNdkCWE22AFaTLcB/ANW//u13z2voAAAAAElFTkSuQmCC");
            }
        }

        //------VALORI------

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaC1"); //in questo caso modifico il valore default con il nome del prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(productName);
        }

        nodeList = doc.getElementsByTagName("colonnaC2"); //successivamente modifico il valore default con la quantità ordinata
        if (nodeList.getLength() > 0) {
            int quantity;
            if(itsRefund){
                quantity = quantityOld - quantityNew;
                nodeList.item(0).setTextContent("-" + quantity);
            }
            else{
                quantity = quantityNew - quantityOld;
                nodeList.item(0).setTextContent("+" + quantity);
            }
        }

        nodeList = doc.getElementsByTagName("colonnaC3"); //poi modifico il valore default con il prezzo unitario del prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(Utility.formatValueBigDecimal(uniPrice));
        }

        nodeList = doc.getElementsByTagName("colonnaPC"); //modifico il valore default con il totale acquistato
        if (nodeList.getLength() > 0) {
            BigDecimal totalOld = uniPrice.multiply(new BigDecimal(quantityOld));
            BigDecimal totalNew = uniPrice.multiply(new BigDecimal(quantityNew));

            if(itsRefund){
                nodeList.item(0).setTextContent("- C          " + Utility.formatValueBigDecimal(totalOld.subtract(totalNew)));
            }
            else{
                nodeList.item(0).setTextContent("+ C          " + Utility.formatValueBigDecimal(totalNew.subtract(totalOld)));
            }

        }

        //ricavo la data e l'ora
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        nodeList = doc.getElementsByTagName("colonnaDC"); //modifico il valore default con la data e l'ora
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(giornoEsatto+"/"+meseEsatto+"/"+anno + "  " + LocalTime.now().format(timeFormatter));
        }

        nodeList = doc.getElementsByTagName("colonnaNC"); //infine modifico il valore default con il nome e il cognome del cliente
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(user.getCognome() + " " + user.getNome());
        }

        // Scrivi il nuovo XML su file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");//evita di creare gli spazi
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(inputFile);
        transformer.transform(source, result);

        System.out.println("XML modificato con successo.");

        //File xconfFile = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/_test/Java/JavaFXTest/src/main/resources/org/example/javafxtest/xml/propReceipt.xconf");

        // Configurazione di FOP
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        //System.out.println("FOP configuration file exists: " + xconfFile.exists() xconfFile.getAbsolutePath());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        foUserAgent.setProducer("FOP with custom font debug");


        // Creazione del PDF
        try (OutputStream out = new FileOutputStream(new File(pdfPath + pdfNameOrderedProduct))) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Trasformazione XSLT: XML -> XSL-FO
            TransformerFactory factory2 = TransformerFactory.newInstance();
            Transformer transformer2 = factory2.newTransformer(new StreamSource(new File(xslPath + "styleReceiptUOJapanese.xsl")));// XSLT file che trasforma XML in XSL-FO

            Source src = new StreamSource(inputFile); // XML di input
            Result res = new SAXResult(fop.getDefaultHandler()); // PDF di output

            transformer2.transform(src, res);
            System.out.println("PDF generato con successo.");
        }
    }

    private static void savingReceiptAfterOrderedTotalPrice(BigDecimal dailyTotalPrice, Timestamp totalDate, Map<Integer, Ordine> listaOrdini, Utente user) throws Exception { //salvo lo scontrino dopo aver saputo il prezzo totale speso
        //mi setto il path dei pdf, xml e xsl
        String pdfPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        String xmlPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";
        String xslPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/stylesheets/";

        String IDReceipt = Utility.getFirstThreeLettersAndLastThreeNumbers(); //recupero l'id random
        String pdfNameOrderedProduct = "receiptTotalOrderPrice" + LocalDate.now().toString().replace("-", "") + IDReceipt + ".pdf";
        Locale locale = new Locale(Translater.getLanguage()); // Setti il linguaggio di default da prendere il resource
        ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

        Path xmlAbsPath = Paths.get(xmlPath + "receiptTD.xml"); //mi prendo in considerazione l'xml giapponese della modifica ordine
        File inputFile = xmlAbsPath.toFile();

        // Parsing XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputFile);

        //------TITOLI------

        // Modifica il valore di un nodo specifico
        NodeList nodeList = doc.getElementsByTagName("nomeNegozio"); //in questo caso modifico il titolo nome negozio
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.name"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("via"); //in questo caso modifico il titolo via
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.location"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaT1"); //in questo caso modifico il titolo prodotto
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.product"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaT2"); //in questo caso modifico il titolo prezzo unitario
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.priceTD"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaPDT"); //in questo caso modifico il titolo data ordini
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.dateOrders"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaPT"); //in questo caso modifico il titolo totale
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.total"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaDT"); //in questo caso modifico il titolo id documento
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.id"));
        }

        // Modifica il valore di un nodo specifico
        nodeList = doc.getElementsByTagName("colonnaNT"); //in questo caso modifico il titolo cliente
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.user"));
        }

        //------VALORI------

        Node corpoNodo = doc.getElementsByTagName("Corpo").item(0); //innanzitutto mi trovo la radice del ciclo for ovvero Corpo

        List<Node> checkedCorpoTab = new ArrayList<>();//creo una lista in modo da aggiornare quanti tag in tempo reale
        NodeList tagEsistenti = doc.getElementsByTagName("CorpoTabella"); //trovo i tag che si chiamano CorpoTabella
        for(int i = 0; i < tagEsistenti.getLength(); i++){ //li ciclo per quanti ne trovo
            checkedCorpoTab.add(tagEsistenti.item(i)); //li inserisce
        }
        for (Node n : checkedCorpoTab) { //lo ciclo
            corpoNodo.removeChild(n);//rimuove qualsiasi tag CorpoTabella dalla radice Corpo
        }

        //una volta rimosso le reinserisco con nuovo tag (chiamato allo stesso nome) e i figli dentro

        for(Ordine ordine : listaOrdini.values()){
            //mi creo un nuovo tag CorpoTabella
            Element corpoTab = doc.createElement("CorpoTabella");

            //creo il figlio tag colonnaC1
            Element col1 = doc.createElement("colonnaC1");
            if(Translater.getLanguage().equals("it")){ //se il programma è settato in italiano allora scrivo in italiano
                col1.setTextContent(ordine.getProdotto().getNome() + "\nqt. " + ordine.getQuantita() + " prz. C " + Utility.formatValueBigDecimal(ordine.getPrezzo_unitario()));//mando a capo la quantità e prezzo
            }
            else if(Translater.getLanguage().equals("en")){
                col1.setTextContent(ordine.getProdotto().getNome() + "\nqt. " + ordine.getQuantita() + " prc. C " + Utility.formatValueBigDecimal(ordine.getPrezzo_unitario()));//mando a capo la quantità e prezzo
            }
            else{
                System.err.println("ERRORE LINGUAGGIO PROGRAMMA");
            }
            corpoTab.appendChild(col1); //aggancio il tag figlio colonnaC1 al tag CorpoTabella

            //creo il figlio tag colonnaC2
            Element col2 = doc.createElement("colonnaC2");

            BigDecimal totalOrder = ordine.getPrezzo_unitario().multiply(new BigDecimal(ordine.getQuantita()));
            col2.setTextContent(Utility.formatValueBigDecimal(totalOrder)); //inserisco il totale dell'ordine
            corpoTab.appendChild(col2); //aggancio anche la colonnaC2 nel tag CorpoTabella

            corpoNodo.appendChild(corpoTab); //aggancio CorpoTabella nel tag radice Corpo
        }

        //ricavo la data e l'ora
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(totalDate);
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        nodeList = doc.getElementsByTagName("colonnaPDC"); //modifico il valore default con il totale acquistato
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(giornoEsatto+"/"+meseEsatto+"/"+anno);
        }

        nodeList = doc.getElementsByTagName("colonnaPC"); //modifico il valore default con il totale acquistato
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent("C          " + Utility.formatValueBigDecimal(dailyTotalPrice));
        }

        //ricavo la data e l'ora
        calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        giorno = calendario.get(Calendar.DAY_OF_MONTH);
        mese = calendario.get(Calendar.MONTH) + 1;
        anno = calendario.get(Calendar.YEAR);

        giornoEsatto = String.format("%02d", giorno);
        meseEsatto = String.format("%02d", mese);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        nodeList = doc.getElementsByTagName("colonnaDC"); //modifico il valore default con la data e l'ora
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(giornoEsatto+"/"+meseEsatto+"/"+anno + "  " + LocalTime.now().format(timeFormatter));
        }

        nodeList = doc.getElementsByTagName("colonnaNC"); //infine modifico il valore default con il nome e il cognome del cliente
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(user.getCognome() + " " + user.getNome());
        }

        // Scrivi il nuovo XML su file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");//evita di creare gli spazi
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(inputFile);
        transformer.transform(source, result);

        System.out.println("XML modificato con successo.");

        // Configurazione di FOP
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        //System.out.println("FOP configuration file exists: " + xconfFile.exists() xconfFile.getAbsolutePath());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        foUserAgent.setProducer("FOP with custom font debug");


        // Creazione del PDF
        try (OutputStream out = new FileOutputStream(new File(pdfPath + pdfNameOrderedProduct))) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Trasformazione XSLT: XML -> XSL-FO
            TransformerFactory factory2 = TransformerFactory.newInstance();
            Transformer transformer2 = factory2.newTransformer(new StreamSource(new File(xslPath + "styleReceiptTD.xsl")));// XSLT file che trasforma XML in XSL-FO

            Source src = new StreamSource(inputFile); // XML di input
            Result res = new SAXResult(fop.getDefaultHandler()); // PDF di output

            transformer2.transform(src, res);
            System.out.println("PDF generato con successo.");
        }
    }

    private static void savingJAReceiptAfterOrderedTotalPrice(BigDecimal dailyTotalPrice, Timestamp totalDate, Map<Integer, Ordine> listaOrdini, Utente user) throws Exception { //salvo lo scontrino dopo aver saputo il prezzo totale speso
        //mi setto il path dei pdf, xml e xsl
        String pdfPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        String xmlPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";
        String xslPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/stylesheets/";

        String IDReceipt = Utility.getFirstThreeLettersAndLastThreeNumbers(); //recupero l'id random
        String pdfNameOrderedProduct = "receiptTotalOrderPrice" + LocalDate.now().toString().replace("-", "") + IDReceipt + ".pdf";

        Path xmlAbsPath = Paths.get(xmlPath + "receiptTDJapanese.xml"); //mi prendo in considerazione l'xml della modifica ordine
        File inputFile = xmlAbsPath.toFile();

        // Parsing XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputFile);

        //------VALORI------

        Node corpoNodo = doc.getElementsByTagName("Corpo").item(0); //innanzitutto mi trovo la radice del ciclo for ovvero Corpo

        List<Node> checkedCorpoTab = new ArrayList<>();//creo una lista in modo da aggiornare quanti tag in tempo reale
        NodeList tagEsistenti = doc.getElementsByTagName("CorpoTabella"); //trovo i tag che si chiamano CorpoTabella
        for(int i = 0; i < tagEsistenti.getLength(); i++){ //li ciclo per quanti ne trovo
            checkedCorpoTab.add(tagEsistenti.item(i)); //li inserisce
        }
        for (Node n : checkedCorpoTab) { //lo ciclo
            corpoNodo.removeChild(n);//rimuove qualsiasi tag CorpoTabella dalla radice Corpo
        }

        //una volta rimosso le reinserisco con nuovo tag (chiamato allo stesso nome) e i figli dentro

        for(Ordine ordine : listaOrdini.values()){
            //mi creo un nuovo tag CorpoTabella
            Element corpoTab = doc.createElement("CorpoTabella");

            //creo il figlio tag colonnaC1
            Element col1 = doc.createElement("colonnaC1");
            col1.setTextContent(ordine.getProdotto().getNome() + "\nqt. " + ordine.getQuantita() + " prc. C " + Utility.formatValueBigDecimal(ordine.getPrezzo_unitario()));//mando a capo la quantità e prezzo
            corpoTab.appendChild(col1); //aggancio il tag figlio colonnaC1 al tag CorpoTabella

            //creo il figlio tag colonnaC2
            Element col2 = doc.createElement("colonnaC2");

            BigDecimal totalOrder = ordine.getPrezzo_unitario().multiply(new BigDecimal(ordine.getQuantita()));
            col2.setTextContent(Utility.formatValueBigDecimal(totalOrder)); //inserisco il totale dell'ordine
            corpoTab.appendChild(col2); //aggancio anche la colonnaC2 nel tag CorpoTabella

            corpoNodo.appendChild(corpoTab); //aggancio CorpoTabella nel tag radice Corpo
        }

        //ricavo la data e l'ora
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(totalDate);
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        NodeList nodeList = doc.getElementsByTagName("colonnaPDC"); //modifico il valore default con il totale acquistato
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(giornoEsatto+"/"+meseEsatto+"/"+anno);
        }

        nodeList = doc.getElementsByTagName("colonnaPC"); //modifico il valore default con il totale acquistato
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent("C          " + Utility.formatValueBigDecimal(dailyTotalPrice));
        }

        //ricavo la data e l'ora
        calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        giorno = calendario.get(Calendar.DAY_OF_MONTH);
        mese = calendario.get(Calendar.MONTH) + 1;
        anno = calendario.get(Calendar.YEAR);

        giornoEsatto = String.format("%02d", giorno);
        meseEsatto = String.format("%02d", mese);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        nodeList = doc.getElementsByTagName("colonnaDC"); //modifico il valore default con la data e l'ora
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(giornoEsatto+"/"+meseEsatto+"/"+anno + "  " + LocalTime.now().format(timeFormatter));
        }

        nodeList = doc.getElementsByTagName("colonnaNC"); //infine modifico il valore default con il nome e il cognome del cliente
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(user.getCognome() + " " + user.getNome());
        }

        // Scrivi il nuovo XML su file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");//evita di creare gli spazi
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(inputFile);
        transformer.transform(source, result);

        System.out.println("XML modificato con successo.");

        // Configurazione di FOP
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        //System.out.println("FOP configuration file exists: " + xconfFile.exists() xconfFile.getAbsolutePath());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        foUserAgent.setProducer("FOP with custom font debug");


        // Creazione del PDF
        try (OutputStream out = new FileOutputStream(new File(pdfPath + pdfNameOrderedProduct))) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Trasformazione XSLT: XML -> XSL-FO
            TransformerFactory factory2 = TransformerFactory.newInstance();
            Transformer transformer2 = factory2.newTransformer(new StreamSource(new File(xslPath + "styleReceiptTDJapanese.xsl")));// XSLT file che trasforma XML in XSL-FO

            Source src = new StreamSource(inputFile); // XML di input
            Result res = new SAXResult(fop.getDefaultHandler()); // PDF di output

            transformer2.transform(src, res);
            System.out.println("PDF generato con successo.");
        }
    }
}
