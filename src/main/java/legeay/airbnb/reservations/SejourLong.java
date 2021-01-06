package legeay.airbnb.reservations;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.MaDate;
import java.text.ParseException;

public class SejourLong extends Sejour  implements ConditionsTarifairesInterface {

    private static final int MIN_NB_NUITS = 6;
    private static final int MAX_NB_NUITS = 30;
    private static final int PROMOTION_EN_POURCENTAGE = 20;

    private int promotion;

    public SejourLong(MaDate dateArrivee, int nbNuits, Logement logement, int nbVoyageurs) throws ParseException {
        // miseAJourDuTarif() implemented here but called in super()
        super(dateArrivee, nbNuits, logement, nbVoyageurs);
    }

    @Override
    protected void miseAJourDuTarif() {
        promotion = getTarif() * PROMOTION_EN_POURCENTAGE / 100;
        setTarif(tarif - promotion);
    }

    @Override
    public boolean beneficiePromotion() {
        return true;
    }

    @Override
    public boolean verificationNombreDeNuits() {
        return getNbNuits()  >= MIN_NB_NUITS && getNbNuits()  <= MAX_NB_NUITS;
    }

    @Override
    public void afficher() throws ParseException  {
        super.afficher();
        System.out.println("Le prix de ce séjour est de "+getTarif()+"€ ("+promotion+"€ de promotion).");
    }

}
