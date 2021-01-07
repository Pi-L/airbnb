package legeay.airbnb.reservations;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.MaDate;

import java.text.ParseException;

public abstract class Sejour implements SejourInterface{

    private static final int MIN_NB_NUITS = 1;
    private static final int MAX_NB_NUITS = 30;

    private MaDate dateArrivee;
    private MaDate dateDepart;
    private int nbNuits;
    private Logement logement;
    private int nbVoyageurs;

    protected int tarif;

    public Sejour(MaDate dateArrivee, int nbNuits, Logement logement, int nbVoyageurs) throws ParseException {
        this.dateArrivee = dateArrivee;
        this.nbNuits = nbNuits;
        this.logement = logement;
        this.nbVoyageurs = nbVoyageurs;
        dateDepart =  new MaDate(dateArrivee, nbNuits);

        tarif = getNbNuits()  * getLogement().getTarifJournalier();
        // abstract method implemented in sub-classes
        miseAJourDuTarif();
    }

    @Override
    public abstract boolean verificationNombreDeNuits();

    // needed to be here instead of ConditionsTarifairesInterface because it is used in Reservation
    protected abstract void miseAJourDuTarif();



    @Override
    public boolean verficationDateArrivee() {
        return dateArrivee.after(new MaDate());
    }

    @Override
    public boolean verificationNombreDeVoyageurs() {
        return nbVoyageurs <= logement.getNbVoyageursMax();
    }

    public void afficher() throws ParseException {
        logement.afficher();
        System.out.println("Séjour du "+dateArrivee.toString()+" au "+getDateDepart().toString()+" ("+nbNuits+" nuit"+(nbNuits>1?"s":"")+").");
    }

    public boolean isValid() {
        return verficationDateArrivee() && verificationNombreDeNuits() && verificationNombreDeVoyageurs();
    }

    public MaDate getDateArrivee() {
        return dateArrivee;
    }

    public MaDate getDateDepart() {
        return dateDepart;
    }

    protected int getNbNuits() {
        return nbNuits;
    }

    protected Logement getLogement() {
        return logement;
    }

    public int getTarif() {
        return tarif;
    };

    protected void setTarif(int pTarif) {
        tarif = pTarif;
    };



}
