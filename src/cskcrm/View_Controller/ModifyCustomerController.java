/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.View_Controller;

import cskcrm.CSKCRM;
import static cskcrm.DAO.CustomerDAO.deleteCustomer;
import static cskcrm.DAO.CustomerDAO.updateCustomer;
import static cskcrm.Model.Country.populateCountries;
import cskcrm.Model.Customer;
import cskcrm.Model.User;
import static cskcrm.View_Controller.CustomerViewController.tCustomer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ckeller22
 */
public class ModifyCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField address1Field;

    @FXML
    private TextField address2Field;

    @FXML
    private ComboBox<String> comboBoxCountry;

    @FXML
    private CheckBox active;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField cityField;

    private CSKCRM cskcrm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxCountry.setItems(populateCountries());
        populateCustomer(tCustomer);
    }

    public void handleCancelButton() {
        if (cskcrm.throwConfirmation("cancel modifying this customer entry")) {
            cskcrm.showCustomerScreen();
        }
    }

    public void handleDeleteButton() {
        if (cskcrm.throwConfirmation("delete this customer entry")) {
            User sessionUser = cskcrm.getSessionUser();
            boolean success = deleteCustomer(tCustomer.getCustomerId(), sessionUser);
            if (success == true) {
                cskcrm.showCustomerScreen();
            } else {
                cskcrm.throwAlert("Error updating the database, please try again!");

            }
        }
    }

    public void handleSaveButton() {
        if (isInputValid()) {
            User sessionUser = cskcrm.getSessionUser();
            String customerName = nameField.getText();
            String address1 = address1Field.getText();
            String address2 = address2Field.getText();
            String number = phoneField.getText();
            String city = cityField.getText();
            String country = comboBoxCountry.getValue();
            String postalCode = postalCodeField.getText();
            int isActive;
            if (active.isSelected()) {
                isActive = 1;
            } else {
                isActive = 0;
            }
            boolean success = updateCustomer(tCustomer.getCustomerId(), customerName, address1, address2, city, country, postalCode, number, isActive, sessionUser);
            if (success == true) {
                cskcrm.showCustomerScreen();
            } else {
                cskcrm.throwAlert("Error updating the database, please try again!");

            }
        }
    }

    public void setMainApp(CSKCRM cskcrm) {
        this.cskcrm = cskcrm;
    }

    public void populateCustomer(Customer tCustomer) {
        nameField.setText(tCustomer.getCustomerName());
        phoneField.setText(tCustomer.getNumber());
        address1Field.setText(tCustomer.getAddress1());
        address2Field.setText(tCustomer.getAddress2());
        cityField.setText(tCustomer.getCity());
        comboBoxCountry.setValue(tCustomer.getCountry());
        postalCodeField.setText(tCustomer.getPostalCode());
        if (tCustomer.getActive() == 1) {
            active.setSelected(true);
        } else {
            active.setSelected(false);
        }
    }

    public boolean isInputValid() {
        String errorMessage = "";
        String phoneFormat = "\\d{3}-\\d{3}-\\d{4}";
        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "You must enter a customer name. \n";
        }
        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "You must enter a phone number. \n";
        } else {
            if (!phoneField.getText().matches(phoneFormat)) {
                errorMessage += "You must enter a valid phone number in ###-###-#### format. \n";
            }
        }
        if (address1Field.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "You must enter an address. \n";
        }
        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "You must enter a city. \n";
        }
        if (comboBoxCountry.getValue() == null) {
            errorMessage += "You must select a country. \n";
        }
        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "You must enter a postal code. \n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            cskcrm.throwAlert(errorMessage);
            return false;
        }

    }
}
