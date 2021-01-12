package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

/**
 * Created by vitaliy.babenko
 * on 29.03.2017.
 */
public class RoomListRequest extends BaseRequest {

    private String playerID;

    /**
     * Gets the value of the playerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Sets the value of the playerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlayerID(String value) {
        this.playerID = value;
    }

    @Override
    protected String getStringHashBuild() {
        return playerID;
    }
}
