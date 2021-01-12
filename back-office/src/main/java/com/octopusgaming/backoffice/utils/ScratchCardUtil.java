package com.octopusgaming.backoffice.utils;

public class ScratchCardUtil {
	private static final String BETTER_LUCK_PRIZEID = "1";
	private static final String FREE_SPIN_PRIZEID = "2";
	private static final String BONUS_PRIZEID = "3";
	private static final String CASHBACK_PRIZEID = "4";
	
	public static String getStringAmount(int amount, String prizeTypeId) {
		String amountS = Integer.toString(amount);
		if(prizeTypeId.equals(BONUS_PRIZEID) || prizeTypeId.equals(CASHBACK_PRIZEID)) {
			amountS = amountS + "%";
		}
		return amountS;
	}
	
	public static String getStringPercentage(float percentage) {
		return Float.toString(percentage) + "%";
	}
}
