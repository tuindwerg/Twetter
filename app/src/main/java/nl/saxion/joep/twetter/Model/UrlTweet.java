package nl.saxion.joep.twetter.Model;

/**
 * Created by Dilan on 13-5-2016.
 */
public class UrlTweet {

    private String url; // dit is de expended url
    private String displayUrl;

    private int startIndex;
    private int eindIndex;


    public UrlTweet(String url,String displayUrl, int startIndex, int eindIndex) {
        this.url = url;
        this.displayUrl = displayUrl;
        this.startIndex = startIndex;
        this.eindIndex = eindIndex;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public String getUrl() {
        return url;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEindIndex() {
        return eindIndex;
    }
}
