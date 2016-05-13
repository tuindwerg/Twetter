package nl.saxion.joep.twetter.Model;

/**
 * Created by Dilan on 13-5-2016.
 */
public class HashTags {

    private String text;

    private int startIndex;
    private int endIndex;

    public HashTags(String text, int startIndex, int endIndex) {
        this.text = text;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getText() {
        return text;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }
}
