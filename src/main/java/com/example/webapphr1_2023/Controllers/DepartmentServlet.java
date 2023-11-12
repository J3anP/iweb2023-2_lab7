package com.example.webapphr1_2023.Controllers;

import com.example.webapphr1_2023.Beans.Employee;
import com.example.webapphr1_2023.Beans.Job;
import com.example.webapphr1_2023.Beans.Department;
import com.example.webapphr1_2023.Beans.Location;
import com.example.webapphr1_2023.Daos.DepartmentDao;
import com.example.webapphr1_2023.Daos.EmployeeDao;
import com.example.webapphr1_2023.Daos.JobDao;
import com.example.webapphr1_2023.Daos.LocationDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "DepartmentServlet", urlPatterns = {"/DepartmentServlet"})
public class DepartmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "lista" : req.getParameter("action");
        RequestDispatcher view;
        EmployeeDao employeeDao = new EmployeeDao();
        JobDao jobDao = new JobDao();
        DepartmentDao departmentDao = new DepartmentDao();
        LocationDao locationDao = new LocationDao();
        int departmentId = 0;

        switch (action){
            case "lista":
                req.setAttribute("departmentList", departmentDao.listarDepartments());
                view = req.getRequestDispatcher("department/list.jsp");
                view.forward(req, resp);
                break;
            case "Crear":

                req.setAttribute("listaJefes",employeeDao.listarEmpleados());
                req.setAttribute("listaLocations",locationDao.lista());
                req.setAttribute("lastD",departmentDao.lista().get(departmentDao.lista().size()-1));
                view = req.getRequestDispatcher("department/formularioNuevo.jsp");
                view.forward(req, resp);

                break;
            case "editar":
                Department dep = departmentDao.obtenerDepartment(departmentId);
                req.setAttribute("department", dep);
                req.setAttribute("listaJefes",employeeDao.listarEmpleados());
                req.setAttribute("listaLocations",locationDao.lista());
                view = req.getRequestDispatcher("department/formularioEditar.jsp");
                view.forward(req, resp);
                break;
            case "borrar":
                try{
                    departmentId = Integer.parseInt(req.getParameter("id"));
                    departmentDao.borrarDepartamento(departmentId);
                    resp.sendRedirect("DepartmentServlet");
                }catch (NumberFormatException e){
                    resp.sendRedirect("DepartmentServlet");
                }
                break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action") == null ? "lista" : req.getParameter("action");
        DepartmentDao departmentDao = new DepartmentDao();
        Department department = new Department();
        EmployeeDao employeeDao = new EmployeeDao();
        LocationDao locationDao = new LocationDao();

        department.setDepartmentId(Integer.parseInt(req.getParameter("department_id")));

        boolean valido = true;

        if(req.getParameter("department_name").isEmpty()){
            valido = false;
        }
        department.setDepartmentName(req.getParameter("department_name"));
        Employee manager = new Employee();
        manager.setEmployeeId(Integer.parseInt(req.getParameter("manager_id")));
        department.setManager(manager);
        Location location = new Location();
        location.setLocationId(Integer.parseInt(req.getParameter("location_id")));
        department.setLocation(location);

        switch (action){
            case "crear":
                if(valido){
                    departmentDao.crearDepartamento(department);
                    resp.sendRedirect("/DepartmentServlet");
                }else{
                    req.setAttribute("listaJefes",employeeDao.listarEmpleados());
                    req.setAttribute("listaLocations",locationDao.lista());
                    req.setAttribute("lastD",departmentDao.lista().get(departmentDao.lista().size()-1));
                    req.getRequestDispatcher("department/formularioEditar.jsp").forward(req,resp);
                }
                break;
            case "editar":
                if(valido){
                    departmentDao.editarDepartamento(department);
                    resp.sendRedirect("/DepartmentServlet");
                }else{
                    req.setAttribute("listaJefes",employeeDao.listarEmpleados());
                    req.setAttribute("listaLocations",locationDao.lista());
                    req.setAttribute("lastD",departmentDao.obtenerDepartment(Integer.parseInt(req.getParameter("department_id"))));
                    req.getRequestDispatcher("department/formularioNuevo.jsp").forward(req,resp);
                }
                break;
        }


    }

}
