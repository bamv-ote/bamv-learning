package bamv.training.microposts.dto;

public class UserListItemDto {
    private String userId;
    private String name;
    private boolean following;

    public UserListItemDto(String userId, String name, boolean following) {
        this.userId = userId;
        this.name = name;
        this.following = following;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isFollowing() { return following; }
    public void setFollowing(boolean following) { this.following = following; }
}