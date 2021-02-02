package legeay.airbnb.utilisateurs;

import legeay.airbnb.AffichableInterface;
import legeay.airbnb.Comparaison;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.outils.ConsoleColors;

import java.util.Comparator;
import java.util.Objects;

public abstract class Personne implements AffichableInterface, Comparable<Personne>, Comparaison {
    protected int id;
    private String prenom;
    private String nom;
    private int age;

    public Personne(String prenom, String nom, int age) {
        super();

        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
    }

    public void afficher() {
        System.out.print(ConsoleColors.RED+prenom+" "+nom+ConsoleColors.RESET+" ("+age+" ans)");
    }

    public int getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
    }

    public int getComparable() {
        return age;
    }

    @Override
    public int compareTo(Personne personne) {
        return Integer.compare(this.getComparable(), personne.getComparable());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return age == personne.age && prenom.equals(personne.prenom) && nom.equals(personne.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prenom, nom, age);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+" -> {\n"+
                "\tprenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", age=" + age +
                '}';
    }
}
