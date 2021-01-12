package biz.digitalhouse.integration.v3.services.bingoGame.dto;

import java.util.Map;

/**
 * @author Vitalii Babenko
 *         on 04.03.2016.
 */
public class JackpotDTO {
    private String jackpotName;
    private int bingoType;
    private Map<String, Double> jackpotAmount;

    public JackpotDTO(String jackpotName, Map<String, Double> jackpotAmount, int bingoType) {
        this.jackpotName = jackpotName;
        this.bingoType = bingoType;
        this.jackpotAmount = jackpotAmount;
    }

    public String getJackpotName() {
        return jackpotName;
    }

    public void setJackpotName(String jackpotName) {
        this.jackpotName = jackpotName;
    }

    public int getBingoType() {
        return bingoType;
    }

    public void setBingoType(int bingoType) {
        this.bingoType = bingoType;
    }

    public Map<String, Double> getJackpotAmount() {
        return jackpotAmount;
    }

    public void setJackpotAmount(Map<String, Double> jackpotAmount) {
        this.jackpotAmount = jackpotAmount;
    }


    @Override
    public String toString() {
        return "JackpotDTO{" +
                "jackpotName='" + jackpotName + '\'' +
                ", bingoType=" + bingoType +
                ", jackpotAmount=" + jackpotAmount +
                '}';
    }
}
