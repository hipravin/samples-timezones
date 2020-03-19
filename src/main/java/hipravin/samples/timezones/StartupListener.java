package hipravin.samples.timezones;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Date;

@Component
@Profile("!test")
public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private final MeetingDaoJdbcTemplateDaoImpl meetingDaoJdbcTemplateDao;
    private final MeetingDaoSpringDataJpa meetingDaoSpringDataJpa;

    public StartupListener(MeetingDaoJdbcTemplateDaoImpl meetingDaoJdbcTemplateDao, MeetingDaoSpringDataJpa meetingDaoSpringDataJpa) {
        this.meetingDaoJdbcTemplateDao = meetingDaoJdbcTemplateDao;
        this.meetingDaoSpringDataJpa = meetingDaoSpringDataJpa;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        MeetingDto now = new MeetingDto(null, "startup java jdbctemplate", new Date(), OffsetDateTime.now());

        meetingDaoJdbcTemplateDao.add(now);

        now.setDescription("startup java spring data jpa");
        now.setId(null);
        meetingDaoSpringDataJpa.add(now);
    }
}
