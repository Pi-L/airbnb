package legeay.airbnb.reservations;

import legeay.airbnb.outils.ConsoleColors;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.utilisateurs.Voyageur;

import java.text.ParseException;
import java.util.List;
import java.util.stream.IntStream;

public class Reservation {
    private static int index = 0;

    private int identifiant;
    private List<Sejour> sejours;
    private Voyageur voyageur;
    private boolean estValidee;
    private MaDate dateDeReservation;
    private int prixReservation;


    public Reservation(List<Sejour> sejours, Voyageur voyageur) throws ParseException {
        this.sejours = sejours;
        this.voyageur = voyageur;
        dateDeReservation = new MaDate();
        estValidee = isValid();
        identifiant = ++index;

        prixReservation = this.sejours.stream().reduce(0, (acc, sejour) -> acc + sejour.getTarif(), Integer::sum);
    }

    public void afficher() {
        System.out.println();

        int SejoursListLength = sejours.size();
        IntStream.range(0, SejoursListLength).forEach(index -> {
            System.out.println(ConsoleColors.CYAN+"SEJOUR N°"+(index+1)+ConsoleColors.RESET+" ------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
            voyageur.afficher();
            System.out.print(" a fait une réservation chez ");
            try {
                sejours.get(index).afficher();
            } catch (ParseException e) {
                System.out.println("ERROR => Le sejour ne peut etre affiché car la date fournie est invalide");
                System.out.println(e.getMessage());
            }
            System.out.println();
        });

        String message = estValidee ? "VALIDE" : "INVALIDE";

        if(estValidee) System.out.print(ConsoleColors.GREEN_BOLD);
        else System.out.print(ConsoleColors.RED_BOLD);

        System.out.println("#######################################################");
        System.out.println("  "+(estValidee?"#":"")+"###########  La reservation est "+message+"  #########"+(estValidee?"##":""));
        if(estValidee) System.out.println("  ####  Montant reservation n°"+identifiant+" : "+prixReservation+" euros  ####");
        System.out.println("#######################################################");
        System.out.print(ConsoleColors.RESET);
    }

    private boolean isValid() throws ParseException {
        return !isDatesOverlap() &&
                sejours.stream().allMatch(sejour ->
                    sejour.isValid() && dateDeReservation.before(sejour.getDateArrivee())
                );
    }

    private boolean isDatesOverlap() throws ParseException {
        long timestampArrivee1;
        long timestampDepart1;
        long timestampArrivee2;
        long timestampDepart2;

        for (int i = 0; i < sejours.size(); i++) {
            for (int j = 0; j < sejours.size(); j++) {
                if(i >= j ) continue;

                timestampArrivee1 = sejours.get(i).getDateArrivee().getTime();
                timestampDepart1 = sejours.get(i).getDateDepart().getTime();
                timestampArrivee2 = sejours.get(j).getDateArrivee().getTime();
                timestampDepart2 = sejours.get(j).getDateDepart().getTime();

               // !(timestampArrivee1 >= timestampDepart2 || timestampDepart1 <= timestampArrivee2)

                if(timestampArrivee2 < timestampDepart1 && timestampArrivee1 < timestampDepart2) return true;
            }
        }

        return false;
    }
}
