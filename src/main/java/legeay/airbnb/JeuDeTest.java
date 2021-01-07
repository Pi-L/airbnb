package legeay.airbnb;


import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;
import legeay.airbnb.outils.MaDate;
import legeay.airbnb.reservations.Reservation;
import legeay.airbnb.reservations.Sejour;
import legeay.airbnb.reservations.SejourCourt;
import legeay.airbnb.reservations.SejourLong;
import legeay.airbnb.utilisateurs.Hote;
import legeay.airbnb.utilisateurs.Voyageur;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>copied without remorse :] from Audrey Dubois</p>
 * <p>https://github.com/aydubois/project_java/blob/master/src/dubois/airbnb/JeuDeTest.java</p>
 */
public class JeuDeTest {
    private static final String[] listePrenom = {"Amélie","Angélique","Corinne","Juliette","Stéphanie","Marjorie","Adam","Alex","Alexandre","Alexis","Anthony","Antoine","Benjamin","Cédric","Charles","Christopher","David","Dylan","Édouard","Elliot","Émile","Étienne","Félix","Gabriel","Guillaume","Hugo","Benoîst","Jacob","Jérémy","Jonathan"};
    private static final String[] listeNom = {"Dupond","Durand","Dubois","Desbois","Martin","Moreau","Richard","Devanneaux","Lawniczak","Dumas","Lacroix","Perrot","Marchal","Leclerc","Laffont","Vidal","Corona","Fontaine","Marty","Poiret","Brun","Laporte","Adam","Martinez"};
    private static final String[] listeAdresse = {"31 allée de la fontaine, 37000 Tours","2 avenue Margot, 38000 Grenoble","10 rue du sac, 37000 Tours","453 champ des meilleurs vignerons, 47000 Bordeaux","21 rue de la porte, 37100 Tours Nord"};
    private static final String[] listeDateDeReservation = {"abcdefg", "12/04/2021","28/01/2020", "15/01/2020", "21/01/2020", "15/06/2021","01/01/2041","31/12/2021","28/07/2025","01/11/2022","31/04/2021"};

    private Reservation reservation;

    /**
     * Mise en place aléatoire d'un jeu de test : hote/voyageur/logement/sejour/reservation
     */
    public JeuDeTest(int nbSejours) throws ParseException {

        Hote hote = new Hote(getListePrenom(),getListeNom(), randomInt(2,100), randomInt(1,60));
        Voyageur voyageur = new Voyageur(getListePrenom(),getListeNom(), randomInt(2,100));

        List<Sejour> sejourList = new ArrayList<>();

        for (int i = 0; i < nbSejours; i++) {

            Logement logement;
            Date dateArrivee = new MaDate();

            if(randomInt(0,1) == 0) {
                logement = new Maison(hote, randomInt(50,1500), getListeAdresse(), randomInt(20,5000), randomInt(1,15), randomInt(0,500), randomInt(0,1) == 0);
            } else {
                logement = new Appartement(hote, randomInt(50,1500), getListeAdresse(), randomInt(20,500), randomInt(1,15), randomInt(1,12),randomInt(0,20));
            }

            String stringDate = getListeDateDeReservation();

            try {
                dateArrivee = new MaDate(stringDate);
            }
            catch (ParseException e) {
                System.out.println("La Date << "+ stringDate +" >> n'est pas au bon format. L'application ne peut continuer");
                System.out.println(e.getMessage());
                System.exit(0);
            }

            int nbNuits = randomInt(1,35);
            int nbVoyageur = randomInt(1,15);
            Sejour sejour;

            if(nbNuits < 6) sejour = new SejourCourt(dateArrivee, nbNuits, logement, nbVoyageur);
            else sejour = new SejourLong(dateArrivee, nbNuits, logement, nbVoyageur);

            sejourList.add(sejour);
        }

        reservation = new Reservation(sejourList, voyageur);
    }

//        /**
//         * Mise en place aléatoire d'un jeu de test : hote/voyageur/logement/sejour/reservation
//         * @param pTypeLogement => "maison" OU "appartement". Si une autre valeur -> automatiquement "appartement"
//         * @param pTypeDuree => "court" OU "long". Si une autre valeur -> automatiquement "long"
//         */
//        public JeuDeTest(String pTypeLogement, String pTypeDuree) throws ParseException {
//            hote = new Hote(getListePrenom(),getListeNom(), randomInt(2,100), randomInt(1,60));
//            voyageur = new Voyageur(getListePrenom(),getListeNom(), randomInt(2,100));
//            maison =  new Maison(hote, randomInt(50,1500), getListeAdresse(), randomInt(20,5000), randomInt(1,15), getPiscine(),randomInt(50,8000));
//            appartement = new Appartement(hote, randomInt(50,1500), getListeAdresse(), randomInt(20,500), randomInt(1,15), randomInt(1,50),randomInt(0,20));
//
//            Date dateN = new MaDate("dd/MM/yyyy",getListeDateDeReservation());
//            sejour = createSejour(dateN, pTypeLogement, pTypeDuree);
//            dateN = new MaDate("dd/MM/yyyy",getListeDateDeReservation());
//
//            reservation = new Reservation(sejour, voyageur);
//
//        }

    private String getListePrenom(){
        return listePrenom[new Random().nextInt(listePrenom.length)];
    }
    private String getListeNom(){
        return listeNom[new Random().nextInt(listeNom.length)];
    }
    private String getListeAdresse(){
        return listeAdresse[new Random().nextInt(listeAdresse.length)];
    }
    private String getListeDateDeReservation(){
        return listeDateDeReservation[new Random().nextInt(listeDateDeReservation.length)];
    }

    private int randomInt(int min,int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public Reservation getReservation() {
        return reservation;
    }
}