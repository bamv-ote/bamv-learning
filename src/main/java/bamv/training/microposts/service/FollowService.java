package bamv.training.microposts.service;

import bamv.training.microposts.entity.TFollow;

import java.util.List;

public interface FollowService {
    int findFollowNumber(String userId);

    int findFollowerNumber(String userId);

    void followUser(String followingId, String followedId);
    //自ユーザがフォローしているリストを返す
    List<TFollow> getFollowList(String userId);
    //フォロー解除
    void unfollow(String followingId, String followedId);
}
