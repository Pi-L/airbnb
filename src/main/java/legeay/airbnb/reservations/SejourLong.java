package legeay.airbnb.reservations;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.Utile;
import java.util.Date;

public class SejourLong extends Sejour  implements ConditionsTarifairesInterface {

    private static final int MIN_NB_NUITS = 6;
    private static final int MAX_NB_NUITS = 30;
    private static final int PROMOTION_EN_POURCENTAGE = 20;

    private int promotion;

    public SejourLong(Date dateArrivee, int nbNuits, Logement logement, int nbVoyageurs) {
        // miseAJourDuTarif() implemented here but called in super()
        super(dateArrivee, nbNuits, logement, nbVoyageurs);
    }

    @Override
    protected void miseAJourDuTarif() {
        setTarif(getNbNuits() * getLogement().getTarifJournalier());

        promotion = getTarif() * PROMOTION_EN_POURCENTAGE / 100;

        setTarif(getTarif() - promotion);
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
    public void afficher()  {
        super.afficher();
        Utile.logger.info("Le prix de ce séjour est de "+getTarif()+"€ ("+promotion+"€ de promotion).");
    }

    @Override
    public Object clone() {
        SejourLong sejourLong;
        sejourLong = (SejourLong) super.clone();
        return sejourLong;
    }

}
