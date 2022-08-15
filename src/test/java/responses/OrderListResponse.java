package responses;

public class OrderListResponse {
    private int track;
    private String id;

    public String getId() {
        return id;
    }

    public String getTrack() {
        return track + ":" + id;
    }
}