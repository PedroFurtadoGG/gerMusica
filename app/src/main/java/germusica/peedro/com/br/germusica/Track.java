package germusica.peedro.com.br.germusica;


public class Track {

    private String trackID;
    private String trackName;
    private int trackRating;

    public Track(){}

    public  Track(String trackID, String trackName, int trackRating) {
        this.trackID = trackID;
        this.trackName = trackName;
        this.trackRating = trackRating;
    }

    public String getTrackID() {
        return trackID;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getTrackRating() {
        return trackRating;
    }
}
