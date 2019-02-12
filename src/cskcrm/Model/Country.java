/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ckeller22
 */
public class Country {

    private int countryId;
    private String country;
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public static ObservableList<String> populateCountries() {
        ObservableList<String> cities = FXCollections.observableArrayList();
        String[] locales1 = Locale.getISOCountries();
        for (String countrylist : locales1) {
            Locale obj = new Locale("", countrylist);
            String[] city = {obj.getDisplayCountry()};
            for (int x = 0; x < city.length; x++) {
                cities.add(obj.getDisplayCountry());
            }
        }
        return cities;
    }

}
