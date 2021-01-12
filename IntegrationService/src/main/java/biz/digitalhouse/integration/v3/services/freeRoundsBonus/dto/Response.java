package biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto;

import java.io.Serializable;

/**
 * Created by vitaliy.babenko
 * on 13.01.2016
 */
public class Response<T> extends BaseResponse implements Serializable {

    private T value;

    public T getValue(T obj) {
        return value != null ? value : obj;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Response{" +
                "value=" + value +
                "} " + super.toString();
    }
}
