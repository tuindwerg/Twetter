package nl.saxion.joep.twetter.Model;

/**
 * Created by joepv on 30.apr.2016.
 */
public class TwetterModel {
    private static TwetterModel ourInstance = new TwetterModel();

    public static TwetterModel getInstance() {
        return ourInstance;
    }

    private TwetterModel() {
    }
}
