package biz.digitalhouse.integration.v3.services.userservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * @author vitalii.babenko
 * created: 11.04.2018 10:53
 */
@Component
public class UserService {

    public enum CODE {
        OK("OK", 0),
        EmptyMandatoryField("EmptyMandatoryField", 1),
        TypeMismatchForValue("TypeMismatchForValue", 2),
        MemberIsNotFound("MemberIsNotFound.", 4),
        RequestInProcessingNow("RequestInProcessing now.", 6),
        TokenNotFound("Token not found", 7),
        ActiveOTPNotFound("Active OTP not found", 8),
        OTPExpired("OTP expired", 9),
        UserWebSessionIDNotFound("User webSessionID not found", 24),
        CurrencyCodeIsIncorrectOrUnsupported("Currency code is incorrect or unsupported.", 25),
        GameWebSessionIDNotFound("Game webSessionID not found", 26),
        DuplicateAccount("Duplicate account", 33),
        FrozenPlayerAccount("Frozen player account", 34),
        InvalidAccount("Invalid account", 37),
        CouldNotCreateToken("Could not create token", 38),
        DenyInternalMemberLogin("Deny internal member login.", 39),
        LanguageCodeIsIncorrectOrUnsupported("Language code is incorrect or unsupported.", 40),
        CasinoNotFound("Casino not found", 41),
        NicknameDuplicated("Nickname duplicated", 42),
        EmailDuplicated("Email duplicated", 43),
        UnsupportedJurisdiction("Unsupported jurisdiction", 44),
        InternalServiceError("Internal Service Error", 100);

        private int code;
        private String description;

        CODE(String description, int code) {
            this.code = code;
            this.description = description;
        }

        public boolean equalCode(BaseResponse response) {
            return this.code == response.getError();
        }

        public CODE getCode(BaseResponse response) {
            for(CODE code : CODE.values()) {
                if(code.code == response.getError()) {
                    return code;
                }
            }
            return null;
        }
    }

    private final Log log = LogFactory.getLog(getClass());
    private UserServiceClient client;

    @Autowired
    public UserService(UserServiceClient client) {
        this.client = client;
    }

    public GetExternalPlayerResponse getExternalPlayerByExtID(long brandID, String externalPlayerID) throws Exception {

        return isSuccessful(client.getExternalPlayerByExtID(brandID, externalPlayerID).execute());
    }

    public GetExternalPlayerResponse getExternalPlayer(long playerID) throws Exception {

        return isSuccessful(client.getExternalPlayer(playerID).execute());
    }

    public CreateExternalPlayerResponse createExternalPlayer(long brandID, String externalPlayerID, String nickname,
                                                             String email, String firstName, String lastName, String currency,
                                                             String language ) throws Exception {

        CreateExternalPlayerRequest req = new CreateExternalPlayerRequest(nickname, email, firstName, lastName, currency, language);

        return isSuccessful(client.createExternalPlayer(brandID, externalPlayerID, req).execute());
    }

    public UpdateExternalPlayerResponse updateExternalPlayer(long playerID, String nickname, String email, String firstName,
                                                             String lastName, String language ) throws Exception {

        UpdateExternalPlayerRequest req = new UpdateExternalPlayerRequest(nickname, email, firstName, lastName, language);

        return isSuccessful(client.updateExternalPlayer(playerID, req).execute());
    }

    public BanPlayerResponse banPlayerByExtID(long brandID, String externalPlayerID, Boolean ban) throws Exception {
        return isSuccessful(client.banPlayerByExtID(brandID, externalPlayerID, ban).execute());
    }

    private <T> T isSuccessful(Response<T> response) throws Exception {
        if (!response.isSuccessful()) {
            throw new Exception(Objects.requireNonNull(response.errorBody()).string());
        }
        return Objects.requireNonNull(response.body(), "User Service return null.");
    }
}
