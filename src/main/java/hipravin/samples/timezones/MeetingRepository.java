package hipravin.samples.timezones;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.stream.Stream;

public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {
    Stream<MeetingEntity> findByMeetingTimeOdtAfterOrderByMeetingTimeOdt(OffsetDateTime time);
}
