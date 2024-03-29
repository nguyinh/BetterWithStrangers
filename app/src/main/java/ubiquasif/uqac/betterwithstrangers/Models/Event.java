package ubiquasif.uqac.betterwithstrangers.Models;

import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.List;

public class Event {
    private String userId;
    private String name;
    private boolean isPrivate;
    private List<String> tags;
    private Date timestamp;
    private String placeName;
    private GeoPoint location;
    private int numberOfRatings;
    private double consensus;

    public Event() {}

    public Event(String userId, String name, boolean isPrivate, List<String> tags,
                 Date timestamp, String placeName, GeoPoint location) {
        this(userId, name, isPrivate, tags, timestamp, placeName, location, 0, 0);
    }

    public Event(String userId, String name, boolean isPrivate, List<String> tags, Date timestamp,
                 String placeName, GeoPoint location, int numberOfRatings, double consensus) {
        this.userId = userId;
        this.name = name;
        this.isPrivate = isPrivate;
        this.tags = tags;
        this.timestamp = timestamp;
        this.placeName = placeName;
        this.location = location;
        this.numberOfRatings = numberOfRatings;
        this.consensus = consensus;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public List<String> getTags() {
        return tags;
    }

    public Date getTimestamp() { return timestamp; }

    public String getPlaceName() {
        return placeName;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public int getNumberOfRatings() { return numberOfRatings; }

    public double getConsensus() { return consensus; }
}
