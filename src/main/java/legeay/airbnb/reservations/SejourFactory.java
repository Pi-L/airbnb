package legeay.airbnb.reservations;

import legeay.airbnb.logements.Logement;

import java.util.Date;

public class SejourFactory {

    public static Sejour getSejour(Date dateArrivee, Logement logement, int nbNuits, int nbVoyageurs) {
        if(nbNuits > 5) {
            return new SejourLong(dateArrivee, nbNuits, logement, nbVoyageurs);
        } else {
            return new SejourCourt(dateArrivee, nbNuits, logement, nbVoyageurs);
        }
    }

}
