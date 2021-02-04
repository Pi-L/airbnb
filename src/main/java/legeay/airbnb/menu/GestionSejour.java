package legeay.airbnb.menu;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.reservations.*;
import legeay.airbnb.outils.Utile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GestionSejour {

    static void listerSejours() {

        System.out.println();
        Utile.info("-------------------------------------");
        Utile.info("Liste des sejours ");
        Utile.info("---------------");
        afficherSejourList();
        Utile.info("-------------------------------------");
        Utile.info("Saisir une option : ");
        System.out.println("1 : Ajouter un sejour");
        System.out.println("2 : Supprimer un sejour");
        System.out.println("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                if (GestionLogements.getLogementList().size() == 0){
                    Utile.alert("Vous ne pouvez continuer car il n'y a pas de logements enregistrés !");
                    Utile.warn("Retour menu principal...");
                    Menu.listerMenu();
                    break;
                }

                try {
                    ajouterSejour();
                } catch (ParseException e) {
                    Utile.alert(" Mauvais format de la date d'arrivée. Le sejour n'a pas été enregistré ");
                } finally {
                    listerSejours();
                }
                break;
            case 2:
                if(getSejourList().size() == 0) Utile.alert("Il n'y a pas de sejour à supprimer !");
                else supprimerSejour();

                listerSejours();
                break;
            case 3:
                Menu.listerMenu();
                break;
        }
    }

    static List<Sejour> getSejourList() {
        return  GestionLogements.getLogementList().stream()
                .flatMap(logement -> logement.getSejourList().stream())
                .collect(Collectors.toList());
    }

    static void afficherSejourList() {
        List<Sejour> sejourList = getSejourList();

        if(sejourList.size() == 0) Utile.warn("Il n'y a pas de sejour à afficher");
        else {
            for (int i = 0; i < sejourList.size(); i++) {
                if (i>0) System.out.println();
                System.out.print((i + 1) + ". ");
                sejourList.get(i).afficher();
            }
        }
    }

    /**
     *
     * @throws ParseException thrown if dateArrivee format not respected
     */
    static Sejour ajouterSejour() throws ParseException {
        List<Logement> logementList = GestionLogements.getLogementList();

        Utile.info("-------------------------------------");
        Utile.info("Ajouter un sejour");
        Utile.info("Liste des logements ");
        GestionLogements.afficherLogementList();

        int indexLogement = -1;

        System.out.println("Numéro du logement : ");
        indexLogement = Menu.getInputInteger(1, logementList.size()) - 1;
        Logement logement = logementList.get(indexLogement);

        System.out.println("Nombre de voyageurs (max: "+logement.getNbVoyageursMax()+") :");
        int nbVoyageurs = Menu.getInputInteger(1, logement.getNbVoyageursMax());

        System.out.println("Nombre de nuits : ");
        int nbNuits = Menu.getInputInteger(1, 30);

        System.out.println("Date Arrivée (Format jj/mm/aaaa) :");
        String dateArriveeString = Menu.getInputString();
        Date dateArrivee = new MaDate(dateArriveeString);

         return SejourFactory.getSejour(dateArrivee, logement, nbNuits, nbVoyageurs);

    }


    static void supprimerSejour() {
        List<Sejour> sejourList = getSejourList();

        Utile.info("-------------------------------------");
        Utile.info("Supprimer un sejour");
        System.out.println("Choisir un sejour à supprimer :");

        int index = Menu.getInputInteger(1, sejourList.size()) - 1;

        Sejour sejourToRemove = sejourList.get(index);
        Logement logement = sejourToRemove.getLogement();

        logement.getSejourList().remove(sejourToRemove);

        Reservation reservation = sejourToRemove.getReservation();

        if(reservation != null) {
            // commenter car TP9
            // reservation.removeASejour(sejourToRemove);
        }
    }
}
