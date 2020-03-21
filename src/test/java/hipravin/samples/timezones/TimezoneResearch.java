package hipravin.samples.timezones;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;

public class TimezoneResearch {

    @Test
    void testGroupOffsets() {
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();

        Instant now = Instant.ofEpochMilli(0);

        Map<OffsetDateTime, List<String>> zonesGrouped = new TreeMap<>();
        zoneIds.forEach(z-> {
            OffsetDateTime odt = OffsetDateTime.ofInstant(now, ZoneId.of(z));

            zonesGrouped.putIfAbsent(odt, new ArrayList<>());
            zonesGrouped.get(odt).add(z);
        });

        zonesGrouped.forEach((odt, l) -> {

//            System.out.println(odt.getOffset().toString() + "\t" + l.toString());
            System.out.println("<tr><td>" + odt.getOffset().toString() + "</td><td>" + String.join(", ", l) + "</td></tr>");
        });


    }
}
