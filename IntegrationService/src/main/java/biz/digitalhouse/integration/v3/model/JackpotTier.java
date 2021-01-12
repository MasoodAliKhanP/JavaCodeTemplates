/**
 * 
 */
package biz.digitalhouse.integration.v3.model;

/**
 * @author Masood Ali Khan
 * 11-Jul-2018
 * 3:02:36 PM
 */
public class JackpotTier {
private double currentAmount = 0;
    
    private double wonAmount = 0;
    
    /** 1 - Plus, 2 - Extra, 3 - Super, 4 - Ultra. */
    //jackpot_tier_typeid
    private String typeid;
    
    private double playerContributionPercent = 0;

	public double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public double getWonAmount() {
		return wonAmount;
	}

	public void setWonAmount(double wonAmount) {
		this.wonAmount = wonAmount;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String string) {
		this.typeid = string;
	}

	public double getPlayerContributionPercent() {
		return playerContributionPercent;
	}

	public void setPlayerContributionPercent(double playerContributionPercent) {
		this.playerContributionPercent = playerContributionPercent;
	}
}
