package hipravin.samples.timezones;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaTimeIntroducktion {

    @Test
    void testLocalDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2022, 1, 2, 12, 59, 0);
        System.out.println(ldt);//2022-01-02T12:59
        System.out.println(ldt.plusMonths(6));//2022-07-02T12:59
        System.out.println(ldt.toInstant(ZoneOffset.UTC));//2022-01-02T12:59:00Z
        //туда и обратно. начинается небольшая путаница, почему в одну сторону ZoneOffset, а обратно ZoneId
        assertEquals(ldt, LocalDateTime.ofInstant(
                ldt.toInstant(ZoneOffset.UTC), ZoneId.of("UTC")));//test passed
    }

    @Test
    void testInstant() {
        Instant instant = Instant.now();
        //toString у Instant правильнее, чем у Date - использует UTC, а не местное время
        System.out.println(instant);//2020-03-22T14:00:54.071078700Z
        ZonedDateTime zdt = instant.atZone(ZoneId.of("Europe/Moscow"));
        System.out.println(zdt);//2020-03-22T17:00:54.071078700+03:00[Europe/Moscow]
    }

    @Test
    void testZonedDateTime() {
        ZonedDateTime zdtLondon = ZonedDateTime.of(
                LocalDateTime.of(2020, 10, 24, 10, 0), ZoneId.of("Europe/London"));

        OffsetDateTime odtLondon = zdtLondon.toOffsetDateTime();

        System.out.println(zdtLondon.plusHours(24).getOffset());//Z
        System.out.println(odtLondon.plusHours(24).getOffset());//+01:00
    }

    @Test
    void testZdtOdtComparison() {
        Instant now = Instant.now();
        ZonedDateTime zdtMoscow = now.atZone(ZoneId.of("Europe/Moscow"));
        ZonedDateTime zdtMinsk = now.atZone(ZoneId.of("Europe/Minsk"));

        System.out.println(
                zdtMoscow.equals(zdtMinsk));//false
        System.out.println(
                zdtMoscow.toOffsetDateTime().equals(zdtMinsk.toOffsetDateTime()));//true
        System.out.println(
                //false, потеряли строковое представление. Было "Europe/Moscow", стало "+03:00"
                zdtMoscow.equals(zdtMoscow.toOffsetDateTime().toZonedDateTime()));
        System.out.println(
                zdtMinsk.equals(zdtMinsk.toOffsetDateTime().atZoneSameInstant(ZoneId.of("Europe/Minsk"))));//true

    }
}
