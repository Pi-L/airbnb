package legeay.airbnb.menu;

import legeay.airbnb.outils.Constants;
import legeay.airbnb.outils.Utile;
import legeay.airbnb.utilisateurs.Hote;

import javax.management.BadAttributeValueExpException;
import java.util.logging.Logger;

public class GestionHotes {
    
    private GestionHotes() {}

    static void listerHotes() {
        Utile.logger.info("");
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Liste des hôtes ");
        Utile.info(Constants.SMALL_LINE_SEPARATOR);
        Menu.afficherPersonneList(Menu.hoteList);
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Saisir une option : ");
        Utile.logger.info("1 : Ajouter un hôte");
        Utile.logger.info("2 : Supprimer un hôte");
        Utile.logger.info("3 : Retour");

        switch (Menu.getInputInteger(1,3)) {
            case 1:
                try {
                    ajouterHote();
                }
                catch (Exception e) {
                    Menu.scanner.nextLine(); // in order to "consume" the line
                    Utile.alert("Ajout d'un nouvel hote impossible, saisie erronée : "+e.getMessage());
                } finally {
                    listerHotes();
                }
                break;
            case 2:
                try {
                    supprimerHote();
                } catch (IndexOutOfBoundsException e) {
                    Utile.alert("Echec de la suppression - l'hôte choisi n'existe pas : "+e.getMessage());
                } finally {
                    listerHotes();
                }
                break;
            case 3:
                Menu.listerMenu();
                break;
            default:
                break;
        }
    }

    static void ajouterHote() throws BadAttributeValueExpException {
        // would be cleaner to use Menu.getInputString() though =)

        Utile.logger.info(Constants.LINE_SEPARATOR);
        Utile.logger.info("Ajouter un Hôte");
        Utile.logger.info("Nom :");
        String nom = Menu.scanner.nextLine();
        Utile.logger.info("Prenom :");
        String prenom = Menu.scanner.nextLine();
        Utile.logger.info("Age :");
        int age = Menu.scanner.nextInt();

        if (age < 0 || age > 150) throw new BadAttributeValueExpException("L'age entré n'est pas realiste");

        Utile.logger.info("Delais reponse :");
        int delais = Menu.scanner.nextInt();

        if (delais < 0 || delais > 500) throw new BadAttributeValueExpException("Le delais entré n'est pas realiste");

        Hote hote = new Hote(prenom, nom, age, delais);

        Menu.hoteList.add(hote);
    }

    /**
     *
     * @throws IndexOutOfBoundsException doesn't really throw anything but let say it does =)
     */
    static void supprimerHote() {
        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("Supprimer un Hôte");

        if(Menu.hoteList.isEmpty()) Utile.alert("Il n'y a pas d'hote à supprimer !");
        else {
            Utile.logger.info("Choisir un hote à supprimer :");
            int indexPlusOne = Menu.getInputInteger(1, Menu.hoteList.size());

            Menu.hoteList.remove(indexPlusOne - 1);
        }
    }

}
