/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.View_Controller;

import cskcrm.CSKCRM;
import cskcrm.DAO.AppointmentDAO;
import cskcrm.DAO.CustomerDAO;
import cskcrm.DAO.UserDAO;
import cskcrm.Model.Appointment;
import cskcrm.Model.AppointmentReport;
import cskcrm.Model.Customer;
import cskcrm.Model.User;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author ckeller22
 */
public class ReportsController implements Initializable {

    @FXML
    private TableView<AppointmentReport> appointmentTypeTable;

    @FXML
    private TableColumn<AppointmentReport, String> appointmentMonthColumn;

    @FXML
    private TableColumn<AppointmentReport, String> appointmentTypeColumn;

    @FXML
    private TableColumn<AppointmentReport, String> appointmentCountColumn;

    @FXML
    private TableView<Appointment> customerAppointmentsTable;

    @FXML
    private TableColumn<Appointment, String> customerStartTimeColumn;

    @FXML
    private TableColumn<Appointment, String> customerEndTimeColumn;

    @FXML
    private TableColumn<Appointment, String> customerNameColumn;

    @FXML
    private TableColumn<Appointment, String> customerTypeColumn;

    @FXML
    private TableColumn<Appointment, String> customerDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> customerLocationColumn;

    @FXML
    private TableColumn<Appointment, String> customerConsultantColumn;

    @FXML
    private TableView<Appointment> consultantAppointmentsTable;

    @FXML
    private TableColumn<Appointment, String> consultantStartTimeColumn;

    @FXML
    private TableColumn<Appointment, String> consultantEndTimeColumn;

    @FXML
    private TableColumn<Appointment, String> consultantCustomerNameColumn;

    @FXML
    private TableColumn<Appointment, String> consultantTypeColumn;

    @FXML
    private TableColumn<Appointment, String> consultantDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> consultantLocationColumn;

    @FXML
    private ComboBox consultantComboBox;

    @FXML
    private Button consultantRefresh;

    @FXML
    private Button customerRefresh;

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnLogout;

    @FXML
    private ComboBox customerComboBox;

    private CSKCRM cskcrm;
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
    private ObservableList<Appointment> consultantAppointments = FXCollections.observableArrayList();
    private ObservableList<AppointmentReport> appointmentTypes = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateCustomerComboBox();
        populateConsultantComboBox();
        populateAppointmentType();

    }

    public void handleCustomersButton() {
        cskcrm.showCustomerScreen();
    }

    public void handleAppointmentsButton() {
        cskcrm.showAppointmentScreen();
    }

    public void handleLogoutButton() {
        cskcrm.showLoginScreen();
    }

    public void populateCustomerComboBox() {
        CustomerDAO custDao = new CustomerDAO();
        customers = custDao.getCustomers();
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customer customer : customers) {
            customerNames.add(customer.getCustomerName());
        }
        customerComboBox.setItems(customerNames);
    }

    public void populateConsultantComboBox() {
        UserDAO dao = new UserDAO();
        users = dao.getActiveUsers();
        ObservableList<String> userNames = FXCollections.observableArrayList();
        for (User user : users) {
            userNames.add(user.getUserName());
        }
        consultantComboBox.setItems(userNames);
    }

    public int generateCustomerId() {
        String customerName = customerComboBox.getValue().toString();
        int customerId = 0;
        for (Customer customer : customers) {
            if (customer.getCustomerName() == customerName) {
                customerId = customer.getCustomerId();
                break;
            }
        }
        return customerId;
    }

    public int generateUserId() {
        String consultant = consultantComboBox.getValue().toString();
        int userId = 0;
        for (User user : users) {
            if (user.getUserName() == consultant) {
                userId = user.getUserId();
                break;
            }
        }
        return userId;
    }

    public void populateCustomerAppointments(int customerId) {
        AppointmentDAO dao = new AppointmentDAO();
        customerAppointments = dao.getAppointmentsByCustomer(customerId);
        customerAppointmentsTable.setItems(customerAppointments);
        customerStartTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        customerEndTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        customerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        customerConsultantColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
        customerLocationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        customerTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        customerDescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }

    public void populateConsultantAppointments(int userId) {
        AppointmentDAO dao = new AppointmentDAO();
        consultantAppointments = dao.getAppointmentsByUser(userId);
        consultantAppointmentsTable.setItems(consultantAppointments);
        consultantStartTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        consultantEndTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        consultantCustomerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        consultantLocationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        consultantTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        consultantDescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }

    public void populateAppointmentType() {
        AppointmentDAO dao = new AppointmentDAO();
        appointmentTypes = dao.getAppointmentsByType();
        appointmentTypeTable.setItems(appointmentTypes);
        appointmentMonthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMonth()));
        appointmentTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        appointmentCountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCount()));

    }

    public void handleCustomerRefreshButton() {
        populateCustomerAppointments(generateCustomerId());
    }

    public void handleConsultantRefreshButton() {
        populateConsultantAppointments(generateUserId());
    }

    public void setMainApp(CSKCRM cskcrm) {
        this.cskcrm = cskcrm;
    }
}
