package legeay.airbnb.outils;

import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>Builder pattern</p>
 * <p>On evite de devoir creer de nouveaux constructeurs à chaque fois qu'on veut rajouter une propriété</p>
 * <p>Permet de faire une recherche de logements</p>
 *
 */
public class Search {

//    public enum SearchChoice {
//        YES(0, "Yes"),
//        NO(1, "No"),
//        NEITHER(3, "Neither");
//
//        private int value;
//        private String name;
//
//        private SearchChoice(int value, String name) {
//            this.value = value;
//            this.name = name;
//        }
//
//        public int getValue() {
//            return value;
//        }
//    }

    AirBnBData airBnBData = AirBnBData.getInstance();

    /**
     * Predicate == un fonction qui retourne un booleen .. en gros
     */
    // obligatoire
    private final Predicate<Logement> nbVoyageursPredicate;
    // optionnels
    private final Predicate<Logement> tarifMinParNuitPredicate;
    private final Predicate<Logement> tarifMaxParNuitPredicate;
    private final Predicate<Logement> possedePiscinePredicate;
    private final Predicate<Logement> possedeJardinPredicate;
    private final Predicate<Logement> possedeBalconPredicate;

    private SearchBuilder searchBuilder;


    private Search(SearchBuilder searchBuilder) {
        this.searchBuilder = searchBuilder;

        this.nbVoyageursPredicate = lgt -> searchBuilder.nbVoyageurs <= lgt.getNbVoyageursMax();

        this.tarifMinParNuitPredicate = lgt -> searchBuilder.tarifMinParNuit == null
                                                || searchBuilder.tarifMinParNuit <= lgt.getTarifJournalier();

        this.tarifMaxParNuitPredicate = lgt ->  searchBuilder.tarifMaxParNuit == null
                                                || searchBuilder.tarifMaxParNuit >= lgt.getTarifJournalier();

        this.possedePiscinePredicate = lgt ->  searchBuilder.possedePiscine == null
                                                || lgt instanceof Appartement
                                                || (lgt instanceof Maison
                                                    && searchBuilder.possedePiscine == ((Maison) lgt).isPossedePiscine());

        this.possedeJardinPredicate = lgt ->  searchBuilder.possedeJardin == null
                                                || lgt instanceof Appartement
                                                || (lgt instanceof Maison
                                                    && searchBuilder.possedeJardin == (((Maison) lgt).getSuperficieJardin() > 0));

        this.possedeBalconPredicate = lgt ->  searchBuilder.possedeBalcon == null
                                                || lgt instanceof Maison
                                                || (lgt instanceof Appartement
                                                    && searchBuilder.possedeBalcon == (((Appartement) lgt).getSuperficieBalcon() > 0));
    }

    public ArrayList<Logement> result() {

        return airBnBData.getLogements().stream()
                .parallel()
                .filter(new Predicate<Logement>() {
                            @Override
                            public boolean test(Logement logement) {
                                return searchBuilder.nbVoyageurs <= logement.getNbVoyageursMax();
                            }
                        }

                )
                .filter(tarifMinParNuitPredicate)
                .filter(tarifMaxParNuitPredicate)
                .filter(possedePiscinePredicate)
                .filter(possedeJardinPredicate)
                .filter(possedeBalconPredicate)
                .sorted(/*Comparator.reverseOrder()*/)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static class SearchBuilder {

        // Attribut obligatoire
        private final Integer nbVoyageurs;
        // Attributs optionnels
        private Integer tarifMinParNuit;
        private Integer tarifMaxParNuit; // = Integer.MAX_VALUE;
        private Boolean possedePiscine;
        private Boolean possedeJardin;
        private Boolean possedeBalcon;

        public SearchBuilder(int nbVoyageurs) {
            if (nbVoyageurs == 0) throw new IllegalArgumentException();

            this.nbVoyageurs = nbVoyageurs;
        }

        public SearchBuilder tarifMinParNuit(int tarifMinParNuit) {
            if (tarifMinParNuit < 1) throw new IllegalArgumentException();

            this.tarifMinParNuit = tarifMinParNuit;
            return this;
        }

        public SearchBuilder tarifMaxParNuit(int tarifMaxParNuit) {
            if (tarifMaxParNuit < 1) throw new IllegalArgumentException();

            this.tarifMaxParNuit = tarifMaxParNuit;
            return this;
        }

        public SearchBuilder possedePiscine(boolean possedePiscine) {
            this.possedePiscine = possedePiscine;
            return this;
        }

        public SearchBuilder possedeJardin(boolean possedeJardin) {
            this.possedeJardin = possedeJardin;
            return this;
        }

        public SearchBuilder possedeBalcon(boolean possedeBalcon) {
            this.possedeBalcon = possedeBalcon;
            return this;
        }

        public Search build() {
            Search search =  new Search(this);
            // validateSearchObject(search);
            return search;
        }
    }

}
