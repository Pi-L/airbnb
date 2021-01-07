package legeay.airbnb.menu;

import legeay.airbnb.utilisateurs.Hote;

public class GestionHotes {

    static void listerHotes() {
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println("Liste des hôtes ");
        System.out.println("---------------");
        Menu.afficherList(Menu.hoteList);
        System.out.println("-------------------------------------");
        System.out.println("Saisir une option : ");
        System.out.println("1 : Ajouter un hôte");
        System.out.println("2 : Supprimer un hôte");
        System.out.println("3 : Retour");

        switch (Menu.getInteger(1,3)) {
            case 1:
                try {
                    ajouterHote();
                }
                catch (Exception e) {
                    Menu.scanner.nextLine(); // in order to "consume" the "\n" which the nextInt() does not
                    System.out.println("Ajout d'un nouvel hote impossible : saisie erronée");
                }
                break;
            case 2:
                supprimerHote();
                break;
            case 3:
                Menu.listerMenu();
                break;

        }

        listerHotes();
    }

    static void ajouterHote() throws Exception {
        // would be cleaner to use Menu.getString() though =)

        System.out.println("-------------------------------------");
        System.out.println("Nom :");
        String nom = Menu.scanner.nextLine();
        System.out.println("Prenom :");
        String prenom = Menu.scanner.nextLine();
        System.out.println("Age :");
        int age = Menu.scanner.nextInt();
        System.out.println("Delais reponse :");
        int delais = Menu.scanner.nextInt();

        Hote hote = new Hote(prenom, nom, age, delais);

        // hote.afficher();
        Menu.hoteList.add(hote);
    }

    static void supprimerHote() {

    }

}
