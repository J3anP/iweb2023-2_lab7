package com.example.webapphr1_2023.Controllers;

import com.example.webapphr1_2023.Beans.Employee;
import com.example.webapphr1_2023.Beans.Job;
import com.example.webapphr1_2023.Beans.Department;
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

        switch (action){
            case "lista":
                req.setAttribute("departmentList", departmentDao.lista());
                view = req.getRequestDispatcher("department/list.jsp");
                view.forward(req, resp);
                break;
            case "crear":
                view = req.getRequestDispatcher("jobs/newDepartment.jsp");
                view.forward(req, resp);
                break;
            case "editar":

                if (req.getParameter("id") != null) {
                    String departmentIdString = req.getParameter("id");
                    int departmentId = 0;
                    try {
                        departmentId = Integer.parseInt(departmentIdString);
                    } catch (NumberFormatException ex) {
                        resp.sendRedirect("EmployeeServlet");

                    }

                    Department dep = departmentDao.obtenerDepartment(departmentId);

                    if (dep != null) {
                        req.setAttribute("department", dep);
                        req.setAttribute("listaJefes",employeeDao.listarEmpleados());
                        req.setAttribute("listaLocations",locationDao.lista());
                        view = req.getRequestDispatcher("department/editDepartment.jsp");
                        view.forward(req, resp);
                    } else {
                        resp.sendRedirect("DepartmentServlet");
                    }

                } else {
                    resp.sendRedirect("DepartmentServlet");
                }
                break;
            case "borrar":
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        String action = req.getParameter("action") == null ? "lista" : req.getParameter("action");
        DepartmentDao departmentDao = new DepartmentDao();
    }
}
