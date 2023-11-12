<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.webapphr1_2023.Beans.*" %>
<jsp:useBean id="location" type="com.example.webapphr1_2023.Beans.Location" scope="request" />
<jsp:useBean id="countryList" type="ArrayList<com.example.webapphr1_2023.Beans.Country>" scope="request" />
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../includes/bootstrap_header.jsp"/>
    <title>Editar location</title>
</head>
<body>
<div class='container'>
    <div class="row justify-content-center">
        <form method="POST" action="LocationServlet?action=editar" class="col-md-6 col-lg-6">
            <h1 class='mb-3'>Editar location</h1>
            <hr>
            <div class="mb-3">
                <input type="text" value = "<%=location.getLocationId()%>"  class="form-control form-control-sm" name="locationId" id="first_name" hidden>
            </div>
            <div class="mb-3">
                <label for="last_name">Street Address</label>
                <input type="text" value = "<%=location.getStreetAddress()==null?"":location.getStreetAddress()%>" class="form-control form-control-sm" name="streetAddress" id="last_name">
            </div>
            <div class="mb-3">
                <label for="email">Postal code</label>
                <input type="text" value = "<%=location.getPostalCode()==null?"":location.getPostalCode()%>" class="form-control form-control-sm" name="postalCode" id="email">
            </div>
            <div class="mb-3">
                <label for="email">City</label>
                <input type="text" value = "<%=location.getCity()%>" class="form-control form-control-sm" name="city" id="email2">
            </div>
            <div class="mb-3">
                <label for="phone">State province</label>
                <input type="text" value = "<%=location.getStateProvince()==null?"":location.getStateProvince()%>" class="form-control form-control-sm" name="stateProvince" id="phone">
            </div>
            <div class="mb-3">
                <label for="job_id">Country ID</label>
                <select name="countryId" class="form-select" id="job_id">
                    <option value="0" <% if(location.getCountry() == null){%> selected <%}%>>-- Sin país ---</option>
                    <% for(Country country: countryList ){ %>
                    <option <%if(location.getCountry() != null){ if(country.getCountryId().equals(location.getCountry().getCountryId())){%> selected <%}}%> value="<%=country.getCountryId()%>"> <%=country.getCountryName()%> </option>
                    <% } %>
                </select>
            </div>
            <a href="<%= request.getContextPath()%>/LocationServlet" class="btn btn-danger">Cancelar</a>
            <input type="submit" value="Guardar" class="btn btn-primary"/>
        </form>
    </div>
</div>
<jsp:include page="../includes/bootstrap_footer.jsp"/>
</body>
</html>

