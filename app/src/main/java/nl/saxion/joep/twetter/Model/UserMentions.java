package nl.saxion.joep.twetter.Model;

/**
 * Created by Dilan on 13-5-2016.
 */
public class UserMentions {

    private String screemName;
    private String name;

    private int id;
    private int startIndex;
    private int endIndex;


    public UserMentions(String screemName, String name, int id, int startIndex, int endIndex) {
        this.screemName = screemName;
        this.name = name;
        this.id = id;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getScreemName() {
        return screemName;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}
