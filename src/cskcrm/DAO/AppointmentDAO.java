/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.DAO;

import cskcrm.Model.Appointment;
import cskcrm.Model.AppointmentReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ckeller22
 */
public class AppointmentDAO {

    public ObservableList<Appointment> getMonthlyAppointments(LocalDate start) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        LocalDate end = start.plusMonths(1);
        String query = "SELECT * FROM appointment a INNER JOIN customer c ON c.customerId = a.customerId WHERE start BETWEEN '" + start + "' AND '" + end + "' ORDER BY start";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment tAppointment = new Appointment();
                tAppointment.setAppointmentId(rs.getInt("a.appointmentId"));
                tAppointment.setCustomerId(rs.getInt("a.customerId"));
                tAppointment.setCustomerName(rs.getString("c.customerName"));
                tAppointment.setTitle(rs.getString("a.title"));
                tAppointment.setDescription(rs.getString("a.description"));
                tAppointment.setLocation(rs.getString("a.location"));
                tAppointment.setContact(rs.getString("a.contact"));
                tAppointment.setUrl(rs.getString("a.url"));
                tAppointment.setStart(rs.getTimestamp("a.start").toLocalDateTime());
                tAppointment.setEnd(rs.getTimestamp("a.end").toLocalDateTime());
                tAppointment.setCreateDate(rs.getDate("a.createDate").toLocalDate());
                tAppointment.setCreatedBy(rs.getString("a.createdBy"));
                tAppointment.setLastUpdate(rs.getTimestamp("a.lastUpdate").toLocalDateTime());
                tAppointment.setLastUpdateBy(rs.getString("a.lastUpdateBy"));
                tAppointment.setUserId(rs.getInt("a.userId"));
                allAppointments.add(tAppointment);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public ObservableList<Appointment> getWeeklyAppointments(LocalDate start) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        LocalDate end = start.plusWeeks(1);
        String query = "SELECT * FROM appointment a INNER JOIN customer c ON c.customerId = a.customerId WHERE start BETWEEN '" + start + "' AND '" + end + "' ORDER BY start";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment tAppointment = new Appointment();
                tAppointment.setAppointmentId(rs.getInt("a.appointmentId"));
                tAppointment.setCustomerId(rs.getInt("a.customerId"));
                tAppointment.setCustomerName(rs.getString("c.customerName"));
                tAppointment.setTitle(rs.getString("a.title"));
                tAppointment.setDescription(rs.getString("a.description"));
                tAppointment.setLocation(rs.getString("a.location"));
                tAppointment.setContact(rs.getString("a.contact"));
                tAppointment.setUrl(rs.getString("a.url"));
                tAppointment.setStart(rs.getTimestamp("a.start").toLocalDateTime());
                tAppointment.setEnd(rs.getTimestamp("a.end").toLocalDateTime());
                tAppointment.setCreateDate(rs.getDate("a.createDate").toLocalDate());
                tAppointment.setCreatedBy(rs.getString("a.createdBy"));
                tAppointment.setLastUpdate(rs.getTimestamp("a.lastUpdate").toLocalDateTime());
                tAppointment.setLastUpdateBy(rs.getString("a.lastUpdateBy"));
                tAppointment.setUserId(rs.getInt("a.userId"));
                allAppointments.add(tAppointment);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public ObservableList<Appointment> getAppointmentsByCustomer(int customerId) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointment a INNER JOIN customer c ON c.customerId = a.customerId WHERE a.customerId = ?;";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment tAppointment = new Appointment();
                tAppointment.setAppointmentId(rs.getInt("a.appointmentId"));
                tAppointment.setCustomerId(rs.getInt("a.customerId"));
                tAppointment.setCustomerName(rs.getString("c.customerName"));
                tAppointment.setTitle(rs.getString("a.title"));
                tAppointment.setDescription(rs.getString("a.description"));
                tAppointment.setLocation(rs.getString("a.location"));
                tAppointment.setContact(rs.getString("a.contact"));
                tAppointment.setUrl(rs.getString("a.url"));
                tAppointment.setStart(rs.getTimestamp("a.start").toLocalDateTime());
                tAppointment.setEnd(rs.getTimestamp("a.end").toLocalDateTime());
                tAppointment.setCreateDate(rs.getDate("a.createDate").toLocalDate());
                tAppointment.setCreatedBy(rs.getString("a.createdBy"));
                tAppointment.setLastUpdate(rs.getTimestamp("a.lastUpdate").toLocalDateTime());
                tAppointment.setLastUpdateBy(rs.getString("a.lastUpdateBy"));
                allAppointments.add(tAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }
    
    public ObservableList<Appointment> getAppointmentsByCustomer(int customerId, int appointmentId) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointment a INNER JOIN customer c ON c.customerId = a.customerId WHERE a.customerId = ? AND a.appointmentId != ?;";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setInt(2, appointmentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment tAppointment = new Appointment();
                tAppointment.setAppointmentId(rs.getInt("a.appointmentId"));
                tAppointment.setCustomerId(rs.getInt("a.customerId"));
                tAppointment.setCustomerName(rs.getString("c.customerName"));
                tAppointment.setTitle(rs.getString("a.title"));
                tAppointment.setDescription(rs.getString("a.description"));
                tAppointment.setLocation(rs.getString("a.location"));
                tAppointment.setContact(rs.getString("a.contact"));
                tAppointment.setUrl(rs.getString("a.url"));
                tAppointment.setStart(rs.getTimestamp("a.start").toLocalDateTime());
                tAppointment.setEnd(rs.getTimestamp("a.end").toLocalDateTime());
                tAppointment.setCreateDate(rs.getDate("a.createDate").toLocalDate());
                tAppointment.setCreatedBy(rs.getString("a.createdBy"));
                tAppointment.setLastUpdate(rs.getTimestamp("a.lastUpdate").toLocalDateTime());
                tAppointment.setLastUpdateBy(rs.getString("a.lastUpdateBy"));
                allAppointments.add(tAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public ObservableList<Appointment> getAppointmentsByUser(int userId) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointment a INNER JOIN customer c ON c.customerId = a.customerId WHERE a.userId = ? ";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment tAppointment = new Appointment();
                tAppointment.setAppointmentId(rs.getInt("a.appointmentId"));
                tAppointment.setCustomerId(rs.getInt("a.customerId"));
                tAppointment.setCustomerName(rs.getString("customerName"));
                tAppointment.setTitle(rs.getString("a.title"));
                tAppointment.setDescription(rs.getString("a.description"));
                tAppointment.setLocation(rs.getString("a.location"));
                tAppointment.setContact(rs.getString("a.contact"));
                tAppointment.setUrl(rs.getString("a.url"));
                tAppointment.setStart(rs.getTimestamp("a.start").toLocalDateTime());
                tAppointment.setEnd(rs.getTimestamp("a.end").toLocalDateTime());
                tAppointment.setCreateDate(rs.getDate("a.createDate").toLocalDate());
                tAppointment.setCreatedBy(rs.getString("a.createdBy"));
                tAppointment.setLastUpdate(rs.getTimestamp("a.lastUpdate").toLocalDateTime());
                tAppointment.setLastUpdateBy(rs.getString("a.lastUpdateBy"));
                allAppointments.add(tAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public ObservableList<Appointment> getAppointmentsByUser(int userId, int appointmentId) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointment a INNER JOIN customer c ON c.customerId = a.customerId WHERE a.userId = ? AND a.appointmentId != ? ";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, appointmentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment tAppointment = new Appointment();
                tAppointment.setAppointmentId(rs.getInt("a.appointmentId"));
                tAppointment.setCustomerId(rs.getInt("a.customerId"));
                tAppointment.setCustomerName(rs.getString("customerName"));
                tAppointment.setTitle(rs.getString("a.title"));
                tAppointment.setDescription(rs.getString("a.description"));
                tAppointment.setLocation(rs.getString("a.location"));
                tAppointment.setContact(rs.getString("a.contact"));
                tAppointment.setUrl(rs.getString("a.url"));
                tAppointment.setStart(rs.getTimestamp("a.start").toLocalDateTime());
                tAppointment.setEnd(rs.getTimestamp("a.end").toLocalDateTime());
                tAppointment.setCreateDate(rs.getDate("a.createDate").toLocalDate());
                tAppointment.setCreatedBy(rs.getString("a.createdBy"));
                tAppointment.setLastUpdate(rs.getTimestamp("a.lastUpdate").toLocalDateTime());
                tAppointment.setLastUpdateBy(rs.getString("a.lastUpdateBy"));
                allAppointments.add(tAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }
    
    public ObservableList<AppointmentReport> getAppointmentsByType() {
        ObservableList<AppointmentReport> allAppointments = FXCollections.observableArrayList();
        String query = "SELECT MONTHNAME(start) AS 'month', title, COUNT(*) AS 'amount' FROM appointment GROUP BY month, title";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AppointmentReport tAppointmentReport = new AppointmentReport();
                tAppointmentReport.setMonth(rs.getString("month"));
                tAppointmentReport.setType(rs.getString("title"));
                tAppointmentReport.setCount(rs.getString("amount"));
                allAppointments.add(tAppointmentReport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public boolean addAppointment(int customerId, String title, String description, String location, String contact, Timestamp start, Timestamp end, String createdBy, String lastUpdateBy, int userId) {
        boolean result = false;
        String insert = "INSERT INTO appointment (customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy, type, userId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE, ?, CURRENT_TIMESTAMP, ?, ?, ?);";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setInt(1, customerId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, contact);
            ps.setString(6, "");
            ps.setTimestamp(7, start);
            ps.setTimestamp(8, end);
            ps.setString(9, createdBy);
            ps.setString(10, lastUpdateBy);
            ps.setString(11, null);
            ps.setInt(12, userId);
            int indicator = ps.executeUpdate();
            result = indicator > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateAppointment(int appointmentId, int customerId, String title, String description, String location, String contact, Timestamp start, Timestamp end, String lastUpdateBy, int userId) {
        boolean result = false;
        String update = "UPDATE appointment SET  title = ?, description = ?, location = ?, contact = ?, url = ?, start = ?, end = ?, lastUpdate = CURRENT_TIMESTAMP,"
                + "lastUpdateBy = ?, type = ?, userId = ? WHERE appointmentId = ?";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(update);
            //ps.setInt(1, customerId);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, contact);
            ps.setString(5, "");
            ps.setTimestamp(6, start);
            ps.setTimestamp(7, end);
            ps.setString(8, lastUpdateBy);
            ps.setString(9, null);
            ps.setInt(10, userId);
            ps.setInt(11, appointmentId);
            int indicator = ps.executeUpdate();
            result = indicator > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteAppointment(int appointmentId) {
        boolean result = false;
        String delete = "DELETE FROM appointment WHERE appointmentId = ?";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(delete);
            ps.setInt(1, appointmentId);
            int indicator = ps.executeUpdate();
            result = indicator > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
