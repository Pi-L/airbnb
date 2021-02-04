package legeay.airbnb.outils;

import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;

import java.util.List;
import java.util.stream.Collectors;

public class Search {

    AirBnBData airBnBData = AirBnBData.getInstance();

    // Attribut obligatoire
    private final int nbVoyageurs;
    // Attributs optionnels
    private final int tarifMinParNuit;
    private final int tarifMaxParNuit;
    private final Boolean possedePiscine;
    private final Boolean possedeJardin;
    private final Boolean possedeBalcon;

    public int getNbVoyageurs() {
        return nbVoyageurs;
    }

    public int getTarifMinParNuit() {
        return tarifMinParNuit;
    }

    public int getTarifMaxParNuit() {
        return tarifMaxParNuit;
    }

    public Boolean getPossedePiscine() {
        return possedePiscine;
    }

    public Boolean getPossedeJardin() {
        return possedeJardin;
    }

    public Boolean getPossedeBalcon() {
        return possedeBalcon;
    }

    public List<Logement> result() {
        return airBnBData.getLogements().stream().filter(logement -> {

            boolean condition = true;

            if(nbVoyageurs > 0) condition = condition && nbVoyageurs < logement.getNbVoyageursMax();
            if(tarifMinParNuit > 0) condition = condition && tarifMinParNuit <= logement.getTarifJournalier();
            if(tarifMaxParNuit > 0) condition = condition && tarifMaxParNuit >= logement.getTarifJournalier();
            if(possedePiscine != null) condition = condition
                    && (logement instanceof Appartement
                        || (logement instanceof Maison
                            && possedePiscine == ((Maison) logement).isPossedePiscine())
                        );
            if(possedeJardin != null) condition = condition
                    && (logement instanceof Appartement
                        || (logement instanceof Maison
                            && possedeJardin == (((Maison) logement).getSuperficieJardin() > 0))
                        );
            if(possedeBalcon != null) condition = condition
                    && ( logement instanceof Maison
                        || (logement instanceof Appartement
                            && possedeBalcon == (((Appartement) logement).getSuperficieBalcon() > 0))
                        );

            return condition;

        }).collect(Collectors.toList());
    }

    private Search(SearchBuilder searchBuilder) {
        this.nbVoyageurs = searchBuilder.nbVoyageurs;
        this.tarifMinParNuit = searchBuilder.tarifMinParNuit;
        this.tarifMaxParNuit = searchBuilder.tarifMaxParNuit;
        this.possedePiscine = searchBuilder.possedePiscine;
        this.possedeJardin = searchBuilder.possedeJardin;
        this.possedeBalcon = searchBuilder.possedeBalcon;
    }

    public static class SearchBuilder {

        // Attribut obligatoire
        private final int nbVoyageurs;
        // Attributs optionnels
        private int tarifMinParNuit;
        private int tarifMaxParNuit;
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
