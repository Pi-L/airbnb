package legeay.airbnb;

import legeay.airbnb.utilisateurs.Personne;

import java.util.Comparator;

public interface Comparaison {

    // public static Comparator<? extends Comparaison> comparator = Comparator.comparing(::getComparable);

    public int getComparable();

}
