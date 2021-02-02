package legeay.airbnb;

import java.util.*;

public class CompareGenericList <T extends AffichableInterface & Comparable> extends ArrayList<T> {

        public CompareGenericList(List<T> list) {
            super(list);
        }


        public Optional<T> getHigher() {
            Optional<T> max = Optional.empty();
            for (T currentEl: this) {
                if(max.isEmpty() || currentEl.compareTo(max.get()) > 0) max = Optional.of(currentEl);
            }
            return max;
        }

        public Optional<T> getLower() {
            Optional<T> min = Optional.empty();
            for (T currentEl: this) {
                if(min.isEmpty() || currentEl.compareTo(min.get()) < 0) min = Optional.of(currentEl);
            }
            return min;
        }

        public void sortAsc() {
            Comparator<T> comparator = (T obj1, T obj2) -> obj1.compareTo(obj2);
            Collections.sort(this);
        }

    public void sortDesc() {
        Comparator<T> comparator = (T obj1, T obj2) -> obj1.compareTo(obj2);
        Collections.sort(this, Collections.reverseOrder());
    }

        public void afficher() {
            for (T obj: this) {
                obj.afficher();
            }
        }


    }
