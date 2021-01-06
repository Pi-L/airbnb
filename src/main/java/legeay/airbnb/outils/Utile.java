package legeay.airbnb.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utile {
    private static final String DATE_PATTERN = "dd/MM/yy";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

    public static Date parseDate(String date) throws ParseException {
        return DATE_FORMAT.parse(date);
    }

    public static Date parseDate(int jour, int mois, int annee) throws ParseException {
        if(jour>0 && jour<31 && mois>0 && mois<13)
            return DATE_FORMAT.parse(""+jour+"/"+mois+"/"+annee);
        else throw new Error("parseDate -> la date n'est pas valide");
    }

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static Date dateWithaddedDays(Date date, int nbDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, nbDays);

        return calendar.getTime();
    }

}
