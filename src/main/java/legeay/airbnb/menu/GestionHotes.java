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

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                try {
                    ajouterHote();
                }
                catch (Exception e) {
                    Menu.scanner.nextLine(); // in order to "consume" the "\n" which the nextInt() does not
                    System.out.println("Ajout d'un nouvel hote impossible, saisie erronée : "+e.getMessage());
                } finally {
                    listerHotes();
                }
                break;
            case 2:
                try {
                    supprimerHote();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Echec de la suppression - l'hôte choisi n'existe pas : "+e.getMessage());
                } finally {
                    listerHotes();
                }
                break;
            case 3:
                Menu.listerMenu();
                break;
        }
    }

    static void ajouterHote() throws Exception {
        // would be cleaner to use Menu.getInputString() though =)

        System.out.println("-------------------------------------");
        System.out.println("Ajouter un Hôte");
        System.out.println("Nom :");
        String nom = Menu.scanner.nextLine();
        System.out.println("Prenom :");
        String prenom = Menu.scanner.nextLine();
        System.out.println("Age :");
        int age = Menu.scanner.nextInt();

        if (age < 0 || age > 150) throw new Exception("L'age entré n'est pas realiste");

        System.out.println("Delais reponse :");
        int delais = Menu.scanner.nextInt();

        if (delais < 0 || delais > 500) throw new Exception("Le delais entré n'est pas realiste");

        Hote hote = new Hote(prenom, nom, age, delais);

        Menu.hoteList.add(hote);
    }

    /**
     *
     * @throws IndexOutOfBoundsException doesn't really throw anything but let say it does =)
     */
    static void supprimerHote() throws IndexOutOfBoundsException {
        System.out.println("-------------------------------------");
        System.out.println("Supprimer un Hôte");

        // Uncomment to test the IndexOutOfBoundsException
        // Menu.hoteList.remove(555);

        if(Menu.hoteList.size() == 0) System.out.println("Il n'y a pas d'hote à supprimer !");
        else {
            System.out.println("Choisir un hote à supprimer :");
            int indexPlusOne = Menu.getInputInteger(1, Menu.hoteList.size());

            Menu.hoteList.remove(indexPlusOne - 1);
        }
    }

}
