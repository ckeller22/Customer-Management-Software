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
import cskcrm.Model.Customer;
import cskcrm.Model.User;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ckeller22
 */
public class AddAppointmentController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox customerComboBox;

    @FXML
    private ComboBox typeComboBox;

    @FXML
    private ComboBox consultantComboBox;

    @FXML
    private ComboBox locationComboBox;

    @FXML
    private DatePicker startDateField;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField endTimeField;

    @FXML
    private TextArea descriptionText;

    private CSKCRM cskcrm;
    private CustomerDAO custDao;
    private UserDAO userDao;

    ObservableList<Customer> customers = FXCollections.observableArrayList();
    ObservableList<User> users = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateCustomerComboBox();
        populateConsultantComboBox();
        populateTypeComboBox();
        populateLocationComboBox();
    }

    public void handleSaveButton() {
        if (isInputValid()) {
            AppointmentDAO dao = new AppointmentDAO();
            User sessionUser = cskcrm.getSessionUser();
            String customerName = customerComboBox.getValue().toString();
            String consultant = consultantComboBox.getValue().toString();
            int customerId = generateCustomerId();
            int userId = generateUserId();
            LocalDateTime start = LocalDateTime.of(startDateField.getValue(), LocalTime.parse(startTimeField.getText(), DateTimeFormatter.ofPattern("hh:mm a")));
            LocalDateTime end = LocalDateTime.of(startDateField.getValue(), LocalTime.parse(endTimeField.getText(), DateTimeFormatter.ofPattern("hh:mm a")));
            Timestamp startTimestamp = Timestamp.valueOf(start);
            Timestamp endTimestamp = Timestamp.valueOf(end);
            System.out.println(start);
            System.out.println(end);
            String type = typeComboBox.getValue().toString();
            String location = locationComboBox.getValue().toString();
            String description = descriptionText.getText();
            String userName = sessionUser.getUserName();
            boolean success = dao.addAppointment(customerId, type, description, location, consultant, startTimestamp, endTimestamp, userName, userName, userId);
            if (success == true) {
                cskcrm.showAppointmentScreen();
            } else {
                cskcrm.throwAlert("Error updating the database, please try again!");
            }

        }
    }

    public void handleCancelButton() {
        if (cskcrm.throwConfirmation("to cancel adding an appoinment")) {
            cskcrm.showAppointmentScreen();
        }
    }

    public void setMainApp(CSKCRM cskcrm) {
        this.cskcrm = cskcrm;
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

    public void populateCustomerComboBox() {
        custDao = new CustomerDAO();
        customers = custDao.getCustomers();
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customer customer : customers) {
            customerNames.add(customer.getCustomerName());
        }
        customerComboBox.setItems(customerNames);
    }

    public void populateConsultantComboBox() {
        userDao = new UserDAO();
        users = userDao.getActiveUsers();
        ObservableList<String> userNames = FXCollections.observableArrayList();
        for (User user : users) {
            userNames.add(user.getUserName());
        }
        consultantComboBox.setItems(userNames);
    }

    public void populateTypeComboBox() {
        ObservableList<String> appointmentType = FXCollections.observableArrayList("Consulting", "Meeting", "Other");
        typeComboBox.setItems(appointmentType);
    }

    public void populateLocationComboBox() {
        ObservableList<String> locations = FXCollections.observableArrayList("New York, New York", "London, England", "Phoenix, Arizona", "Online");
        locationComboBox.setItems(locations);
    }

    public boolean isInputValid() {
        String errorMessage = "";
        LocalTime startTime = null;
        LocalTime endTime = null;
        if (customerComboBox.getSelectionModel().isEmpty()) {
            errorMessage += "You must select a customer. \n";
        }
        if (startDateField == null) {
            errorMessage += "You must select a date. \n";
        }
        if (startTimeField.getText() == null || startTimeField.getText().length() == 0) {
            errorMessage += "You must enter a valid start time. \n";
        } else {
            try {
                startTime = LocalTime.parse(startTimeField.getText(), DateTimeFormatter.ofPattern("hh:mm a"));
                endTime = LocalTime.parse(endTimeField.getText(), DateTimeFormatter.ofPattern("hh:mm a"));
                if (startTime.isBefore(LocalTime.of(9, 0))
                        || endTime.isAfter(LocalTime.of(17, 0))) {
                    errorMessage += "Start time must be during business hours, 9AM - 5PM.\n";
                }
            } catch (DateTimeParseException e) {
                errorMessage += "You must enter a valid start time in hh:mm AM/PM format.\n";
            }
        }
        if (typeComboBox.getSelectionModel().isEmpty()) {
            errorMessage += "You must select an appointment type. \n";
        }
        if (consultantComboBox.getSelectionModel().isEmpty()) {
            errorMessage += "You must select a consultant. \n";
        }
        if (endTimeField.getText() == null || endTimeField.getText().length() == 0) {
            errorMessage += "You must enter a valid end time. \n";
        } else {
            try {
                endTime = LocalTime.parse(endTimeField.getText(), DateTimeFormatter.ofPattern("hh:mm a"));
                if (endTime.isBefore(startTime)) {
                    errorMessage += "End time cannot be before start time.\n";
                }
            } catch (DateTimeParseException e) {
                errorMessage += "You must enter a valid end time in hh:mm AM/PM format.\n";
            }
        }
        if (locationComboBox.getSelectionModel().isEmpty()) {
            errorMessage += "You must select a location. \n";
        }
        if (descriptionText.getText() == null || descriptionText.getText().length() == 0) {
            errorMessage += "You must enter a description. \n";
        }
        if (errorMessage.length() == 0) {
            boolean conflicts = checkAppointmentConflict(startTime, endTime);
            if (conflicts) {
                cskcrm.throwAlert("This appointment conflicts with an existing appointment");
                return false;
            } else {
                return true;
            }
        } else {
            cskcrm.throwAlert(errorMessage);
            return false;
        }
    }

    public boolean checkAppointmentConflict(LocalTime startTime, LocalTime endTime) {
        int userId = generateUserId();
        int customerId = generateCustomerId();
        AppointmentDAO dao = new AppointmentDAO();
        ObservableList<Appointment> appointments = dao.getAppointmentsByCustomer(customerId);
        appointments.addAll(dao.getAppointmentsByUser(userId));
        if (appointments.stream().anyMatch((possibleConflict) -> ((!startTime.isBefore(possibleConflict.getStart().toLocalTime()) && startTime.isBefore(possibleConflict.getEnd().toLocalTime()))
                || (!endTime.isBefore(possibleConflict.getStart().toLocalTime()) && endTime.isBefore(possibleConflict.getEnd().toLocalTime()))))) {
            return true;
        }
        return false;
    }

}
