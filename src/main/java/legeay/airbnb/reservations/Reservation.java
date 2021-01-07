package legeay.airbnb.reservations;

import legeay.airbnb.outils.ConsoleColors;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.utilisateurs.Voyageur;
import java.text.ParseException;
import java.util.List;
import java.util.stream.IntStream;

public class Reservation {
    // In memory latest reservation identifiant
    private static int index = 0;

    private final int identifiant;
    private List<Sejour> sejours;
    private Voyageur voyageur;
    private boolean estValidee;
    private MaDate dateDeReservation;
    private int prixReservation;

    /**
     * <p>Reservation constructor</p>
     *
     * @param sejours
     * @param voyageur
     * @throws ParseException
     */
    public Reservation(List<Sejour> sejours, Voyageur voyageur) throws ParseException {
        this.sejours = sejours;
        this.voyageur = voyageur;
        dateDeReservation = new MaDate();
        estValidee = isValid();

        identifiant = ++index;

        // prixReservation is affected with the sum of all sejour's prices
        prixReservation = this.sejours.stream().reduce(0, (acc, sejour) -> acc + sejour.getTarif(), Integer::sum);
    }

    /**
     * <p>Display all sejours, the reservation validity, and the total cost of the reservation</p>
     */
    public void afficher() {
        System.out.println();

        for (int i = 0; i < sejours.size(); i++) {

            System.out.println(ConsoleColors.CYAN+"SEJOUR N°"+(i+1)+ConsoleColors.RESET+" ------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
            voyageur.afficher();
            System.out.print(" a fait une réservation chez ");

            // try catch needed but useless ... why ?
            try {
                sejours.get(i).afficher();
            } catch (ParseException e) {
                System.out.println("ERROR => Le sejour ne peut etre affiché car la date fournie est invalide");
                System.out.println(e.getMessage());
            }

            System.out.println();
        }

        String message = estValidee ? "VALIDE" : "INVALIDE";

        if(estValidee) System.out.print(ConsoleColors.GREEN_BOLD);
        else System.out.print(ConsoleColors.RED_BOLD);

        System.out.println("#######################################################");
        System.out.println("  "+(estValidee?"#":"")+"###########  La reservation est "+message+"  #########"+(estValidee?"##":""));
        if(estValidee) System.out.println("  ####  Montant reservation n°"+identifiant+" : "+prixReservation+" euros  ####");
        System.out.println("#######################################################");
        System.out.print(ConsoleColors.RESET);
    }

    /**
     * <ul>Check if a reservation is valid by :
     * <li>Checking that sejours date range don't overlap</li>
     * <li>Checking that all individual sejours are valid</li>
     * </ul>
     * @return
     * @throws ParseException
     */
    private boolean isValid() throws ParseException {
        return !isDatesOverlap() && sejours.stream().allMatch(sejour -> sejour.isValid());
    }

    /**
     * <p>Compare all sejours date ranges with each other to determine if there is overlapping</p>
     * @return
     * @throws ParseException
     */
    private boolean isDatesOverlap() {
        long timestampArrivee1;
        long timestampDepart1;
        long timestampArrivee2;
        long timestampDepart2;

        for (int i = 0; i < sejours.size(); i++) {
            for (int j = 0; j < sejours.size(); j++) {
                if(i >= j ) return false;

                timestampArrivee1 = sejours.get(i).getDateArrivee().getTime();
                timestampDepart1 = sejours.get(i).getDateDepart().getTime();
                timestampArrivee2 = sejours.get(j).getDateArrivee().getTime();
                timestampDepart2 = sejours.get(j).getDateDepart().getTime();

                // not used, but help understanding how it work
                // test not(is not overlapping)
               // !(timestampArrivee1 >= timestampDepart2 || timestampDepart1 <= timestampArrivee2)

                // test (is overlapping)
                // a piece of paper is needed to explain it
                if(timestampArrivee2 < timestampDepart1 && timestampArrivee1 < timestampDepart2) return true;
            }
        }

        return false;
    }
}
