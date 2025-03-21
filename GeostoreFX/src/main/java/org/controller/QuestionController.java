package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.models.Cliente;
import org.models.Utente;
import org.services.LoadPage;

import java.text.ParseException;
import java.util.ResourceBundle;

public class QuestionController {
    @FXML
    private Label request;

    private String chooseByCode;
    private Cliente user;
    private Object maskController;

    //------------------INITIALIZE-----------------------

    public void request(String question, ResourceBundle resLang, Cliente user, Object maskController) {
        System.out.println(question);

        String requestText = "";

        switch(question) {
            case "Q-CR":
                request.setText(resLang.getString("question.creating"));
                chooseByCode = question;
                break;
            case "Q-UP":
                request.setText(resLang.getString("question.updating"));
                chooseByCode = question;
                break;
            case "Q-DL":
                request.setText(resLang.getString("question.deleting"));
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
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void accept(ActionEvent event) throws ParseException {
        System.out.println("YES");
        LoadPage.saveStage(event);

        if (chooseByCode.equals("Q-CR")) {
            CreateController creating = new CreateController();

            creating.startCreating(event, user, maskController);
        }
    }

    @FXML
    private void deny(ActionEvent event) throws ParseException {
        System.out.println("YES");
        LoadPage.saveStage(event);

        LoadPage.goesToMenu(user, null);
    }

}