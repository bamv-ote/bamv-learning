package bamv.training.microposts.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface TFollowDao {
    int countFollowingNumber(String userId);

    int countFollowerNumber(String userId);

    int addFollow(String followingUserId, String followedUserId);

    int delFollow(String followingUserId, String followedUserId);

    boolean isFollow(String followingUserId, String followedUserId);
}
