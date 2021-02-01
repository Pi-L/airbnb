package legeay.airbnb.logements;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.reservations.Sejour;
import legeay.airbnb.utilisateurs.Hote;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public abstract class Logement {
    private static int index = 0;
    private int id;

    private Hote hote;
    @XStreamAlias("tarifParNuit")
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

    public void setHote(Hote hote) {
        this.hote = hote;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logement logement = (Logement) o;
        return tarifJournalier == logement.tarifJournalier && superficie == logement.superficie && nbVoyageursMax == logement.nbVoyageursMax && hote.equals(logement.hote) && adresse.equals(logement.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hote, tarifJournalier, adresse, superficie, nbVoyageursMax);
    }
}
