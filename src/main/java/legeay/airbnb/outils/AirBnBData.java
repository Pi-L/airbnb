package legeay.airbnb.outils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.security.NoTypePermission;
import legeay.airbnb.logements.Appartement;
import legeay.airbnb.logements.Logement;
import legeay.airbnb.logements.Maison;
import legeay.airbnb.menu.GestionLogements;
import legeay.airbnb.menu.Menu;
import legeay.airbnb.utilisateurs.Hote;
import legeay.airbnb.utilisateurs.Voyageur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AirBnBData {
    // ici permet d'instancier avant même d'avoir appeler getInstance() ... quoi que
    private static volatile AirBnBData instance = new AirBnBData();

    private List<Hote> hotes = new ArrayList<>();
    private List<Voyageur> voyageurs = new ArrayList<>();
    private List<Logement> logements = new ArrayList<>();

    private AirBnBData() {
        super();

        setHotes();
        setVoyageurs();
        setLogements();
    }

    public static AirBnBData getInstance() {
        return instance;
    }

    private void setHotes() {
        XStream xstream = new XStream();
        //Clear out all permissions
        xstream.addPermission(NoTypePermission.NONE);
        //Add permission to deserialize the Logements class
        xstream.allowTypes(new Class[]{Appartement.class, Logements.class, Maison.class, Hote.class});
        xstream.processAnnotations(Logements.class);

        String xmlString;

        try {
            xmlString = fileToString("docs/logements.xml");
            xstream.alias("Appartement", Appartement.class);
            xstream.alias("Maison", Maison.class);
            xstream.alias("hote", Hote.class);
            Logements logements = (Logements) xstream.fromXML(xmlString);

            hotes = logements.getHoteList();

        } catch (IOException e){
            Utile.alert("Impossible de lire le fichier "+e.getMessage());
        } catch (Exception e) {
            Utile.alert("Erreur de parsing "+e.getMessage());
            e.printStackTrace();
        }
    }

    private void setVoyageurs() {
        voyageurs.add(new Voyageur("Helmut", "Shmit", 33));
        voyageurs.add(new Voyageur("Ken", "Hokuto No", 54));
    }

    private void setLogements() {
        logements = hotes.stream()
                .flatMap(hote -> hote.getLogementList().stream())
                .collect(Collectors.toList());
    }

    // les 3 entités hote, voyageur, logement sont immuables
    // et on doit pouvoir ajouter / supprimer de ces lists
    public List<Hote> getHotes() {
        return hotes;
    }

    public List<Voyageur> getVoyageurs() {
        return voyageurs;
    }

    public List<Logement> getLogements() {
        return logements;
    }

    @XStreamAlias("Logements")
    private class Logements {

        @XStreamImplicit
        private List<Appartement> appartementList = new ArrayList<>();

        @XStreamImplicit
        private List<Maison> maisonList = new ArrayList<>();

        public List<Hote> getHoteList() {
            List<Logement> logementList = new ArrayList<>();
            List<Hote> hoteList;

            logementList.addAll(appartementList);
            logementList.addAll(maisonList);

            // enleve les logements en double - utilise la methode equals
            Set<Logement> logementSet = new HashSet<>(logementList);

            hoteList = logementSet.stream()
                    .map(Logement::getHote).distinct() // distinct() utilise la methode equals de Hote
                    .map(hote -> new Hote(hote))
                    .map(hote -> {
                        logementSet.forEach(logement -> {
                            if (hote.equals(logement.getHote())) {
                                if (logement instanceof Maison) (new Maison(hote, (Maison) logement)).setName(hote.getNom()+logement.getId());
                                else if (logement instanceof Appartement) (new Appartement(hote, (Appartement) logement)).setName(hote.getNom()+logement.getId());
                            }
                        });

                        return hote;
                    })
                    .collect(Collectors.toList());

            return hoteList;
        }
    }

    private static String fileToString(String filePath) throws IOException {
        String content = "";
        content = new String ( Files.readAllBytes( Paths.get(filePath) ) );

        return content;
    }


}
