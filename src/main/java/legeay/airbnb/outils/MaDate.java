package legeay.airbnb.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MaDate extends Date {
    private static final String DATE_PATTERN = "dd/MM/yy";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
    private static final long NB_MS_IN_DAY = 86400000L;

    public MaDate() {
        super();
    }

    public MaDate(long dateInMs) {
        super(dateInMs);
    }

    public MaDate(String date) throws ParseException {
        super(DATE_FORMAT.parse(date).getTime());
    }

    public MaDate(int jour, int mois, int annee) throws ParseException {
        super(DATE_FORMAT.parse(""+jour+"/"+mois+"/"+annee).getTime());
    }

    public MaDate(MaDate date, int nbDays) throws ParseException {
        super(date.getTime() + nbDays * NB_MS_IN_DAY);
    }

    @Override
    public String toString() {
        return DATE_FORMAT.format(this);
    }

}
