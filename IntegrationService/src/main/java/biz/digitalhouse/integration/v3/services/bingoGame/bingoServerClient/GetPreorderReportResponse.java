package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class GetPreorderReportResponse implements Serializable {

    private List<Preordered> preorderedList;

    public List<Preordered> getPreorderedList() {
        return preorderedList;
    }

    public void setPreorderedList(List<Preordered> preorderedList) {
        this.preorderedList = preorderedList;
    }

    @Override
    public String toString() {
        return "GetPreorderReportResponse{" +
                "preorderedList=" + preorderedList +
                '}';
    }
}
