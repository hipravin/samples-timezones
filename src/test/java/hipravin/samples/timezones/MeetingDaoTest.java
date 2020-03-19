package hipravin.samples.timezones;

import org.h2.util.DateTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles(profiles = {"test"})
class MeetingDaoTest {
    @Autowired
    MeetingDaoJdbcTemplateDaoImpl meetingDaoJdbcTemplateDao;
    @Autowired
    MeetingDaoSpringDataJpa meetingDaoSpringDataJpa;

    @BeforeAll
    static void setUp() {
        //imitating DB in different timezone
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        DateTimeUtils.resetCalendar();
    }

    @AfterEach
    void tearDown() {
        //imitating DB in different timezone
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        DateTimeUtils.resetCalendar();
    }

    @Test
    void testAddFindJpa() {
        MeetingDto now = new MeetingDto(null, "jpa now", new Date(), OffsetDateTime.now());
        meetingDaoSpringDataJpa.add(now);

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        DateTimeUtils.resetCalendar();
        //should return 2: one from schema.sql and one here
        List<MeetingDto> meetings = meetingDaoSpringDataJpa.findAllActual();

        assertEquals(2, meetings.size());
        assertMillisDiffWithNowisSmall(meetings.get(0).getMeetingTimeDate().getTime());
        assertMillisDiffWithNowisSmall(meetings.get(1).getMeetingTimeOffsetDateTime().toInstant().toEpochMilli());
    }

    @Test
    void testAddFindJdbc() {
        MeetingDto now = new MeetingDto(null, "jdbc now", new Date(), OffsetDateTime.now());
        meetingDaoJdbcTemplateDao.add(now);

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        DateTimeUtils.resetCalendar();
        //should return 2: one from schema.sql and one from here
        List<MeetingDto> meetings = meetingDaoJdbcTemplateDao.findAllActual();

        assertEquals(2, meetings.size());
        assertMillisDiffWithNowisSmall(meetings.get(0).getMeetingTimeDate().getTime());
        assertMillisDiffWithNowisSmall(meetings.get(1).getMeetingTimeOffsetDateTime().toInstant().toEpochMilli());
    }

    @Test
    void testDeleteJpa() {
        List<MeetingDto> meetings = meetingDaoSpringDataJpa.findAllActual();

        assertEquals(1, meetings.size());
        meetings.forEach(m -> meetingDaoSpringDataJpa.delete(m.getId()));
        assertEquals(0, meetingDaoSpringDataJpa.findAllActual().size());
    }

    void assertMillisDiffWithNowisSmall(long epochMillis) {
        assertTrue(Math.abs(System.currentTimeMillis() - epochMillis) < 5 * 60 * 1000);
    }
}