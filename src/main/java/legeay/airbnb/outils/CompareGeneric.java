package legeay.airbnb.outils;

import legeay.airbnb.Comparaison;
import legeay.airbnb.utilisateurs.Hote;

public class CompareGeneric <T extends Comparaison> {

    private T obj1;
    private T obj2;

    public CompareGeneric(T obj1, T obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public T getHigher() {
        if((obj1.getComparable() - obj2.getComparable()) > 0) return obj1;
        else return obj2;
    }

    public T getLower() {
        if((obj1.getComparable() - obj2.getComparable()) > 0) return obj2;
        else return obj1;
    }

}
