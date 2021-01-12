/**
 * 
 */
package biz.digitalhouse.integration.v3.model;

import java.util.ArrayList;

import biz.digitalhouse.integration.v3.model.JackpotTier;

/**
 * @author Masood Ali Khan
 * 11-Jul-2018
 * 5:08:42 PM
 */
public class CasinoJackpot {
	private long casinoID;
    private String gameSymbol;
    private long jackpotID;
    private String jackpotName;
    private double jackpotRealAmount;
    private int tier;
    
    public long getCasinoID() {
        return casinoID;
    }

    public void setCasinoID(long casinoID) {
        this.casinoID = casinoID;
    }

    public String getGameSymbol() {
        return gameSymbol;
    }

    public void setGameSymbol(String gameSymbol) {
        this.gameSymbol = gameSymbol;
    }

    public long getJackpotID() {
        return jackpotID;
    }

    public void setJackpotID(long jackpotID) {
        this.jackpotID = jackpotID;
    }

    public String getJackpotName() {
        return jackpotName;
    }

    public void setJackpotName(String jackpotName) {
        this.jackpotName = jackpotName;
    }

    public double getJackpotRealAmount() {
        return jackpotRealAmount;
    }

    public void setJackpotRealAmount(double jackpotRealAmount) {
        this.jackpotRealAmount = jackpotRealAmount;
    }

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}
	
	@Override
    public String toString() {
        return "jackpots{" +
                "jackpotID=" + jackpotID + '\'' +
                ", name='" + jackpotName + '\'' +
                ", level='" + tier + '\'' +
                ", amount='" + jackpotRealAmount + '\'' +
                ", games=" + gameSymbol + '\'' +
                ", winAmount=" + jackpotRealAmount +
                '}';
    }
}
