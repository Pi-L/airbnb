package legeay.airbnb.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Subclass of Date</p>
 * <p>Make it easier to use dates</p>
 */
public class MaDate extends Date {
    /**
     * <p>European Date pattern - Ex: 31/12/00</p>
     */
    private static final String DATE_PATTERN = "dd/MM/yy";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
    /**
     * Constant storing the number of milliseconds in a day
     */
    private static final long NB_MS_IN_DAY = 86400000L;

    /**
     * Construct a MaDate Object with current timestamp
     */
    public MaDate() {
        super();
    }

    /**
     * Construct a MaDate Object from a timestamp in millisecond
     * @param dateInMs timestamp
     */
    public MaDate(long dateInMs) {
        super(dateInMs);
    }

    /**
     * Construct a MaDate Object from a string
     * @param date as to be formatted with numerics values of <strong>days/month/year</strong>
     * @throws ParseException
     */
    public MaDate(String date) throws ParseException {
        super(DATE_FORMAT.parse(date).getTime());
    }

    /**
     *Construct a MaDate Object from 3 integers
     * @param jour
     * @param mois
     * @param annee
     * @throws ParseException
     */
    public MaDate(int jour, int mois, int annee) throws ParseException {
        super(DATE_FORMAT.parse(""+jour+"/"+mois+"/"+annee).getTime());
    }

    /**
     * Construct a new MaDate Object with added days from an existing MaDate
     *
     * @param date
     * @param nbDays
     * @throws ParseException
     */
    public MaDate(MaDate date, int nbDays) throws ParseException {
        super(date.getTime() + nbDays * NB_MS_IN_DAY);
    }

    /**
     *
     * @return a DATE_PATTERN formatted String of this MaDate value
     */
    @Override
    public String toString() {
        return DATE_FORMAT.format(this);
    }

}
