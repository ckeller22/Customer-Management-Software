/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm;

import cskcrm.DAO.DAO;
import cskcrm.Model.User;
import cskcrm.View_Controller.AddAppointmentController;
import cskcrm.View_Controller.AddCustomerController;
import cskcrm.View_Controller.AppointmentViewController;
import cskcrm.View_Controller.CustomerViewController;
import cskcrm.View_Controller.LoginController;
import cskcrm.View_Controller.ModifyAppointmentController;
import cskcrm.View_Controller.ModifyCustomerController;
import cskcrm.View_Controller.ReportsController;
import cskcrm.util.LoginLogger;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author ckeller22
 */
public class CSKCRM extends Application {

    private Stage primaryStage;
    private User sessionUser;
    private ResourceBundle rb;
    private boolean firstLogin = true;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Customer Relationship Management System");
        showLoginScreen();
    }

    public void setSessionUser(User user) {
        this.sessionUser = user;
    }

    public User getSessionUser() {
        return this.sessionUser;
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CSKCRM.class.getResource("View_Controller/Login.fxml"));
            AnchorPane mainScreen = (AnchorPane) loader.load();

            LoginController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(mainScreen);
            primaryStage.setScene(scene);
            primaryStage.show();

            if (!firstLogin) {
                LoginLogger.logSignOut(sessionUser.getUserName());
            } else {
                firstLogin = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAppointmentScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CSKCRM.class.getResource("View_Controller/AppointmentView.fxml"));
            AnchorPane mainScreen = (AnchorPane) loader.load();

            AppointmentViewController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(mainScreen);
            primaryStage.setScene(scene);
            primaryStage.show();
            //Requirement H. 
            controller.checkAppointmentsAtLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddAppointmentScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CSKCRM.class.getResource("View_Controller/AddAppointment.fxml"));
            Parent addCustomerScreenParent = loader.load();
            Scene addCustomerScreen = new Scene(addCustomerScreenParent);

            AddAppointmentController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(addCustomerScreen);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showModifyAppointmentScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CSKCRM.class.getResource("View_Controller/ModifyAppointment.fxml"));
            Parent modifyAppointmentScreenParent = loader.load();
            Scene modifyAppointmentScreen = new Scene(modifyAppointmentScreenParent);

            ModifyAppointmentController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(modifyAppointmentScreen);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCustomerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CSKCRM.class.getResource("View_Controller/CustomerView.fxml"));
            Parent customerScreenParent = loader.load();
            Scene customerScreen = new Scene(customerScreenParent);

            CustomerViewController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(customerScreen);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddCustomerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CSKCRM.class.getResource("View_Controller/AddCustomer.fxml"));
            Parent addCustomerScreenParent = loader.load();
            Scene addCustomerScreen = new Scene(addCustomerScreenParent);

            AddCustomerController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(addCustomerScreen);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showModifyCustomerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CSKCRM.class.getResource("View_Controller/ModifyCustomer.fxml"));
            Parent modifyCustomerScreenParent = loader.load();
            Scene modifyCustomerScreen = new Scene(modifyCustomerScreenParent);

            ModifyCustomerController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(modifyCustomerScreen);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showReportsScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CSKCRM.class.getResource("View_Controller/Reports.fxml"));
            Parent reportsScreenParent = loader.load();
            Scene reportsScreen = new Scene(reportsScreenParent);

            ReportsController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(reportsScreen);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void throwAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Invaild fields");
        alert.setHeaderText("Please correct the following:");
        alert.getDialogPane().setContent(new Label(errorMessage));
        alert.showAndWait();
    }

    public boolean throwConfirmation(String actionMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Confirm");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to " + actionMessage + "?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void throwNotification(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Upcoming appointments");
        alert.setHeaderText("The following consultants have upcoming appointments.");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DAO.initConnection();
        launch(args);
    }

}
