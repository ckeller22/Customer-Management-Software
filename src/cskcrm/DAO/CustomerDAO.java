/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.DAO;

import cskcrm.Model.City;
import cskcrm.Model.Country;
import cskcrm.Model.Customer;
import cskcrm.Model.User;
import cskcrm.util.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author ckeller22
 */
public class CustomerDAO {

    public ObservableList<Customer> getCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String query = "SELECT c.customerId, c.customerName, c.active, a.address, a.address2, a.postalCode, city.cityId, city.city, country.country, a.phone "
                + "FROM " + Constants.DB_USER + ".customer c "
                + "INNER JOIN " + Constants.DB_USER + ".address a "
                + "ON c.addressId = a.addressId "
                + "INNER JOIN " + Constants.DB_USER + ".city "
                + "ON a.cityId = city.cityId "
                + "INNER JOIN " + Constants.DB_USER + ".country "
                + "ON city.countryId = country.countryId "
                + "ORDER BY c.customerName";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer tCustomer = new Customer();
                tCustomer.setCustomerId(rs.getInt("c.customerId"));
                tCustomer.setCustomerName(rs.getString("c.customerName"));
                tCustomer.setActive(rs.getInt("c.active"));
                tCustomer.setAddress1(rs.getString("a.address"));
                tCustomer.setAddress2(rs.getString("a.address2"));
                tCustomer.setPostalCode(rs.getString("a.postalCode"));
                tCustomer.setCityId(rs.getInt("city.cityId"));
                tCustomer.setCity(rs.getString("city.city"));
                tCustomer.setCountry(rs.getString("country.country"));
                tCustomer.setNumber(rs.getString("a.phone"));
                allCustomers.add(tCustomer);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    public static boolean addCustomer(String customerName, String address1, String address2, String city, String country, String postalCode, String phone, int active, User sessionUser) {
        boolean result = false;
        int countryId = checkCountry(country, sessionUser);
        int cityId = checkCity(city, countryId, sessionUser);
        int addressId = checkAddress(address1, address2, postalCode, phone, cityId, sessionUser);
        try {
            String insert = "INSERT INTO " + Constants.DB_USER + ".customer "
                    + "(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, ?, ?, CURRENT_DATE, ?, CURRENT_TIMESTAMP, ?);";
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.setInt(3, active);
            ps.setString(4, sessionUser.getUserName());
            ps.setString(5, sessionUser.getUserName());
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean updateCustomer(int customerId, String customerName, String address1, String address2, String city, String country, String postalCode, String phone, int active, User sessionUser) {
        boolean result = false;
        int countryId = checkCountry(country, sessionUser);
        int cityId = checkCity(city, countryId, sessionUser);
        int addressId = checkAddress(address1, address2, postalCode, phone, cityId, sessionUser);
        try {
            String update = "UPDATE " + Constants.DB_USER + ".customer SET customerName = ?, addressId = ?, active = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? "
                    + "WHERE customerId = ?";
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(update);
            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.setInt(3, active);
            ps.setString(4, sessionUser.getUserName());
            ps.setInt(5, customerId);
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean deleteCustomer(int customerId, User sessionUser) {
        boolean result = false;
        String delete = "DELETE FROM " + Constants.DB_USER + ".customer WHERE customerId = ?";
        try {
            Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(delete);
            ps.setInt(1, customerId);
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int checkCity(String city, int countryId, User sessionUser) {
        try {
            Connection con = DAO.getConnection();
            Statement s = con.createStatement();
            ResultSet cityIdCheck = s.executeQuery("SELECT cityId FROM city WHERE city = '" + city + "' AND countryid = " + countryId);
            if (cityIdCheck.next()) {
                int cityId = cityIdCheck.getInt(1);
                cityIdCheck.close();
                return cityId;
            } else {
                cityIdCheck.close();
                int cityId;
                ResultSet allCityId = s.executeQuery("SELECT cityId FROM city ORDER BY cityId");
                if (allCityId.last()) {
                    cityId = allCityId.getInt(1) + 1;
                    allCityId.close();
                } else {
                    allCityId.close();
                    cityId = 1;
                }
                s.executeUpdate("INSERT INTO city VALUES (" + cityId + ", '" + city + "', " + countryId + ", CURRENT_DATE, "
                        + "'" + sessionUser.getUserName() + "', CURRENT_TIMESTAMP, '" + sessionUser.getUserName() + "')");
                return cityId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int checkCountry(String country, User sessionUser) {
        try {
            Connection con = DAO.getConnection();
            Statement s = con.createStatement();
            ResultSet countryIdCheck = s.executeQuery("SELECT countryId FROM country WHERE country = '" + country + "'");
            if (countryIdCheck.next()) {
                int countryId = countryIdCheck.getInt(1);
                countryIdCheck.close();
                return countryId;
            } else {
                countryIdCheck.close();
                int countryId;
                ResultSet allCountryId = s.executeQuery("SELECT countryId FROM country ORDER BY countryId");
                if (allCountryId.last()) {
                    countryId = allCountryId.getInt(1) + 1;
                    allCountryId.close();
                } else {
                    allCountryId.close();
                    countryId = 1;
                }
                s.executeUpdate("INSERT INTO country VALUES (" + countryId + ", '" + country + "', CURRENT_DATE, "
                        + "'" + sessionUser.getUserName() + "', CURRENT_TIMESTAMP, '" + sessionUser.getUserName() + "')");;
                return countryId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int checkAddress(String address1, String address2, String postalCode, String phone, int cityId, User sessionUser) {
        try {
            Connection con = DAO.getConnection();
            Statement s = con.createStatement();
            ResultSet addressIdCheck = s.executeQuery("SELECT addressId FROM address WHERE address = '" + address1 + "' AND "
                    + "address2 = '" + address2 + "' AND postalCode = '" + postalCode + "' AND phone = '" + phone + "' AND cityId = " + cityId);
            if (addressIdCheck.next()) {
                int addressId = addressIdCheck.getInt(1);
                addressIdCheck.close();
                return addressId;
            } else {
                addressIdCheck.close();
                int addressId;
                ResultSet allAddressId = s.executeQuery("SELECT addressId FROM address ORDER BY addressId");
                if (allAddressId.last()) {
                    addressId = allAddressId.getInt(1) + 1;
                    allAddressId.close();
                } else {
                    allAddressId.close();
                    addressId = 1;
                }
                s.executeUpdate("INSERT INTO address VALUES (" + addressId + ", '" + address1 + "', '" + address2 + "', " + cityId + ", "
                        + "'" + postalCode + "', '" + phone + "', CURRENT_DATE, '" + sessionUser.getUserName() + "', CURRENT_TIMESTAMP, '" + sessionUser.getUserName() + "')");
                return addressId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void populateCountries() {
        ObservableList<String> cities = FXCollections.observableArrayList();
        ComboBox<String> country = new ComboBox<String>(cities);

        String[] locales1 = Locale.getISOCountries();
        for (String countrylist : locales1) {
            Locale obj = new Locale("", countrylist);
            String[] city = {obj.getDisplayCountry()};
            for (int x = 0; x < city.length; x++) {
                cities.add(obj.getDisplayCountry());
            }
        }
        country.setItems(cities);
    }

}
