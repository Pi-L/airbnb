package legeay.airbnb.menu;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.security.NoTypePermission;
import legeay.airbnb.AffichableInterface;
import legeay.airbnb.CompareGenericList;
import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;
import legeay.airbnb.outils.*;
import legeay.airbnb.reservations.Reservation;
import legeay.airbnb.reservations.Sejour;
import legeay.airbnb.reservations.SejourCourt;
import legeay.airbnb.reservations.SejourLong;
import legeay.airbnb.utilisateurs.Hote;
import legeay.airbnb.utilisateurs.Personne;
import legeay.airbnb.utilisateurs.Voyageur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    static Scanner scanner;
    static List<Hote> hoteList;
    static List<Voyageur> voyageurList;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        try {
            init();
        } catch (ParseException e) {
            Utile.alert("Erreur dans le format de la date d'un sejour");
        }

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
        System.out.println("5 : Trouver logement par nom");
        System.out.println("6 : Fermer le programme");
        Utile.info("-----------------------------");
        switch (Menu.getInputInteger(1,6)) {
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
                GestionReservations.listerReservations();
                break;
            case 5:

                Search search = new Search.SearchBuilder(2).possedeBalcon(false).possedeJardin(false).build();

                List<Logement> logementsFound = search.result();

                logementsFound.forEach(logement -> logement.afficher());

                CompareGeneric<Hote> compareGenericHote = new CompareGeneric<>(hoteList.get(0), hoteList.get(1));
//                compareGenericHote.getLower().afficher();
//                compareGenericHote.getHigher().afficher();

                CompareGenericList<Hote> compareGenericList = new CompareGenericList(hoteList);
                Optional<Hote> myHoteHigher = compareGenericList.getHigher();
                Optional<Hote> myHoteLower = compareGenericList.getLower();

                if(myHoteHigher.isPresent() && myHoteLower.isPresent()) {
//                    myHoteLower.get().afficher();
//                    myHoteHigher.get().afficher();
                }

//                compareGenericList.sortAsc();
//                compareGenericList.afficher();
//                compareGenericList.sortDesc();
//                compareGenericList.afficher();

                List<Maison> maisonList = GestionLogements.getLogementList().stream()
                        .filter(logt -> logt instanceof Maison)
                        .map(maison -> (Maison) maison)
                        .collect(Collectors.toList());
                // Maison maMaison = findMaisonByName(maisonList, "Bardu");
                Optional<Maison> maMaison = findLogementByNameWithGenericity(maisonList, "Bardu");
                // if(maMaison.isPresent()) maMaison.get().afficher();

                // Logement monLogement = findLogementByName(GestionLogements.getLogementList(), "Bardu");
                Optional<Logement> monLogement = findLogementByNameWithGenericity(GestionLogements.getLogementList(), "Bardu");
                // if(monLogement.isPresent()) monLogement.get().afficher();

                listerMenu();
                break;
            case 6:
                // not needed here but good to know
                // 0 means normal exit
                // System.exit(0);
                break;
        }
    }

    static void init() throws ParseException {
        AirBnBData airBnBData = AirBnBData.getInstance();

        hoteList = airBnBData.getHotes();
        voyageurList = airBnBData.getVoyageurs();

        Sejour sejour1 = new SejourCourt(new MaDate("25/02/2021"), 5, hoteList.get(0).getLogementList().get(0), 2);
        Sejour sejour2 = new SejourLong(new MaDate("6/03/2021"), 12, hoteList.get(0).getLogementList().get(1), 1);

        List<Sejour> resa1SejourList = new ArrayList<>();
        resa1SejourList.add(sejour1);
        resa1SejourList.add(sejour2);

        try {
            Reservation reservation1 = new Reservation(resa1SejourList, voyageurList.get(1));
        } catch (Exception e) {
            Utile.alert(e.getMessage());
        }
    }

    static void afficherPersonneList(List<? extends Personne> pList) {
        if(pList.size() == 0) Utile.warn("Il n'y a pas d'éléments à afficher");
        else {
            // System.out.println("Liste des "+pList.get(0).getClass().getSimpleName()+"s :");
            for (int i = 0; i < pList.size(); i++) {
                System.out.print((i+1)+". ");
                pList.get(i).afficher();

                if(pList.get(i).getClass().getSimpleName().equals("Voyageur")) System.out.println();
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
                Utile.alert(" Votre saisie << " + scanner.nextLine() + " >> n'est pas un entier. ");
                System.out.println("Veuillez (vous) ressaisir : ");
                isValid = false;
            }
        }
        while (!isValid);

        return value;
    }

    static int getInputInteger(int min, int max) {
        int value = getInputInteger();

        while (value < min || value > max) {
            Utile.alert(" La valeur entrée n'est pas dans l'interval demandé : ["+min+" - "+max+"] ");
            System.out.println("Veuillez (vous) ressaisir : ");
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

    static Maison findMaisonByName(List<Maison> maisonList, String name) {
        for (Maison maison : maisonList) if(maison.getName().contains(name)) return maison;

        return null;
    }

    static Appartement findAppartementByName(List<Appartement> appartementList, String name) {
        for (Appartement appartement : appartementList)  if(appartement.getName().contains(name)) return appartement;

        return null;
    }

    static Logement findLogementByName(List<Logement> logementList, String name) {
        for (Logement logement : logementList) if(logement.getName().contains(name)) return logement;

        return null;
    }

    static <T extends Logement> Optional<T> findLogementByNameWithGenericity(List<T> logementList, String name) {
        Optional<T> logementOptional = Optional.empty();

        for (T logement : logementList) {
            if(logement.getName().contains(name)) return logementOptional;
        }

        return logementOptional;
    }
}
