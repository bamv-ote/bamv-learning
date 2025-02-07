package bamv.training.microposts.dao.impl;

import bamv.training.microposts.dao.TFollowDao;
import bamv.training.microposts.entity.TFollow;
import bamv.training.microposts.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TFollowDaoImpl implements TFollowDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SequenceService sequenceService;
    @Override
    public int countFollowingNumber(String userId) {
        String query = "SELECT COUNT(*) as count FROM t_follow WHERE following_user_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, userId);
    }

    @Override
    public int countFollowerNumber(String userId) {
        String query = "SELECT COUNT(*) as count FROM t_follow WHERE followed_user_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, userId);
    }

    public void insertFollowTable(String followingId, String followedId){
        String query = "INSERT INTO t_follow VALUES(?, ?, ?)";
        String followId = sequenceService.issueSequence("follow_id");

        jdbcTemplate.update(query,
                followId,
                followingId,
                followedId);
    }
    public void deleteFollowing(String followingId,String followedId){
        String query = "DELETE FROM t_follow WHERE following_user_id = ? AND followed_user_id = ?";

        jdbcTemplate.update(query, followingId, followedId);
    }

    public List<TFollow> findFollowing(String userId){
        RowMapper<TFollow> rowMapper = new BeanPropertyRowMapper<>(TFollow.class);
        String query = "SELECT * FROM t_follow WHERE following_user_id = ?";
        return jdbcTemplate.query(query, rowMapper, userId);
    }
}
