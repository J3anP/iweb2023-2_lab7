<%@page import="java.util.ArrayList" %>
<%@page import="com.example.webapphr1_2023.Beans.Employee" %>
<%@page import="com.example.webapphr1_2023.Beans.Job" %>
<%@page import="com.example.webapphr1_2023.Beans.Department" %>
<%@page import="com.example.webapphr1_2023.Beans.Location" %>

<jsp:useBean id="listaJefes" type="ArrayList<Employee>" scope="request" />
<jsp:useBean id="listaLocations" type="ArrayList<com.example.webapphr1_2023.Beans.Location>" scope="request" />
<jsp:useBean id="lastD" type="com.example.webapphr1_2023.Beans.Department" scope="request" />

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../includes/bootstrap_header.jsp"/>
    <title>Nuevo Departamento</title>
</head>
<body>
<div class='container'>
    <div class="row justify-content-center">
        <form method="POST" action="DepartmentServlet?action=crear" class="col-md-6 col-lg-6">
            <h1 class='mb-3'>Nuevo Departamento</h1>
            <div class="mb-3">
                <input type="hidden" class="form-control form-control-sm" name="department_id" id="department_id" value="<%=lastD.getDepartmentId()+10%>">
            </div>
            <hr>
            <div class="mb-3">
                <label for="department_name">Department Name</label>
                <input type="text" class="form-control form-control-sm" name="department_name" id="department_name">
            </div>

            <div class="mb-3">
                <label for="manager_id">Manager</label>
                <select name="manager_id" class="form-select" id="manager_id">
                    <option value="0">-- Sin jefe --</option>
                    <% for(Employee manager: listaJefes){ %>
                    <option value="<%=manager.getEmployeeId()%>"> <%=manager.getFullName()%> </option>
                    <% } %>
                </select>
            </div>

            <div class="mb-3">
                <label for="location_id">Location</label>
                <select name="location_id" class="form-select" id="location_id">
                    <option value="0">-- Sin lugar --</option>
                    <% for(Location location: listaLocations){ %>
                    <option value="<%=location.getLocationId()%>"> <%=location.getFullAddress()%> </option>
                    <% } %>
                </select>
            </div>
            <a href="<%= request.getContextPath()%>/DepartmentServlet" class="btn btn-danger">Cancelar</a>
            <input type="submit" value="Guardar" class="btn btn-primary"/>
        </form>
    </div>
</div>
<jsp:include page="../includes/bootstrap_footer.jsp"/>
</body>
</html>

