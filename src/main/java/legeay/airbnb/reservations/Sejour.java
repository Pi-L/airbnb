package legeay.airbnb.reservations;

import legeay.airbnb.AffichableInterface;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.outils.Utile;

import java.util.Date;

public abstract class Sejour implements SejourInterface, AffichableInterface, Cloneable {

    private static final int MIN_NB_NUITS = 1;
    private static final int MAX_NB_NUITS = 30;

    private Date dateArrivee;
    private Date dateDepart;
    private int nbNuits;
    private Logement logement;
    private int nbVoyageurs;
    private Reservation reservation;

    private int tarif;

    public Sejour(Date dateArrivee, int nbNuits, Logement logement, int nbVoyageurs) {
        this.dateArrivee = dateArrivee;
        this.nbNuits = nbNuits;
        this.logement = logement;
        this.nbVoyageurs = nbVoyageurs;
        dateDepart =  new MaDate(dateArrivee, nbNuits);

        // abstract method implemented in sub-classes
        miseAJourDuTarif();

        this.logement.getSejourList().add(this);
    }

    /**
     * Init tarif
     */
    protected abstract void miseAJourDuTarif();

    @Override
    public boolean verficationDateArrivee() {
        return dateArrivee.after(new Date());
    }

    @Override
    public boolean verificationNombreDeVoyageurs() {
        return nbVoyageurs <= logement.getNbVoyageursMax();
    }

    public void afficher() {
        logement.afficher();
        Utile.warn("Sejour pour la reservation : "+(reservation != null ? "n°"+reservation.getId(): "reservation non créée"));
        System.out.println("Séjour du "+dateArrivee.toString()+" au "+getDateDepart().toString()+" ("+nbNuits+" nuit"+(nbNuits>1?"s":"")+").");
    }

    public boolean isValid() {
        return verficationDateArrivee() && verificationNombreDeNuits() && verificationNombreDeVoyageurs();
    }

    public Date getDateArrivee() {
        return new MaDate(dateArrivee);
    }

    public Date getDateDepart() {
        return new MaDate(dateDepart);
    }

    protected int getNbNuits() {
        return nbNuits;
    }

    public Logement getLogement() {
        return logement;
    }

    public int getNbVoyageurs() {
        return nbVoyageurs;
    }

    public Reservation getReservation() { return  reservation; }

    // needed to be here instead of ConditionsTarifairesInterface because it is used in Reservation
    public int getTarif() {
        return tarif;
    };

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = new MaDate(dateArrivee);
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = new MaDate(dateDepart);
    }

    public void setNbNuits(int nbNuits) {
        this.nbNuits = nbNuits;
    }

    // pas besoin de faire un new ou clone car logement est immuable
    public void setLogement(Logement logement) throws IllegalArgumentException {
        if(logement == null || logement.getNbVoyageursMax() < nbVoyageurs) throw new IllegalArgumentException("arg invalide pour setLogement");

        this.logement = logement;
        miseAJourDuTarif();
    }

    public void setNbVoyageurs(int nbVoyageurs) throws IllegalArgumentException {
        if(nbVoyageurs == 0 || logement.getNbVoyageursMax() < nbVoyageurs) throw new IllegalArgumentException("arg invalide pour setNbVoyageurs");
        this.nbVoyageurs = nbVoyageurs;
    }

    protected void setTarif(int pTarif) {
        tarif = pTarif;
    };

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    protected Object clone() {
        Sejour sejour = null;
        try {
            sejour = (Sejour) super.clone();
        } catch(CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons l'interface Cloneable
            cnse.printStackTrace(System.err);
        }

        return sejour;
    }
}
