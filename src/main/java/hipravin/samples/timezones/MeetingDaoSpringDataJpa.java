package hipravin.samples.timezones;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class MeetingDaoSpringDataJpa implements MeetingDao {
    private final MeetingRepository meetingRepository;

    public MeetingDaoSpringDataJpa(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public void add(MeetingDto meetingDto) {
        MeetingEntity me = MeetingEntity.fromDto(meetingDto);
        meetingRepository.save(me);
        meetingDto.setId(me.getId());
    }

    @Override
    public List<MeetingDto> findAllActual() {
        return meetingRepository.findByMeetingTimeOdtAfterOrderByMeetingTimeOdt(OffsetDateTime.now().minusHours(1))
                .map(MeetingEntity::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        try {
            meetingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Meeting with id " + id + " not found.", e);
        }
    }
}
