package legeay.airbnb.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {


    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        System.out.println("-----------------------------");
        System.out.println("--- Bienvenue chez AirBnB ---");
        listerMenu();


        scanner.close();
    }

    static void listerMenu() {
        int maxValue = 5;

        System.out.println("-----------------------------");
        System.out.println("- Choisir une option : ");
        System.out.println("1 : Liste des hôtes");
        System.out.println("2 : Liste des logements");
        System.out.println("3 : Liste des voyageurs");
        System.out.println("4 : Liste des réservations");
        System.out.println("5 : Fermer le programme");

        System.out.println(faireChoix(maxValue));
    }

    static int faireChoix(int maxValue) {
        boolean isValid;
        int value = 0;

        do {
            try {
                value = scanner.nextInt();
                isValid = value <= maxValue && value > 0;

                if(!isValid) System.out.println("Veuillez faire un choix compris entre 1 et "+maxValue);

            } catch (Exception exception) {
                System.out.println("Votre saisie << " + scanner.nextLine() + " >> n'est pas un entier. Veuillez (vous) ressaisir : ");
                isValid = false;
            }
        }
        while (!isValid);

        return value;
    }


}
