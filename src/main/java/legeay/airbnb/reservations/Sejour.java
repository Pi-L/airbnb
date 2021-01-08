package legeay.airbnb.reservations;

import legeay.airbnb.AffichableInterface;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.MaDate;
import java.util.Date;

public abstract class Sejour implements SejourInterface, AffichableInterface {

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
        System.out.println("Séjour du "+dateArrivee.toString()+" au "+getDateDepart().toString()+" ("+nbNuits+" nuit"+(nbNuits>1?"s":"")+").");
    }

    public boolean isValid() {
        return verficationDateArrivee() && verificationNombreDeNuits() && verificationNombreDeVoyageurs();
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    protected int getNbNuits() {
        return nbNuits;
    }

    protected Logement getLogement() {
        return logement;
    }

    // needed to be here instead of ConditionsTarifairesInterface because it is used in Reservation
    public int getTarif() {
        return tarif;
    };

    protected void setTarif(int pTarif) {
        tarif = pTarif;
    };



}
