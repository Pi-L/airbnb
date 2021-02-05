package legeay.airbnb.utilisateurs;

import legeay.airbnb.reservations.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Voyageur extends Personne {
    private static int index = 0;

    private List<Reservation> reservationList;

    public Voyageur(String prenom, String nom, int age) {

        super(prenom, nom, age);

        reservationList = new ArrayList<>();
        id = ++index;
    }

    public Voyageur(Voyageur voyageur) {
        this(voyageur.getPrenom(), voyageur.getNom(), voyageur.getAge());
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Voyageur voyageur = (Voyageur) o;
        return Objects.equals(reservationList, voyageur.reservationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reservationList);
    }
}
