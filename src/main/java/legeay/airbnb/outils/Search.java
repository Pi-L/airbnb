package legeay.airbnb.outils;

import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;

import javax.management.ConstructorParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Search {

    AirBnBData airBnBData = AirBnBData.getInstance();

    // obligatoire
    private final Predicate<Logement> nbVoyageursPredicate;
    // optionnels
    private final Predicate<Logement> tarifMinParNuitPredicate;
    private final Predicate<Logement> tarifMaxParNuitPredicate;
    private final Predicate<Logement> possedePiscinePredicate;
    private final Predicate<Logement> possedeJardinPredicate;
    private final Predicate<Logement> possedeBalconPredicate;


    private Search(SearchBuilder searchBuilder) {
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
                .filter(nbVoyageursPredicate)
                .filter(tarifMinParNuitPredicate)
                .filter(tarifMaxParNuitPredicate)
                .filter(possedePiscinePredicate)
                .filter(possedeJardinPredicate)
                .filter(possedeBalconPredicate)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static class SearchBuilder {

        // Attribut obligatoire
        private final Integer nbVoyageurs;
        // Attributs optionnels
        private Integer tarifMinParNuit;
        private Integer tarifMaxParNuit;
        private Boolean possedePiscine;
        private Boolean possedeJardin;
        private Boolean possedeBalcon;

        public SearchBuilder(int nbVoyageurs) {
            this.nbVoyageurs = nbVoyageurs;
        }

        public SearchBuilder tarifMinParNuit(int tarifMinParNuit) {
            this.tarifMinParNuit = tarifMinParNuit;
            return this;
        }

        public SearchBuilder tarifMaxParNuit(int tarifMaxParNuit) {
            this.tarifMaxParNuit = tarifMaxParNuit;
            return this;
        }

        public SearchBuilder possedePiscine(Boolean possedePiscine) {
            this.possedePiscine = possedePiscine;
            return this;
        }

        public SearchBuilder possedeJardin(Boolean possedeJardin) {
            this.possedeJardin = possedeJardin;
            return this;
        }

        public SearchBuilder possedeBalcon(Boolean possedeBalcon) {
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
