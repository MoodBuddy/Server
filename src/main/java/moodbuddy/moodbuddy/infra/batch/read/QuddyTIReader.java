package moodbuddy.moodbuddy.infra.batch.read;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.List;

@Component
@StepScope
public class QuddyTIReader implements ItemReader<Long> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Iterator<Long> userIds;

    @Override
    public Long read() {
        if (userIds == null) {
            List<Long> activeUserIds = jdbcTemplate.queryForList("SELECT user_id FROM users WHERE mood_buddy_status = 'ACTIVE' and user_role = 'USER_ROLE'", Long.class);
            userIds = activeUserIds.iterator();
        }
        return userIds.hasNext() ? userIds.next() : null;
    }
}