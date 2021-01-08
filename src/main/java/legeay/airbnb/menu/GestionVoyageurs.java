package legeay.airbnb.menu;

import legeay.airbnb.utilisateurs.Voyageur;

public class GestionVoyageurs {
    static void listerVoyageurs() {
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println("Liste des voyageurs ");
        System.out.println("---------------");
        Menu.afficherList(Menu.voyageurList);
        System.out.println("-------------------------------------");
        System.out.println("Saisir une option : ");
        System.out.println("1 : Ajouter un voyageur");
        System.out.println("2 : Supprimer un voyageur");
        System.out.println("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                ajouterVoyageur();
                listerVoyageurs();
                break;
            case 2:
                supprimerVoyageur();
                listerVoyageurs();
                break;
            case 3:
                Menu.listerMenu();
                break;
        }
    }

    static void ajouterVoyageur() {
        System.out.println("-------------------------------------");
        System.out.println("Ajouter un voyageur");
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
        System.out.println("-------------------------------------");
        System.out.println("Supprimer un voyageur");
        if(Menu.voyageurList.size() == 0) System.out.println("Il n'y a pas d'Voyageur à supprimer !");
        else {
            System.out.println("Choisir un Voyageur à supprimer :");
            int indexPlusOne = Menu.getInputInteger(1, Menu.voyageurList.size());
            Menu.voyageurList.remove(indexPlusOne - 1);
        }
    }
}
