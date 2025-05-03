package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;
import org.utility.Sounds;

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
                Sounds.soundLogout(); //genera il suono quando si procede al logout
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
            case "USR-UY":
                response.setText(resLang.getString("answer.user.updatedUser"));
                break;
            case "USR-UN":
                response.setText(resLang.getString("answer.user.noUpdatedUser"));
                break;
            case "USR-UWN":
                responseText = resLang.getString("answer.user.wrongAge");
                responseText += " " + resLang.getString("answer.user.noUpdatedUser");
                response.setText(responseText);
                break;
            case "USR-URN":
                responseText = resLang.getString("answer.user.alreadyCreated");
                responseText += " " + resLang.getString("answer.user.noUpdatedUser");
                response.setText(responseText);
                break;
            case "USR-UAN":
                responseText = resLang.getString("answer.user.noAdminCode");
                responseText += " " + resLang.getString("answer.user.noUpdatedUser");
                response.setText(responseText);
                break;
            case "USR-UFN":
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
            case "COD-UY":
                response.setText(resLang.getString("answer.code.updatedCode"));
                break;
            case "COD-UN":
                response.setText(resLang.getString("answer.code.noUpdatedCode"));
                break;
            case "COD-URN":
                responseText = resLang.getString("answer.code.alreadyCreated");
                responseText += " " + resLang.getString("answer.code.noUpdatedCode");
                response.setText(responseText);
                break;
            case "COD-UFN":
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
            case "NWS-UY":
                response.setText(resLang.getString("answer.news.updatedNews"));
                break;
            case "NWS-UN":
                response.setText(resLang.getString("answer.news.noUpdatedNews"));
                break;
            case "NWS-UFN":
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
            case "PRD-CY":
                response.setText(resLang.getString("answer.product.createdProduct"));
                break;
            case "PRD-CN":
                response.setText(resLang.getString("answer.product.noCreatedProduct"));
                break;
            case "PRD-CFN":
                responseText = resLang.getString("answer.product.requiredFill");
                responseText += " " + resLang.getString("answer.product.noCreatedProduct");
                response.setText(responseText);
                break;
            case "PRD-UY":
                response.setText(resLang.getString("answer.product.updatedProduct"));
                break;
            case "PRD-UN":
                response.setText(resLang.getString("answer.product.noUpdatedProduct"));
                break;
            case "PRD-UFN":
                responseText = resLang.getString("answer.product.requiredFill");
                responseText += " " + resLang.getString("answer.product.noUpdatedProduct");
                response.setText(responseText);
                break;
            case "PRD-DY":
                response.setText(resLang.getString("answer.product.deletedProduct"));
                break;
            case "PRD-DN":
                response.setText(resLang.getString("answer.product.noDeletedProduct"));
                break;
            case "PRD-IR":
                response.setText(resLang.getString("answer.product.infoRefundDelete"));
                break;
            case "ODR-CY":
                responseText = resLang.getString("answer.order.payed");
                responseText += " " + resLang.getString("answer.order.orderedProduct");
                response.setText(responseText);
                break;
            case "ODR-CN":
                responseText = resLang.getString("answer.order.noPayed");
                responseText += " " + resLang.getString("answer.order.noOrderedProduct");
                response.setText(responseText);
                break;
            case "ODR-CMN":
                responseText = resLang.getString("answer.order.insufficientMoney");
                responseText += " " + resLang.getString("answer.order.noOrderedProduct");
                response.setText(responseText);
                break;
            case "ODR-CQN":
                responseText = resLang.getString("answer.order.limitQuantity");
                responseText += " " + resLang.getString("answer.order.noOrderedProduct");
                response.setText(responseText);
                break;
            case "ODR-CVN":
                responseText = resLang.getString("answer.order.noAvailableProduct");
                responseText += " " + resLang.getString("answer.order.noOrderedProduct");
                response.setText(responseText);
                break;
            case "ODR-CFN":
                responseText = resLang.getString("answer.order.requiredFill");
                responseText += " " + resLang.getString("answer.order.noOrderedProduct");
                response.setText(responseText);
            case "ODR-IPR":
                response.setText(resLang.getString("answer.order.infoPayment"));
                break;
            case "ODR-SAR":
                response.setText(resLang.getString("answer.order.infoReceipt"));
                break;
            case "ODR-UY":
                responseText = resLang.getString("answer.order.payed");
                responseText += " " + resLang.getString("answer.order.updatedOrder");
                response.setText(responseText);
                break;
            case "ODR-UN":
                responseText = resLang.getString("answer.order.noPayed");
                responseText += " " + resLang.getString("answer.order.noUpdatedOrder");
                response.setText(responseText);
                break;
            case "ODR-URY":
                responseText = resLang.getString("answer.order.refund");
                responseText += " " + resLang.getString("answer.order.updatedOrder");
                response.setText(responseText);
                break;
            case "ODR-URN":
                responseText = resLang.getString("answer.order.noRefund");
                responseText += " " + resLang.getString("answer.order.noUpdatedOrder");
                response.setText(responseText);
                break;
            case "ODR-UMN":
                responseText = resLang.getString("answer.order.insufficientMoney");
                responseText += " " + resLang.getString("answer.order.noUpdatedOrder");
                response.setText(responseText);
                break;
            case "ODR-UQN":
                responseText = resLang.getString("answer.order.limitQuantity");
                responseText += " " + resLang.getString("answer.order.noUpdatedOrder");
                response.setText(responseText);
                break;
            case "ODR-UCY":
                responseText = resLang.getString("answer.order.noChanges");
                responseText += " " + resLang.getString("answer.order.updatedOrder");
                response.setText(responseText);
                break;
            case "ODR-UFN":
                responseText = resLang.getString("answer.order.requiredFill");
                responseText += " " + resLang.getString("answer.order.noUpdatedOrder");
                response.setText(responseText);
            case "ODR-DY":
                response.setText(resLang.getString("answer.order.deletedOrder"));
                break;
            case "ODR-DN":
                response.setText(resLang.getString("answer.order.noDeletedOrder"));
                break;
            case "ODR-DSN":
                responseText = resLang.getString("answer.order.noStatusElab");
                responseText += " " + resLang.getString("answer.order.noDeletedOrder");
                response.setText(responseText);
                break;
            case "ODR-IRR":
                response.setText(resLang.getString("answer.order.infoRefund"));
                break;
            case "CAT-CY":
                response.setText(resLang.getString("answer.category.createdCategory"));
                break;
            case "CAT-CN":
                response.setText(resLang.getString("answer.category.noCreatedCategory"));
                break;
            case "CAT-CRN":
                responseText = resLang.getString("answer.category.alreadyCreated");
                responseText += " " + resLang.getString("answer.category.noCreatedCategory");
                response.setText(responseText);
                break;
            case "CAT-CFN":
                responseText = resLang.getString("answer.category.requiredFill");
                responseText += " " + resLang.getString("answer.category.noCreatedCategory");
                response.setText(responseText);
                break;
            case "CAT-UY":
                response.setText(resLang.getString("answer.category.updatedCategory"));
                break;
            case "CAT-UN":
                response.setText(resLang.getString("answer.category.noUpdatedCategory"));
                break;
            case "CAT-URN":
                responseText = resLang.getString("answer.category.alreadyCreated");
                responseText += " " + resLang.getString("answer.category.noUpdatedCategory");
                response.setText(responseText);
                break;
            case "CAT-UFN":
                responseText = resLang.getString("answer.category.requiredFill");
                responseText += " " + resLang.getString("answer.category.noUpdatedCategory");
                response.setText(responseText);
                break;
            case "CAT-DY":
                response.setText(resLang.getString("answer.category.deletedCategory"));
                break;
            case "CAT-DN":
                response.setText(resLang.getString("answer.category.noDeletedCategory"));
                break;
            case "CAT-IR":
                response.setText(resLang.getString("answer.category.infoChanges"));
                break;
            case "MAT-CY":
                response.setText(resLang.getString("answer.material.createdMaterial"));
                break;
            case "MAT-CN":
                response.setText(resLang.getString("answer.material.noCreatedMaterial"));
                break;
            case "MAT-CRN":
                responseText = resLang.getString("answer.material.alreadyCreated");
                responseText += " " + resLang.getString("answer.material.noCreatedMaterial");
                response.setText(responseText);
                break;
            case "MAT-CFN":
                responseText = resLang.getString("answer.material.requiredFill");
                responseText += " " + resLang.getString("answer.material.noCreatedMaterial");
                response.setText(responseText);
                break;
            case "MAT-UY":
                response.setText(resLang.getString("answer.material.updatedMaterial"));
                break;
            case "MAT-UN":
                response.setText(resLang.getString("answer.material.noUpdatedMaterial"));
                break;
            case "MAT-URN":
                responseText = resLang.getString("answer.material.alreadyCreated");
                responseText += " " + resLang.getString("answer.material.noUpdatedMaterial");
                response.setText(responseText);
                break;
            case "MAT-UFN":
                responseText = resLang.getString("answer.material.requiredFill");
                responseText += " " + resLang.getString("answer.material.noUpdatedMaterial");
                response.setText(responseText);
                break;
            case "MAT-DY":
                response.setText(resLang.getString("answer.material.deletedMaterial"));
                break;
            case "MAT-DN":
                response.setText(resLang.getString("answer.material.noDeletedMaterial"));
                break;
            case "MAT-IR":
                response.setText(resLang.getString("answer.material.infoChanges"));
                break;
            default:
                break;
        }
    }

}