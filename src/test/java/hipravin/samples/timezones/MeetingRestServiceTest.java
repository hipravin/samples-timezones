package hipravin.samples.timezones;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MeetingRestServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetActual() throws Exception {

        ResponseEntity<MeetingDto[]> actual = restTemplate.getForEntity("http://localhost:" + port + "/api/meeting/actual", MeetingDto[].class);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void testPut() throws Exception {

        MeetingDto newMtg = new MeetingDto(null, "mtg test", new Date(), OffsetDateTime.now());

        ResponseEntity<MeetingDto> added = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/meeting/add", newMtg, MeetingDto.class);

        assertEquals(HttpStatus.OK, added.getStatusCode());
        assertNotNull(added);
        assertNotNull(added.getBody());
        assertNotNull(added.getBody().getId());
    }
}