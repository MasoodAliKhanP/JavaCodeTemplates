package biz.digitalhouse.integration.v3.services.casinoGames.dto;

/**
 * @author Vitalii Babenko
 *         on 29.02.2016.
 */
public class CasinoGameInfoDTO {

    private String gameName;
    private String gameSymbol;
    private String gameTechnology;

    public CasinoGameInfoDTO(String gameName, String gameSymbol, String gameTechnology) {
        this.gameName = gameName;
        this.gameSymbol = gameSymbol;
        this.gameTechnology = gameTechnology;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameSymbol() {
        return gameSymbol;
    }

    public String getGameTechnology() {
        return gameTechnology;
    }

    @Override
    public String toString() {
        return "CasinoGameInfoDTO{" +
                "gameName='" + gameName + '\'' +
                ", gameSymbol='" + gameSymbol + '\'' +
                ", gameTechnology='" + gameTechnology + '\'' +
                '}';
    }
}
