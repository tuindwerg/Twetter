package nl.saxion.joep.twetter.Model;

/**
 * Created by joepv on 24.jun.2016.
 */

public class DirectMessage {
    private String createdAt, sender, text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
