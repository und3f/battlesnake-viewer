package name.zasenko.battlesnake.utils;

public class Ansi {

    public static final String Reset = csi("0", "", 'm');
    public static String wrapWithForegroundColor(String text, int r, int g, int b) {
        return wrap(text, foregroundTrueColor(r, g, b));
    }
    public static String wrapWithBackgroundColor(String text, int r, int g, int b) {
        return wrap(text, backgroundTrueColor(r, g, b));
    }

    public static String wrap(String text, String ansiEscape) {
        StringBuilder sb = new StringBuilder();

        sb.append(ansiEscape);
        sb.append(text);
        sb.append(Reset);

        return sb.toString();
    }

    public static String foregroundTrueColor(int r, int g, int b) {
        return csi("38;2;%d;%d;%d".formatted(r, g, b), "", 'm');
    }
    public static String backgroundTrueColor(int r, int g, int b) {
        return csi("48;2;%d;%d;%d".formatted(r, g, b), "", 'm');
    }

    public static String csi(String parameterBytes, String intermediateBytes, char finalByte) {
        StringBuilder sb = new StringBuilder();
        sb.append("\033[");
        sb.append(parameterBytes);
        sb.append(intermediateBytes);
        sb.append(finalByte);
        return sb.toString();
    }

}
