package legeay.airbnb.utilisateurs;

import legeay.airbnb.AffichableInterface;
import legeay.airbnb.outils.ConsoleColors;

public abstract class Personne implements AffichableInterface {

    private String prenom;
    private String nom;
    private int age;

    @Override
    public String toString() {
        return getClass().getSimpleName()+" -> {\n"+
                "\tprenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", age=" + age +
                '}';
    }

    public Personne(String prenom, String nom, int age) {
        super();

        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
    }

    public void afficher() {
        System.out.print(ConsoleColors.RED+prenom+" "+nom+ConsoleColors.RESET+" ("+age+" ans)");
    }
}
