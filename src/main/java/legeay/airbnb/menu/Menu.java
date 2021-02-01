package legeay.airbnb.menu;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.security.NoTypePermission;
import legeay.airbnb.AffichableInterface;
import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.outils.Utile;
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
                GestionReservations.listerReservations();
                break;
            case 5:
                // not needed here but good to know
                // 0 means normal exit
                // System.exit(0);
                break;
        }
    }

    static void init() throws ParseException {
        hoteList = new ArrayList<>();
        voyageurList = new ArrayList<>();

        initFromXml();
//        hoteList.add(new Hote("titi", "rodriguez", 54, 234));
//        hoteList.add(new Hote("Son", "Goku", 54, 1));
//        Logement logement1 = new Maison(hoteList.get(0), 50, "32 rue de la raclette", 120, 12, 0, false);
//        Logement logement2 = new Appartement(hoteList.get(0), 31, "33 rue de la fondu", 42, 5, 1, 4);

        voyageurList.add(new Voyageur("Helmut", "Shmit", 33));
        voyageurList.add(new Voyageur("Ken", "Hokuto No", 54));

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

    @XStreamAlias("Logements")
    public class Logements {

        @XStreamImplicit
        private List<Appartement> appartementList = new ArrayList<>();

        @XStreamImplicit
        private List<Maison> maisonList = new ArrayList<>();

        public List<Hote> getHoteList() {
            List<Logement> logementList = new ArrayList<>();
            List<Hote> hoteList;

            logementList.addAll(appartementList);
            logementList.addAll(maisonList);

            // enleve les logements en double - utilise la methode equals
            Set<Logement> logementSet = new HashSet<>(logementList);

            hoteList = logementSet.stream()
                    .map(Logement::getHote).distinct() // distinct() utilise la methode equals de Hote
                    .map(hote -> new Hote(hote))
                    .map(hote ->
                        {
                            logementSet.forEach(logement ->
                                {
                                    if (hote.equals(logement.getHote())) {
                                        if (logement instanceof Maison) new Maison(hote, (Maison) logement);
                                        else if (logement instanceof Appartement) new Appartement(hote, (Appartement) logement);
                                    }
                                }
                            );
                            return hote;
                        }
                    )
                    .collect(Collectors.toList());

            return hoteList;
        }
    }

    static void initFromXml() {
        XStream xstream = new XStream();
        //Clear out all permissions
        xstream.addPermission(NoTypePermission.NONE);
        //Add permission to deserialize the Logements class
        xstream.allowTypes(new Class[]{Appartement.class, Logements.class, Maison.class, Hote.class});
        xstream.processAnnotations(Logements.class);

        String xmlString;

        try {
            xmlString = fileToString("docs/logements.xml");
            xstream.alias("Appartement", Appartement.class);
            xstream.alias("Maison", Maison.class);
            xstream.alias("hote", Hote.class);
            Logements logements = (Logements) xstream.fromXML(xmlString);

            hoteList = logements.getHoteList();

        } catch (IOException e){
            Utile.alert("Impossible de lire le fichier "+e.getMessage());
        } catch (Exception e) {
            Utile.alert("Erreur de parsing "+e.getMessage());
            e.printStackTrace();
        }
    }

    private static String fileToString(String filePath) throws IOException {
        String content = "";
        content = new String ( Files.readAllBytes( Paths.get(filePath) ) );

        return content;
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
}
