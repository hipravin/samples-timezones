package hipravin.samples.timezones;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Table(name = "MEETINGS")
public class MeetingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mtgSeq")
    @SequenceGenerator(sequenceName = "MTG_ID_SEQ", allocationSize = 100, name = "mtgSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date meetingTimeDate;

    @Column(name = "DATE_OFFSETDT")
    private OffsetDateTime meetingTimeOdt;

    public static MeetingEntity fromDto(MeetingDto dto) {
        MeetingEntity me = new MeetingEntity();
        me.id = dto.getId();
        me.description = dto.getDescription();
        me.meetingTimeDate = dto.getMeetingTimeDate();
        me.meetingTimeOdt = dto.getMeetingTimeOffsetDateTime();

        return me;
    }

    MeetingDto toDto() {
        return new MeetingDto(id, description, meetingTimeDate, meetingTimeOdt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getMeetingTimeDate() {
        return meetingTimeDate;
    }

    public void setMeetingTimeDate(Date meetingTimeDate) {
        this.meetingTimeDate = meetingTimeDate;
    }

    public OffsetDateTime getMeetingTimeOdt() {
        return meetingTimeOdt;
    }

    public void setMeetingTimeOdt(OffsetDateTime meetingTimeOdt) {
        this.meetingTimeOdt = meetingTimeOdt;
    }
}
