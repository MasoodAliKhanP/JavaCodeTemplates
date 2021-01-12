/**
 * 
 */
package biz.digitalhouse.integration.v3.web.ws.http.casinoGameAPI.dto;

import java.util.ArrayList;
import biz.digitalhouse.integration.v3.model.CasinoJackpot;

/**
 * @author Masood Ali Khan
 * 11-Jul-2018
 * 2:59:10 PM
 */
public class CasinoJackpotResponse extends BaseResponse{
	private ArrayList<CasinoJackpot> casinoJackpotList;

	public ArrayList<CasinoJackpot> getCasinoJackpotList() {
		return casinoJackpotList;
	}

	public void setCasinoJackpotList(ArrayList<CasinoJackpot> casinoJackpotList) {
		this.casinoJackpotList = casinoJackpotList;
	}
	
	@Override
    public String toString() {
        return "{" +
                "jackpots=" + casinoJackpotList +
                "} " + super.toString();
    }
}
