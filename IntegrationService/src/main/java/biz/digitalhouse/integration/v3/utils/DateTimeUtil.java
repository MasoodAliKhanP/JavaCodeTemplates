package biz.digitalhouse.integration.v3.utils;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Vitalii Babenko
 *         on 29.02.2016.
 */
public class DateTimeUtil {

    public static XMLGregorianCalendar convertDateToXMLCalendar(Date date) {
        if(date != null) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            return new XMLGregorianCalendarImpl(calendar);
        }
        return null;
    }

    public static XMLGregorianCalendar convertCalendarToXMLCalendar(Calendar calendar) {
        if(calendar != null) {
            return new XMLGregorianCalendarImpl((GregorianCalendar) calendar);
        }
        return null;
    }

    public static Calendar convertXMLCalendarToCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        if(xmlGregorianCalendar != null) {
            return xmlGregorianCalendar.toGregorianCalendar();
        }
        return null;
    }

    public static Date convertXMLCalendarToDate(XMLGregorianCalendar xmlGregorianCalendar) {
        if(xmlGregorianCalendar != null) {
            return xmlGregorianCalendar.toGregorianCalendar().getTime();
        }
        return null;
    }

    public static Calendar convertDateToCalendar(Date date) {
        if(date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }
        return null;
    }
}
