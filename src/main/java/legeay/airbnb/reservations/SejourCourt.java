package legeay.airbnb.reservations;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.MaDate;

import java.text.ParseException;
import java.util.Date;

public class SejourCourt extends Sejour implements ConditionsTarifairesInterface{

    private static final int MIN_NB_NUITS = 1;
    private static final int MAX_NB_NUITS = 5;

    public SejourCourt(Date dateArrivee, int nbNuits, Logement logement, int nbVoyageurs) throws ParseException {
        super(dateArrivee, nbNuits, logement, nbVoyageurs);
    }

    @Override
    public boolean beneficiePromotion() {
        return false;
    }

    @Override
    protected void miseAJourDuTarif() {
        return;
    }

    @Override
    public boolean verificationNombreDeNuits() {
        return getNbNuits() >= MIN_NB_NUITS && getNbNuits() <= MAX_NB_NUITS;
    }

    @Override
    public void afficher() throws ParseException  {
        super.afficher();
        System.out.println("Le prix de ce séjour est de "+getTarif()+"€.");
    }
}
