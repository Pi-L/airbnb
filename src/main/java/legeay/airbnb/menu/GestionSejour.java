package legeay.airbnb.menu;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.Constants;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.reservations.*;
import legeay.airbnb.outils.Utile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GestionSejour {

    private GestionSejour() {}

    static void listerSejours() {

        Utile.logger.info("");
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Liste des sejours ");
        Utile.info(Constants.SMALL_LINE_SEPARATOR);
        afficherSejourList();
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Saisir une option : ");
        Utile.logger.info("1 : Ajouter un sejour");
        Utile.logger.info("2 : Supprimer un sejour");
        Utile.logger.info("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                if (GestionLogements.getLogementList().isEmpty()){
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
                if(getSejourList().isEmpty()) Utile.alert("Il n'y a pas de sejour à supprimer !");
                else supprimerSejour();

                listerSejours();
                break;
            case 3:
                Menu.listerMenu();
                break;
            default:
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

        if(sejourList.isEmpty()) Utile.warn("Il n'y a pas de sejour à afficher");
        else {
            for (int i = 0; i < sejourList.size(); i++) {
                if (i>0) Utile.logger.info("");
                Utile.logger.info((i + 1) + ". ");
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

        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Ajouter un sejour");
        Utile.info("Liste des logements ");
        GestionLogements.afficherLogementList();

        int indexLogement = -1;

        Utile.logger.info("Numéro du logement : ");
        indexLogement = Menu.getInputInteger(1, logementList.size()) - 1;
        Logement logement = logementList.get(indexLogement);

        Utile.logger.info("Nombre de voyageurs (max: "+logement.getNbVoyageursMax()+") :");
        int nbVoyageurs = Menu.getInputInteger(1, logement.getNbVoyageursMax());

        Utile.logger.info("Nombre de nuits : ");
        int nbNuits = Menu.getInputInteger(1, 30);

        Utile.logger.info("Date Arrivée (Format jj/mm/aaaa) :");
        String dateArriveeString = Menu.getInputString();
        Date dateArrivee = new MaDate(dateArriveeString);

         return SejourFactory.getSejour(dateArrivee, logement, nbNuits, nbVoyageurs);

    }


    static void supprimerSejour() {
        List<Sejour> sejourList = getSejourList();

        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Supprimer un sejour");
        Utile.logger.info("Choisir un sejour à supprimer :");

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
