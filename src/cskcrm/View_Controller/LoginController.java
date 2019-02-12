/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.View_Controller;

import cskcrm.CSKCRM;
import cskcrm.DAO.UserDAO;
import cskcrm.Model.User;
import cskcrm.util.LoginLogger;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ckeller22
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnLogin;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private CSKCRM cskcrm;
    private ResourceBundle rb;
    private UserDAO dao;

    @FXML
    void handleLogin(ActionEvent event) {
        if (isInputValid()) {
            String userName = usernameField.getText();
            String password = passwordField.getText();
            User validUser = dao.validateUser(userName, password);
            if (validUser == null) {
                String errorMessage = rb.getString("invalidCredentials");
                LoginLogger.logInvalidSignIn(userName);
                cskcrm.throwAlert(errorMessage);
                return;
            }
            cskcrm.setSessionUser(validUser);
            LoginLogger.logValidSignIn(userName);
            cskcrm.showAppointmentScreen();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resb) {
        //Requirement A. Comment out line below, and uncomment second line to view localization of the login screen.
        Locale currentLocale = Locale.getDefault();
        //Locale currentLocale = Locale.GERMANY;
        rb = rb.getBundle("cskcrm.util/lang", currentLocale);
        usernameField.setPromptText(rb.getString("username"));
        passwordField.setPromptText(rb.getString("password"));
        btnLogin.setText(rb.getString("loginButton"));
        dao = new UserDAO();

    }

    public void setMainApp(CSKCRM cskcrm) {
        this.cskcrm = cskcrm;
    }

    public boolean isInputValid() {
        String errorMessage = "";
        if (usernameField.getText().length() == 0) {
            errorMessage += rb.getString("missingUsername");
        }
        if (passwordField.getText().length() == 0) {
            errorMessage += rb.getString("missingPassword");
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            cskcrm.throwAlert(errorMessage);
            return false;
        }

    }

}
