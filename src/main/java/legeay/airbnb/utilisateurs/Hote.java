package legeay.airbnb.utilisateurs;

public class Hote extends Personne{

    private int delaiDeReponse;

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
