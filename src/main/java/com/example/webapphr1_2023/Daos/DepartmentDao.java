package com.example.webapphr1_2023.Daos;


import com.example.webapphr1_2023.Beans.Department;
import com.example.webapphr1_2023.Beans.Employee;
import com.example.webapphr1_2023.Beans.Job;
import com.example.webapphr1_2023.Beans.Location;

import java.sql.*;
import java.util.ArrayList;

public class DepartmentDao extends DaoBase {

    public ArrayList<Department> lista() {

        ArrayList<Department> list = new ArrayList<>();
        String sql = " ";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Department department = new Department();
                department.setDepartmentId(rs.getInt(1));
                department.setDepartmentName(rs.getString(2));
                list.add(department);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return list;
    }
    public Department obtenerDepartment(int departmentId){
        Department department = new Department();

        String sql = "select * from departments  d inner join employees m on d.manager_id = m.employee_id left join locations l on d.location_id = l.location_id where d.department_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, departmentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    department = fetchDepartmentData(rs);

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return department;
    }

    private Department fetchDepartmentData(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setDepartmentId(rs.getInt(1));
        department.setDepartmentName(rs.getString(2));

        Employee manager = null;
        if(rs.getInt("m.employee_id") != 0){
            manager = new Employee();
            manager.setEmployeeId(rs.getInt("m.employee_id"));
            manager.setFirstName(rs.getString("m.first_name"));
            manager.setLastName(rs.getString("m.last_name"));
            department.setManager(manager);
        }

        Location location = new Location();
        location.setLocationId(rs.getInt("location_id"));
        location.setStreetAddress(rs.getString("street_address"));
        location.setCity(rs.getString("city"));
        location.setStateProvince("state_province");
        department.setLocation(location);

        return department;
    }
}