package bamv.training.microposts.service.impl;

import bamv.training.microposts.dao.TFollowDao;
import bamv.training.microposts.service.FollowService;
import bamv.training.microposts.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private TFollowDao tFollowDao;

    @Autowired
    private SequenceService sequenceService;

    @Override
    public int findFollowNumber(String userId) {
        return tFollowDao.countFollowingNumber(userId);
    }

    @Override
    public int findFollowerNumber(String userId) {
        return tFollowDao.countFollowerNumber(userId);
    }

    @Override
    public boolean isFollowing(String followingUserId, String followedUserId) {
        return tFollowDao.countFollow(followingUserId, followedUserId) > 0;
    }

    @Override
    public int follow(String followingUserId, String followedUserId) {

        String followId = sequenceService.issueSequence("follow_id");
        return tFollowDao.insertFollow(followId, followingUserId, followedUserId);
    }

    @Override
    public int unfollow(String followingUserId, String followedUserId) {
        return tFollowDao.deleteFollow(followingUserId, followedUserId);
    }
}