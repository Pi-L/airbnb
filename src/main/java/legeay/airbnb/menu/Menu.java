package legeay.airbnb.menu;

import legeay.airbnb.AffichableInterface;
import legeay.airbnb.outils.Utile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    static Scanner scanner;
    static List<AffichableInterface> hoteList;
    static List<AffichableInterface> logementList;
    static List<AffichableInterface> voyageurList;
    static List<AffichableInterface> reservationList;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        hoteList = new ArrayList<>();
        logementList = new ArrayList<>();
        voyageurList = new ArrayList<>();
        reservationList = new ArrayList<>();

        Utile.info("-----------------------------");
        Utile.info("--- Bienvenue chez AirBnB ---");
        listerMenu();

        scanner.close();
    }

    static void listerMenu() {

        Utile.info("-----------------------------");
        Utile.info("- Choisir une option : ");
        System.out.println("1 : Liste des hôtes");
        System.out.println("2 : Liste des logements");
        System.out.println("3 : Liste des voyageurs");
        System.out.println("4 : Liste des réservations");
        System.out.println("5 : Fermer le programme");
        Utile.info("-----------------------------");
        switch (Menu.getInputInteger(1,5)) {
            case 1:
                GestionHotes.listerHotes();
                break;
            case 2:
                GestionLogements.listerLogements();
                break;
            case 3:
                GestionVoyageurs.listerVoyageurs();
                break;
            case 4:
                break;
            case 5:
                // not needed here but good to know
                // 0 means normal exit
                // System.exit(0);
                break;
        }
    }

    static void afficherList(List<AffichableInterface> pList) {
        if(pList.size() == 0) Utile.warn("Il n'y a pas d'éléments à afficher");
        else {
            // System.out.println("Liste des "+pList.get(0).getClass().getSimpleName()+"s :");
            for (int i = 0; i < pList.size(); i++) {
                System.out.print((i+1)+". ");
                pList.get(i).afficher();
            }
        }
    }

    static int getInputInteger() {
        boolean isValid;
        int value = 0;

        do {
            try {
                value = scanner.nextInt();
                scanner.nextLine(); // in order to "consume" the "\n" which the nextInt() does not
                isValid = true;

            } catch (Exception exception) {
                Utile.alert("Votre saisie << " + scanner.nextLine() + " >> n'est pas un entier. Veuillez (vous) ressaisir : ");
                isValid = false;
            }
        }
        while (!isValid);

        return value;
    }

    static int getInputInteger(int min, int max) {
        int value = getInputInteger();

        while (value < min || value > max) {
            Utile.alert("La valeur entrée n'est pas dans l'interval demandé : ["+min+" - "+max+"]");
            System.out.print("veuillez ressaisir : ");
            value = getInputInteger();
        }
        return value;
    }

   static String getInputString() {
        String value = scanner.nextLine();

        while (value.isBlank()) {
            Utile.alert("Votre saisie est vide, merci de ressaisir : ");
            value = scanner.nextLine();
        }
        return value;
    }
}
