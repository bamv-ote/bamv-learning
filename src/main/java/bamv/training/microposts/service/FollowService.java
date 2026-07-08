package bamv.training.microposts.service;

public interface FollowService {
    int findFollowNumber(String userId);

    int findFollowerNumber(String userId);

    int addFollow(String followingUserId, String followedUserId);

    int delFollow(String followingUserId, String followedUserId);

    boolean isFollow(String followingUserId, String followedUserId);
}
