package biz.digitalhouse.integration.v3.utils;

/**
 * Created by vitaliy.babenko
 * on 20.01.2017.
 */
public class TimeUtils {

    public static long requestTime() {
        return (System.currentTimeMillis()/1000) * 1000 - 300000;
    }
}
