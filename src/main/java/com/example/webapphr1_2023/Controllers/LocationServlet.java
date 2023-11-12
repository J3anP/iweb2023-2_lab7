package com.example.webapphr1_2023.Controllers;

import com.example.webapphr1_2023.Beans.Country;
import com.example.webapphr1_2023.Beans.Location;
import com.example.webapphr1_2023.Daos.CountryDao;
import com.example.webapphr1_2023.Daos.LocationDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LocationServlet", urlPatterns = {"/LocationServlet"})
public class LocationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view;

        String action = req.getParameter("action") == null? "listar":req.getParameter("action");


        LocationDao locationDao = new LocationDao();
        CountryDao countryDao = new CountryDao();
        ArrayList<Country> countryList = countryDao.listarCountries();
        int locationId = 0;

        switch (action){

            case "listar":
                ArrayList<Location> locationList = locationDao.listaFull();
                req.setAttribute("locationList", locationList);
                view = req.getRequestDispatcher("location/list.jsp");
                view.forward(req, resp);
                break;
            case "crear":
                req.setAttribute("countryList",countryList);
                view = req.getRequestDispatcher("location/formularioNuevo.jsp");
                view.forward(req, resp);
                break;
            case "editar":
                try{
                    locationId = Integer.parseInt(req.getParameter("id"));
                }catch (NumberFormatException e){
                    resp.sendRedirect("LocationServlet");
                }
                req.setAttribute("countryList",countryList);
                req.setAttribute("location",locationDao.obtenerLocationPorId(locationId));
                view = req.getRequestDispatcher("location/formularioEditar.jsp");
                view.forward(req, resp);
                break;
            case "borrar":
                try{
                    locationId = Integer.parseInt(req.getParameter("id"));
                    locationDao.borrar(locationId);
                    resp.sendRedirect("LocationServlet");
                }catch (NumberFormatException e){
                    resp.sendRedirect("LocationServlet");
                }
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action") == null? "listar":req.getParameter("action");

        LocationDao locationDao = new LocationDao();
        CountryDao countryDao = new CountryDao();

        Location location = new Location();
        boolean validacion = true;

        try{
            location.setLocationId(Integer.parseInt(req.getParameter("locationId")));
        }catch (NumberFormatException e){
            validacion = false;
        }

        if(location.getLocationId() == 0){
            validacion = false;
        }

        location.setStreetAddress(req.getParameter("streetAddress"));
        location.setPostalCode(req.getParameter("postalCode"));
        location.setCity(req.getParameter("city"));
        location.setStateProvince(req.getParameter("stateProvince"));

        if(location.getCity().isEmpty() || location.getCity() == null){
            validacion = false;
        }

        Country country = new Country();

        if(!req.getParameter("countryId").equals("0")){
            country = countryDao.obtenerCountryPorId(req.getParameter("countryId"));

        }else{
            country.setCountryId(req.getParameter("countryId"));
        }

        location.setCountry(country);

        if(!validacion){
            req.setAttribute("countryList",countryDao.listarCountries());

            if(action.equals("crear")){
                req.getRequestDispatcher("location/formularioNuevo.jsp").forward(req,resp);
            }else{
                req.setAttribute("location",locationDao.obtenerLocationPorId(Integer.parseInt(req.getParameter("locationId"))));
                req.getRequestDispatcher("location/formularioEditar.jsp").forward(req,resp);
            }
        }

        if(validacion){

        switch (action){

            case "listar":
                resp.sendRedirect("LocationServlet");
                break;
            case "crear":
                locationDao.crear(location);
                resp.sendRedirect("LocationServlet");
                break;
            case "editar":
                locationDao.editar(location);
                resp.sendRedirect("LocationServlet");
                break;

        }
        }







    }



}