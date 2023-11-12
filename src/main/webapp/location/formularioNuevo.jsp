<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.webapphr1_2023.Beans.*" %>
<jsp:useBean id="countryList" type="ArrayList<com.example.webapphr1_2023.Beans.Country>" scope="request" />
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../includes/bootstrap_header.jsp"/>
    <title>Crear location</title>
</head>
<body>
<div class='container'>
    <div class="row justify-content-center">
        <form method="POST" action="LocationServlet?action=crear" class="col-md-6 col-lg-6">
            <h1 class='mb-3'>Nueva location</h1>
            <hr>
            <div class="mb-3">
                <label for="first_name">Loc ID</label>
                <input type="text" class="form-control form-control-sm" name="locationId" id="first_name">
            </div>
            <div class="mb-3">
                <label for="last_name">Street Address</label>
                <input type="text" class="form-control form-control-sm" name="streetAddress" id="last_name">
            </div>
            <div class="mb-3">
                <label for="email">Postal code</label>
                <input type="text" class="form-control form-control-sm" name="postalCode" id="email">
            </div>
            <div class="mb-3">
                <label for="email">City</label>
                <input type="text" class="form-control form-control-sm" name="city" id="email2">
            </div>
            <div class="mb-3">
                <label for="phone">State province</label>
                <input type="text" class="form-control form-control-sm" name="stateProvince" id="phone">
            </div>
            <div class="mb-3">
                <label for="job_id">Country ID</label>
                <select name="countryId" class="form-select" id="job_id">
                    <option value="0">-- Sin país ---</option>
                    <% for(Country country: countryList ){ %>
                    <option value="<%=country.getCountryId()%>"> <%=country.getCountryName()%> </option>
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
