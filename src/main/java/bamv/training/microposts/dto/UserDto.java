package bamv.training.microposts.dto;

public class UserDto {
    private String userId;

    private String name;

    private int followNumber;

    private int followerNumber;

    public UserDto(String userId, String name, int followNumber, int followerNumber) {
        this.userId = userId;
        this.name = name;
        this.followNumber = followNumber;
        this.followerNumber = followerNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowNumber() {
        return followNumber;
    }

    public void setFollowNumber(int followNumber) {
        this.followNumber = followNumber;
    }

    public int getFollowerNumber() {
        return followerNumber;
    }

    public void setFollowerNumber(int followerNumber) {
        this.followerNumber = followerNumber;
    }
}