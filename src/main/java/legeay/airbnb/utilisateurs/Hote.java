package legeay.airbnb.utilisateurs;

import legeay.airbnb.logements.Logement;

import java.util.ArrayList;
import java.util.List;

public class Hote extends Personne {
    private static int index = 0;

    private int delaiDeReponse;
    private List<Logement> logementList;

    public Hote(String prenom, String nom, int age, int delaiDeReponse) {
        super(prenom, nom, age);
        logementList = new ArrayList<>();
        this.delaiDeReponse = delaiDeReponse;
        id = ++index;
    }

    @Override
    public void afficher() {
        String messageStart = " qui s'engage à répondre dans ";
        String messageEnd = delaiDeReponse > 1 ? "les "+delaiDeReponse+" heures" : "l'heure";
        super.afficher();
        System.out.println(messageStart+messageEnd);

    }

    public List<Logement> getLogementList() {
        return logementList;
    }
}
