package legeay.airbnb.reservations;

import java.text.ParseException;

public interface SejourInterface {

    public boolean verficationDateArrivee();
    public boolean verificationNombreDeNuits();
    public boolean verificationNombreDeVoyageurs();
    public void afficher() throws ParseException;

}
