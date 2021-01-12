package biz.digitalhouse.integration.v3.model;

import java.util.Calendar;

/**
 * @author Vitalii Babenko
 *         on 04.03.2016.
 */
public class BingoJackpotWinner {

    protected String extPlayerId;
    protected String nickname;
    protected double amount;
    protected Calendar dateTime;
    protected String jackpotName;
    protected long roomId;
    protected String gameName;
    protected long roundId;
    private int bingoType;
    protected String currencySymbol;


    public BingoJackpotWinner(String extPlayerId, String nickname, double amount, Calendar dateTime, String jackpotName, long roomId, String gameName, long roundId, int bingoType, String currencySymbol) {
        this.extPlayerId = extPlayerId;
        this.nickname = nickname;
        this.amount = amount;
        this.dateTime = dateTime;
        this.jackpotName = jackpotName;
        this.roomId = roomId;
        this.gameName = gameName;
        this.roundId = roundId;
        this.bingoType = bingoType;
        this.currencySymbol = currencySymbol;
    }

    public String getExtPlayerId() {
        return extPlayerId;
    }

    public String getNickname() {
        return nickname;
    }

    public double getAmount() {
        return amount;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public String getJackpotName() {
        return jackpotName;
    }

    public void setJackpotName(String jackpotName) {
        this.jackpotName = jackpotName;
    }

    public long getRoomId() {
        return roomId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public long getRoundId() {
        return roundId;
    }

    public int getBingoType() {
        return bingoType;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    @Override
    public String toString() {
        return "BingoJackpotWinner{" +
                "extPlayerId='" + extPlayerId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                ", jackpotName='" + jackpotName + '\'' +
                ", roomId=" + roomId +
                ", gameName='" + gameName + '\'' +
                ", roundId=" + roundId +
                ", bingoType=" + bingoType +
                ", currencySymbol=" + currencySymbol +
                '}';
    }
}
