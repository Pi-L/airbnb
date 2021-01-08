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

        switch (Menu.getInteger(1,3)) {
            case 1:
                try {
                    ajouterVoyageur();
                }
                catch (Exception e) {
                    Menu.scanner.nextLine(); // in order to "consume" the "\n" which the nextInt() does not
                    System.out.println("Ajout d'un nouveau voyageur impossible : saisie erronée");
                } finally {
                    listerVoyageurs();
                }
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
        // would be cleaner to use Menu.getString() though =)

        System.out.println("-------------------------------------");
        System.out.println("Nom :");
        String nom = Menu.getString();

        System.out.println("Prenom :");
        String prenom = Menu.getString();

        System.out.println("Age :");
        int age = Menu.getInteger(0, 150);

        Voyageur Voyageur = new Voyageur(prenom, nom, age);

        Menu.voyageurList.add(Voyageur);
    }

    static void supprimerVoyageur() {
        if(Menu.voyageurList.size() == 0) System.out.println("Il n'y a pas d'Voyageur à supprimer !");
        else {
            System.out.println("Choisir un Voyageur à supprimer :");
            int indexPlusOne = Menu.getInteger(1, Menu.voyageurList.size());
            Menu.voyageurList.remove(indexPlusOne - 1);
        }
    }
}
