package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.controller.items.*;
import org.models.Cliente;
import org.services.LoadPage;
import org.utility.Sounds;

import java.text.ParseException;
import java.util.ResourceBundle;

public class QuestionController {
    @FXML
    private Label request;

    private String chooseByCode, idForDelete;
    private Cliente user;
    private Object maskController;
    private boolean boolForReceipt;

    //------------------INITIALIZE-----------------------

    public void request(String question, ResourceBundle resLang, Cliente user, Object maskController, String idForDelete, boolean boolForReceipt) {
        System.out.println(question);

        switch(question) {
            case "Q-CR":
                request.setText(resLang.getString("question.creating"));
                chooseByCode = question;
                break;
            case "Q-CS":
                request.setText(resLang.getString("question.associating"));
                chooseByCode = question;
                break;
            case "Q-CO":
                request.setText(resLang.getString("question.ordering"));
                chooseByCode = question;
                break;
            case "Q-UP":
                request.setText(resLang.getString("question.updating"));
                chooseByCode = question;
                break;
            case "Q-US":
                request.setText(resLang.getString("question.updatingAssociation"));
                chooseByCode = question;
                break;
            case "Q-UO":
                request.setText(resLang.getString("question.updatingOrder"));
                chooseByCode = question;
                break;
            case "Q-DU":
            case "Q-DA":
            case "Q-DN":
            case "Q-DP":
            case "Q-DC":
            case "Q-DM":
                request.setText(resLang.getString("question.deleting"));
                chooseByCode = question;
                break;
            case "Q-DS":
                request.setText(resLang.getString("question.dissociating"));
                chooseByCode = question;
                break;
            case "Q-DO":
                request.setText(resLang.getString("question.deletingOrder"));
                chooseByCode = question;
                break;
            case "Q-RG":
                request.setText(resLang.getString("question.register"));
                chooseByCode = question;
                break;
            case "Q-LG":
                request.setText(resLang.getString("question.logout"));
                chooseByCode = question;
                break;
            default:
                break;

        }

        this.user = user;
        this.maskController = maskController;
        this.idForDelete = idForDelete;
        this.boolForReceipt = boolForReceipt;
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void accept(ActionEvent event) throws ParseException {
        System.out.println("YES");
        Sounds.soundGo();

        LoadPage.saveStage(event);

        switch(chooseByCode) {
            case "Q-CR":
            case "Q-CO":
                CreateController creating = new CreateController();

                creating.startCreating(event, user, maskController, boolForReceipt);
                break;
            case "Q-CS":
                CreateAssociateUserController createAssociating = new CreateAssociateUserController();

                createAssociating.startAssociating(event, user, maskController);
                break;
            case "Q-UP":
            case "Q-UO":
                UpdateController updating = new UpdateController();

                updating.startUpdating(event, user, maskController, boolForReceipt);
                break;
            case "Q-US":
                UpdateAssociateUserController updatingAssociation = new UpdateAssociateUserController();

                updatingAssociation.startUpdatingAssociation(event, user, maskController);
                break;
            case "Q-DU":
                UserItemController deletingUser = new UserItemController();

                deletingUser.startDeleting(idForDelete, user);
                break;
            case "Q-DA":
                CodeItemController deletingCode = new CodeItemController();

                deletingCode.startDeleting(idForDelete, user);
                break;
            case "Q-DS":
                CodeAssociateItemController dissociatingCode = new CodeAssociateItemController();

                dissociatingCode.startDissociating(idForDelete, user);
                break;
            case "Q-DN":
                NewsItemController deletingNews = new NewsItemController();

                deletingNews.startDeleting(idForDelete, user);
                break;
            case "Q-DP":
                ProductItemController deletingProduct = new ProductItemController();

                deletingProduct.startDeleting(idForDelete, user);
                break;
            case "Q-DO":
                OrderItemController deletingOrder = new OrderItemController();

                deletingOrder.startDeleting(idForDelete, user);
                break;
            case "Q-DC":
                ProductTypeButtonController deletingCategory = new ProductTypeButtonController();

                deletingCategory.startDeletingCategory(idForDelete, user);
                break;
            case "Q-DM":
                ProductTypeButtonController deletingMaterial = new ProductTypeButtonController();

                deletingMaterial.startDeletingMaterial(idForDelete, user);
                break;
            case "Q-RG":
                AccessController accessToSignUp = new AccessController();

                accessToSignUp.startSigningUp(user);
                break;
            case "Q-LG":
                MenuController menuToLogout = new MenuController();

                menuToLogout.startLoggingOut();
                break;
            default:
                break;
        }
    }

    @FXML
    private void deny(ActionEvent event){
        System.out.println("NO");
        Sounds.soundBack();

        LoadPage.saveStage(event);

        if(chooseByCode.equals("Q-RG")){
            LoadPage.access("register", null);
        }
        else{
            LoadPage.goesToMenu(user, null, false);
        }
    }

}