/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.DAO;

import cskcrm.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ckeller22
 */
public class UserDAO {

    public User validateUser(String userName, String password) {
        User validUser = new User();
        String query = "SELECT * FROM user "
                + "WHERE userName =? AND password =?";

        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                validUser.setUserName(rs.getString("userName"));
                validUser.setPassword(rs.getString("password"));
                validUser.setUserId(rs.getInt("userId"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return validUser;
    }

    public ObservableList<User> getActiveUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String query = "SELECT * FROM user WHERE active = 1";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User tUser = new User();
                tUser.setActive(rs.getInt("active"));
                tUser.setCreateDate(rs.getDate("createDate").toLocalDate());
                tUser.setCreatedBy(rs.getString("createBy"));
                tUser.setLastUpdate(rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                tUser.setLastUpdateBy(rs.getString("lastUpdateBy"));
                tUser.setPassword(rs.getString("password"));
                tUser.setUserId(rs.getInt("userId"));
                tUser.setUserName(rs.getString("userName"));
                allUsers.add(tUser);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
}
