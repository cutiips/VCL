<%@ page import="ch.hearc.heg.scl.db.dao.VilleDAO" %>
<%@ page import="ch.hearc.heg.scl.business.Ville" %>
<%@ page import="ch.hearc.heg.scl.business.Mesure" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: christop.francill
  Date: 30.05.2024
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% int idVille = Integer.parseInt(request.getParameter("idVille"));
   Ville v = VilleDAO.researchByNumero(idVille);
    List<Mesure> mesures = new ArrayList<Mesure>(v.getMesures().values()); %>
<html>
<head>
    <title>Mesures de <%= v.getNom()%> (<%= v.getPays().getNom() %>)</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1 class="text-center">Mesures de <%= v.getNom()%> (<%= v.getPays().getNom() %>)</h1>
        </div>
    </div>
    <div class="row">
        <div class="col text-center">
            <a href="index.jsp" role="button" class="btn btn-primary"><i class="bi bi-arrow-left"></i> retour à la liste des villes</a>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <table class="table table-striped">
                <thead>
                <th class="text-center">Date</th>
                <th class="text-center">Température</th>
                <th class="text-center">Température ressentie</th>
                <th class="text-center">Pression</th>
                <th class="text-center">Humidité</th>
                <th class="text-center">Vent</th>
                <th>Description</th>
                </thead>
                <tbody>
                <% for(Mesure m : mesures){ %>
                <tr>
                    <td class="text-center"><%= m.getDateTime() %></td>
                    <td class="text-center"><%= m.getTemperature() %></td>
                    <td class="text-center"><%= m.getTemperature_ressentie() %></td>
                    <td class="text-center"><%= m.getPression() %></td>
                    <td class="text-center"><%= m.getHumidite() %></td>
                    <td class="text-center"><%= m.getVent().getVitesse() %> m/s (<%= m.getVent().getDirection() %>°)</td>
                    <td class="text-center"><%= m.getDescription() %></td>
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
