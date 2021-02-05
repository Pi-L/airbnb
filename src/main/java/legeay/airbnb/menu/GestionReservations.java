package legeay.airbnb.menu;

import legeay.airbnb.outils.Constants;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.reservations.Reservation;
import legeay.airbnb.reservations.Sejour;
import legeay.airbnb.utilisateurs.Voyageur;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestionReservations {

    static void listerReservations() {

        Utile.logger.info("");
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Liste des reservations ");
        Utile.info(Constants.SMALL_LINE_SEPARATOR);
        afficherReservationList();
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Saisir une option : ");
        Utile.logger.info("1 : Ajouter un reservation");
        Utile.logger.info("2 : Supprimer un reservation");
        Utile.logger.info("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                if (GestionLogements.getLogementList().isEmpty()){
                    Utile.alert("Vous ne pouvez continuer car il n'y a pas de logements enregistrés !");
                    Utile.warn("Retour menu principal...");
                    Menu.listerMenu();
                    break;
                } else if(Menu.voyageurList.isEmpty()) {
                    Utile.alert("Vous ne pouvez continuer car il n'y a pas de voyageurs enregistrés !");
                    Utile.warn("Retour menu principal...");
                    Menu.listerMenu();
                    break;
                }

                try {
                    ajouterReservation();
                } catch (Exception e) {
                    Utile.alert(e.getMessage());
                } finally {
                    listerReservations();
                }
                break;
            case 2:
                if(getReservationList().isEmpty()) Utile.alert("Il n'y a pas de reservation à supprimer !");
                else supprimerReservation();

                listerReservations();
                break;
            case 3:
                Menu.listerMenu();
                break;
        }
    }

    static List<Reservation> getReservationList() {
        return  Menu.voyageurList.stream()
                .flatMap(voyageur -> voyageur.getReservationList().stream())
                .collect(Collectors.toList());
    }

    static void afficherReservationList() {
        List<Reservation> reservationList = getReservationList();

        if(reservationList.isEmpty()) Utile.warn("Il n'y a pas de reservation à afficher");
        else {
            for (int i = 0; i < reservationList.size(); i++) {
                if (i>0) Utile.logger.info("");
                Utile.logger.info((i + 1) + ". ");
                reservationList.get(i).afficher();
            }
        }
    }

    /**
     *
     * @throws Exception thrown if validity conditions aren't met
     */
    static void ajouterReservation() {
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Ajouter un reservation");
        Utile.info("Liste des voyageurs ");
        Menu.afficherPersonneList(Menu.voyageurList);
        Utile.logger.info("Numéro du voyageur : ");
        int indexVoyageur = Menu.getInputInteger(1, Menu.voyageurList.size()) - 1;
        Voyageur currentVoyageur = Menu.voyageurList.get(indexVoyageur);

        boolean isFinishedAddingSejour = false;
        List<Sejour> currentSejourList = new ArrayList<>();
        Sejour currentSejour;

        do {
            try {
                currentSejour = GestionSejour.ajouterSejour();
                currentSejourList.add(currentSejour);

            } catch (ParseException e) {
                Utile.alert(" Mauvais format de la date d'arrivée. Le sejour n'a pas été enregistré ");
            }

            Utile.logger.info("Souhaitez vous ajouter un autre sejour ? (0: non, 1: oui) : ");
            isFinishedAddingSejour = Menu.getInputInteger(0, 1) == 0;

        } while (!isFinishedAddingSejour);

        if (currentSejourList.isEmpty()) {
            Utile.alert(" Vous n'avez ajouter aucun séjour, aucun réservation n'a été crée ! ");

        }else {
            new Reservation(currentSejourList, currentVoyageur);
        }
    }


    static void supprimerReservation() {
        List<Reservation> reservationList = getReservationList();

        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Supprimer un reservation");
        Utile.logger.info("Choisir un reservation à supprimer :");

        int index = Menu.getInputInteger(1, reservationList.size()) - 1;

        Reservation reservationToRemove = reservationList.get(index);

        reservationToRemove.close();
    }
}
