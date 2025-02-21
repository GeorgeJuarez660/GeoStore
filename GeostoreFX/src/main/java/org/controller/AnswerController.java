package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;

import java.sql.Date;
import java.util.ResourceBundle;

public class AnswerController {
    @FXML
    private Label response;

    //------------------INITIALIZE-----------------------

    public void response(String answer, ResourceBundle resLang) {
        System.out.println(answer);

        String responseText = "";

        switch(answer) {
            case "REG-Y":
                response.setText(resLang.getString("answer.register.positive"));
                break;
            case "LOG-Y":
                response.setText(resLang.getString("answer.login.positive"));
                break;
            case "LOG-N":
                response.setText(resLang.getString("answer.login.negative"));
                break;
            case "LOG-O":
                response.setText(resLang.getString("logout.title"));
                break;
            case "REG-N":
                response.setText(resLang.getString("answer.register.negative"));
                break;
            case "BD-ERR":
                response.setText(resLang.getString("answer.register.errorBornDate"));
                break;
            case "PWD-NOMATCH":
                response.setText(resLang.getString("answer.register.differentPasswords"));
                break;
            case "AGE-ERR":
                response.setText(resLang.getString("answer.register.wrongAge"));
                break;
            case "USR-CY":
                response.setText(resLang.getString("answer.user.createdUser"));
                break;
            case "USR-CN":
                response.setText(resLang.getString("answer.user.noCreatedUser"));
                break;
            case "USR-CWN":
                responseText = resLang.getString("answer.user.wrongAge");
                responseText += " " + resLang.getString("answer.user.noCreatedUser");
                response.setText(responseText);
                break;
            case "USR-CRN":
                responseText = resLang.getString("answer.user.alreadyCreated");
                responseText += " " + resLang.getString("answer.user.noCreatedUser");
                response.setText(responseText);
                break;
            case "USR-CAN":
                responseText = resLang.getString("answer.user.noAdminCode");
                responseText += " " + resLang.getString("answer.user.noCreatedUser");
                response.setText(responseText);
                break;
            case "USR-CFN":
                responseText = resLang.getString("answer.user.requiredFill");
                responseText += " " + resLang.getString("answer.user.noCreatedUser");
                response.setText(responseText);
                break;
            case "USR-MY":
                response.setText(resLang.getString("answer.user.updatedUser"));
                break;
            case "USR-MN":
                response.setText(resLang.getString("answer.user.noUpdatedUser"));
                break;
            case "USR-MWN":
                responseText = resLang.getString("answer.user.wrongAge");
                responseText += " " + resLang.getString("answer.user.noUpdatedUser");
                response.setText(responseText);
                break;
            case "USR-MRN":
                responseText = resLang.getString("answer.user.alreadyCreated");
                responseText += " " + resLang.getString("answer.user.noUpdatedUser");
                response.setText(responseText);
                break;
            case "USR-MAN":
                responseText = resLang.getString("answer.user.noAdminCode");
                responseText += " " + resLang.getString("answer.user.noUpdatedUser");
                response.setText(responseText);
                break;
            case "USR-MFN":
                responseText = resLang.getString("answer.user.requiredFill");
                responseText += " " + resLang.getString("answer.user.noUpdatedUser");
                response.setText(responseText);
                break;
            case "USR-DY":
                response.setText(resLang.getString("answer.user.deletedUser"));
                break;
            case "USR-DN":
                response.setText(resLang.getString("answer.user.noDeletedUser"));
                break;
            case "USR-DLN":
                responseText = resLang.getString("answer.user.noDeleteLoggedUser");
                responseText += " " + resLang.getString("answer.user");
                response.setText(responseText);
                break;
            case "COD-CY":
                response.setText(resLang.getString("answer.code.createdCode"));
                break;
            case "COD-CN":
                response.setText(resLang.getString("answer.code.noCreatedCode"));
                break;
            case "COD-CRN":
                responseText = resLang.getString("answer.code.alreadyCreated");
                responseText += " " + resLang.getString("answer.code.noCreatedCode");
                response.setText(responseText);
                break;
            case "COD-CFN":
                responseText = resLang.getString("answer.code.requiredFill");
                responseText += " " + resLang.getString("answer.code.noCreatedCode");
                response.setText(responseText);
                break;
            case "COD-MY":
                response.setText(resLang.getString("answer.code.updatedCode"));
                break;
            case "COD-MN":
                response.setText(resLang.getString("answer.code.noUpdatedCode"));
                break;
            case "COD-MRN":
                responseText = resLang.getString("answer.code.alreadyCreated");
                responseText += " " + resLang.getString("answer.code.noUpdatedCode");
                response.setText(responseText);
                break;
            case "COD-MFN":
                responseText = resLang.getString("answer.code.requiredFill");
                responseText += " " + resLang.getString("answer.code.noUpdatedCode");
                response.setText(responseText);
                break;
            case "COD-DY":
                response.setText(resLang.getString("answer.code.deletedCode"));
                break;
            case "COD-DN":
                response.setText(resLang.getString("answer.code.noDeletedCode"));
                break;
            case "COD-ACY":
                response.setText(resLang.getString("answer.code.associatedCode"));
                break;
            case "COD-ACN":
                response.setText(resLang.getString("answer.code.noAssociatedCode"));
                break;
            case "COD-ACRN":
                responseText = resLang.getString("answer.code.alreadyAssociated");
                responseText += " " + resLang.getString("answer.code.noAssociatedCode");
                response.setText(responseText);
                break;
            case "COD-ACFN":
                responseText = resLang.getString("answer.code.associatingRequiredFill");
                responseText += " " + resLang.getString("answer.code.noAssociatedCode");
                response.setText(responseText);
            case "COD-ADY":
                response.setText(resLang.getString("answer.code.dissociatedCode"));
                break;
            case "COD-ADN":
                response.setText(resLang.getString("answer.code.noDissociatedCode"));
                break;
            case "NWS-CY":
                response.setText(resLang.getString("answer.news.createdNews"));
                break;
            case "NWS-CN":
                response.setText(resLang.getString("answer.news.noCreatedNews"));
                break;
            case "NWS-CFN":
                responseText = resLang.getString("answer.news.requiredFill");
                responseText += " " + resLang.getString("answer.news.noCreatedNews");
                response.setText(responseText);
                break;
            case "NWS-MY":
                response.setText(resLang.getString("answer.news.updatedNews"));
                break;
            case "NWS-MN":
                response.setText(resLang.getString("answer.news.noUpdatedNews"));
                break;
            case "NWS-MFN":
                responseText = resLang.getString("answer.news.requiredFill");
                responseText += " " + resLang.getString("answer.news.noUpdatedNews");
                response.setText(responseText);
                break;
            case "NWS-DY":
                response.setText(resLang.getString("answer.news.deletedNews"));
                break;
            case "NWS-DN":
                response.setText(resLang.getString("answer.news.noDeletedNews"));
                break;
            default:
                break;
        }
    }

}