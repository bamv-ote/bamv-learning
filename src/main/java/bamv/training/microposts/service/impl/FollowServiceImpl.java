package bamv.training.microposts.service.impl;

import bamv.training.microposts.dao.TFollowDao;
import bamv.training.microposts.entity.TFollow;
import bamv.training.microposts.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private TFollowDao tFollowDao;

    @Override
    public int findFollowNumber(String userId) {
        return tFollowDao.countFollowingNumber(userId);
    }

    @Override
    public int findFollowerNumber(String userId) {
        return tFollowDao.countFollowerNumber(userId);
    }


    public void followUser(String followingId, String followedId){
        if(followingId.equals(followedId)){return;}
         tFollowDao.insertFollowTable(followingId,followedId);
    }

    public List<TFollow>getFollowList(String userId){
        List<TFollow>list = tFollowDao.findFollowing(userId);
        return list;
    }

    public void unfollow(String followingId, String followedId){
        tFollowDao.deleteFollowing(followingId, followedId);
    }
}