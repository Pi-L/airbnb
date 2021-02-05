package legeay.airbnb.menu;

import legeay.airbnb.outils.Constants;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.utilisateurs.Voyageur;

public class GestionVoyageurs {

    private GestionVoyageurs() {}

    static void listerVoyageurs() {
        Utile.logger.info("");
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Liste des voyageurs ");
        Utile.info(Constants.SMALL_LINE_SEPARATOR);
        Menu.afficherPersonneList(Menu.voyageurList);
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Saisir une option : ");
        Utile.logger.info("1 : Ajouter un voyageur");
        Utile.logger.info("2 : Supprimer un voyageur");
        Utile.logger.info("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                ajouterVoyageur();
                listerVoyageurs();
                break;
            case 2:
                if(Menu.voyageurList.isEmpty()) Utile.alert("Il n'y a pas d'Voyageur à supprimer !");
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
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Ajouter un voyageur");
        Utile.logger.info("Nom :");
        String nom = Menu.getInputString();

        Utile.logger.info("Prenom :");
        String prenom = Menu.getInputString();

        Utile.logger.info("Age :");
        int age = Menu.getInputInteger(0, 150);

        Voyageur voyageur = new Voyageur(prenom, nom, age);

        Menu.voyageurList.add(voyageur);
    }

    static void supprimerVoyageur() {
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Supprimer un voyageur");
        Utile.logger.info("Choisir un Voyageur à supprimer :");

        int indexPlusOne = Menu.getInputInteger(1, Menu.voyageurList.size());
        Menu.voyageurList.remove(indexPlusOne - 1);
    }
}
