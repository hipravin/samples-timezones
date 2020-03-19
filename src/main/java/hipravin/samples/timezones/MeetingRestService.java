package hipravin.samples.timezones;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/meeting")
public class MeetingRestService {
    private final MeetingDao meetingDao;

    public MeetingRestService(@Qualifier("meetingDaoSpringDataJpa") MeetingDao meetingDao) {
        this.meetingDao = meetingDao;
    }

    @GetMapping("/actual")
    List<MeetingDto> actual() {
        return meetingDao.findAllActual();
    }

    @PostMapping("/add")
    MeetingDto addMeeting(@RequestBody MeetingDto meeting) {
        meetingDao.add(meeting);
        return meeting;
    }

    @DeleteMapping("/delete/{id}")
    Long delete(@PathVariable("id") Long id) {
        try {
            meetingDao.delete(id);

            return id;
        } catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
