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



    public static void savingReceiptAfterOrderedProduct(String productName, BigDecimal uniPrice, Integer quantity, Utente user) throws Exception { //salvo lo scontrino dopo l'ordinazione del prodotto
        //mi setto il path dei pdf, xml e xsl
        String pdfPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        String xmlPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";
        String xslPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";

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

        nodeList = doc.getElementsByTagName("colonnaDT"); //modifico il valore default con l'id dello scontrino
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.id") + "   " + IDReceipt);
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

    public static void savingReceiptAfterUpdatedOrder(String productName, BigDecimal uniPrice, Integer quantityOld, Integer quantityNew, boolean itsRefund, Utente user) throws Exception{ //salvo lo scontrino dopo la modifica dell'ordine
        //mi setto il path dei pdf, xml e xsl
        String pdfPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        String xmlPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";
        String xslPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";

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

        nodeList = doc.getElementsByTagName("colonnaDT"); //modifico il valore default con l'id dello scontrino
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.id") + "   " + IDReceipt);
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

    public static void savingReceiptAfterOrderedTotalPrice(BigDecimal dailyTotalPrice, Timestamp totalDate, Map<Integer, Ordine> listaOrdini, Utente user) throws Exception { //salvo lo scontrino dopo aver saputo il prezzo totale speso
        //mi setto il path dei pdf, xml e xsl
        String pdfPath = System.getProperty("user.dir").replace("\\", "/") + "/";
        String xmlPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";
        String xslPath = "C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/xml/";

        String IDReceipt = Utility.getFirstThreeLettersAndLastThreeNumbers(); //recupero l'id random
        String pdfNameOrderedProduct = "receiptTotalOrderPrice" + LocalDate.now().toString().replace("-", "") + IDReceipt + ".pdf";
        Locale locale = new Locale(Translater.getLanguage()); // Setti il linguaggio di default da prendere il resource
        ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

        Path xmlAbsPath = Paths.get(xmlPath + "receiptTD.xml"); //mi prendo in considerazione l'xml della modifica ordine
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

        NodeList tagEsistenti = doc.getElementsByTagName("CorpoTabella"); //trovo i tag che si chiamano CorpoTabella
        for(int i = 0; i < tagEsistenti.getLength(); i++){ //li ciclo per quanti ne trovo
            corpoNodo.removeChild(tagEsistenti.item(i)); //rimuove qualsiasi tag CorpoTabella dalla radice Corpo
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
            else if(Translater.getLanguage().equals("ja")){
                col1.setTextContent(ordine.getProdotto().getNome() + "\n額 " + ordine.getQuantita() + " 価格 C " + Utility.formatValueBigDecimal(ordine.getPrezzo_unitario()));//mando a capo la quantità e prezzo
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

        nodeList = doc.getElementsByTagName("colonnaDT"); //modifico il valore default con l'id dello scontrino
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(resLang.getString("receipt.id") + "   " + IDReceipt);
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
}
