package hipravin.samples.timezones;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateCodeSamples {
    @Test
    void dateSamples() throws ParseException {
        Date epoch = new Date(0);
        Date y1999 = new Date(1000L * 60 * 60 * 24 * 365 * 29);
        System.out.println(epoch.getTime() + ", " + epoch);//0, Thu Jan 01 03:00:00 MSK 1970

        //1999 не получился, потеряли високосные года
        System.out.println(y1999.getTime() + ", " + y1999);//914544000000, Fri Dec 25 03:00:00 MSK 1998

        Date now = new Date();//equivalent to new Date(System.currentTimeMillis())
        System.out.println(now.getTime() + ", " + now);//1584695414171, Fri Mar 20 12:10:14 MSK 2020

        System.out.println(now.after(epoch)); //true

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        System.out.println(sdf.format(epoch));//1970-01-01T00:00:00 +0000
        System.out.println(sdf.format(now));  //2020-03-20T09:01:21 +0000

        Date parsed = sdf.parse("2022-03-20T09:01:21 +0000");
        System.out.println(parsed.getTime() + ", " + parsed);//1647766881000, Sun Mar 20 12:01:21 MSK 2022
    }


    @Test
    void name() {
        //2147483647
        System.out.println(Integer.MAX_VALUE - 2147483647);
    }
}
