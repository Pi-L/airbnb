package legeay.airbnb.menu;

import legeay.airbnb.outils.Utile;
import legeay.airbnb.utilisateurs.Voyageur;

public class GestionVoyageurs {
    static void listerVoyageurs() {
        System.out.println();
        Utile.info("-------------------------------------");
        Utile.info("Liste des voyageurs ");
        Utile.info("---------------");
        Menu.afficherList(Menu.voyageurList);
        Utile.info("-------------------------------------");
        Utile.info("Saisir une option : ");
        System.out.println("1 : Ajouter un voyageur");
        System.out.println("2 : Supprimer un voyageur");
        System.out.println("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                ajouterVoyageur();
                listerVoyageurs();
                break;
            case 2:
                if(Menu.voyageurList.size() == 0) Utile.alert("Il n'y a pas d'Voyageur à supprimer !");
                else {
                    supprimerVoyageur();
                }
                listerVoyageurs();
                break;
            case 3:
                Menu.listerMenu();
                break;
        }
    }

    static void ajouterVoyageur() {
        Utile.info("-------------------------------------");
        Utile.info("Ajouter un voyageur");
        System.out.println("Nom :");
        String nom = Menu.getInputString();

        System.out.println("Prenom :");
        String prenom = Menu.getInputString();

        System.out.println("Age :");
        int age = Menu.getInputInteger(0, 150);

        Voyageur Voyageur = new Voyageur(prenom, nom, age);

        Menu.voyageurList.add(Voyageur);
    }

    static void supprimerVoyageur() {
        Utile.info("-------------------------------------");
        Utile.info("Supprimer un voyageur");
        System.out.println("Choisir un Voyageur à supprimer :");

        int indexPlusOne = Menu.getInputInteger(1, Menu.voyageurList.size());
        Menu.voyageurList.remove(indexPlusOne - 1);
    }
}
