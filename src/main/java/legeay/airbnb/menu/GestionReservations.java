package legeay.airbnb.menu;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.reservations.Reservation;
import legeay.airbnb.reservations.Sejour;
import legeay.airbnb.utilisateurs.Voyageur;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GestionReservations {

    static void listerReservations() {

        System.out.println();
        Utile.info("-------------------------------------");
        Utile.info("Liste des reservations ");
        Utile.info("---------------");
        afficherReservationList();
        Utile.info("-------------------------------------");
        Utile.info("Saisir une option : ");
        System.out.println("1 : Ajouter un reservation");
        System.out.println("2 : Supprimer un reservation");
        System.out.println("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                if (GestionLogements.getLogementList().size() == 0){
                    Utile.alert("Vous ne pouvez continuer car il n'y a pas de logements enregistrés !");
                    Utile.warn("Retour menu principal...");
                    Menu.listerMenu();
                    break;
                } else if(Menu.voyageurList.size() == 0) {
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
                if(getReservationList().size() == 0) Utile.alert("Il n'y a pas de reservation à supprimer !");
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

        if(reservationList.size() == 0) Utile.warn("Il n'y a pas de reservation à afficher");
        else {
            for (int i = 0; i < reservationList.size(); i++) {
                if (i>0) System.out.println();
                System.out.print((i + 1) + ". ");
                reservationList.get(i).afficher();
            }
        }
    }

    /**
     *
     * @throws Exception thrown if validity conditions aren't met
     */
    static void ajouterReservation() throws Exception {
        Utile.info("-------------------------------------");
        Utile.info("Ajouter un reservation");
        Utile.info("Liste des voyageurs ");
        Menu.afficherPersonneList(Menu.voyageurList);
        System.out.println("Numéro du voyageur : ");
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

            System.out.println("Souhaitez vous ajouter un autre sejour ? (0: non, 1: oui) : ");
            isFinishedAddingSejour = Menu.getInputInteger(0, 1) == 0;

        } while (!isFinishedAddingSejour);

        if (currentSejourList.size() == 0) {
            Utile.alert(" Vous n'avez ajouter aucun séjour, aucun réservation n'a été crée ! ");

        }else {
            new Reservation(currentSejourList, currentVoyageur);
        }
    }


    static void supprimerReservation() {
        List<Reservation> reservationList = getReservationList();

        Utile.info("-------------------------------------");
        Utile.info("Supprimer un reservation");
        System.out.println("Choisir un reservation à supprimer :");

        int index = Menu.getInputInteger(1, reservationList.size()) - 1;

        Reservation reservationToRemove = reservationList.get(index);

        Voyageur voyageur = reservationToRemove.getVoyageur();
        voyageur.getReservationList().remove(reservationToRemove);

        reservationToRemove.removeAllSejours();
        reservationToRemove = null;
    }
}
