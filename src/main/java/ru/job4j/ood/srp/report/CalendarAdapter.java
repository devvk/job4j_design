package ru.job4j.ood.srp.report;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarAdapter extends XmlAdapter<String, Calendar> {

    private static final SimpleDateFormat FORMAT =
            new SimpleDateFormat("dd:MM:yyyy HH:mm");

    @Override
    public Calendar unmarshal(String v) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(FORMAT.parse(v));
        return cal;
    }

    @Override
    public String marshal(Calendar v) {
        return v == null ? null : FORMAT.format(v.getTime());
    }
}
