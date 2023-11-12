package com.example.webapphr1_2023.Daos;

import com.example.webapphr1_2023.Beans.*;


import com.example.webapphr1_2023.Beans.Department;

import java.sql.*;
import java.util.ArrayList;

public class LocationDao extends DaoBase{
    public ArrayList<Location> lista() {

        ArrayList<Location> list = new ArrayList<>();
        String sql = " select * from locations";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Location location = new Location();

                location.setLocationId(rs.getInt(1));
                location.setStreetAddress(rs.getString(2));
                location.setPostalCode(rs.getString(3));
                location.setCity(rs.getString(4));
                location.setStateProvince(rs.getString(5));
                location.setCountryId(rs.getString(6));

                list.add(location);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return list;
    }

    public ArrayList<Location> listaFull() {

        ArrayList<Location> list = new ArrayList<>();
        String sql = "SELECT l.*,c.* FROM locations l LEFT JOIN  countries c ON (l.country_id=c.country_id)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Location location = new Location();

                location.setLocationId(rs.getInt(1));
                location.setStreetAddress(rs.getString(2));
                location.setPostalCode(rs.getString(3));
                location.setCity(rs.getString(4));
                location.setStateProvince(rs.getString(5));

                Country country = new Country();

                country.setCountryId(rs.getString(7));
                country.setCountryName(rs.getString(8));
                country.setRegionId(rs.getFloat(9));

                location.setCountry(country);

                list.add(location);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return list;
    }


    public Location obtenerLocationPorId(int locationId){
        Location location = new Location();
        String sql = "SELECT l.*,c.* FROM locations l LEFT JOIN countries c ON (l.country_id=c.country_id) WHERE l.location_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1,locationId);
            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {

                    location.setLocationId(rs.getInt(1));
                    location.setStreetAddress(rs.getString(2));
                    location.setPostalCode(rs.getString(3));
                    location.setCity(rs.getString(4));
                    location.setStateProvince(rs.getString(5));

                    Country country = new Country();

                    country.setCountryId(rs.getString(7));
                    country.setCountryName(rs.getString(8));
                    country.setRegionId(rs.getFloat(9));

                    location.setCountry(country);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return location;
    }

    public void crear(Location location){

        String sql = "INSERT INTO locations(location_id,street_address,postal_code,city,state_province,country_id) VALUES (?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1,location.getLocationId());
            pstmt.setString(2,location.getStreetAddress());
            pstmt.setString(3,location.getPostalCode());
            pstmt.setString(4,location.getCity());
            pstmt.setString(5,location.getStateProvince());
            if(location.getCountry().getCountryId().equals("0")){
                pstmt.setNull(6,Types.CHAR);
            }else{
                pstmt.setString(6,location.getCountry().getCountryId());
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    public void editar(Location location){

        String sql = "UPDATE locations SET street_address=?,postal_code=?,city=?,state_province=?,country_id=? WHERE location_id=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1,location.getStreetAddress());
            pstmt.setString(2,location.getPostalCode());
            pstmt.setString(3,location.getCity());
            pstmt.setString(4,location.getStateProvince());
            if(location.getCountry().getCountryId().equals("0")){
                pstmt.setNull(5,Types.CHAR);
            }else{
                pstmt.setString(5,location.getCountry().getCountryId());
            }
            pstmt.setInt(6,location.getLocationId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    public void borrar(int locationId){

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM locations WHERE location_id = ?")) {

            pstmt.setInt(1, locationId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
