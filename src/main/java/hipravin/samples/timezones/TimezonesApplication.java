package hipravin.samples.timezones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class TimezonesApplication {

    public static void main(String[] args) {
        //To load H2 driver with different time offset
        //imitating timezone mess issues which proper dao implementation have to overcome
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        SpringApplication.run(TimezonesApplication.class, args);
    }
}
