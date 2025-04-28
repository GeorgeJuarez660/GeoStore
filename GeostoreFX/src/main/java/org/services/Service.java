package org.services;

import org.models.*;
import org.utility.Utility;


import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Service {

    ProdottoRepository pr = new ProdottoRepository();
    UtenteRepository ur = new UtenteRepository();
    CodiceRepository cor = new CodiceRepository();
    NewsRepository nr = new NewsRepository();
    CategoriaRepository cr = new CategoriaRepository();
    MaterialeRepository mr = new MaterialeRepository();
    OrdineRepository or = new OrdineRepository();
    DisponibilitaRepository dr = new DisponibilitaRepository();
    StatusRepository sr = new StatusRepository();


    public Utente ottieniProfiloUtente(Integer idUtente){
        return ur.getUtenteWithDB(idUtente);
    }

    public Map<Integer, Utente> ottieniUtenteByKeyword(String keyword){
        return ur.getUtentiByKeywordWithDB(keyword);
    }

    public Map<Integer, Utente> elencoUtenti(){
        return ur.getUtentiWithDB();
    }

    public void loginUtente(Cliente user){
        //TODO: inserire il check se l'utente trovato dalla query è un admin:
        // se affermativo, allora viene restituito un messaggio d'errore indicando che si deve inserire il codice admin per loggarti

        int num = 0;
        if(user instanceof Amministratore){   //LOGIN ADMIN
            Amministratore admin = (Amministratore) user;

            boolean checkNN = user.checkNotNullLoginAdmin(admin);

            if(checkNN){
                admin = ur.checkAdmin(admin.getEmail(), admin.getPassword(), admin.getCodiceAdmin().getCodice());

                if(admin.getEmail() != null && admin.getPassword() != null && admin.getCodiceAdmin().getCodice() != null) {
                    num = 1;
                }
                user = admin;
            }
            else{
                num = 0;
            }

        }
        else{   //LOGIN CLIENTE
            boolean checkNN = user.checkNotNullLoginCliente(user);

            if(checkNN){
                user = ur.checkCliente(user.getEmail(), user.getPassword());

                if(user.getEmail() != null && user.getPassword() != null){
                    num = 1;
                }

                //non potendo gestire qui il check, altrimenti viene generato l'exc ClassCastException poichè è stato
                //inizializzato come Cliente e non come Amministratore

            }
            else{
                num = 0;
            }
        }

        Utility.sendResponseLogin(num, user);
    }

    public void registerUtente(Cliente user){
        int num = 0;

        boolean checkNN = user.checkNotNullUtente(user);

        if(checkNN){
            if(user instanceof Amministratore){
                Codice codice = new Codice();
                Amministratore admin = (Amministratore) user;
                num = cor.getIDIfExistCode(admin.getCodiceAdmin().getCodice());
                codice.setCodice(String.valueOf(num));
                admin.setCodeAdmin(codice);
            }
            else{
                num = 1;
            }

            if(num > 0){
                num = ur.checkDuplicatesUtente(user);

                if(num == 0){
                    num = ur.insertUtenteWithDB(user.getId(), user);

                    Utility.sendResponseRegister(num);
                }
                else{
                    Utility.sendResponseRegister(0);
                }
            }
            else{
                Utility.sendResponseRegister(0);
            }
        }
        else{
            Utility.sendResponseRegister(0);
        }
    }

    public void creazioneUtente(Cliente user, Cliente userID){
        int num = 0;
        boolean checkNN = user.checkNotNullUtente(user);

        if(checkNN){
            if(user instanceof Amministratore){
                Codice codice = new Codice();
                Amministratore admin = (Amministratore) user;
                num = cor.getIDIfExistCode(admin.getCodiceAdmin().getCodice());
                codice.setCodice(String.valueOf(num));
                admin.setCodeAdmin(codice);
            }
            else{
                num = 1;
            }

            if(num > 0){
                num = ur.checkDuplicatesUtente(user);

                if(num == 0){
                    if(Utility.getAge(user.getDataNascita())){
                        num = ur.insertUtenteWithDB(null, user);

                        Utility.sendResponse(num, "USR-C", userID);
                    }
                    else{
                        Utility.sendResponse(0, "USR-CW", userID);
                    }
                }
                else{
                    Utility.sendResponse(0, "USR-CR", userID);
                }
            }
            else{
                Utility.sendResponse(0, "USR-CA", userID);
            }
        }
        else{
            Utility.sendResponse(0, "USR-CF", userID);
        }

    }

    public void modificaUtente(Utente u, Cliente userID){
        int num = 0;

        boolean checkNN = u.checkNotNullUtente(u);

        if(checkNN) {
            if(u instanceof Amministratore){
                Codice codice = new Codice();
                Amministratore admin = (Amministratore) u;
                num = cor.getIDIfExistCode(admin.getCodiceAdmin().getCodice());
                codice.setCodice(String.valueOf(num));
                admin.setCodeAdmin(codice);
            }
            else{
                num = 1;
            }

            if(num > 0){
                if(Utility.getAge(u.getDataNascita())){
                    num = ur.updateUtenteWithDB(u.getId(), u);

                    Utility.sendResponse(num, "USR-U", userID);
                }
                else{
                    Utility.sendResponse(0, "USR-UW", userID);
                }
            }
            else{
                Utility.sendResponse(0, "USR-UA", userID);
            }
        }
        else{
            Utility.sendResponse(0, "USR-UF", userID);
        }

    }

    public void eliminazioneUtente(String IDkey, Cliente userID){
        int num = ur.deleteUtenteWithDB(Integer.parseInt(IDkey));

        Utility.sendResponse(num, "USR-D", userID);
    }

    public Map<Integer, Codice> elencoCodici(){
        return cor.getCodiciWithDB();
    }

    public Codice ottieniCodice(Integer idCodice){
        return cor.getCodiceWithDB(idCodice);
    }

    public Map<Integer, Codice> ottieniCodiceByKeyword(String keyword){
        return cor.getCodiceByKeyword(keyword);
    }

    public void creazioneCodice(Codice code, Cliente user){
        int num = 0;

        boolean checkNN = code.checkNotNullCodice(code);

        if(checkNN) {
            num = cor.checkDuplicatesCodice(code);

            if(num == 0){
                num = cor.insertCodiceWithDB(null, code);

                Utility.sendResponse(num, "COD-C", user);
            }
            else{
                Utility.sendResponse(0, "COD-CR", user);
            }
        }
        else{
            Utility.sendResponse(0,"COD-CF", user);
        }

    }

    public void modificaCodice(Codice code, Cliente user){
        int num = 0;

        boolean checkNN = code.checkNotNullCodice(code);

        if(checkNN) {
            num = cor.checkDuplicatesCodice(code);

            if(num == 0){
                num = cor.updateCodiceWithDB(code.getId(), code);

                Utility.sendResponse(num, "COD-U", user);
            }
            else{
                Utility.sendResponse(0, "COD-UR", user);
            }
        }
        else{
            Utility.sendResponse(0, "COD-UF", user);
        }

    }

    public void eliminazioneCodice(String IDkey, Cliente user){
        int num = 0;

        //TODO: informare ai clienti che il codice è stato eliminato e che sono stati aggiornati gli utenti

        num = cor.setNullAfterDeleteCode(Integer.parseInt(IDkey));
        if(num > 0){
            Utility.msgInf("GEOSTORE", "Utenti aggiornati\n");
        }
        else{
            Utility.msgInf("GEOSTORE", "Utenti non aggiornati\n");
        }

        num = cor.deleteCodiceWithDB(Integer.parseInt(IDkey));

        Utility.sendResponse(num, "COD-D", user);

    }

    public Map<Integer, CodiceAssociateDTO> elencoCodiciAssociati(){
        return cor.getCodiciAssociatiWithDB();
    }

    public CodiceAssociateDTO ottieniCodiceAssociato(String emailCodiceAssociato){
        return cor.getCodiceAssociatoWithDB(emailCodiceAssociato);
    }

    public Map<Integer, CodiceAssociateDTO> ottieniCodiceAssociatoByKeyword(String keyword){
        return cor.getCodiceAssociatoByEmailKeyword(keyword);
    }

    public void associazioneCodice(CodiceAssociateDTO codeAssociate, Cliente user){
        int num = 0;

        boolean checkNN = codeAssociate.checkNotNullCodiceAssociato(codeAssociate);

        if(checkNN) {
            num = cor.checkAlreadyAssociatedCodice(codeAssociate.getIdCodice(), codeAssociate.getEmailUtente());

            if(num == 0){
                num = cor.associateCodiceToUtenteWithDB(codeAssociate.getIdCodice(), codeAssociate.getEmailUtente());

                Utility.sendResponse(num, "COD-AC", user);
            }
            else{
                Utility.sendResponse(0, "COD-ACR", user);
            }
        }
        else{
            Utility.sendResponse(0, "COD-ACF", user);
        }

    }

    public void modificaAssociazioneCodice(CodiceAssociateDTO codeAssociate, Cliente user){
        int num = 0;

        //TODO: informare ai clienti che il codice è stato dissociato al precedente utente

        boolean checkNN = codeAssociate.checkNotNullCodiceAssociato(codeAssociate);

        if(checkNN) {
            num = cor.checkAlreadyAssociatedCodice(codeAssociate.getIdCodice(), codeAssociate.getEmailUtente());

            if(num == 0){

                num = cor.dissociateCodiceToUtenteWithDB(codeAssociate.getIdCodice(), codeAssociate.getKey());

                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Codice dissociato all'utente precedente\n");
                }
                else{
                    Utility.msgInf("GEOSTORE", "Utenti aggiornati\n");
                }


                num = cor.associateCodiceToUtenteWithDB(codeAssociate.getIdCodice(), codeAssociate.getEmailUtente());

                Utility.sendResponse(num, "COD-AC", user);
            }
            else{
                Utility.sendResponse(0, "COD-ACR", user);
            }
        }
        else{
            Utility.sendResponse(0, "COD-ACF", user);
        }

    }

    public void dissociazioneCodice(String email, Cliente user){
        int num = 0;

        CodiceAssociateDTO codiceAssociateDTO = cor.getCodiceAssociatoWithDB(email);

        num = cor.dissociateCodiceToUtenteWithDB(codiceAssociateDTO.getIdCodice(), email);

        Utility.sendResponse(num, "COD-AD", user);
    }

    public ArrayList<News> elencoNotizie(){
        return nr.getNotizieWithDB();
    }

    public News ottieniNotiziaByID(String IDNews){
        return nr.getNotiziaWithByIdWithDB(IDNews);
    }

    public ArrayList<News> ottieniNotizieByKeyword(String keyword){
        return nr.getNotizieByKeywordWithDB(keyword);
    }

    public void creazioneNotizia(News notizia, Cliente user){
        notizia.setUtente(user);
        int num = 0;

        if(notizia.checkNotNullNotizia(notizia)){
            num = nr.insertNotizieWithDB(notizia.getDataPub(), notizia.getDataMod(), notizia.getTesto(), notizia.getUtente().getId());

            Utility.sendResponse(num, "NWS-C", user);
        }
        else{
            Utility.sendResponse(0, "NWS-CF", user);
        }

    }

    public void creazioneNotiziaSenzaRisposta(News notizia){
        int num = 0;

        nr.insertNotizieWithDB(notizia.getDataPub(), notizia.getDataMod(), notizia.getTesto(), notizia.getUtente().getId());
    }

    public void modificaNotizia(News notizia, Cliente user){
        notizia.setUtente(user);
        int num = 0;

        if(notizia.checkNotNullNotizia(notizia)){
            num = nr.updateNotizieWithDB(notizia.getId(), notizia.getDataMod(), notizia.getTesto(), notizia.getUtente().getId());

            Utility.sendResponse(num, "NWS-U", user);
        }
        else{
            Utility.sendResponse(0, "NWS-UF", user);
        }

    }

    public void eliminazioneNotizia(String IDKey, Cliente user){
        int num = 0;

        num = nr.deleteNotizieWithDB(Integer.parseInt(IDKey));

        Utility.sendResponse(num, "NWS-D", user);
    }

    public Map<Integer, Prodotto> elencoProdotti(){
        return pr.getProdottiWithDB();
    }

    public Map<Integer, Prodotto> elencoProdottiDisponibili(){
        return pr.getProdottiDispWithDB();
    }

    public Map<Integer, Prodotto> ottieniProdottoByKeyword(String keyword){
        return pr.getProdottoByKeywordWithDB(keyword);
    }

    public Prodotto ottieniProdotto(Integer idProdotto){
        return pr.getProdottoWithDB(idProdotto);
    }

    public Map<Integer, Prodotto> ottieniProdottoDisponibileByKeyword(String keyword){
        return pr.getProdottoDispByKeywordWithDB(keyword);
    }

    public void creazioneProdotto(Prodotto product, Cliente user){
        int num = 0;

        boolean checkNN = product.checkNotNullProdotto(product);

        if(checkNN) {

            product.getDisponibilita().setId(dr.getIdByCode(product.getDisponibilita().getCode()));
            product.getCategoria().setId(cr.getIdByCode(product.getCategoria().getCodice()));
            product.getMateriale().setId(mr.getIdByCode(product.getMateriale().getCodice()));

            num = pr.insertProdottoWithDB(product.getId(), product);

            if(num > 0){
                //notizia per la creazione prodotto
                News notiziaCreazione = new News();
                notiziaCreazione.setUtente(user);
                notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
                notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
                notiziaCreazione.setTesto("PRD-CN1: " + product.getNome() + " PRD-CN2 " + Utility.formatValueInStringWithZeros(product.getPrezzo()) + " C. " + product.getDisponibilita().getCode() + " PRD-CN3");
                this.creazioneNotiziaSenzaRisposta(notiziaCreazione);
            }

            Utility.sendResponse(num, "PRD-C", user);
        }
        else{
            Utility.sendResponse(0, "PRD-CF", user);
        }
    }

    public void modificaProdotto(Prodotto product, Cliente user){
        int num = 0;

        boolean checkNN = product.checkNotNullProdotto(product);

        if(checkNN) {
            Prodotto p = pr.getProdottoWithDB(product.getId()); //per la notizia della modifica da prodotto "old" a "new"

            product.getDisponibilita().setId(dr.getIdByCode(product.getDisponibilita().getCode()));
            product.getCategoria().setId(cr.getIdByCode(product.getCategoria().getCodice()));
            product.getMateriale().setId(mr.getIdByCode(product.getMateriale().getCodice()));

            num = pr.updateProdottoWithDB(product.getId(), product);

            if(num > 0){
                //notizia per la modifica prodotto
                News notiziaCreazione;

                if(!p.getNome().equals(product.getNome())){
                    notiziaCreazione = new News();
                    notiziaCreazione.setUtente(user);
                    notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setTesto("PRD-UNN1: PRD-UNN2 " + p.getNome() + " PRD-UNN3 " + product.getNome());
                    this.creazioneNotiziaSenzaRisposta(notiziaCreazione);
                }

                if(!p.getPrezzo().equals(product.getPrezzo())){
                    notiziaCreazione = new News();
                    notiziaCreazione.setUtente(user);
                    notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));

                    if(p.getPrezzo().compareTo(product.getPrezzo()) > 0){
                        notiziaCreazione.setTesto("PRD-UNPD " + product.getNome() + ": PRD-UNP2 " + Utility.formatValueInStringWithZeros(p.getPrezzo()) + " C PRD-UNP3 " + Utility.formatValueInStringWithZeros(product.getPrezzo()) + " C");
                    }
                    else{
                        notiziaCreazione.setTesto("PRD-UNPI " + product.getNome() + ": PRD-UNP2 " + Utility.formatValueInStringWithZeros(p.getPrezzo()) + " C PRD-UNP3 " + Utility.formatValueInStringWithZeros(product.getPrezzo()) + " C");
                    }

                    this.creazioneNotiziaSenzaRisposta(notiziaCreazione);
                }

                if(!p.getCategoria().getId().equals(product.getCategoria().getId())){
                    notiziaCreazione = new News();
                    notiziaCreazione.setUtente(user);
                    notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setTesto("PRD-UNC1 " + product.getNome() + " PRD-UNC2: PRD-UNC3 " + p.getCategoria().getNome() + " PRD-UNC4 " + product.getCategoria().getNome());
                    this.creazioneNotiziaSenzaRisposta(notiziaCreazione);
                }

                if(!p.getDisponibilita().getId().equals(product.getDisponibilita().getId())){
                    notiziaCreazione = new News();
                    notiziaCreazione.setUtente(user);
                    notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));

                    if(product.getDisponibilita().getId() == 1){
                        notiziaCreazione.setTesto("PRD-UNA1 " + product.getNome() + " PRD-UNA2 " + product.getDisponibilita().getCode() + " PRD-UND3");
                    }
                    else if(product.getDisponibilita().getId() == 2){
                        notiziaCreazione.setTesto("PRD-UNA1 " + product.getNome() + " PRD-UNA2 " + product.getDisponibilita().getCode() + " PRD-UND3");
                    }
                    else if(product.getDisponibilita().getId() == 3){
                        notiziaCreazione.setTesto("PRD-UNA1 " + product.getNome() + " PRD-UNA2 " + product.getDisponibilita().getCode() + ". PRD-UNM3 " + product.getQuantita_disp() + " PRD-UNM4");
                    }
                    else if(product.getDisponibilita().getId() == 4){
                        notiziaCreazione.setTesto("PRD-UNA1 " + product.getNome() + " PRD-UNA2 " + product.getDisponibilita().getCode() + ". PRD-UNR3");
                    }
                    else{
                        notiziaCreazione.setTesto("PRD-UNA1 " + product.getNome() + " PRD-UNO2 " + product.getDisponibilita().getCode() + ". PRD-UNO3");
                    }

                    this.creazioneNotiziaSenzaRisposta(notiziaCreazione);
                }
            }

            Utility.sendResponse(num, "PRD-U", user);
        }
        else{
            Utility.sendResponse(0, "PRD-UF", user);
        }
    }

    public void eliminazioneProdotto(String IDkey, Cliente user){
        Prodotto product = pr.getProdottoWithDB(Integer.parseInt(IDkey));
        HashMap<Integer, Ordine> ordini = or.getOrdiniByProductWithDB(Integer.parseInt(IDkey));

        for(Ordine ordine : ordini.values()){
            //rimborso agli utenti durante l'eliminazione degli ordini in stato diverso da RIFIUTATO
            if(ordine.getStato().getId() != 3 && ordine.getStato().getId() != 5){
                refundBeforeDeleteOrUpdateOrder(ordine, ordine.getUtente());
            }
            else{
                Utility.msgInf("GEOSTORE", "L'ordine è già stato rifiutato oppure consegnato\n");
            }
        }

        //elimina gli ordini prima dell'eliminazione del prodotto
        int num = or.deleteOrdineBeforeDeleteProduct(Integer.parseInt(IDkey));

        if(num > 0){
            Utility.msgInf("GEOSTORE", "Ordini eliminati\n");
        }
        else{
            Utility.msgInf("GEOSTORE", "Ordini non eliminati\n");
        }

        num = 0;
        num = pr.deleteProdottoWithDB(Integer.parseInt(IDkey));

        if(num > 0){
            //notizia per l'eliminazione prodotto
            News notiziaCreazione = new News();
            notiziaCreazione.setUtente(user);
            notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setTesto("PRD-DN1 " + product.getNome() + ". PRD-DN2");
            this.creazioneNotiziaSenzaRisposta(notiziaCreazione);
        }

        Utility.sendResponseDeletedProducts(num, user);
    }

    public Map<Integer, Ordine> elencoOrdini(){
        return or.getOrdiniWithDB();
    }

    public Map<Integer, Ordine> elencoPropriOrdini(Integer idUtente){
        return or.getOrdiniByUserWithDB(idUtente);
    }

    public Map<Integer, Ordine> elencoOrdiniByEmail(String email){
        return or.getOrdiniByEmailWithDB(email);
    }

    public Map<Integer, Ordine> elencoPropriOrdiniByKeyword(Integer idUtente, String keyword){
        return or.getOrdiniByUserAndKeywordWithDB(idUtente, keyword);
    }

    public Ordine ottieniOrdine(Integer idOrdine){
        return or.getOrdineWithDB(idOrdine);
    }

    public void ordinazioneProdotto(Ordine o, Cliente user, boolean saveReceipt){
        if(o.checkNotNullOrdine(o)){
            String canOrder = checkAmountOrderAndSufficientWallet(o, user);
            char firstchar = canOrder.charAt(0);
            String response = canOrder.substring(4);
            if(firstchar == 'T'){
                int num = or.insertOrdineWithDB(null, o);

                //TODO: creare il pdf chiamato scontrino con l'ordine effettuato e salvarlo nella stessa cartella del programma

                if(num > 0 && saveReceipt){
                    try{
                        Utility.checkLangBeforeSavingReceiptOP(o.getProdotto().getNome(), o.getPrezzo_unitario(), o.getQuantita(), user);
                    } catch (Exception e) {
                        System.err.println("Errore salvataggio scontrino: " + e.getMessage());
                    }
                }

                Utility.sendResponseOrderedProducts(num, response, user, saveReceipt);
            }
            else{
                Utility.sendResponseOrderedProducts(0, response, user, false);
            }
        }
        else{
            Utility.sendResponse(0, "ODR-CF", user);
        }
    }

    public void modificaOrdine(Ordine order, Cliente user, boolean saveReceipt){
        if(order.checkNotNullOrdine(order)){
            Stato s = sr.getStatoWithDB(order.getStato().getCode());
            order.getStato().setId(s != null ? s.getId() : 0); //inserisco l'id di stato poichè precedentemente non predisponeva

            if(s != null && s.getCode() != null){
                Ordine orderOld = or.getOrdineWithDB(order.getId());
                Utente u = ur.getUtenteWithDB(orderOld.getUtente().getId());
                String responseCheckOrder;

                responseCheckOrder = checkOrderChanged(orderOld, order, u);
                char firstchar = responseCheckOrder.charAt(0);
                String response = responseCheckOrder.substring(4);
                if(firstchar == 'T'){
                    changeStatusProdottoAfterOrder(orderOld, order);

                    int num = or.updateOrdineWithDB(order.getId(), order);

                    //TODO: creare il pdf chiamato scontrino con l'ordine modificato e salvarlo nella stessa cartella del programma

                    if(num > 0 && saveReceipt){
                        boolean itsRefund = responseCheckOrder.contains("ODR-UR");

                        try{
                            Utility.checkLangBeforeSavingReceiptUO(order.getProdotto().getNome(), order.getPrezzo_unitario(), orderOld.getQuantita(), order.getQuantita(), itsRefund, user);
                        } catch (Exception e) {
                            System.err.println("Errore salvataggio scontrino: " + e.getMessage());
                        }
                    }

                    Utility.sendResponseUpdatedOrders(num, response, user, saveReceipt);
                }
                else{
                    Utility.sendResponseUpdatedOrders(0, response, user, saveReceipt);
                }
            }
        }
        else{
            Utility.sendResponse(0, "ODR-UF", user);
        }

    }

    private static String checkAmountOrderAndSufficientWallet(Ordine o, Utente u){
        String canOrder = "";
        ProdottoRepository pr = new ProdottoRepository();
        UtenteRepository ur = new UtenteRepository();

        Prodotto p = pr.getProdottoDispWithDB(o.getProdotto().getId());
        if(p != null && p.getNome() != null){
            Utility.msgInf("GEOSTORE", "Il prodotto è disponibile\n");
            o.setProdotto(p);

            if(o.getQuantita() <= p.getQuantita_disp()){
                o.setPrezzo_unitario(p.getPrezzo());

                BigDecimal pagamento = o.getPrezzo_unitario().multiply(BigDecimal.valueOf(o.getQuantita()));
                BigDecimal denaro = new BigDecimal(0);

                if(u instanceof Amministratore){
                    Amministratore aDenaro = (Amministratore) u;
                    denaro = aDenaro.getPortafoglio();
                }
                else{
                    Cliente cDenaro = (Cliente) u;
                    denaro = cDenaro.getPortafoglio();
                }

                if(pagamento.compareTo(denaro) <= 0){

                    denaro = denaro.subtract(pagamento);

                    if(u instanceof Amministratore){
                        Amministratore aDenaro = (Amministratore) u;
                        aDenaro.setPortafoglio(denaro);
                        u = aDenaro;
                    }
                    else{
                        Cliente cDenaro = (Cliente) u;
                        cDenaro.setPortafoglio(denaro);
                        u = cDenaro;
                    }

                    int num = ur.updateWalletUtente(u.getId(), u);

                    if(num > 0){
                        Utility.msgInf("GEOSTORE", "T - Pagamento riuscito\n");
                        canOrder = "T - ODR-C";
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "F - Pagamento non riuscito\n");
                        canOrder = "F - ODR-C";
                    }

                }
                else{
                    Utility.msgInf("GEOSTORE", "F - Non hai abbastanza denaro\n");
                    canOrder = "F - ODR-CM";
                }
            }
            else{
                Utility.msgInf("GEOSTORE", "F - La quantità ordinata supera quella disponibile\n");
                canOrder = "F - ODR-CQ";
            }
        }
        else{
            Utility.msgInf("GEOSTORE", "F - Il prodotto ordinato non è disponibile oppure è inesistente\n");
            canOrder = "F - ODR-CV";
        }
        return canOrder;
    }

    private String checkOrderChanged(Ordine oOld, Ordine oNew, Utente u){
        UtenteRepository ur = new UtenteRepository();
        BigDecimal pagamentoNuovo = new BigDecimal(0);
        BigDecimal pagamentoVecchio = new BigDecimal(0);
        BigDecimal pagamentoDecisivo = new BigDecimal(0);
        BigDecimal denaro;
        String choose = "", response = "";

        if(!Objects.equals(oOld.getQuantita(), oNew.getQuantita())){
            Utility.msgInf("GEOSTORE", "è stata modificata la quantità ordinata\n");

            if(oOld.getQuantita() < oNew.getQuantita()) {

                Prodotto p = pr.getProdottoDispWithDB(oNew.getProdotto().getId());
                if(oNew.getQuantita() > p.getQuantita_disp()){
                    choose = "N";
                }
                else{
                    Utility.msgInf("GEOSTORE", "Procediamo con il pagamento del denaro richiesto\n");

                    pagamentoNuovo = oNew.getPrezzo_unitario().multiply(BigDecimal.valueOf(oNew.getQuantita()));
                    pagamentoVecchio = oOld.getPrezzo_unitario().multiply(BigDecimal.valueOf(oOld.getQuantita()));

                    pagamentoDecisivo = pagamentoNuovo.subtract(pagamentoVecchio);
                    denaro = new BigDecimal(0);

                    choose = "S";
                }
            }
            else if(oOld.getQuantita() > oNew.getQuantita()) {
                Utility.msgInf("GEOSTORE", "Procediamo con il rimborso del denaro in eccesso\n");

                pagamentoNuovo = oNew.getPrezzo_unitario().multiply(BigDecimal.valueOf(oNew.getQuantita()));
                pagamentoVecchio = oOld.getPrezzo_unitario().multiply(BigDecimal.valueOf(oOld.getQuantita()));

                pagamentoDecisivo = pagamentoVecchio.subtract(pagamentoNuovo);
                denaro = new BigDecimal(0);

                choose = "A";
            }

            if (u instanceof Amministratore) {
                Amministratore aDenaro = (Amministratore) u;
                denaro = aDenaro.getPortafoglio();
            } else {
                Cliente cDenaro = (Cliente) u;
                denaro = cDenaro.getPortafoglio();
            }



            if(choose.equals("S")){
                if (pagamentoDecisivo.compareTo(denaro) <= 0) {

                    denaro = denaro.subtract(pagamentoDecisivo);

                    if (u instanceof Amministratore) {
                        Amministratore aDenaro = (Amministratore) u;
                        aDenaro.setPortafoglio(denaro);
                        u = aDenaro;
                    } else {
                        Cliente cDenaro = (Cliente) u;
                        cDenaro.setPortafoglio(denaro);
                        u = cDenaro;
                    }

                    int num = ur.updateWalletUtente(u.getId(), u);

                    if(num > 0){
                        Utility.msgInf("GEOSTORE", "Pagamento riuscito\n");
                        response = "T - ODR-U";
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "Pagamento non riuscito\n");
                        response = "F - ODR-U";
                    }
                }
                else{
                    Utility.msgInf("GEOSTORE", "Denaro insufficiente\n");
                    response = "F - ODR-UM";
                }
            }
            else if(choose.equals("A")){
                denaro = denaro.add(pagamentoDecisivo);

                if (u instanceof Amministratore) {
                    Amministratore aDenaro = (Amministratore) u;
                    aDenaro.setPortafoglio(denaro);
                    u = aDenaro;
                } else {
                    Cliente cDenaro = (Cliente) u;
                    cDenaro.setPortafoglio(denaro);
                    u = cDenaro;
                }

                int num = ur.updateWalletUtente(u.getId(), u);

                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Rimborso riuscito\n");
                    response = "T - ODR-UR";
                }
                else{
                    Utility.msgInf("GEOSTORE", "Rimborso non riuscito\n");
                    response = "F - ODR-UR";
                }
            }
            else if(choose.equals("N")){
                response = "F - ODR-UQ";
            }
        }
        else{
            response = "T - ODR-UC";
        }
        return response;
    }

    public void changeStatusProdottoAfterOrder(Ordine oOld, Ordine oNew){
        OrdineRepository or = new OrdineRepository();
        if(oOld.getStato().getId() == 1 && oNew.getStato().getId() == 2){
            ProdottoRepository pr = new ProdottoRepository();

            Integer subQuantita = oOld.getProdotto().getQuantita_disp() - oNew.getQuantita();
            Integer newDisp;

            if(subQuantita == 0){
                newDisp = 4;
            }
            else if(subQuantita >= 1 && subQuantita <=3){
                newDisp = 3;
            }
            else{
                newDisp = 1;
            }

            int num = pr.updateProdottoAfterAccOrdineWithDB(oOld.getProdotto().getId(), newDisp, subQuantita);

            if(num > 0){
                Utility.msgInf("GEOSTORE", "Quantità e/o disponibilità aggiornati\n");
            }
            else{
                Utility.msgInf("GEOSTORE", "Quantità e/o disponibilità non aggiornati\n");
            }
        }
        else if(oOld.getStato().getId() == 1 && oNew.getStato().getId() == 3){
            Utente u = oOld.getUtente();

            refundBeforeDeleteOrUpdateOrder(oOld, u);
        }
    }

    public void eliminazioneOrdine(String IDkey, Cliente user){
        Ordine order = or.getOrdineWithDB(Integer.parseInt(IDkey));

        if(order.getStato().getId() == 1) {
            //solo l'ordine con stato ELABORAZIONE si può effettuare il rimborso
            Utente uOrd = order.getUtente();
            refundBeforeDeleteOrUpdateOrder(order, uOrd);

            int num = or.deleteOrdineWithDB(order.getId());
            Utility.sendResponseDeletedOrders(num, user);
        }
        else{
            Utility.sendResponse(0, "ODR-DS", user);
        }
    }

    public void creazioneCategoria(Categoria category, Cliente user){
        int num = 0;
        boolean isNotNull = true;

        // Divide la stringa usando il simbolo "#"
        String[] traduzioni = category.getNome().split("#");

        isNotNull = category.checkNotNullCategoria(traduzioni[0], traduzioni[1], traduzioni[2]);

        if(isNotNull){
            num = cr.checkDuplicatesCategoria(traduzioni[0], traduzioni[1], traduzioni[2]);

            if(num == 0){
                num = cr.insertCategoriaWithDB(category.getId(), category);

                if(num > 0){
                    //notizia per la creazione categoria
                    News notiziaCreazione = new News();
                    notiziaCreazione.setUtente(user);
                    notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setTesto("CAT-CN1: " + category.getNome() + ". CAT-CN2");
                    this.creazioneNotiziaSenzaRisposta(notiziaCreazione);
                }

                Utility.sendResponse(num, "CAT-C", user);
            }
            else{
                Utility.sendResponse(0, "CAT-CR", user);
            }
        }
        else{
            Utility.sendResponse(0, "CAT-CF", user);
        }

    }

    public void modificaCategoria(Categoria category, Cliente user){
        int num = 0;
        boolean isNotNull = true;

        // Divide la stringa usando il simbolo "#"
        String[] traduzioni = category.getNome().split("#");

        isNotNull = category.checkNotNullCategoria(traduzioni[0], traduzioni[1], traduzioni[2]);

        if(isNotNull) {
            Categoria c = cr.getCategoriaWithDB(category.getCodice(), true); //per la notizia della modifica

            num = cr.checkDuplicatesCategoria(traduzioni[0], traduzioni[1], traduzioni[2]);

            if(num == 0){
                num = cr.updateCategoriaWithDB(category.getCodice(), category);

                if(num > 0){
                    //notizia per la modifica categoria
                    News notiziaCreazione = new News();
                    notiziaCreazione.setUtente(user);
                    notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
                    notiziaCreazione.setTesto("CAT-UN1: CAT-UN2 " + c.getNome() + " CAT-UN3 " + category.getNome());
                    this.creazioneNotiziaSenzaRisposta(notiziaCreazione);
                }

                Utility.sendResponse(num, "CAT-U", user);
            }
            else{
                Utility.sendResponse(0, "CAT-UR", user);
            }
        }
        else{
            Utility.sendResponse(0, "CAT-UF", user);
        }

    }

    public void eliminazioneCategoria(String codeKey, Cliente user){
        Categoria category = cr.getCategoriaWithDB(codeKey, true);

        int num = pr.updateIdBeforeDeleteCategory(0, category.getId());
        if(num > 0){
            Utility.msgInf("GEOSTORE", "Prodotti aggiornati\n");
        }
        else{
            Utility.msgInf("GEOSTORE", "Prodotti non aggiornati\n");
        }

        num = cr.deleteCategoriaWithDB(category.getCodice());

        if(num > 0){
            //notizia per l'eliminazione categoria
            News notiziaCreazione = new News();
            notiziaCreazione.setUtente(user);
            notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setTesto("CAT-DN1 " + category.getNome() + ". CAT-DN2");
            this.creazioneNotiziaSenzaRisposta(notiziaCreazione);
        }

        Utility.sendResponseDeletedCategories(num, user);
    }

    public HashMap<Integer, Categoria> ottieniCategorie() {
        return cr.getCategorieWithDB();
    }

    public Categoria ottieniCategoria(String codiceCategoria, boolean multiLang) {
        return cr.getCategoriaWithDB(codiceCategoria, multiLang);
    }

    public void refundBeforeDeleteOrUpdateOrder(Ordine o, Utente u){
        BigDecimal pagamento = o.getPrezzo_unitario().multiply(BigDecimal.valueOf(o.getQuantita()));

        if(u instanceof Amministratore){
            Amministratore aDenaro = (Amministratore) u;
            aDenaro.setPortafoglio(aDenaro.getPortafoglio().add(pagamento));
            u = aDenaro;
        }
        else{
            Cliente cDenaro = (Cliente) u;
            cDenaro.setPortafoglio(cDenaro.getPortafoglio().add(pagamento));
            u = cDenaro;
        }

        int num = ur.updateWalletUtente(u.getId(), u);

        if(num > 0){
            Utility.msgInf("GEOSTORE", "Denaro rimborsato\n");
        }
        else{
            Utility.msgInf("GEOSTORE", "Denaro non rimborsato\n");
        }
    }

    public Ordine ordiniTotaliGiornalieri(Utente u, String chooseDate){
        return or.getOrdineTotGiorWithDB(u, chooseDate);
    }

    public HashMap<Integer, Ordine> ottieniListaOrdiniAccettati(Utente u, String chooseDate){
        return or.getAcceptedOrdersByUserAndDate(u, chooseDate);
    }

    public Map<Integer, Prodotto> prodottiViaCategoria(String IDCategoryKey){
        return pr.getProdottiViaCategoriaWithDB(Integer.parseInt(IDCategoryKey));
    }

    public Map<Integer, Prodotto> prodottiViaCategoriaByKeyword(String IDCategoryKey, String keyword){
        return pr.getProdottiViaCategoriaByKeywordWithDB(Integer.parseInt(IDCategoryKey), keyword);
    }

    public void creazioneMateriale(Materiale material, Cliente user){
        int num = 0;
        boolean isNotNull = true;

        //divide la stringa usando il simbolo "#"
        String[] traduzioni = material.getNome().split("#");

        isNotNull = material.checkNotNullMateria(traduzioni[0], traduzioni[1], traduzioni[2]);

        if(isNotNull){
            num = mr.checkDuplicatesMateriale(traduzioni[0], traduzioni[1], traduzioni[2]);

            if(num == 0){
                num = mr.insertMaterialeWithDB(material.getId(), material);

                Utility.sendResponse(num, "MAT-C", user);
            }
            else {
                Utility.sendResponse(0, "MAT-CR", user);
            }
        }
        else{
            Utility.sendResponse(0, "MAT-CF", user);
        }

    }

    public void modificaMateriale(Materiale material, Cliente user){
        int num = 0;
        boolean isNotNull = true;

        // Divide la stringa usando il simbolo "#"
        String[] traduzioni = material.getNome().split("#");

        isNotNull = material.checkNotNullMateria(traduzioni[0], traduzioni[1], traduzioni[2]);

        if(isNotNull) {
            num = mr.checkDuplicatesMateriale(traduzioni[0], traduzioni[1], traduzioni[2]);

            if(num == 0){
                num = mr.updateMaterialeWithDB(material.getCodice(), material);

                Utility.sendResponse(num, "MAT-U", user);
            }
            else{
                Utility.sendResponse(0, "MAT-UR", user);
            }
        }
        else{
            Utility.sendResponse(0, "MAT-UF", user);
        }

    }

    public void eliminazioneMateriale(String codeKey, Cliente user){
        Materiale material = mr.getMaterialeWithDB(codeKey, true);

        int num = pr.updateIdBeforeDeleteMaterial(0, material.getId());
        if(num > 0){
            Utility.msgInf("GEOSTORE", "Prodotti aggiornati\n");
        }
        else{
            Utility.msgInf("GEOSTORE", "Prodotti non aggiornati\n");
        }

        num = mr.deleteMaterialeWithDB(material.getCodice());

        Utility.sendResponseDeletedMaterials(num, user);
    }

    public HashMap<Integer, Materiale> ottieniMateriali(){
        return mr.getMaterialiWithDB();
    }

    public Materiale ottieniMateriale(String codiceMateriale, boolean multiLang){
        return mr.getMaterialeWithDB(codiceMateriale, multiLang);
    }

    public HashMap<Integer, Prodotto> prodottiViaMateriale(String IDMaterialKey){
        return pr.getProdottiViaMaterialeWithDB(Integer.parseInt(IDMaterialKey));
    }

    public Map<Integer, Prodotto> prodottiViaMaterialeByKeyword(String IDMaterialKey, String keyword){
        return pr.getProdottiViaMaterialeByKeywordWithDB(Integer.parseInt(IDMaterialKey), keyword);
    }

    public HashMap<Integer, Disponibilita> ottieniDisponibilita(){
        return dr.getDisponibilitaWithDB();
    }

    public HashMap<Integer, Stato> ottieniStato(){
        return sr.getStatusWithDB();
    }
}
