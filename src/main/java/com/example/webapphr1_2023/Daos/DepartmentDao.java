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
        String sql = "select * from departments";
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
    public ArrayList<Department> listarDepartments() {

        ArrayList<Department> listaDepartamentos = new ArrayList<>();

        String sql = "select * from departments  d inner join employees m on d.manager_id = m.employee_id left join locations l on d.location_id = l.location_id order by d.department_id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Department department = fetchDepartmentData(rs);
                listaDepartamentos.add(department);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaDepartamentos;
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

    public void crearDepartamento(Department department) {

        String sql = "insert into departments (department_id,department_name,manager_id,location_id) values (?,?,?,?) ";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,department.getDepartmentId());

            pstmt.setString(2,department.getDepartmentName());

            if (department.getManager().getEmployeeId() == 0) {
                pstmt.setNull(3, Types.INTEGER);
            } else {
                pstmt.setInt(3, department.getManager().getEmployeeId());
            }

            if (department.getLocation().getLocationId() == 0) {
                pstmt.setNull(4, Types.INTEGER);
            } else {
                pstmt.setInt(4, department.getLocation().getLocationId());
            }

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            //ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    public void editarDepartamento(Department department) {

        String sql = "update departments set department_name = ? , manager_id = ? , location_id =  ? where department_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setDepartmentData(department, pstmt);
            pstmt.setInt(4, department.getDepartmentId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            //ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    public void borrarDepartamento(int departmentId) {

        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update employees set department_id = null where department_id=?")){
            pstmt.setInt(1,departmentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update job_history set department_id = null where department_id=?")){
            pstmt.setInt(1,departmentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Los pasos anteriores son para poder borrar departments, porque depende de foreign keys
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("delete from departments where department_id = ?")) {

            pstmt.setInt(1, departmentId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Department fetchDepartmentData(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setDepartmentId(rs.getInt("d.department_id"));
        department.setDepartmentName(rs.getString("d.department_name"));

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
        location.setStateProvince(rs.getString("state_province"));
        department.setLocation(location);

        return department;
    }
    private void setDepartmentData(Department department, PreparedStatement pstmt) throws SQLException {

        pstmt.setString(1,department.getDepartmentName());

        if (department.getManager().getEmployeeId() == 0) {
            pstmt.setNull(2, Types.INTEGER);
        } else {
            pstmt.setInt(2, department.getManager().getEmployeeId());
        }

        if (department.getLocation().getLocationId() == 0) {
            pstmt.setNull(3, Types.INTEGER);
        } else {
            pstmt.setInt(3, department.getLocation().getLocationId());
        }
    }
}