package legeay.airbnb.menu;

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
import java.text.ParseException;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("--- Bienvenue chez AirBnB ---");
        listerMenu();

        scanner.close();
    }

    static void listerMenu() {

        Utile.info(Constants.LINE_SEPARATOR);
        Utile.info("- Choisir une option : ");
        Utile.logger.info("1 : Liste des hôtes");
        Utile.logger.info("2 : Liste des logements");
        Utile.logger.info("3 : Liste des voyageurs");
        Utile.logger.info("4 : Liste des réservations");
        Utile.logger.info("5 : Trouver logement par nom");
        Utile.logger.info("6 : Fermer le programme");
        Utile.info(Constants.LINE_SEPARATOR);
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
                Search search = new Search.SearchBuilder(2)
                        .possedeBalcon(true)
                        .possedeJardin(false)
                        .tarifMinParNuit(49)
                        .tarifMaxParNuit(71)
                        .build();

                List<Logement> logementsFound = search.result();

                // logementsFound.forEach(Logement::afficher);

                ArrayList<String> maList = new ArrayList<>(Arrays.asList(
                        "aaaaaaaaaa",
                        "bbbbbbbbb",
                        "cccccccc",
                        "ddddddd",
                        "eeeeee",
                        "fffff",
                        "gggg",
                        "hhh"
                ));

                String maString = maList.stream().reduce("Les noms : \n", (acc, x) -> acc +" -> "+ x);
                Utile.warn(maString);
                Utile.info("HashCode : "+ maList.hashCode());
                maList.stream().skip(2).limit(3).forEach(Utile::alert);
                maList.stream().dropWhile(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.length() >= 5;
                    }
                }).forEach(Utile::info);
                maList.stream().filter(s -> s.length() % 2 == 0 ).forEach(Utile::info);

                try {
                    Hote monHote = hoteList.stream()
                            .flatMap(hote -> hote.getLogementList().stream())
                            .skip(2)
                            .limit(2)
                            .map(logement -> logement.getHote())
                            .distinct()
                            .filter(hote -> hote.getAge() > 72)
                            .findFirst()
                            .orElseThrow(new Supplier<Throwable>() {
                                @Override
                                public Throwable get() {
                                    return new NoSuchElementException("Element non trouvé");
                                }
                            });

                    Utile.fonkie(Constants.LINE_SEPARATOR);
                    monHote.afficher();
                    Utile.fonkie(Constants.LINE_SEPARATOR);
                } catch (Throwable throwable) {
                    Utile.alert("ERROR : "+ throwable.getMessage());
                }


                //              CompareGeneric<Hote> compareGenericHote = new CompareGeneric<>(hoteList.get(0), hoteList.get(1));
//                compareGenericHote.getLower().afficher();
//                compareGenericHote.getHigher().afficher();

                CompareGenericList<Hote> compareGenericList = new CompareGenericList<Hote>(hoteList);
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
            default:
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
            new Reservation(resa1SejourList, voyageurList.get(1));
        } catch (Exception e) {
            Utile.alert(e.getMessage());
        }
    }

    static void afficherPersonneList(List<? extends Personne> pList) {
        if(pList.isEmpty()) Utile.warn("Il n'y a pas d'éléments à afficher");
        else {
            // Utile.logger.info("Liste des "+pList.get(0).getClass().getSimpleName()+"s :");
            for (int i = 0; i < pList.size(); i++) {
                Utile.logger.info((i+1)+". ");
                pList.get(i).afficher();

                if(pList.get(i) instanceof Voyageur) Utile.logger.info("");
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
                Utile.logger.info("Veuillez (vous) ressaisir : ");
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
            Utile.logger.info("Veuillez (vous) ressaisir : ");
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
        for (T logement : logementList) {
            if(logement.getName().contains(name)) return Optional.of(logement);
        }

        return Optional.empty();
    }
}
