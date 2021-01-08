package legeay.airbnb.logements;

import legeay.airbnb.reservations.Sejour;
import legeay.airbnb.utilisateurs.Hote;

import java.util.List;

/**
 *
 */
public abstract class Logement {
    private static int index = 0;
    private int id;

    private Hote hote;
    private int tarifJournalier;
    private String adresse;
    private int superficie;
    private int nbVoyageursMax;
    private List<Sejour> sejourList;

    /**
     * <p>Logement constructor</p>
     * @param hote
     * @param tarifJournalier
     * @param adresse
     * @param superficie
     * @param nbVoyageursMax
     */
    public Logement(Hote hote, int tarifJournalier, String adresse, int superficie, int nbVoyageursMax) {
        super();

        this.hote = hote;
        this.tarifJournalier = tarifJournalier;
        this.adresse = adresse;
        this.superficie = superficie;
        this.nbVoyageursMax = nbVoyageursMax;

        this.hote.getLogementList().add(this);

        id = ++index;
    }

    public abstract void afficher();

    public abstract int getSuperficieTotale();


    public int getNbVoyageursMax() {
        return nbVoyageursMax;
    }

    public Hote getHote() {
        return hote;
    }

    public int getTarifJournalier() {
        return tarifJournalier;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getSuperficie() {
        return superficie;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName()+" -> {\n" +
                "\thote=" + hote +
                ", tarifJournalier=" + tarifJournalier +
                ", adresse='" + adresse + '\'' +
                ", superficie=" + superficie +
                ", nbVoyageursMax=" + nbVoyageursMax +
                "\n}";
    }
}
