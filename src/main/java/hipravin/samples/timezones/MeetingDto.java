package hipravin.samples.timezones;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.Date;

public class MeetingDto {
    private Long id;
    private String description;

    //two below fields are equal and illustrate old and java8 approaches
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date meetingTimeDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime meetingTimeOffsetDateTime;

    public MeetingDto() {
    }

    public MeetingDto(Long id, String description, Date meetingTimeDate,
                      OffsetDateTime meetingTimeJavaTime) {
        this.id = id;
        this.description = description;
        this.meetingTimeDate = meetingTimeDate;
        this.meetingTimeOffsetDateTime = meetingTimeJavaTime;
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

    public OffsetDateTime getMeetingTimeOffsetDateTime() {
        return meetingTimeOffsetDateTime;
    }

    public void setMeetingTimeOffsetDateTime(OffsetDateTime meetingTimeOffsetDateTime) {
        this.meetingTimeOffsetDateTime = meetingTimeOffsetDateTime;
    }

    public Date getMeetingTimeDate() {
        return meetingTimeDate;
    }

    public void setMeetingTimeDate(Date meetingTimeDate) {
        this.meetingTimeDate = meetingTimeDate;
    }

    @Override
    public String toString() {
        return "MeetingDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", meetingTimeDate=" + meetingTimeDate +
                ", meetingTimeOffsetDateTime=" + meetingTimeOffsetDateTime +
                '}';
    }
}
