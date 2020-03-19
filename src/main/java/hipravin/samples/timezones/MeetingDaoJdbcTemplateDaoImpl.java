package hipravin.samples.timezones;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

@Repository
@Transactional
public class MeetingDaoJdbcTemplateDaoImpl implements MeetingDao {

    //if you remove any usage of CALENDAR - unit test will fail because of false time shifts
    public static final Calendar CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String MTG_ID_NEXTVAL_QUERY =
            "SELECT MTG_ID_SEQ.NEXTVAL FROM DUAL";

    public static final String INSERT_MTG_QUERY =
            "INSERT INTO MEETINGS (ID, DESCRIPTION, DATE_DATE, DATE_OFFSETDT) " +
                    "VALUES (?, ?, ?, ?);";

    public static final String FIND_ACTUAL_QUERY =
            "SELECT * FROM MEETINGS WHERE DATE_OFFSETDT > ?";

    public static final String DELETE_BY_ID_QUERY =
            "DELETE FROM MEETINGS WHERE ID = :id";

    public MeetingDaoJdbcTemplateDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void add(MeetingDto meetingDto) {
        Long nextId = namedParameterJdbcTemplate.queryForObject(MTG_ID_NEXTVAL_QUERY, Collections.emptyMap(), Long.class);

        if(nextId == null) {
            throw new DataRetrievalFailureException("Can't fetch next id, got null: " + MTG_ID_NEXTVAL_QUERY);
        }

        meetingDto.setId(nextId);

        namedParameterJdbcTemplate.getJdbcTemplate().update(INSERT_MTG_QUERY, ps -> {
            ps.setLong(1, meetingDto.getId());
            ps.setString(2, meetingDto.getDescription());
            ps.setTimestamp(3, new Timestamp(meetingDto.getMeetingTimeDate().getTime()), CALENDAR);
            ps.setTimestamp(4, Timestamp.from(meetingDto.getMeetingTimeOffsetDateTime().toInstant()), CALENDAR);
        });
    }

    @Override
    public List<MeetingDto> findAllActual() {

        return namedParameterJdbcTemplate.getJdbcTemplate().query(FIND_ACTUAL_QUERY,
                ps -> ps.setTimestamp(1, Timestamp.from(Instant.now().minusSeconds(60 * 60)), CALENDAR),
                (rs, i) -> new MeetingDto(
                        rs.getLong("ID"),
                        rs.getString("DESCRIPTION"),
                        rs.getTimestamp("DATE_DATE", CALENDAR),
                        //here zone id can't lead to timezone issues, because instant is same
                        OffsetDateTime.ofInstant(rs.getTimestamp("DATE_OFFSETDT", CALENDAR).toInstant(), ZoneId.systemDefault())));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        int deleted = namedParameterJdbcTemplate.update(DELETE_BY_ID_QUERY, Collections.singletonMap("id", id));
        if(deleted == 0) {
            throw new NotFoundException("Meeting with id " + id + " not found.");
        }
    }
}
