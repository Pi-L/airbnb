package legeay.airbnb.reservations;

import legeay.airbnb.logements.Logement;

import java.util.Date;

/**
 * On evite la repetition de code
 * et on centralise
 */
public final class SejourFactory {

    private static final int LIMITE_NUIT = 5;

    /**
     * On bloque la capacité de faire un new
     */
    private SejourFactory() {}

    /**
     *
     * @param dateArrivee
     * @param logement
     * @param nbNuits
     * @param nbVoyageurs
     * @return new Sejour
     */
    public static Sejour getSejour(Date dateArrivee, Logement logement, int nbNuits, int nbVoyageurs) {

        if(nbNuits > LIMITE_NUIT) {
            return new SejourLong(dateArrivee, nbNuits, logement, nbVoyageurs);
        } else {
            return new SejourCourt(dateArrivee, nbNuits, logement, nbVoyageurs);
        }
    }

}
