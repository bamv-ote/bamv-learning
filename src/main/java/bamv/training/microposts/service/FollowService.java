package bamv.training.microposts.service;

public interface FollowService {
    int findFollowNumber(String userId);

    int findFollowerNumber(String userId);

    boolean isFollowing(String followingUserId, String followedUserId);

    int follow(String followingUserId, String followedUserId);

    int unfollow(String followingUserId, String followedUserId);
}
