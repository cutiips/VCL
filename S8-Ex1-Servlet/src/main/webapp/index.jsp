<%@ page import="java.util.List" %>
<%@ page import="ch.hearc.heg.scl.business.Ville" %>
<%@ page import="ch.hearc.heg.scl.db.dao.VilleDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Villes</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
<% List<Ville> villes = new ArrayList<>(VilleDAO.list().values()); %>
<div class="container">
    <div class="row">
        <div class="col">
            <h1 class="text-center">Villes</h1>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <table class="table table-striped">
                <thead>
                    <th>&nbsp;</th>
                    <th class="text-center">OpenWeatherMapId</th>
                    <th>Nom</th>
                    <th>Pays</th>
                    <th class="text-center">Latitude</th>
                    <th class="text-center">Longitude</th>
                    <th class="text-center">Timezone</th>
                </thead>
                <tbody>
                    <% for(Ville v : villes){ %>
                        <tr>
                            <td class="text-center"><a href="city.jsp?idVille=<%= v.getNumero() %>"><i class="bi bi-eye"></i></a></td>
                            <td class="text-center"><%= v.getOpenWeatherMapId() %></td>
                            <td><%= v.getNom() %></td>
                            <td><%= v.getPays().getNom() %> (<%= v.getPays().getCode() %>)</td>
                            <td class="text-center"><%= v.getLatitude() %></td>
                            <td class="text-center"><%= v.getLongitude() %></td>
                            <td class="text-center"><%= v.getTimezone()/3600 %>h</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>