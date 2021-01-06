package legeay.airbnb;

import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.reservations.Reservation;
import legeay.airbnb.reservations.Sejour;
import legeay.airbnb.utilisateurs.Hote;
import legeay.airbnb.utilisateurs.Personne;
import legeay.airbnb.utilisateurs.Voyageur;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {

        Hote toto = new Hote("Goku", "San", 12, 10);
        Voyageur ken = new Voyageur("Ken", "Du Haut Couteau", 12);

        Maison maison1 = new Maison(toto, 12, "12 rue des douzaine, 37000 TOURS", 212, 12, 5, false);
        Appartement appartement1 = new Appartement(toto, 60, "13 rue de la douzaine, 37000 TOURS", 12, 3, 0, 2);

        Sejour sejour1 = new Sejour(new MaDate("11/01/2021"), 3, maison1, 12);
        Sejour sejour2 = new Sejour(new MaDate(10,01,2021), 1, appartement1, 2);

        List<Sejour> sejourList = new ArrayList<>();
        sejourList.add(sejour1);
        sejourList.add(sejour2);

        Reservation resa1 = new Reservation(sejourList, ken);

        resa1.afficher();

    }
}
