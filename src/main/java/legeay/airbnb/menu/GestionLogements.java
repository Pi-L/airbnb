package legeay.airbnb.menu;

import legeay.airbnb.logements.Logement;

public class GestionLogements {

    static void listerLogements() {
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println("Liste des logements ");
        System.out.println("---------------");
        Menu.afficherList(Menu.logementList);
        System.out.println("-------------------------------------");
        System.out.println("Saisir une option : ");
        System.out.println("1 : Ajouter un logement");
        System.out.println("2 : Supprimer un logement");
        System.out.println("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                ajouterLogement();
                listerLogements();
                break;
            case 2:
                supprimerLogement();
                listerLogements();
                break;
            case 3:
                Menu.listerMenu();
                break;
        }
    }

    static void ajouterLogement() {
        System.out.println("-------------------------------------");
        System.out.println("Ajouter un logement");
        System.out.println("Saisir une option : ");
        System.out.println("1 : Ajouter une maison");
        System.out.println("2 : Ajouter un appartement");
        System.out.println("3 : Retour");

        int choixLogement = Menu.getInputInteger(1,3);

        if (choixLogement == 3) {
            listerLogements();
            return;
        }

        System.out.println("Liste des hôtes ");

        Menu.afficherList(Menu.hoteList);

        int indexHote = -1;

        if(Menu.hoteList.size() == 0) {
            System.out.println("Vous ne pouvez continuer car il n'y a pas d'hotes enregistrés !");
            System.out.println("Retour menu principal");
            Menu.listerMenu();
            return;
        }
        else {
            indexHote = Menu.getInputInteger(1, Menu.hoteList.size()) - 1;
        }



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
        System.out.println("-------------------------------------");
        System.out.println("Supprimer un logement");
        if(Menu.logementList.size() == 0) System.out.println("Il n'y a pas d'Logement à supprimer !");
        else {
            System.out.println("Choisir un Logement à supprimer :");
            int indexPlusOne = Menu.getInputInteger(1, Menu.logementList.size());
            Menu.logementList.remove(indexPlusOne - 1);
        }
    }
}
