package biz.digitalhouse.integration.v3.model;

import biz.digitalhouse.integration.v3.InternalServiceResponseStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vitalii Babenko
 *         on 10.04.2016.
 */
@Document(collection = "transactions")
@CompoundIndex(name = "transaction_TypeStatus", def = "{'transactionStatus': 1, 'transactionType': 1}")
public class Transaction implements Serializable {

    public enum TransactionType {
        BEFORE_BET, BET, WIN, REFUND, JACKPOT_WIN
    }

    public enum TransactionStatus {
        INTERNAL_ERROR, PROCESSING, COMPLETED, ERROR
    }

    private static final long serialVersionUID = 1L;

    @Id
    private String transactionID;
    @Indexed(expireAfterSeconds=7776000)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createDate = new Date();
    @Indexed
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date lastProcessingDate;


    private TransactionType transactionType;
    private TransactionStatus transactionStatus;

    private String vendorID;
    @Indexed
    private String incomingTransactionID;
    @Indexed
    private String incomingRoundID;
    private String incomingOriginalRoundID;
    private Boolean gambling;
    private long gameID;
    private String gameSymbol;
    private double amount;

    private long brandID;
    private long roundID;
    private Long originalRoundID;
    // Only win(gablingEnd)
    private Long internalTransactionID;

    private String externalTransactionID;
    private double cash;
    private double bonus;
    //	TODO - change the remark below
    // Only bet(gamblingStart)
    private Double usedBonus;
    private String bonusCode;
    private String betResultStatusDescription;
    long playerID;
    String externalPlayerID;
    private Boolean endRound;

    private Long jackpotID;
    private String tierTypeID;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastProcessingDate() {
        return lastProcessingDate;
    }

    public void setLastProcessingDate(Date lastProcessingDate) {
        this.lastProcessingDate = lastProcessingDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getIncomingTransactionID() {
        return incomingTransactionID;
    }

    public void setIncomingTransactionID(String incomingTransactionID) {
        this.incomingTransactionID = incomingTransactionID;
    }

    public String getIncomingRoundID() {
        return incomingRoundID;
    }

    public void setIncomingRoundID(String incomingRoundID) {
        this.incomingRoundID = incomingRoundID;
    }

    public String getIncomingOriginalRoundID() {
        return incomingOriginalRoundID;
    }

    public void setIncomingOriginalRoundID(String incomingOriginalRoundID) {
        this.incomingOriginalRoundID = incomingOriginalRoundID;
    }

    public Boolean getGambling() {
        return gambling;
    }

    public void setGambling(Boolean gambling) {
        this.gambling = gambling;
    }

    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }

    public String getGameSymbol() {
        return gameSymbol;
    }

    public void setGameSymbol(String gameSymbol) {
        this.gameSymbol = gameSymbol;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getBrandID() {
        return brandID;
    }

    public void setBrandID(long brandID) {
        this.brandID = brandID;
    }

    public long getRoundID() {
        return roundID;
    }

    public void setRoundID(long roundID) {
        this.roundID = roundID;
    }

    public Long getOriginalRoundID() {
        return originalRoundID;
    }

    public void setOriginalRoundID(Long originalRoundID) {
        this.originalRoundID = originalRoundID;
    }

    public Long getInternalTransactionID() {
        return internalTransactionID;
    }

    public void setInternalTransactionID(Long internalTransactionID) {
        this.internalTransactionID = internalTransactionID;
    }

    public String getExternalTransactionID() {
        return externalTransactionID;
    }

    public void setExternalTransactionID(String externalTransactionID) {
        this.externalTransactionID = externalTransactionID;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public Double getUsedBonus() {
        return usedBonus;
    }

    public void setUsedBonus(Double usedBonus) {
        this.usedBonus = usedBonus;
    }

    public String getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(String bonusCode) {
        this.bonusCode = bonusCode;
    }

    public String getBetResultStatusDescription() {
        return betResultStatusDescription;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public String getExternalPlayerID() {
        return externalPlayerID;
    }

    public void setExternalPlayerID(String externalPlayerID) {
        this.externalPlayerID = externalPlayerID;
    }

    public Boolean getEndRound() {
        return endRound;
    }

    public void setEndRound(Boolean endRound) {
        this.endRound = endRound;
    }

    public Long getJackpotID() {
        return jackpotID;
    }

    public void setJackpotID(Long jackpotID) {
        this.jackpotID = jackpotID;
    }

    public void setBetResultStatusDescription(int status) {

        switch (status) {
            case InternalServiceResponseStatus.OK:
                this.betResultStatusDescription = status +": OK";
                break;
            case InternalServiceResponseStatus.INSUFFICIENT_FUNDS_ERROR:
                this.betResultStatusDescription = status +": INSUFFICIENT_FUNDS_ERROR";
                break;
            case InternalServiceResponseStatus.GAME_DISABLED_FOR_BONUS:
                this.betResultStatusDescription = status +": GAME_DISABLED_FOR_BONUS";
                break;
            default:
                this.betResultStatusDescription = status +": OTHER (UNKNOWN)";
                break;
        }
    }

    public String getTierTypeID() {
		return tierTypeID;
	}

	public void setTierTypeID(String tierTypeID) {
		this.tierTypeID = tierTypeID;
	}

	@Override
    public String toString() {
        return "Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", createDate=" + createDate +
                ", lastProcessingDate=" + lastProcessingDate +
                ", transactionType=" + transactionType +
                ", transactionStatus=" + transactionStatus +
                ", vendorID='" + vendorID + '\'' +
                ", incomingTransactionID='" + incomingTransactionID + '\'' +
                ", incomingRoundID='" + incomingRoundID + '\'' +
                ", incomingOriginalRoundID='" + incomingOriginalRoundID + '\'' +
                ", gambling=" + gambling +
                ", gameID=" + gameID +
                ", gameSymbol='" + gameSymbol + '\'' +
                ", amount=" + amount +
                ", brandID=" + brandID +
                ", roundID=" + roundID +
                ", originalRoundID=" + originalRoundID +
                ", internalTransactionID=" + internalTransactionID +
                ", externalTransactionID='" + externalTransactionID + '\'' +
                ", cash=" + cash +
                ", bonus=" + bonus +
                ", usedBonus=" + usedBonus +
                ", bonusCode='" + bonusCode + '\'' +
                ", betResultStatusDescription='" + betResultStatusDescription + '\'' +
                ", playerID=" + playerID +
                ", externalPlayerID='" + externalPlayerID + '\'' +
                ", endRound=" + endRound +
                ", jackpotID=" + jackpotID +
                ", tierTypeID=" + tierTypeID +
                '}';
    }
}
