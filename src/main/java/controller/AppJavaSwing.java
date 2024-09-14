package src.main.java.controller;

import src.main.java.model.*;
import src.main.java.utility.Utility;
import src.main.java.view.View;

public class AppJavaSwing {

     public static void main(String[] args){

          int valueInput = 0;
          boolean flagMenu, flagRegister, flagLogin;
          View view = new View();
          ProdottoRepository pr = new ProdottoRepository();
          UtenteRepository ur = new UtenteRepository();
          CategoriaRepository car = new CategoriaRepository();
          MateriaRepository mr = new MateriaRepository();
          OrdineRepository odr = new OrdineRepository();
          Utente u;
          Cliente c;
          Categoria cat;
          Materia m;
          Ordine o;
          Prodotto p;

          do {
               valueInput = view.registerOrLogin();
               if(valueInput == 1){
                    boolean flagInsert;
                    Utility.msgInf("GEOSTORE", "Registrazione utente\n\n");
                    int csCliente = Utility.insertInt("Scegli 1 per cliente o 2 per amministratore");

                    if(csCliente == 1){
                         u = new Cliente();
                         view.maskInsertUtente(u);

                         do{
                              String question = Utility.insertString("Vuoi procedere? (s/n)");
                              if(question.equalsIgnoreCase("s")) {
                                   int num = ur.insertUtenteWithDB(u.getId(), u);

                                   if (num > 0) {
                                        Utility.msgInf("GEOSTORE", "Nuovo cliente aggiunto\n");
                                   } else {
                                        Utility.msgInf("GEOSTORE", "Cliente non aggiunto\n");
                                   }

                                   flagInsert = false;
                              }else if(question.equalsIgnoreCase("n")){
                                   Utility.msgInf("GEOSTORE", "Operazione annullata");
                                   flagInsert = false;
                              }
                              else{
                                   Utility.msgInf("GEOSTORE", "Rileggi la domanda");
                                   flagInsert = true;
                              }
                         }while(flagInsert);
                    }
                    else if(csCliente == 2){
                         u = new Amministratore();
                         view.maskInsertUtente(u);

                         do{
                              String question = Utility.insertString("Vuoi procedere? (s/n)");
                              if(question.equalsIgnoreCase("s")) {
                                   int num = ur.insertUtenteWithDB(u.getId(), u);

                                   if (num > 0) {
                                        Utility.msgInf("GEOSTORE", "Nuovo cliente aggiunto\n");
                                   } else {
                                        Utility.msgInf("GEOSTORE", "Cliente non aggiunto\n");
                                   }

                                   flagInsert = false;
                              }else if(question.equalsIgnoreCase("n")){
                                   Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                                   flagInsert = false;
                              }
                              else{
                                   Utility.msgInf("GEOSTORE", "Rileggi la domanda\n");
                                   flagInsert = true;
                              }
                         }while(flagInsert);

                    }

                    flagRegister = true;
               }
               else if(valueInput == 2){
                    flagRegister = false;
                    Utility.msgInf("GEOSTORE", "Accesso utente\n\n");
               }
               else{
                    flagRegister = true;
                    Utility.msgInf("GEOSTORE", "Rileggi\n");
               }
          }while(flagRegister);

          valueInput = 0;

          do{
               valueInput = view.readAdminOrUserMenu();

               if(valueInput == 1){
                    Utility.msgInf("GEOSTORE", "Accedi come cliente Geostore\n");
                    c = new Cliente();
                    view.maskCheckUser(c);
                    c = ur.checkCliente(c.getEmail());

                    if(c != null && c.getEmail() != null){
                         Utility.msgInf("GEOSTORE", "Accesso approvato\n");
                         do{
                              valueInput = view.readMenuCliente();
                              switch(valueInput){
                                   case 0:
                                        Utility.msgInf("GEOSTORE", "Nessun operazione\n\n");
                                        break;
                                   case 1:
                                        Utility.msgInf("GEOSTORE", "Profilo utente\n\n");
                                        view.printUtente(ur.getClienteWithDB(c.getNome()));
                                        break;
                                   case 2:
                                        Utility.msgInf("GEOSTORE", "Elenco prodotti\n\n");
                                        view.printProdotti(pr.getProdottiWithDB());
                                        break;
                                   case 3:
                                        Utility.msgInf("GEOSTORE", "Ordinazione di un prodotto\n\n");
                                        view.printProdotti(pr.getProdottiDispWithDB());
                                        o = new Ordine();
                                        view.maskInsertOrdine(o, c);
                                        p = pr.getProdottoDispWithDB(o.getProdotto().getNome());

                                        if(p != null && p.getNome() != null){
                                             Utility.msgInf("GEOSTORE", "Il prodotto è disponibile\n");
                                             o.setProdotto(p);

                                             if(o.getQuantita() <= p.getQuantita_disp()){
                                                  o.setPrezzo_unitario(p.getPrezzo());

                                                  int num = odr.insertOrdineWithDB(null, o);
                                                  if(num > 0){
                                                       Utility.msgInf("GEOSTORE", "Ordine effettuato\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Ordine non effettuato\n");
                                                  }
                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "La quantità ordinata supera quella disponibile\n");
                                             }
                                        }
                                        else{
                                             Utility.msgInf("GEOSTORE", "L'oggetto ordinato non è disponibile oppure è inesistente\n");
                                        }
                                        break;
                                   case 4:
                                        Utility.msgInf("GEOSTORE", "Elenco ordini effettuati\n\n");
                                        view.printOrdini(odr.getOrdiniByUserWithDB(c.getNome()));
                                        break;
                                   case 5:
                                        Utility.msgInf("GEOSTORE", "Eliminazione ordine\n\n");
                                        view.printOrdini(odr.getOrdiniByUserWithDB(c.getNome()));

                                        o = odr.getOrdineByUserAndProdNameWithDB(c.getNome(), Utility.insertInt("Inserisci l'id ordine"));

                                        if(o != null && o.getProdotto() != null && o.getProdotto().getNome() != null){
                                             Utility.msgInf("GEOSTORE", "Ordine trovato\n");
                                             if(Utility.insertString("Sei sicuro di voler eliminare quest'ordine?").equalsIgnoreCase("s")){
                                                  odr.deleteOrdineWithDB(o.getId());
                                                  Utility.msgInf("GEOSTORE", "Ordine eliminato");
                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Operazione annullata");
                                             }
                                        }
                                        else{
                                             Utility.msgInf("GEOSTORE", "Ordine non trovato\n");
                                        }
                                        break;
                                   case 6:
                                        Utility.msgInf("GEOSTORE", "Elenco prodotti per categoria\n\n");
                                        cat = new Categoria();
                                        view.maskObjViaCat(cat);
                                        view.printProdotti(pr.getProdottiViaCategoriaWithDB(cat.getNome()));
                                        break;
                                   case 7:
                                        Utility.msgInf("GEOSTORE", "Elenco prodotti per materia\n\n");
                                        m = new Materia();
                                        view.maskObjViaMat(m);
                                        view.printProdotti(pr.getProdottiViaMateriaWithDB(m.getNome()));
                                        break;
                                   case 8:
                                        Utility.msgInf("GEOSTORE", "Elenco prodotti disponibili\n\n");
                                        view.printProdotti(pr.getProdottiDispWithDB());
                                        break;
                                   case 9:
                                        Utility.msgInf("GEOSTORE", "Ordini totali giornalieri\n\n");
                                        String chooseDate = Utility.insertString("Inserisci la data in formato yyyy-mm-dd");
                                        view.printOrdiniTotGior(odr.getOrdineTotGiorWithDB(c, chooseDate));
                                        break;
                                   default:
                                        Utility.msgInf("GEOSTORE", "Non so cosa hai inserito");
                                        break;
                              }

                              if(Utility.insertString("\nVUOI ANDARE AVANTI?").equalsIgnoreCase("s")){
                                   flagMenu = true;
                                   System.out.println("\n***VA BENE***");
                              }
                              else{
                                   flagMenu = false;
                                   System.out.println("\n***OK COME VUOI***");
                              }
                         }while(flagMenu);
                    }
                    else{
                         Utility.msgInf("GEOSTORE", "Accesso negato - email errata\n");
                    }

               }
               else if(valueInput == 2){
                    Utility.msgInf("GEOSTORE", "Sei un amministratore Geostore\n");
                    c = new Amministratore();
                    view.maskCheckUser(c);
                    Amministratore a = (Amministratore) c;
                    c = ur.checkAdmin(c.getEmail(), a.getCodeAdmin());
                    if(c != null && c.getEmail() != null && a.getCodeAdmin() != null){
                         Utility.msgInf("GEOSTORE", "Accesso approvato\n");

                         do{
                              valueInput = view.readMenuAdmin();
                              switch(valueInput){
                                   case 0:
                                        Utility.msgInf("GEOSTORE", "Nessun operazione\n\n");
                                        break;
                                   case 1:
                                        Utility.msgInf("GEOSTORE", "Profilo utente\n\n");
                                        view.printUtente(ur.getClienteWithDB(c.getNome()));
                                        break;
                                   case 2:
                                        Utility.msgInf("GEOSTORE", "Utenti iscritti su Geostore\n\n");
                                        view.printUtenti(ur.getClientiWithDB());
                                        break;
                                   case 3:
                                        Utility.msgInf("GEOSTORE", "Crea/Modifica/Elimina cliente\n\n");
                                        int chooseC = Utility.insertInt("1 - Crea, 2 - Modifica, 3 - Elimina");

                                        if(chooseC == 1){
                                             int csCliente = Utility.insertInt("Scegli 1 per cliente o 2 per amministratore");

                                             if(csCliente == 1){
                                                  c = new Cliente();
                                                  view.maskInsertUtente(c);
                                                  int num = ur.insertUtenteWithDB(c.getId(), c);

                                                  if(num > 0){
                                                       Utility.msgInf("GEOSTORE", "Nuovo cliente aggiunto\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Cliente non aggiunto\n");
                                                  }

                                             }
                                             else if(csCliente == 2){
                                                  c = new Amministratore();
                                                  view.maskInsertUtente(c);
                                                  int num = ur.insertUtenteWithDB(c.getId(), c);

                                                  if(num > 0){
                                                       Utility.msgInf("GEOSTORE", "Nuovo amministratore aggiunto\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Amministratore non aggiunto\n");
                                                  }
                                             }

                                        }
                                        else if(chooseC == 2){
                                             c = ur.getClienteWithDB(Utility.insertString("Inserisci il nome utente"));

                                             if(c != null && c.getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Utente trovato\n");

                                                  if(c instanceof Amministratore) { //se l'oggetto trovato contiene l'istanza di una classe figlia
                                                       Amministratore cNew = (Amministratore) view.maskUpdateUtente(((Amministratore) c), new Amministratore());

                                                       int num = ur.updateClienteWithDB(cNew.getId(), cNew);

                                                       if(num > 0){
                                                            Utility.msgInf("GEOSTORE", "Amministratore aggiornato\n");
                                                       }
                                                       else{
                                                            Utility.msgInf("GEOSTORE", "Amministratore non aggiornato\n");
                                                       }
                                                  }
                                                  else{
                                                       Cliente cNew = view.maskUpdateUtente(c, new Cliente());

                                                       int num = ur.updateClienteWithDB(cNew.getId(), cNew);

                                                       if(num > 0){
                                                            Utility.msgInf("GEOSTORE", "Cliente aggiornato\n");
                                                       }
                                                       else{
                                                            Utility.msgInf("GEOSTORE", "Cliente non aggiornato\n");
                                                       }
                                                  }

                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Utente non trovato\n");
                                             }
                                        }
                                        else if(chooseC == 3){
                                             c = ur.getClienteWithDB(Utility.insertString("Inserisci il nome utente"));

                                             if(c != null && c.getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Utente trovato\n");
                                                  if(Utility.insertString("Sei sicuro di voler eliminare questo utente?").equalsIgnoreCase("s")){
                                                       int num = ur.deleteClienteWithDB(c.getId());

                                                       if(num > 0){
                                                            Utility.msgInf("GEOSTORE", "Utente eliminato\n");
                                                       }
                                                       else{
                                                            Utility.msgInf("GEOSTORE", "Utente non eliminato\n");
                                                       }

                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                                                  }
                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Utente non trovato\n");
                                             }
                                        }
                                        else{
                                             Utility.msgInf("GEOSTORE", "Non so cosa hai inserito\n");
                                        }
                                        break;
                                   case 4:
                                        Utility.msgInf("GEOSTORE", "Elenco prodotti\n\n");
                                        view.printProdotti(pr.getProdottiWithDB());
                                        break;
                                   case 5:
                                        Utility.msgInf("GEOSTORE", "Crea/Modifica/Elimina prodotto\n\n");
                                        int chooseP = Utility.insertInt("1 - Crea, 2 - Modifica, 3 - Elimina");

                                        if(chooseP == 1){
                                             p = new Prodotto();
                                             view.maskInsertProdotto(p);

                                             cat = car.getCategoriaWithDB(p.getCategoria().getNome());
                                             m = mr.getMateriaWithDB(p.getMateria().getNome());

                                             if(cat != null && m != null && cat.getNome() != null && m.getNome() != null){
                                                  p.setCategoria(cat);
                                                  p.setMateria(m);
                                                  int num = pr.insertProdottoWithDB(p.getId(), p);

                                                  if(num > 0){
                                                       Utility.msgInf("GEOSTORE", "Nuovo prodotto aggiunto\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Prodotto non aggiunto\n");
                                                  }
                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Categoria e/o Materia inesistenti\n");
                                             }

                                        }
                                        else if(chooseP == 2){
                                             p = pr.getProdottoWithDB(Utility.insertString("Inserisci il nome prodotto"));

                                             if(p != null && p.getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Prodotto trovato\n");

                                                  Prodotto pNew = view.maskUpdateProdotto(p, new Prodotto());

                                                  cat = car.getCategoriaWithDB(pNew.getCategoria().getNome());
                                                  m = mr.getMateriaWithDB(pNew.getMateria().getNome());

                                                  if(cat != null && m != null && cat.getNome() != null && m.getNome() != null){
                                                       pNew.setCategoria(cat);
                                                       pNew.setMateria(m);
                                                       int num = pr.updateProdottoWithDB(pNew.getId(), pNew);

                                                       if(num > 0){
                                                            Utility.msgInf("GEOSTORE", "Prodotto aggiornato\n");
                                                       }
                                                       else{
                                                            Utility.msgInf("GEOSTORE", "Prodotto non aggiornato\n");
                                                       }
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Categoria e/o Materia inesistenti\n");
                                                  }

                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Prodotto non trovato\n");
                                             }
                                        }
                                        else if(chooseP == 3){
                                             p = pr.getProdottoWithDB(Utility.insertString("Inserisci il nome prodotto"));

                                             if(p != null && p.getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Prodotto trovato\n");
                                                  if(Utility.insertString("Sei sicuro di voler eliminare questo prodotto?").equalsIgnoreCase("s")){
                                                       int num = pr.deleteProdottoWithDB(p.getId());

                                                       if(num > 0){
                                                            Utility.msgInf("GEOSTORE", "Prodotto eliminato\n");
                                                       }
                                                       else{
                                                            Utility.msgInf("GEOSTORE", "Prodotto non eliminato\n");
                                                       }

                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                                                  }
                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Prodotto non trovato\n");
                                             }
                                        }
                                        else{
                                             Utility.msgInf("GEOSTORE", "Non so cosa hai inserito\n");
                                        }
                                        break;
                                   case 6:
                                        Utility.msgInf("GEOSTORE", "Ordinazione di un prodotto\n\n");
                                        view.printProdotti(pr.getProdottiDispWithDB());
                                        o = new Ordine();
                                        view.maskInsertOrdine(o, c);
                                        p = pr.getProdottoDispWithDB(o.getProdotto().getNome());

                                        if(p != null && p.getNome() != null){
                                             Utility.msgInf("GEOSTORE", "Il prodotto è disponibile\n");
                                             o.setProdotto(p);

                                             if(o.getQuantita() <= p.getQuantita_disp()){
                                                  o.setPrezzo_unitario(p.getPrezzo());

                                                  int num = odr.insertOrdineWithDB(null, o);
                                                  if(num > 0){
                                                       Utility.msgInf("GEOSTORE", "Ordine effettuato\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Ordine non effettuato\n");
                                                  }
                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "La quantità ordinata supera quella disponibile\n");
                                             }
                                        }
                                        else{
                                             Utility.msgInf("GEOSTORE", "L'oggetto ordinato non è disponibile oppure è inesistente\n");
                                        }
                                        break;
                                   case 7:
                                        Utility.msgInf("GEOSTORE", "Elenco ordini effettuati\n\n");
                                        int choose = Utility.insertInt("1 - tuoi ordini, 2 - di un'altra persona ");
                                        if(choose == 1){
                                             Utility.msgInf("GEOSTORE", "Hai scelto i tuoi ordini\n");
                                             view.printOrdini(odr.getOrdiniByUserWithDB(c.getNome()));
                                        }
                                        else if(choose == 2){
                                             Utility.msgInf("GEOSTORE", "Hai scelto di sapere quello di qualcun'altro\n");
                                             c.setNome(Utility.insertString("Inserisci il nome di un'altra persona"));
                                             view.printOrdini(odr.getOrdiniByUserWithDB(c.getNome()));
                                        }
                                        else{
                                             Utility.msgInf("GEOSTORE", "Non so cosa hai inserito\n");
                                        }

                                        break;
                                   case 8:
                                        Utility.msgInf("GEOSTORE", "Elenco ordini generali effettuati\n\n");
                                        view.printOrdini(odr.getOrdiniWithDB());
                                        break;
                                   case 9:
                                        Utility.msgInf("GEOSTORE", "Modifica/elimina ordine\n\n");
                                        view.printOrdini(odr.getOrdiniWithDB());
                                        int choose2 = Utility.insertInt("1 - Modifica, 2 - Elimina");
                                        if(choose2 == 1){
                                             o = odr.getOrdineWithDB(Utility.insertInt("Inserisci l'id ordine"));

                                             if(o != null && o.getProdotto() != null && o.getProdotto().getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Ordine trovato\n");

                                                  Ordine oNew = view.maskUpdateOrdine(o, new Ordine());

                                                  changeStatusProdottoAfterOrder(o, oNew);

                                                  int num = odr.updateOrdineWithDB(oNew.getId(), oNew);

                                                  if(num > 0){
                                                       Utility.msgInf("GEOSTORE", "Ordine aggiornato\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Ordine non aggiornato\n");
                                                  }

                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Ordine non trovato\n");
                                             }
                                        }
                                        else if(choose2 == 2){
                                             o = odr.getOrdineWithDB(Utility.insertInt("Inserisci l'id ordine"));

                                             if(o != null && o.getProdotto() != null && o.getProdotto().getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Ordine trovato\n");
                                                  if(Utility.insertString("Sei sicuro di voler eliminare quest'ordine?").equalsIgnoreCase("s")){
                                                       odr.deleteOrdineWithDB(o.getId());
                                                       Utility.msgInf("GEOSTORE", "Ordine eliminato\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                                                  }
                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Ordine non trovato\n");
                                             }
                                        }
                                        else{
                                             Utility.msgInf("GEOSTORE", "Non so cosa hai inserito\n");
                                        }
                                        break;
                                   case 10:
                                        Utility.msgInf("GEOSTORE", "Elenco prodotti per categoria\n\n");
                                        cat = new Categoria();
                                        view.maskObjViaCat(cat);
                                        view.printProdotti(pr.getProdottiViaCategoriaWithDB(cat.getNome()));
                                        break;
                                   case 11:
                                        Utility.msgInf("GEOSTORE", "Elenco prodotti per materia\n\n");
                                        m = new Materia();
                                        view.maskObjViaMat(m);
                                        view.printProdotti(pr.getProdottiViaMateriaWithDB(m.getNome()));
                                        break;
                                   case 12:
                                        Utility.msgInf("GEOSTORE", "Elenco prodotti disponibili\n\n");
                                        view.printProdotti(pr.getProdottiDispWithDB());
                                        break;
                                   case 13:
                                        Utility.msgInf("GEOSTORE", "Crea/Modifica/Elimina categoria\n\n");
                                        int choose3 = Utility.insertInt("1 - Crea, 2 - Modifica, 3 - Elimina");

                                        if(choose3 == 1){
                                             cat = new Categoria();
                                             view.maskInsertCategoria(cat);
                                             car.insertCategoriaWithDB(cat.getId(), cat);
                                             Utility.msgInf("GEOSTORE", "Nuova categoria aggiunta\n");
                                        }
                                        else if(choose3 == 2){
                                             cat = car.getCategoriaWithDB(Utility.insertString("Inserisci il nome categoria"));

                                             if(cat != null && cat.getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Categoria trovata\n");

                                                  Categoria cNew = view.maskUpdateCategoria(cat, new Categoria());

                                                  int num = car.updateCategoriaWithDB(cNew.getId(), cNew);

                                                  if(num > 0){
                                                       Utility.msgInf("GEOSTORE", "Categoria aggiornata\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Categoria non aggiornata\n");
                                                  }

                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Categoria non trovata\n");
                                             }
                                        }
                                        else if(choose3 == 3){
                                             cat = car.getCategoriaWithDB(Utility.insertString("Inserisci il nome categoria"));

                                             if(cat != null && cat.getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Categoria trovata\n");
                                                  if(Utility.insertString("Sei sicuro di voler eliminare questa categoria?").equalsIgnoreCase("s")){
                                                       car.deleteCategoriaWithDB(cat.getId());
                                                       Utility.msgInf("GEOSTORE", "Categoria eliminata\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                                                  }
                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Categoria non trovata\n");
                                             }
                                        }
                                        else{
                                             Utility.msgInf("GEOSTORE", "Non so cosa hai inserito\n");
                                        }

                                        break;
                                   case 14:
                                        Utility.msgInf("GEOSTORE", "Crea/Modifica/Elimina materia\n\n");
                                        int choose4 = Utility.insertInt("1 - Crea, 2 - Modifica, 3 - Elimina");

                                        if(choose4 == 1){
                                             m = new Materia();
                                             view.maskInsertMateria(m);
                                             mr.insertMateriaWithDB(m.getId(), m);
                                             Utility.msgInf("GEOSTORE", "Nuova materia aggiunta\n");
                                        }
                                        else if(choose4 == 2){
                                             m = mr.getMateriaWithDB(Utility.insertString("Inserisci il nome materia"));

                                             if(m != null && m.getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Materia trovata\n");

                                                  Materia mNew = view.maskUpdateMateria(m, new Materia());

                                                  int num = mr.updateMateriaWithDB(mNew.getId(), mNew);

                                                  if(num > 0){
                                                       Utility.msgInf("GEOSTORE", "Materia aggiornata\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Materia non aggiornata\n");
                                                  }

                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Materia non trovata\n");
                                             }
                                        }
                                        else if(choose4 == 3){
                                             m = mr.getMateriaWithDB(Utility.insertString("Inserisci il nome materia"));

                                             if(m != null && m.getNome() != null){
                                                  Utility.msgInf("GEOSTORE", "Materia trovata\n");
                                                  if(Utility.insertString("Sei sicuro di voler eliminare questa materia?").equalsIgnoreCase("s")){
                                                       mr.deleteMateriaWithDB(m.getId());
                                                       Utility.msgInf("GEOSTORE", "Materia eliminata\n");
                                                  }
                                                  else{
                                                       Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                                                  }
                                             }
                                             else{
                                                  Utility.msgInf("GEOSTORE", "Materia non trovata\n");
                                             }
                                        }
                                        else{
                                             Utility.msgInf("GEOSTORE", "Non so cosa hai inserito\n");
                                        }
                                        break;
                                   case 15:
                                        Utility.msgInf("GEOSTORE", "Ordini totali giornalieri\n\n");
                                        String chooseDate = Utility.insertString("Inserisci la data in formato yyyy-mm-dd");
                                        view.printOrdiniTotGior(odr.getOrdineTotGiorWithDB(c, chooseDate));
                                        break;
                                   default:
                                        Utility.msgInf("GEOSTORE", "Non so cosa hai inserito");
                                        break;
                              }

                              if(Utility.insertString("\nVUOI ANDARE AVANTI CON IL MENU? (s/n)").equalsIgnoreCase("s")){
                                   flagMenu = true;
                                   System.out.println("\n***VA BENE***");
                              }
                              else{
                                   flagMenu = false;
                                   System.out.println("\n***OK COME VUOI***");
                              }
                         }while(flagMenu);
                    }
                    else{
                         Utility.msgInf("GEOSTORE", "Accesso negato - email e/o codice admin errati\n");
                    }

               }
               else{
                    Utility.msgInf("GEOSTORE", "Non so cosa hai inserito\n\n");
               }

               if(Utility.insertString("\nVUOI ANDARE AVANTI?").equalsIgnoreCase("s")){
                    flagLogin = true;
                    System.out.println("\n***VA BENE***");
               }
               else{
                    flagLogin = false;
                    System.out.println("\n***OK COME VUOI***");
               }

               //TODO: da continuare...

          }while(flagLogin);

          Utility.msgInf("GEOSTORE","***GRAZIE PER AVER PROVATO IL MENU***");

     }

     private static void changeStatusProdottoAfterOrder(Ordine oOld, Ordine oNew){
          if(oOld.getStato().getId() == 1 && oNew.getStato().getCode().equals("ACCETTATO")){
               Prodotto p;
               ProdottoRepository pr = new ProdottoRepository();
               p = pr.getProdottoWithDB(oOld.getProdotto().getNome());

               Integer subQuantita = p.getQuantita_disp() - oNew.getQuantita();
               p.setQuantita_disp(subQuantita);
               Disponibilita newDisp = new Disponibilita();

               if(p.getQuantita_disp() == 0){
                    newDisp.setId(4);
               }
               else if(p.getQuantita_disp() >= 1 && p.getQuantita_disp() <=3){
                    newDisp.setId(3);
               }
               else{
                    newDisp.setId(1);
               }
               p.setDisponibilita(newDisp);

               int num = pr.updateProdottoAfterAccOrdineWithDB(p.getId(), p);

               if(num > 0){
                    Utility.msgInf("GEOSTORE", "Quantità e/o disponibilità aggiornati\n");
               }
               else{
                    Utility.msgInf("GEOSTORE", "Quantità e/o disponibilità non aggiornati\n");
               }
          }
     }
}
