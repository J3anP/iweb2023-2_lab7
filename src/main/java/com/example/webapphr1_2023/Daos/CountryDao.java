package com.example.webapphr1_2023.Daos;

import com.example.webapphr1_2023.Beans.Country;
import com.example.webapphr1_2023.Beans.Location;

import java.sql.*;
import java.util.ArrayList;

public class CountryDao extends DaoBase{

    public ArrayList<Country> listarCountries(){

        ArrayList<Country> list = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM COUNTRIES")) {

            while (rs.next()) {
                Country country = new Country();

                country.setCountryId(rs.getString(1));
                country.setCountryName(rs.getString(2));
                country.setRegionId(rs.getFloat(3));
                list.add(country);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }

    public Country obtenerCountryPorId(String countryId){

        Country country = new Country();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM countries WHERE country_id = ?");) {

            pstmt.setString(1,countryId);

            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    country.setCountryId(rs.getString(1));
                    country.setCountryName(rs.getString(2));
                    country.setRegionId(rs.getFloat(3));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return country;






    }


}
