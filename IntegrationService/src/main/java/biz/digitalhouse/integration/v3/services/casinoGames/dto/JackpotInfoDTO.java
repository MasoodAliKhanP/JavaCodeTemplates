/**
 * 
 */
package biz.digitalhouse.integration.v3.services.casinoGames.dto;

import java.util.ArrayList;

import biz.digitalhouse.integration.v3.model.CasinoJackpot;

/**
 * @author Masood Ali Khan
 * 12-Jul-2018
 * 12:44:30 PM
 */
public class JackpotInfoDTO {
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

	/**
	 * @return the tier
	 */
	public int getTier() {
		return tier;
	}

	/**
	 * @param tier the tier to set
	 */
	public void setTier(int tier) {
		this.tier = tier;
	}
}
