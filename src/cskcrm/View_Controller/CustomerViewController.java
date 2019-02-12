/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.View_Controller;

import cskcrm.CSKCRM;
import cskcrm.DAO.CustomerDAO;
import cskcrm.Model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author ckeller22
 */
public class CustomerViewController implements Initializable {

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> numberColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> cityColumn;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    @FXML
    private TableColumn<Customer, String> postalCodeColumn;

    @FXML
    private TableColumn<Customer, Integer> activeColumn;

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnUpdateCustomer;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnReport;

    private CSKCRM cskcrm;
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    public static Customer tCustomer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerDAO dao = new CustomerDAO();
        customers = dao.getCustomers();
        customerTable.setItems(customers);
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        numberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress1()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        postalCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostalCode()));
        activeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getActive()).asObject());
        activeColumn.setCellFactory(cellData -> new TableCell<Customer, Integer>() {
            @Override
            public void updateItem(Integer activeFlag, boolean empty) {
                super.updateItem(activeFlag, empty);
                if (empty) {
                    setText(null);
                } else {
                    if (activeFlag == 1) {
                        setText("Yes");
                    } else {
                        setText("No");
                    }
                }
            }
        });

    }

    public void handleAppointmentsButton() {
        cskcrm.showAppointmentScreen();
    }

    public void handleAddCustomerButton() {
        cskcrm.showAddCustomerScreen();
    }

    public void handleUpdateCustomerButton() {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            cskcrm.throwAlert("Please ensure a customer is selected to be updated.");
        } else {
            tCustomer = customerTable.getSelectionModel().getSelectedItem();
            cskcrm.showModifyCustomerScreen();
        }
    }

    public void handleLogoutButton() {
        cskcrm.showLoginScreen();
    }

    public void handleReportsButton() {
        cskcrm.showReportsScreen();
    }

    public void setMainApp(CSKCRM cskcrm) {
        this.cskcrm = cskcrm;
    }

}
