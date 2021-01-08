package legeay.airbnb.menu;

import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.utilisateurs.Hote;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestionLogements {

    static List<Logement> logementList;

    static void listerLogements() {
        initLogement();

        System.out.println();
        Utile.info("-------------------------------------");
        Utile.info("Liste des logements ");
        Utile.info("---------------");
        afficherLogementList();
        Utile.info("-------------------------------------");
        Utile.info("Saisir une option : ");
        System.out.println("1 : Ajouter un logement");
        System.out.println("2 : Supprimer un logement");
        System.out.println("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                if(Menu.hoteList.size() == 0) {
                    Utile.alert("Vous ne pouvez continuer car il n'y a pas d'hotes enregistrés !");
                    Utile.warn("Retour menu principal...");
                    Menu.listerMenu();
                    break;
                }

                ajouterLogement();
                listerLogements();
                break;
            case 2:
                if(logementList.size() == 0) Utile.alert("Il n'y a pas de logement à supprimer !");
                else supprimerLogement();

                listerLogements();
                break;
            case 3:
                Menu.listerMenu();
                break;
        }
    }

    static void initLogement() {
        logementList = new ArrayList<>();
        logementList = Menu.hoteList.stream()
                .flatMap(hote -> hote.getLogementList().stream())
                .collect(Collectors.toList());
    }

    static void afficherLogementList() {
        if(logementList.size() == 0) Utile.warn("Il n'y a pas de logement à afficher");
        else {
            for (int i = 0; i < logementList.size(); i++) {
                if (i>0) System.out.println();
                System.out.print((i + 1) + ". ");
                logementList.get(i).afficher();
            }
        }
    }

    static void ajouterLogement() {
        Utile.info("-------------------------------------");
        Utile.info("Ajouter un logement");
        Utile.info("Saisir une option : ");
        System.out.println("1 : Ajouter une maison");
        System.out.println("2 : Ajouter un appartement");
        System.out.println("3 : Retour");

        int choixLogement = Menu.getInputInteger(1,3);

        if (choixLogement == 3) {
            listerLogements();
            return;
        }

        Utile.info("Liste des hôtes ");
        Menu.afficherPersonneList(Menu.hoteList);

        int indexHote = -1;
        Hote hote;

        System.out.println("Numéro de l'hôte : ");
        indexHote = Menu.getInputInteger(1, Menu.hoteList.size()) - 1;
        hote = Menu.hoteList.get(indexHote);

        System.out.println("Tarif journalier :");
        int tarifJournalier = Menu.getInputInteger(1,10000);
        System.out.println("Adresse :");
        String adresse = Menu.getInputString();
        System.out.println("Superficie : ");
        int superficie = Menu.getInputInteger(1, 10000);
        System.out.println("Nombre de voyageur max : ");
        int nbVoyageurMax = Menu.getInputInteger(1, 20);

        switch (choixLogement) {
            case 1:
                System.out.println("Superficie Jardin : ");
                int superficieJardin = Menu.getInputInteger(0, 10000);
                System.out.println("Piscine (0: non, 1: oui) :");
                boolean hasJardin = Menu.getInputInteger(0, 1) == 1;

                new Maison(hote, tarifJournalier, adresse, superficie, nbVoyageurMax, superficieJardin, hasJardin);
                break;
            case 2:
                System.out.println("Numero étage  :");
                int numeroEtage = Menu.getInputInteger(0, 200);
                System.out.println("Superficie balcon : ");
                int superficieBalcon = Menu.getInputInteger(0, 100);

                new Appartement(hote, tarifJournalier, adresse, superficie, nbVoyageurMax, numeroEtage, superficieBalcon);
                break;
        }
    }


    static void supprimerLogement() {
        Utile.info("-------------------------------------");
        Utile.info("Supprimer un logement");
        System.out.println("Choisir un Logement à supprimer :");

        int index = Menu.getInputInteger(1, logementList.size()) - 1;

        Logement logementToRemove = logementList.get(index);
        Hote ownerLogement = logementToRemove.getHote();

        ownerLogement.getLogementList().remove(logementToRemove);
    }
}
