package legeay.airbnb.utilisateurs;

import legeay.airbnb.reservations.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Voyageur extends Personne {
    private static int index = 0;

    private List<Reservation> reservationList;

    public Voyageur(String prenom, String nom, int age) {

        super(prenom, nom, age);

        reservationList = new ArrayList<>();
        id = ++index;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

}
