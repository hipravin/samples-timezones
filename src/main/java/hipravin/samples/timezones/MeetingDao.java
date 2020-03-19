package hipravin.samples.timezones;

import java.util.List;

public interface MeetingDao {
    void add(MeetingDto meetingDto);

    /**
     * @return all meetings not earlier than now-1hour
     */
    List<MeetingDto> findAllActual();
    void delete(Long id) throws NotFoundException;
}
