package legeay.airbnb.outils;


public final class Utile {

    private Utile() {}

    public static void alert (String message) {
        System.out.println(ConsoleColors.RED_BACKGROUND+ConsoleColors.WHITE_BOLD_BRIGHT+message+ConsoleColors.RESET);
    }

    public static void info (String message) {
        System.out.println(ConsoleColors.GREEN_BOLD+message+ConsoleColors.RESET);
    }

    public static void warn (String message) {
        System.out.println(ConsoleColors.YELLOW_BOLD+message+ConsoleColors.RESET);
    }


}
