package model;

public class Track {

    int trackId;
    String name;
    String albumName;
    String genreName;

    public Track() {
    }

    public Track(String name) {
        this.name = name;
    }

    public Track(String name, String albumName) {
        this.name = name;
        this.albumName = albumName;
    }

    public Track(String name, String albumName, String genreName) {
        this.name = name;
        this.albumName = albumName;
        this.genreName = genreName;
    }

    public Track(int trackId, String name, String albumName, String genreName) {
        this.trackId = trackId;
        this.name = name;
        this.albumName = albumName;
        this.genreName = genreName;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "Track{" + "trackId=" + trackId + ", name=" + name + ", albumName=" + albumName + ", genreName=" + genreName + '}';
    }

}
