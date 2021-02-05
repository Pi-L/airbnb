package legeay.airbnb.logements;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import legeay.airbnb.Comparaison;
import legeay.airbnb.reservations.Sejour;
import legeay.airbnb.utilisateurs.Hote;
import java.util.*;


/**
 * immutable
 */
public abstract class Logement implements Comparable<Logement>, Comparaison {

    private static int index = 0;
    private final int id;

    private final Hote hote;
    @XStreamAlias("tarifParNuit")
    private final int tarifJournalier;
    private final String adresse;
    private final int superficie;
    private final int nbVoyageursMax;

    // pas final : juste pour l'exo
    private String name;

    private List<Sejour> sejourList;

    /**
     * <p>Logement constructor</p>
     * @param hote
     * @param tarifJournalier
     * @param adresse
     * @param superficie
     * @param nbVoyageursMax
     */
    protected Logement(Hote hote, int tarifJournalier, String adresse, int superficie, int nbVoyageursMax) {
        super();

        this.hote = hote;
        this.tarifJournalier = tarifJournalier;
        this.adresse = adresse;
        this.superficie = superficie;
        this.nbVoyageursMax = nbVoyageursMax;
        this.name = "";

        id = ++index;
        sejourList = new ArrayList<>();

        this.hote.getLogementList().add(this);
    }

    public abstract void afficher();

    public abstract int getSuperficieTotale();

    public int getId() {
        return id;
    }

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

    public List<Sejour> getSejourList() {
        return sejourList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getComparable() {
        return getTarifJournalier();
    }

    @Override
    public int compareTo(Logement logement) {
        return Integer.compare(this.getComparable(), logement.getComparable());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Logement logement = (Logement) o;
        return tarifJournalier == logement.tarifJournalier && superficie == logement.superficie && nbVoyageursMax == logement.nbVoyageursMax && hote.equals(logement.hote) && adresse.equals(logement.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hote, tarifJournalier, adresse, superficie, nbVoyageursMax);
    }
}
