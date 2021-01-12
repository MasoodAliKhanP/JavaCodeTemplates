package biz.digitalhouse.integration.v3;

/**
 * @author Vitalii Babenko
 *         on 18.04.2016.
 */
public interface InternalServiceResponseStatus {

    int OK = 0;
    int ERROR = 1;
    int INSUFFICIENT_FUNDS_ERROR = 2;
    int GAME_DISABLED_FOR_BONUS = 4;
//    public static final int NO_FUNDS = 5;
}
