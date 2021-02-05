package legeay.airbnb.outils;


import java.util.logging.Logger;

public final class Utile {

    public static final Logger logger = Logger.getLogger("logger");

    private Utile() {}

    public static void alert (String message) {
        Utile.logger.info(ConsoleColors.RED_BACKGROUND+ConsoleColors.WHITE_BOLD_BRIGHT+message+ConsoleColors.RESET);
    }

    public static void info (String message) {
        Utile.logger.info(ConsoleColors.GREEN_BOLD+message+ConsoleColors.RESET);
    }

    public static void warn (String message) {
        Utile.logger.info(ConsoleColors.YELLOW_BOLD+message+ConsoleColors.RESET);
    }

    public static void fonkie (String message) {
        Utile.logger.info("\033[106m"+message+ConsoleColors.RESET);
    }

}
