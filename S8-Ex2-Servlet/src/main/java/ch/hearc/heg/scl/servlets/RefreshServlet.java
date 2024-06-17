package ch.hearc.heg.scl.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import ch.hearc.heg.scl.business.Mesure;
import ch.hearc.heg.scl.business.Ville;
import ch.hearc.heg.scl.db.dao.MesureDAO;
import ch.hearc.heg.scl.db.dao.VilleDAO;
import ch.hearc.heg.scl.web.OpenWeatherMapService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "refreshServlet", value = "/refresh")
public class RefreshServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameter("idCity") != null){
            refreshOne(request,response);
        }else{
            refreshAll(request,response);
        }
    }

    private void refreshOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idCity = Integer.parseInt(request.getParameter("idCity"));

        Ville v = VilleDAO.researchByNumero(idCity);

        traitementWebandDB(v);

        response.sendRedirect("city.jsp?idVille=" + idCity);
    }

    private void refreshAll(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List<Ville> villeList = new ArrayList<Ville>(VilleDAO.list().values());

        for(Ville v : villeList){
            traitementWebandDB(v);
        }

        response.sendRedirect("index.jsp");
    }

    private int traitementWebandDB(Ville v){
        //Traitement
        Ville WebResponse = OpenWeatherMapService.getData(v);

        // Affichage de la réponse
        System.out.println(WebResponse);

        // Vérification de la présence de la ville dans la Base de données
        Ville villeDB = VilleDAO.research(WebResponse.getOpenWeatherMapId());
        int numeroVille = -1;
        // Si la ville n'est pas dans la base de données, on l'ajoute
        if(villeDB == null){
            numeroVille = VilleDAO.create(WebResponse);
        }else{
            numeroVille = villeDB.getNumero();
            System.out.println("La ville est déjà dans la base de données, inutile de l'ajouter");
        }

        // Est-ce que la mesure existe déjà dans la base de données ?
        Mesure m = MesureDAO.research(numeroVille, WebResponse.getDerniereMesure().getDateTime());

        // Si la mesure n'est pas dans la base de données, on l'ajoute
        if(m == null){
            MesureDAO.create(WebResponse.getDerniereMesure(), numeroVille);
        }else{
            System.out.println("La mesure est déjà dans la base de données, inutile de l'ajouter");
        }

        return numeroVille;
    }

}