package legeay.airbnb;

import java.text.ParseException;


public class Main {
    public static void main(String[] args) throws ParseException {

//        Hote toto = new Hote("Goku", "San", 12, 10);
//        Voyageur ken = new Voyageur("Ken", "Du Haut Couteau", 12);
//
//        Maison maison1 = new Maison(toto, 100, "12 rue des douzaine, 37000 TOURS", 212, 12, 5, false);
//        Appartement appartement1 = new Appartement(toto, 10, "13 rue de la douzaine, 37000 TOURS", 12, 3, 0, 2);
//
//        Sejour sejour1 = new SejourCourt(new MaDate("11/01/2021"), 5, maison1, 12);
//        Sejour sejour2 = new SejourLong(new MaDate(20,01,2021), 30, appartement1, 2);
//
//        List<Sejour> sejourList = new ArrayList<>();
//        sejourList.add(sejour1);
//        sejourList.add(sejour2);

        JeuDeTest test1 = new JeuDeTest(1);
        test1.getReservation().afficher();
        // Reservation resa1 = new Reservation(sejourList, ken);
        // resa1.afficher();

    }
}
