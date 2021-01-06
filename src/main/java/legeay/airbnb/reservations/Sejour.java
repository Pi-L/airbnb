package legeay.airbnb.reservations;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.MaDate;

import java.text.ParseException;

public class Sejour implements SejourInterface{

    private static final int MIN_NB_NUITS = 1;
    private static final int MAX_NB_NUITS = 31;

    private MaDate dateArrivee;
    private MaDate dateDepart;
    private int nbNuits;
    private Logement logement;
    private int nbVoyageurs;

    public Sejour(MaDate dateArrivee, int nbNuits, Logement logement, int nbVoyageurs) throws ParseException {
        this.dateArrivee = dateArrivee;
        this.nbNuits = nbNuits;
        this.logement = logement;
        this.nbVoyageurs = nbVoyageurs;
        dateDepart =  new MaDate(dateArrivee, nbNuits);
    }

    @Override
    public boolean verficationDateArrivee() {
        return dateArrivee.after(new MaDate());
    }

    @Override
    public boolean verificationNombreDeNuits() {
        return nbNuits >= MIN_NB_NUITS && nbNuits <= MAX_NB_NUITS;
    }

    @Override
    public boolean verificationNombreDeVoyageurs() {
        return nbVoyageurs <= logement.getNbVoyageursMax();
    }

    public void afficher() throws ParseException {
        logement.afficher();
        System.out.println("Séjour du "+dateArrivee.toString()+" au "+getDateDepart().toString()+" ("+nbNuits+" nuit"+(nbNuits>1?"s":"")+").");
        System.out.println("Le prix de ce séjour est de "+getPrixSejour()+"€.");
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

    public int getPrixSejour() {
        return nbNuits * logement.getTarifJournalier();
    }

}
