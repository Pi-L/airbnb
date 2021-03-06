package legeay.airbnb.reservations;

import legeay.airbnb.AffichableInterface;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.ConsoleColors;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.utilisateurs.Voyageur;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Reservation implements AffichableInterface {

//    private class ReservationOperationImpl implements ReservationOperations {
//        public void setEstValidee(boolean isValid) {
//            Reservation.this.estValidee = isValid;
//        }
//    }
// eventuellement faire un getter pour qui a le droit d'utiliser ça ... plop plop
//private ReservationOperations reservationOperations = new ReservationOperationImpl();

    // In memory latest reservation identifiant
    private static int index = 0;

    private int id;
    private final List<Sejour> sejours = new ArrayList<>();
    private final Voyageur voyageur;
    private boolean estValidee = false;
    private int prixReservation;
    private MaDate dateDeReservation;

    /**
     * <p>Reservation constructor</p>
     *
     * @param sejours
     * @param voyageur
     */
    public Reservation(List<Sejour> sejours, Voyageur voyageur) throws IllegalArgumentException {
        if(sejours == null || sejours.isEmpty()) throw new IllegalArgumentException("Param sejours ne peut être null ou vide");
        if(voyageur == null) throw new IllegalArgumentException("Param voyageur ne peut être null");

        sejours.forEach(this::addASejour);

        this.voyageur = voyageur;
        dateDeReservation = new MaDate();

        setEstValidee();

        if(!estValidee) {
            removeAllSejours();
            throw new IllegalArgumentException(" La reservation ne peut être créée car elle ne remplie pas les conditions de validitées ");
        }

        id = ++index;

        voyageur.getReservationList().add(this);
        try {
            writeToFile("reservation" +id+".txt");
        } catch(IOException e) {
            Utile.alert("La reservation n'a pas pus etre enregistré dans le fichier :/");
            Utile.logger.info("Erreur --> " + e.getMessage());
        }
    }

    /**
     * <p>Display all sejours, the reservation validity, and the total cost of the reservation</p>
     */
    public void afficher() {
        Utile.logger.info("");

        for (int i = 0; i < sejours.size(); i++) {

            Utile.logger.info(ConsoleColors.CYAN+"SEJOUR N°"+(i+1)+ConsoleColors.RESET+" ------------------------------------------------------------------------------------------------------------------------------");
            Utile.logger.info("-----------------------------------------------------------------------------------------------------------------------------------------");
            voyageur.afficher();
            Utile.logger.info(" a fait une réservation chez ");

            sejours.get(i).afficher();

            Utile.logger.info("");
        }

        String message = estValidee ? "VALIDE" : "INVALIDE";

        if(estValidee) Utile.logger.info(ConsoleColors.GREEN_BOLD);
        else Utile.logger.info(ConsoleColors.RED_BOLD);

        Utile.logger.info("#######################################################");
        Utile.logger.info("  "+(estValidee?"#":"")+"###########  La reservation est "+message+"  #########"+(estValidee?"##":""));
        if(estValidee) Utile.logger.info("  ####  Montant reservation n°"+id+" : "+prixReservation+" euros  ####");
        Utile.logger.info("#######################################################");
        Utile.logger.info(ConsoleColors.RESET);
    }

    /**
     * <ul>Check if a reservation is valid by :
     * <li>Checking that sejours date range don't overlap</li>
     * <li>Checking that all individual sejours are valid</li>
     * </ul>
     * @return
     */
    private boolean isValid()  {
        return !isDatesOverlap() && sejours.stream().allMatch(Sejour::isValid);
    }

    private Date getPremiereDateArrivee() {
        Date premiereDate = null;

        for (int i = 0; i < sejours.size(); i++) {
            if(premiereDate == null) premiereDate = sejours.get(i).getDateArrivee();
            else if(sejours.get(i).getDateArrivee().before(premiereDate)) {
                premiereDate = sejours.get(i).getDateArrivee();
            }
        }

        return premiereDate;
    }

    private int getNbNuitsTotal() {
        return this.sejours.stream().reduce(0, (acc, sejour) -> acc + sejour.getNbNuits(), Integer::sum);
    }

    /**
     * <p>Compare all sejours date ranges with each other to determine if there is overlapping</p>
     * @return
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

    private void setPrixReservation() {
        // prixReservation is affected with the sum of all sejour's prices
        prixReservation = this.sejours.stream().reduce(0, (acc, sejour) -> acc + sejour.getTarif(), Integer::sum);
    }

    public int getId() {
        return id;
    }

    public Voyageur getVoyageur() {
        return new Voyageur(voyageur);
    }

    public List<Sejour> getSejours() {
        return sejours.stream().map(sejour -> (Sejour) sejour.clone())
                .collect(Collectors.toList());
    }

    private void addASejour(Sejour sejour) {
        sejours.add((Sejour) sejour.clone());
        setPrixReservation();
        sejour.setReservation(this);
    }

    private void removeASejour(Sejour sejour) {
        sejour.getLogement().getSejourList().remove(sejour);
        sejours.remove(sejour);
        setPrixReservation();
    }

    private void removeAllSejours() {
        sejours.forEach(sejour -> {
            sejour.getLogement().getSejourList().remove(sejour);
            sejour.setReservation(null);
        });
        sejours.clear();
        setPrixReservation();
    }

    public void setEstValidee() {
        this.estValidee = isValid();
    }

    public void writeToFile(String fileName) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {

            writer.append("Numero du voyageur : " + voyageur.getId());
            writer.newLine();

            List<Logement> distinctLogements = sejours.stream()
                    .map(Sejour::getLogement)
                    .distinct()
                    .collect(Collectors.toList());

            for (int i = 0; i < distinctLogements.size(); i++) {
                writer.append("Numero du logement : " + distinctLogements.get(i).getId());
                writer.newLine();
            }

            writer.append("Date d'arrivée (JJ/MM/AA) : " + getPremiereDateArrivee());
            writer.newLine();
            writer.append("Nombre de nuits au total : " + getNbNuitsTotal());
            writer.newLine();
            writer.append("Nombre de personnes : " + sejours.get(0).getNbVoyageurs()); // oui je triche là :p
        }
    }

    public void close() {
        voyageur.getReservationList().remove(this);
        removeAllSejours();
    }
}
