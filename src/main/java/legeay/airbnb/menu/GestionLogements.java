package legeay.airbnb.menu;

import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;
import legeay.airbnb.outils.Constants;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.utilisateurs.Hote;
import java.util.List;
import java.util.stream.Collectors;

public class GestionLogements {

    private GestionLogements() {}

    static void listerLogements() {

        Utile.logger.info("");
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Liste des logements ");
        Utile.info(Constants.SMALL_LINE_SEPARATOR);
        afficherLogementList();
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Saisir une option : ");
        Utile.logger.info("1 : Ajouter un logement");
        Utile.logger.info("2 : Supprimer un logement");
        Utile.logger.info("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                if(Menu.hoteList.isEmpty()) {
                    Utile.alert("Vous ne pouvez continuer car il n'y a pas d'hotes enregistrés !");
                    Utile.warn("Retour menu principal...");
                    Menu.listerMenu();
                    break;
                }

                ajouterLogement();
                listerLogements();
                break;
            case 2:
                if(getLogementList().isEmpty()) Utile.alert("Il n'y a pas de logement à supprimer !");
                else supprimerLogement();

                listerLogements();
                break;
            case 3:
                Menu.listerMenu();
                break;
            default:
                break;
        }
    }

    static List<Logement> getLogementList() {
        return  Menu.hoteList.stream()
                .flatMap(hote -> hote.getLogementList().stream())
                .collect(Collectors.toList());
    }

    static void afficherLogementList() {
        List<Logement> logementList = getLogementList();

        if(logementList.isEmpty()) Utile.warn("Il n'y a pas de logement à afficher");
        else {
            for (int i = 0; i < logementList.size(); i++) {
                if (i>0) Utile.logger.info("");
                Utile.logger.info((i + 1) + ". ");
                logementList.get(i).afficher();
            }
        }
    }

    static void ajouterLogement() {
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Ajouter un logement");
        Utile.info("Saisir une option : ");
        Utile.logger.info("1 : Ajouter une maison");
        Utile.logger.info("2 : Ajouter un appartement");
        Utile.logger.info("3 : Retour");

        int choixLogement = Menu.getInputInteger(1,3);

        if (choixLogement == 3) {
            listerLogements();
            return;
        }

        Utile.info("Liste des hôtes ");
        Menu.afficherPersonneList(Menu.hoteList);

        int indexHote = -1;
        Hote hote;

        Utile.logger.info("Numéro de l'hôte : ");
        indexHote = Menu.getInputInteger(1, Menu.hoteList.size()) - 1;
        hote = Menu.hoteList.get(indexHote);

        Utile.logger.info("Tarif journalier :");
        int tarifJournalier = Menu.getInputInteger(1,10000);
        Utile.logger.info("Adresse :");
        String adresse = Menu.getInputString();
        Utile.logger.info("Superficie : ");
        int superficie = Menu.getInputInteger(1, 10000);
        Utile.logger.info("Nombre de voyageur max : ");
        int nbVoyageurMax = Menu.getInputInteger(1, 20);

        if(choixLogement == 1) {

            Utile.logger.info("Superficie Jardin : ");
            int superficieJardin = Menu.getInputInteger(0, 10000);
            Utile.logger.info("Piscine (0: non, 1: oui) :");
            boolean hasJardin = Menu.getInputInteger(0, 1) == 1;

            new Maison(hote, tarifJournalier, adresse, superficie, nbVoyageurMax, superficieJardin, hasJardin);

        } else {
                Utile.logger.info("Numero étage  :");
                int numeroEtage = Menu.getInputInteger(0, 200);
                Utile.logger.info("Superficie balcon : ");
                int superficieBalcon = Menu.getInputInteger(0, 100);

                new Appartement(hote, tarifJournalier, adresse, superficie, nbVoyageurMax, numeroEtage, superficieBalcon);
        }
    }


    static void supprimerLogement() {
        List<Logement> logementList = getLogementList();

        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Supprimer un logement");
        Utile.logger.info("Choisir un Logement à supprimer :");

        int index = Menu.getInputInteger(1, logementList.size()) - 1;

        Logement logementToRemove = logementList.get(index);
        Hote ownerLogement = logementToRemove.getHote();

        ownerLogement.getLogementList().remove(logementToRemove);
    }
}
