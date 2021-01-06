package legeay.airbnb.logements;

import legeay.airbnb.outils.ConsoleColors;
import legeay.airbnb.utilisateurs.Hote;

public class Maison extends Logement{

    private int superficieJardin;
    private boolean possedePiscine;

    /**
     * <p>Maison constructor</p>
     * @param hote
     * @param tarifJournalier
     * @param adresse
     * @param superficie
     * @param nbVoyageursMax
     * @param superficieJardin
     * @param possedePiscine
     */
    public Maison(Hote hote, int tarifJournalier, String adresse, int superficie, int nbVoyageursMax, int superficieJardin, boolean possedePiscine) {
        super(hote, tarifJournalier, adresse, superficie, nbVoyageursMax);
        this.superficieJardin = superficieJardin;
        this.possedePiscine = possedePiscine;
    }

    @Override
    public void afficher() {
        getHote().afficher();
        System.out.println();
        System.out.println("Le logement est une maison située "+ConsoleColors.BLACK_BACKGROUND_BRIGHT+getAdresse()+ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE_BOLD+"Superficie : "+ConsoleColors.RESET+getSuperficie()+"m2");
        System.out.println(ConsoleColors.PURPLE_BOLD+"Jardin : "+ConsoleColors.RESET+(superficieJardin>0 ? "Oui ("+superficieJardin+"m2)" : "Non"));
        System.out.println(ConsoleColors.PURPLE_BOLD+"Piscine : "+ConsoleColors.RESET+(possedePiscine ? "Oui" : "Non"));
    }

    @Override
    public int getSuperficieTotale() {
        return getSuperficie() + superficieJardin;
    }
}
