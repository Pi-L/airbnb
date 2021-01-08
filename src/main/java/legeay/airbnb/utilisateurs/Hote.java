package legeay.airbnb.utilisateurs;

import legeay.airbnb.logements.Logement;
import java.util.List;

public class Hote extends Personne {

    private int delaiDeReponse;
    private List<Logement> logementList;

    public Hote(String prenom, String nom, int age, int delaiDeReponse) {
        super(prenom, nom, age);

        this.delaiDeReponse = delaiDeReponse;
    }

    @Override
    public void afficher() {
        String messageStart = " qui s'engage à répondre dans ";
        String messageEnd = delaiDeReponse > 1 ? "les "+delaiDeReponse+" heures" : "l'heure";
        super.afficher();
        System.out.println(messageStart+messageEnd);

    }
}
