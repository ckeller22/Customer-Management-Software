/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.View_Controller;

import cskcrm.CSKCRM;
import cskcrm.DAO.AppointmentDAO;
import cskcrm.Model.Appointment;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

/**
 * FXML Controller class
 *
 * @author ckeller22
 */
public class AppointmentViewController implements Initializable {

    public CSKCRM cskcrm;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, String> startColumn;

    @FXML
    private TableColumn<Appointment, String> endColumn;

    @FXML
    private TableColumn<Appointment, String> customerColumn;

    @FXML
    private TableColumn<Appointment, String> contactColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnLogout;

    @FXML
    private ToggleButton btnMonth;

    @FXML
    private ToggleButton btnWeek;

    @FXML
    private Button btnRight;

    @FXML
    private Button btnLeft;

    @FXML
    private Label lblDateRange;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnReport;

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private LocalDate currentDate;
    public static Appointment tAppointment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentDate = LocalDate.now();
        populateAppointmentTableMonthly();
        startColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        endColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        contactColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

    }

    public void handleCustomersButton() {
        cskcrm.showCustomerScreen();
    }

    public void handleLogoutButton() {
        cskcrm.showLoginScreen();
    }

    public void handleByWeekButton() {
        while (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
            currentDate = currentDate.minusDays(1);
        }
        populateAppointmentTableWeekly();
    }

    public void handleByMonthButton() {
        populateAppointmentTableMonthly();
    }

    public void handleRightButton() {
        if (btnMonth.isSelected()) {
            currentDate = currentDate.plusMonths(1);
            populateAppointmentTableMonthly();
        } else if (btnWeek.isSelected()) {
            currentDate = currentDate.plusWeeks(1);
            populateAppointmentTableWeekly();
        }
        System.out.println(currentDate);
    }

    public void handleLeftButton() {
        if (btnMonth.isSelected()) {
            currentDate = currentDate.minusMonths(1);
            populateAppointmentTableMonthly();
        } else if (btnWeek.isSelected()) {
            currentDate = currentDate.minusWeeks(1);
            populateAppointmentTableWeekly();
        }
        System.out.println(currentDate);
    }

    public void populateAppointmentTableWeekly() {
        AppointmentDAO dao = new AppointmentDAO();
        appointments = dao.getWeeklyAppointments(currentDate);
        lblDateRange.setText(currentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + " - " + currentDate.plusWeeks(1).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        appointmentTable.setItems(appointments);
    }

    public void populateAppointmentTableMonthly() {
        AppointmentDAO dao = new AppointmentDAO();
        appointments = dao.getMonthlyAppointments(currentDate);
        lblDateRange.setText(currentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + "-" + currentDate.plusMonths(1).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        appointmentTable.setItems(appointments);
    }

    public void checkAppointmentsAtLogin() {
        String message = "";
        List<Appointment> upcomingAppointments = new ArrayList<>();
        appointments.stream().filter(
                tAppointment -> tAppointment.getStart().isAfter(LocalDateTime.now())
                && tAppointment.getStart().isBefore(LocalDateTime.now().plusMinutes(15))).forEach(upcomingAppointments::add);
        if (upcomingAppointments.size() > 0) {
            for (Appointment appointment : upcomingAppointments) {
                message += appointment.getContact() + " : " + appointment.getTitle() + " with " + appointment.getCustomerName() + " at " + appointment.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\n";
            }
            cskcrm.throwNotification(message);
        }
    }

    public void handleAddButton() {
        cskcrm.showAddAppointmentScreen();
    }

    public void handleModifyButton() {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            cskcrm.throwAlert("Please ensure a appointment is selected to be updated.");
        } else {
            tAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            cskcrm.showModifyAppointmentScreen();
        }
    }

    public void handleReportsButton() {
        cskcrm.showReportsScreen();
    }

    public void setMainApp(CSKCRM cskcrm) {
        this.cskcrm = cskcrm;
    }
}
