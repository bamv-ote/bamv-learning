package bamv.training.microposts.dao;

import bamv.training.microposts.entity.TFollow;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TFollowDao {
    int countFollowingNumber(String userId);

    int countFollowerNumber(String userId);

    void insertFollowTable(String followingID, String followedID);

    List<TFollow> findFollowing(String userId);

    public void deleteFollowing(String followingId,String followedId);
}
