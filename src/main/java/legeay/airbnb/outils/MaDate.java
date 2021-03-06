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
     * @throws ParseException if DATE_FORMAT.parse() can't recognize the pattern
     */
    public MaDate(String date) throws ParseException {
        super(DATE_FORMAT.parse(date).getTime());
    }

    /**
     *Construct a MaDate Object from 3 integers
     * @param jour between 1-31
     * @param mois between 1-12
     * @param annee last 2 digits
     * @throws ParseException if DATE_FORMAT.parse() can't recognize the pattern
     */
    public MaDate(int jour, int mois, int annee) throws ParseException {
        // perilous
        // super(annee - 1900, mois - 1, jour)
        super(DATE_FORMAT.parse(""+jour+"/"+mois+"/"+annee).getTime());
    }

    /**
     * Construct a new MaDate Objectfrom an existing Date
     *
     * @param date
     */
    public MaDate(Date date) {
        super(date.getTime());
    }

    /**
     * Construct a new MaDate Objectfrom an existing Date
     *
     * @param maDate
     */
    public MaDate(MaDate maDate) {
        super(maDate.getTime());
    }

    /**
     * Construct a new MaDate Object with added days from an existing Date
     *
     * @param date
     * @param nbDays
     */
    public MaDate(Date date, int nbDays) {
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
