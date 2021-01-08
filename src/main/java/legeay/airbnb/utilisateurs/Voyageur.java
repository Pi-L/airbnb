package legeay.airbnb.utilisateurs;

import legeay.airbnb.reservations.Reservation;
import java.util.List;

public class Voyageur extends Personne {

    private List<Reservation> reservationList;

    public Voyageur(String prenom, String nom, int age) {
        super(prenom, nom, age);
    }
}
