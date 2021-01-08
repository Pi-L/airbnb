package legeay.airbnb.menu;

import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.Utile;

public class GestionLogements {

    static void listerLogements() {
        System.out.println();
        Utile.info("-------------------------------------");
        Utile.info("Liste des logements ");
        Utile.info("---------------");
        Menu.afficherList(Menu.logementList);
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
                if(Menu.logementList.size() == 0) Utile.alert("Il n'y a pas de logement à supprimer !");
                else supprimerLogement();

                listerLogements();
                break;
            case 3:
                Menu.listerMenu();
                break;
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
        Menu.afficherList(Menu.hoteList);

        int indexHote = -1;
        indexHote = Menu.getInputInteger(1, Menu.hoteList.size()) - 1;




        switch (choixLogement) {
            case 1:

                break;
            case 2:

                break;
        }

        System.out.println("Nom :");
        String nom = Menu.getInputString();

        System.out.println("Prenom :");
        String prenom = Menu.getInputString();

        System.out.println("Age :");
        int age = Menu.getInputInteger(0, 150);

//        Logement Logement = new Logement(prenom, nom, age);
//
//        Menu.logementList.add(Logement);
    }

    static void ajouterMaison() {

    }

    static void ajouterAppartement() {

    }

    static void supprimerLogement() {
        Utile.info("-------------------------------------");
        Utile.info("Supprimer un logement");
        System.out.println("Choisir un Logement à supprimer :");

        int indexPlusOne = Menu.getInputInteger(1, Menu.logementList.size());
        Menu.logementList.remove(indexPlusOne - 1);

    }
}
