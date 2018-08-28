package org.ants.opportunity.config;

import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Component
public class TimeConfig {

    private static final  String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss z";

    private SimpleDateFormat simpleDateFormat;
    private Timestamp timestamp;

    public TimeConfig(){
        this.simpleDateFormat = new SimpleDateFormat(TIMESTAMP_PATTERN);
    }

    public String getTimestamp(){
        timestamp = new Timestamp(System.currentTimeMillis());
        return this.simpleDateFormat.format(timestamp);
    }

    public String getTimestampWithTimezone(String timezone){
        timestamp = new Timestamp(System.currentTimeMillis());
        this.simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return this.simpleDateFormat.format(timestamp);
    }

}
