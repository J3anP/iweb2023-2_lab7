<%@ page import="com.example.webapphr1_2023.Beans.Location" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean type="java.util.ArrayList<com.example.webapphr1_2023.Beans.Location>" scope="request" id="locationList"/>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../includes/bootstrap_header.jsp"/>
    <title>Listar Locations</title>
</head>
<body>
<div class='container'>

    <h1 class='mb-3'>Lista de Locations en hr</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%= request.getContextPath()%>">Home</a></li>
            <li class="breadcrumb-item active">Locations</li>
        </ol>
    </nav>
    <a class="btn btn-primary mb-3" href="<%=request.getContextPath()%>/LocationServlet?action=crear">Crear
        Location</a>
    <table class="table">
        <tr>
            <th>Loc ID</th>
            <th>Street Address</th>
            <th>Postal Code</th>
            <th>City</th>
            <th>State Province</th>
            <th>Country id</th>
            <th></th>
            <th></th>
        </tr>
        <%
            for (Location loc : locationList) {
        %>
        <tr>
            <td><%=loc.getLocationId()%>
            </td>
            <td><%=loc.getStreetAddress() == null? "---":loc.getStreetAddress()%>
            </td>
            <td><%=loc.getPostalCode() == null? "---":loc.getPostalCode()%>
            </td>
            <td><%=loc.getCity()%>
            </td>
            <td><%=loc.getStateProvince() == null? "---":loc.getStateProvince()%>
            </td>
            <td><%=loc.getCountry().getCountryId() == null? "---":loc.getCountry().getCountryName()%>
            </td>
            <td>
                <a class="btn btn-primary"
                   href="<%=request.getContextPath()%>/LocationServlet?action=editar&id=<%=loc.getLocationId()%>">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <td>
                <a class="btn btn-danger"
                   href="<%=request.getContextPath()%>/LocationServlet?action=borrar&id=<%=loc.getLocationId()%>">
                    <i class="bi bi-trash3"></i>
                </a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<jsp:include page="../includes/bootstrap_footer.jsp"/>
</body>
</html>


