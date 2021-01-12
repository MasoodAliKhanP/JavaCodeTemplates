package biz.digitalhouse.integration.v3.utils;

/**
 * @author Vitalii Babenko
 *         on 10.04.2016.
 */
public class CommonUtils {

    public static String getRoundDetailsString(boolean isGamble, boolean isBonus) {
        return (isGamble ? "gamble" : "spin") + (isBonus ? ",bonus" : "");
    }
}
