package nl.saxion.joep.twetter.Model;

/**
 * Created by joepv on 22.jun.2016.
 */

public class ActiveUser {
    private long id;
    private String id_str;

    private String name, screenName, location, description;
    private int followersCount, friendsCount;
    private String createdAt;

    private String timeZone;

    public ActiveUser(long id, String id_str, String name, String screenName, String location, String description, int followersCount, int friendsCount, String createdAt, String timeZone) {
        this.id = id;
        this.id_str = id_str;
        this.name = name;
        this.screenName = screenName;
        this.location = location;
        this.description = description;
        this.followersCount = followersCount;
        this.friendsCount = friendsCount;
        this.createdAt = createdAt;
        this.timeZone = timeZone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
