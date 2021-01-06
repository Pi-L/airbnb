package legeay.airbnb.reservations;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.MaDate;

import java.text.ParseException;

public class Sejour {


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

    public void afficher() throws ParseException {
        logement.afficher();
        System.out.println("Séjour du "+dateArrivee.toString()+" au "+getDateDepart().toString()+" ("+nbNuits+" nuit"+(nbNuits>1?"s":"")+").");
        System.out.println("Le prix de ce séjour est de "+getPrixSejour()+"€.");
    }

    public boolean isValid() {
        return nbVoyageurs <= logement.getNbVoyageursMax();
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
