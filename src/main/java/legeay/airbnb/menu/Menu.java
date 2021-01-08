package legeay.airbnb.menu;

import legeay.airbnb.AffichableInterface;
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

        System.out.println("-----------------------------");
        System.out.println("--- Bienvenue chez AirBnB ---");
        listerMenu();

        scanner.close();
    }

    static void listerMenu() {

        System.out.println("-----------------------------");
        System.out.println("- Choisir une option : ");
        System.out.println("1 : Liste des hôtes");
        System.out.println("2 : Liste des logements");
        System.out.println("3 : Liste des voyageurs");
        System.out.println("4 : Liste des réservations");
        System.out.println("5 : Fermer le programme");
        System.out.println("-----------------------------");
        switch (Menu.getInteger(1,5)) {
            case 1:
                GestionHotes.listerHotes();
                break;
            case 2:
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
        if(pList.size() == 0) System.out.println("Il n'y a pas d'éléments à afficher");
        else {
            for (int i = 0; i < pList.size(); i++) {
                System.out.print((i+1)+". ");
                pList.get(i).afficher();
            }
        }
    }

    static int getInteger() {
        boolean isValid;
        int value = 0;

        do {
            try {
                value = scanner.nextInt();
                scanner.nextLine(); // in order to "consume" the "\n" which the nextInt() does not
                isValid = true;

            } catch (Exception exception) {
                System.out.print("Votre saisie << " + scanner.nextLine() + " >> n'est pas un entier. Veuillez (vous) ressaisir : ");
                isValid = false;
            }
        }
        while (!isValid);

        return value;
    }

    static int getInteger(int min, int max) {
        int value = getInteger();

        while (value < min || value > max) {
            System.out.println("La valeur entrée n'est pas dans l'interval demandé : ["+min+" - "+max+"]");
            System.out.print("veuillez ressaisir : ");
            value = getInteger();
        }
        return value;
    }

   static String getString() {
        String value = scanner.nextLine();

        while (value.isBlank()) {
            System.out.print("Votre saisie est vide, merci de ressaisir : ");
            value = scanner.nextLine();
        }
        return value;
    }
}
