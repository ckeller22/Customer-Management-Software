/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ckeller22
 */
public class User {

    private Integer userId;
    private String userName;
    private String password;
    private Integer active;
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;

    public User() {
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getActive() {
        return active;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
}
