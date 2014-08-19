package kke.travelplan.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormats {
    public static final DateFormat date = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseDate(String s) {
        try {
            return date.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("날짜 형식이 안맞는 듯", e);
        }
    }
}
