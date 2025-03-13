package bamv.training.microposts.dao.impl;

import bamv.training.microposts.dao.TFollowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TFollowDaoImpl implements TFollowDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    @Override
    public int countFollow(String followingUserId, String followedUserId) {
        String query = "SELECT COUNT(*) FROM t_follow WHERE following_user_id = ? AND followed_user_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, followingUserId, followedUserId);
    }

    @Override
    public int insertFollow(String followId, String followingUserId, String followedUserId) {
        String query = "INSERT INTO t_follow (follow_id, following_user_id, followed_user_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(query, followId, followingUserId, followedUserId);
    }

    @Override
    public int deleteFollow(String followingUserId, String followedUserId) {
        String query = "DELETE FROM t_follow WHERE following_user_id = ? AND followed_user_id = ?";
        return jdbcTemplate.update(query, followingUserId, followedUserId);
    }
}
