package legeay.airbnb.utilisateurs;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.Utile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hote extends Personne {
    private static int index = 0;

    @XStreamAlias("delaiReponse")
    private int delaiDeReponse;
    private List<Logement> logementList = new ArrayList<>();

    public Hote(String prenom, String nom, int age, int delaiDeReponse) {
        super(prenom, nom, age);
        this.delaiDeReponse = delaiDeReponse;
        id = ++index;
    }

    public Hote(Hote hote) {
        this(hote.getPrenom(), hote.getNom(), hote.getAge(), hote.delaiDeReponse);
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

    public int getDelaiDeReponse() {
        return delaiDeReponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        if (!super.equals(o)) return false;

        Hote hote = (Hote) o;

        return delaiDeReponse == hote.delaiDeReponse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), delaiDeReponse);
    }
}
