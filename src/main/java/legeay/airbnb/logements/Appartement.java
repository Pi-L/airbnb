package legeay.airbnb.logements;

import legeay.airbnb.AffichableInterface;
import legeay.airbnb.outils.ConsoleColors;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.utilisateurs.Hote;

import java.util.Objects;

/**
 * <p></p>
 */
public class Appartement extends Logement implements AffichableInterface {

    private int numeroEtage;
    private int superficieBalcon;

    /**
     * <p>Appartement Constructor</p>
     * @param hote
     * @param tarifJournalier
     * @param adresse
     * @param superficie
     * @param nbVoyageursMax
     * @param numeroEtage
     * @param superficieBalcon
     */
    public Appartement(Hote hote, int tarifJournalier, String adresse, int superficie, int nbVoyageursMax, int numeroEtage, int superficieBalcon) {
        super(hote, tarifJournalier, adresse, superficie, nbVoyageursMax);
        this.numeroEtage = numeroEtage;
        this.superficieBalcon = superficieBalcon;
    }

    public Appartement(Hote hote, Appartement appartement) {
        this(hote, appartement.getTarifJournalier(), appartement.getAdresse(), appartement.getSuperficie(), appartement.getNbVoyageursMax(), appartement.numeroEtage, appartement.superficieBalcon);
    }

    /**
     * <p>Print in terminal informations relative to an appartment</p>
     */
    @Override
    public void afficher() {
        getHote().afficher();
        System.out.println("Le logement est un appartement situé "+ConsoleColors.BLACK_BACKGROUND_BRIGHT+getAdresse()+" au "+etageToString()+ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE_BOLD+"Superficie : "+ConsoleColors.RESET+getSuperficie()+"m2");
        System.out.println(ConsoleColors.PURPLE_BOLD+"Balcon : "+ConsoleColors.RESET+(superficieBalcon>0 ? "Oui ("+superficieBalcon+"m2)" : "Non"));
    }

    /**
     *
     * @return global appartment surface
     */
    @Override
    public int getSuperficieTotale() {
        return getSuperficie() + superficieBalcon;
    }

    /**
     *
     * @return french formatted string from a numeric floor
     */
    private String etageToString() {
        if(numeroEtage == 1) return "1er étage";
        else if(numeroEtage > 1 ) return numeroEtage+"ème étage";

        return "rez-de-chaussée";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Appartement that = (Appartement) o;
        return numeroEtage == that.numeroEtage && superficieBalcon == that.superficieBalcon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeroEtage, superficieBalcon);
    }
}
