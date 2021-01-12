package biz.digitalhouse.integration.v3;

import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
public enum ServiceResponseCode {

    OK                                                      (0, "0", "OK"),
    INTERNAL_ERROR                                          (1, "1", "Internal Service Error."),
    AUTH_ERROR                                              (2, "2", "Incorrect secureLogin or/and securePassword"),

    EMPTY_MANDATORY_FIELD                                   (3,   "3",   "Required field is empty: '{0}' is required"),
    TYPE_MISMATCH                                           (4,   "4",   "{0}"),
    INVALID_PARAMETER                                       (5,   "5",   "{0}"),
    WRONG_FIELD                                             (5,   "5",   "Field {0} is wrong."),
    NICKNAME_DUPLICATE                                      (12,  "12",  "Player with transferred nickname already exists"),
    PLAYER_NOT_FOUND                                        (6,   "6",   "Player is not found."),
    CURRENCY_NOT_ALLOWED                                    (7,   "7",   "Currency code '{0}' is incorrect or unsupported."),
    GAMES_NOT_SUPPORTED                                     (8,   "8",   "Game(s) are not supported: {0}."),
    FREE_ROUNDS_NOT_SUPPORTED                               (9,   "9",   "Game(s) do not support FR bonus: {0}."),
    BONUS_EXISTS                                            (10,  "10",  "Bonus code already exists."),
    ACTIVE_FREE_ROUNDS_NOT_FOUND                            (11,  "11",  "Could not found active free rounds."),
    FREE_ROUNDS_NOT_AVAILABLE                               (12,  "12",  "Specified free rounds is not found or not available more."),
    FREE_ROUNDS_SESSION_NOT_FOUND                           (13,  "13",  "Could not found free round session."),
    FREE_ROUNDS_ALREADY_CANCELED                            (14,  "14",  "FR bonus is canceled."),
    FREE_ROUNDS_COULD_NOT_BE_CANCELED                       (15,  "15",  "FR bonus is closed or started to play."),
    FREE_ROUNDS_DIFFERENT_VENDORS                           (16,  "16",  "Specified games must be supports by only one vendor"),
    EXTERNAL_SERVICE_ERROR                                  (17,  "17",  "External server error"),
    INVALID_ROUNDS                                          (18,  "18",  "Invalid rounds"),
    BONUS_NOT_FOUND                                         (19,  "19",  "Bonus not found"),
    INVALID_START_TIME                                      (20,  "20",  "Invalid startTime"),
    INVALID_EXPIRATION_TIME                                 (21,  "21",  "Invalid expirationTime"),
    EXPIRATION_TIME_LESS_OR_EQUALS_START_TIME               (22,  "22",  "Expiration Time less or equals Start Time"),
    GAME_FORBIDDEN                                          (23, "23", "Game is not found or is not allowed for your system"),

    // Bingo
    PREORDER_CARDS_LIMIT                                    (101, "101", "You cannot order cards more than max available number."),
    PREORDER_CARDS_IS_NOT_ALLOWED                           (102, "102", "Pre-order of cards is not allowed at this point of time for the selected game."),
    PREORDER_GAME_NOT_AVAILABLE                             (103, "103", "This game is not available anymore. Please order cards to another game"),
    THE_REQUESTED_DATA_IS_NOT_FOUND                         (105, "105", "The requested data is not found"),
    CANCEL_PREORDER_CARDS_NOT_FOUND                         (106, "106", "The cards were not found according to the criteria."),
    INCORRECT_NUMBER_OF_CARDS                               (107, "107", "incorrect number of cards"),
    INSUFFICIENT_BALANCE                                    (108, "108", "Insufficient balance"),

    UNKNOWN_ERROR(-1, "-1", "UNKNOWN ERROR.");


//
//    MEMBER_INVALID("11", "Player with specified player ID {0} not found"),
//
//    EMAIL_DUPLICATE("13", "Player with specified email already exists"),
//
//    LANGUAGE_NOT_ALLOWED("20", "Language code {0} is incorrect or unsupported");

    private String code;
    private int intCode;
    private String description;
    private String message = null;

    ServiceResponseCode(int intCode, String code, String description) {
        this.intCode = intCode;
        this.code = code;
        this.description = description;
    }

    public CommonServiceException EXCEPTION() {
        return new CommonServiceException(this);
    }

    public String getCode() {
        return code;
    }

    public int getIntCode() {
        return intCode;
    }


    public String getDescription() {
        return message != null ? description.replace("{0}", message) : description;
    }

    public ServiceResponseCode setMessage(String message) {
        this.message = message;
        return this;
    }
}
