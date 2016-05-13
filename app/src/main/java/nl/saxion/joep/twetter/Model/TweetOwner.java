package nl.saxion.joep.twetter.Model;

/**
 * Created by Dilan on 13-5-2016.
 */
public class TweetOwner {

    private String profileImage;
    private String name;
    private String screen_name;

    public TweetOwner(String profileImage, String name, String screen_name) {
        this.profileImage = profileImage;
        this.name = name;
        this.screen_name = screen_name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getName() {
        return name;
    }

    public String getScreen_name() {
        return screen_name;
    }
}
